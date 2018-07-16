package com.wap.auth.provider.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.sys.redis.RedisSessionService;
import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.AbstractAuthProvider;
import com.wap.util.ConstantUtils;
import com.wx.util.WeixinUtil;
/**
 * 
 * 描述:校验用户是否关注公众号
 *
 * @author 赵硕
 * @version 1.0
 * @since 2018年4月16日 上午10:02:29
 */
public class SubscribeAuthProvider extends AbstractAuthProvider {
	public static final Log logger = LogFactory.getLog(SubscribeAuthProvider.class);
	@Autowired
	private RedisSessionService sessionService;
	/**需要校验是否关注公众号的的url**/
	private List<String> value;
	
	
	@Override
	public boolean doAuthenticate(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String reqEnCoding = request.getCharacterEncoding();
		response.setCharacterEncoding(reqEnCoding);
		String sessionId = (String) request.getAttribute(ConstantUtils._ACCESS_TICKET);
		String openId = sessionService.getAtrribute(sessionId , ConstantUtils.OPEN_ID);
		boolean flag = false; 
		String errMsg = "";
		try {
			if(isExcludeMapping(request)){
				if(StringUtils.isNotBlank(openId)){
					boolean notifyPublicAccount = WeixinUtil.isNotifyPublicAccount(openId);
					if(notifyPublicAccount == false){
						String httpBasePath = (String) request.getSession().getServletContext().getAttribute("httpBasePath");
						String accessSign=(String) request.getAttribute(ConstantUtils._TICKET_SIGN);
						String url = httpBasePath +"dzsw/giftshare/subscribeMe.jsp";
						this.redirectRquest(request, response, url);
					}else{
						flag = true;
					}
				}else{
					//openId为空
					flag = false;
					errMsg = "openId参数不存在";
				}
			}else{
				//不需要校验
				flag = true;
			}
		} catch (Exception e) {
			flag = false;
			errMsg = e.getMessage();
		}finally{
			logger.info("校验用户是否关注公众号，调用时间：["+sdf.format(date)+"],返回参数：[return "+flag+"],错误信息：["+errMsg+"]");
			if(flag==false){
				throw new AuthenticationException("-01",errMsg);
			}
		}
		return flag;
	}
	/**
	 * 
			* 描述:判断是否需要校验关注
			* @param request
			* @return
			* @author 朱久满 2018年4月16日 上午9:39:05
	 */
	private boolean isExcludeMapping(HttpServletRequest request){
		boolean res=false;
		String reqUrl = request.getServletPath();
		if(this.value!=null){
			PathMatcher matcher = new AntPathMatcher();
			for (String antPath : value) {
				res=matcher.match(antPath, reqUrl);
				if(res){
					return res;
				}
			}
		}
		return res;
	}
	/**
	 * 
	* 描述:
	* 		重定向请求
	* @param req
	* @author 许宝众2017年6月19日 上午11:06:20
	 */
	private void redirectRquest(HttpServletRequest req,HttpServletResponse res,String url){
		try {
			req.setAttribute("redirectUrl", url);
			req.getRequestDispatcher("/dzsw/redirectUrl.jsp").forward(req, res);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			try {
				res.getOutputStream().close();
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
	}
//	public static void main(String[] args) {
//		PathMatcher matcher = new AntPathMatcher();
//		boolean res=matcher.match( "/*/**/myCard.jsp","/dzsw/ss/aa/myCard.jsp");
//		System.out.println(res);
//	}
	public List<String> getValue() {
		return value;
	}


	public void setValue(List<String> value) {
		this.value = value;
	}

}
