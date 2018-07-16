package com.wap.auth.filter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.AbstractAuthProvider;
/**
 * 
 * 描述:
 *	用户校验用户合法性的过滤器，类似传统web的登录过滤
 * @author 许宝众
 * @version 1.0
 * @since 2017年6月19日 下午3:43:34
 */
public class AuthenticationFilter implements Filter{
	public static final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	private List<String> exceptServletPaths;
	private List<AbstractAuthProvider> providers;
	private WebApplicationContext wac;
	
	public List<String> getExceptServletPaths() {
		return exceptServletPaths;
	}

	public void setExceptServletPaths(List<String> exceptServletPaths) {
		this.exceptServletPaths = exceptServletPaths;
	}

	public List<AbstractAuthProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<AbstractAuthProvider> providers) {
		this.providers = providers;
	}

	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}


	@Override
	public void init(FilterConfig fc) throws ServletException {
		wac = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());
		Map<String, AbstractAuthProvider> map = wac.getBeansOfType(AbstractAuthProvider.class);
		if(map!=null&&!map.isEmpty()){
			this.providers = new ArrayList();
			TreeMap<Integer,AbstractAuthProvider> treeMap = new TreeMap<Integer,AbstractAuthProvider>(new Comparator<Integer>() {
				@Override
				public int compare(Integer key1, Integer key2) {
					return key1-key2;
				}
			});
			for (Entry<String, AbstractAuthProvider> entrySet : map.entrySet()) {
				AbstractAuthProvider provider = entrySet.getValue();
				treeMap.put(provider.getOrder(), provider);
			}
			for (Entry<Integer, AbstractAuthProvider> entrySet : treeMap.entrySet()) {
				System.out.println(entrySet.getKey()+":"+entrySet.getValue().getClass().getSimpleName());
				providers.add(entrySet.getValue());
			}
		}
		
		//加载排除登录拦截的请求
		AuthencateExceptionServletPaths sp = (AuthencateExceptionServletPaths) wac.getBean("authencateExceptionServletPaths");
		if(sp!=null){
			this.setExceptServletPaths(sp.getValue());
		}
	}
	private boolean isExceptServletPath(String servletPath){
		boolean res=false;

		if(exceptServletPaths!=null){
			PathMatcher matcher = new AntPathMatcher();
			for (String antPath : exceptServletPaths) {
				res=matcher.match(antPath, servletPath);
				if(res){
					return res;
				}
			}
		}
		return res;
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String sp = req.getServletPath();
		//放行不需要进行登录限制的请求
		if(isExceptServletPath(sp)){
			chain.doFilter(req, res);
		}else{
			doAuthenticate(req, res, chain);
		}
	}
	
	private void doAuthenticate(HttpServletRequest req,HttpServletResponse res, FilterChain chain) throws IOException,ServletException {
		
		String servletPath = req.getServletPath();
		String characterEncoding = req.getCharacterEncoding();
		
		if(StringUtils.isNotBlank(characterEncoding)){
			res.setCharacterEncoding(characterEncoding);
		}
		//判断过滤/dzsw/下所有jsp,但是要放过其他资源文件
		if(servletPath.startsWith("/dzsw")&&!servletPath.endsWith(".jsp")){
			chain.doFilter(req, res);
		}else{
			boolean authenticate = true;
			try{
				if(this.providers!=null&&!this.providers.isEmpty()){
					for (AbstractAuthProvider provider : providers) {
						authenticate = provider.doAuthenticate(req, res);
						if(authenticate==false) break;
					}
				}
			}catch(AuthenticationException e){//认证异常
				String resCode = e.getResCode();
				authenticate = false;
				if(StringUtils.isNotBlank(resCode)){
					logger.info("认证失败：{}-{}",resCode,e.getErrMsg());
					//异常信息返回响应
					writeResErrorMsg(res,e);
				}
			}catch (Exception e) {
				logger.info("认证异常：",e);
				e.printStackTrace();
				throw new RuntimeException(e);
			}
			if(authenticate){
				chain.doFilter(req, res);
			}
		}
	}
	
	private void writeResErrorMsg(HttpServletResponse response,AuthenticationException ex){
		BufferedWriter bw = null;
		ServletOutputStream outputStream = null;
		try{
			outputStream = response.getOutputStream();
			String characterEncoding = response.getCharacterEncoding();
			response.setContentType("text/html; charset="+characterEncoding);
			bw=new BufferedWriter(new OutputStreamWriter(outputStream,characterEncoding));
			bw.write(ex.tojson());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try{
				bw.close();
				outputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
