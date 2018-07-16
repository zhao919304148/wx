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
import com.wap.auth.provider.QywxAbstractAuthProvider;

public class QywxAuthenticationFilter implements Filter {

	public static final Logger logger = LoggerFactory.getLogger(QywxAuthenticationFilter.class);
	private List<String> exceptServletPaths;
	private List<QywxAbstractAuthProvider> providers;
	private WebApplicationContext wac;
	
	public List<String> getExceptServletPaths() {
		return exceptServletPaths;
	}

	public void setExceptServletPaths(List<String> exceptServletPaths) {
		this.exceptServletPaths = exceptServletPaths;
	}

	public List<QywxAbstractAuthProvider> getProviders() {
		return providers;
	}

	public void setProviders(List<QywxAbstractAuthProvider> providers) {
		this.providers = providers;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
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

	@Override
	public void init(FilterConfig fc) throws ServletException {
		wac = WebApplicationContextUtils.getWebApplicationContext(fc.getServletContext());
		Map<String, QywxAbstractAuthProvider> map = wac.getBeansOfType(QywxAbstractAuthProvider.class);
		if(map!=null&&!map.isEmpty()){
			this.providers = new ArrayList();
			TreeMap<Integer,QywxAbstractAuthProvider> treeMap = new TreeMap<Integer,QywxAbstractAuthProvider>(new Comparator<Integer>() {
				@Override
				public int compare(Integer key1, Integer key2) {
					return key1-key2;
				}
			});
			for (Entry<String, QywxAbstractAuthProvider> entrySet : map.entrySet()) {
				QywxAbstractAuthProvider provider = entrySet.getValue();
				treeMap.put(provider.getOrder(), provider);
			}
			for (Entry<Integer, QywxAbstractAuthProvider> entrySet : treeMap.entrySet()) {
				System.out.println(entrySet.getKey()+":"+entrySet.getValue().getClass().getSimpleName());
				providers.add(entrySet.getValue());
			}
		}
		//初始化被排除的地址
		AuthencateExceptionQywxPaths sp = (AuthencateExceptionQywxPaths) wac.getBean("authencateExceptionQywxPaths");
		if(sp!=null){
			this.setExceptServletPaths(sp.getValue());
		}
	}
	
	private void doAuthenticate(HttpServletRequest req,HttpServletResponse res, FilterChain chain) throws IOException,ServletException {
		
		String servletPath = req.getServletPath();
		String characterEncoding = req.getCharacterEncoding();
		
		if(StringUtils.isNotBlank(characterEncoding)){
			res.setCharacterEncoding(characterEncoding);
		}
		//判断过滤/dzsw/下所有jsp,但是要放过其他资源文件
		boolean authenticate = true;
		try{
			if(this.providers!=null && !this.providers.isEmpty()){
				for (QywxAbstractAuthProvider provider : providers) {
					authenticate = provider.doAuthenticate(req, res);
					if(authenticate==false) break;
				}
			}
		}catch(AuthenticationException e){//认证异常
			String resCode = e.getResCode();
			authenticate = false;
			if(StringUtils.isNotBlank(resCode)){
				logger.info("企业微信授权认证失败：",resCode,e.getErrMsg());
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

}
