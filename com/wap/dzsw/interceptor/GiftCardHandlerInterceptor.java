package com.wap.dzsw.interceptor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.util.JSONBuilder;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.alibaba.fastjson.JSONObject;
import com.sys.redis.RedisSessionService;
import com.wap.dzsw.entity.CarDataEntity;
import com.wap.dzsw.service.GiftCardService;
//import com.wap.dzsw.entity.UserCarListInfoVo;
import com.wx.util.WeixinUtil;
/**
 * 
		
		* 描述:
		* 拦截电商福袋相关页面，登录拦截
		* @author 许宝众
		* @version 1.0
		* @since 2017年6月7日 上午9:35:15
 */
public class GiftCardHandlerInterceptor extends HandlerInterceptorAdapter{
	@Autowired
	private RedisSessionService sessionService;
	@Autowired
	private GiftCardService giftCardService;
	
	private List<String> excludeMappings;
	
	public List<String> getExcludeMappings() {
		return excludeMappings;
	}
	public void setExcludeMappings(List<String> excludeMappings) {
		this.excludeMappings = excludeMappings;
	}
	/***
	 * 1.更新session有效期 
	 * 2.判断用户是否登录：通过总公司接口得到username,调用用户车辆信息接口（1038）判断用户是否存在关联的车辆信息
	 * 3.约定：
	 * 		用户登录错误：login_error_msg-->具体的登录错误信息
	 * 		登录错误代码：login_error_code
	 * 						-01	openId参数不存在（请求未携带openId）
	 * 						-02	非注册用户
	 * 						-03	未关联投保车辆信息
	 * 						 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
		String reqEnCoding = request.getCharacterEncoding();
		response.setCharacterEncoding(reqEnCoding);
		String openId=request.getParameter("openId");
		String requestUrl=request.getRequestURL().toString();
		if(StringUtils.isNotBlank(openId)){
			//设置session有效时间
			sessionService.setSessionExpire(openId);
			//获取username
			String username = sessionService.getString(openId, "UserName");
			if(StringUtils.isBlank(username)){
				//得到总公司username,保存至session
				Map<String, String> resMap = WeixinUtil.getUserNameFormPICC(openId);
				username = resMap.get("username");
				if(StringUtils.isBlank(username)){
					//用户未注册，跳转至注册页面
					writeResErrorMsg(response,"-02","非注册用户");
					return false;
				}else{
					sessionService.setAttribute(openId, "UserName", username);
				}
			}
			if(!isExcludeMapping(request)){
				List<CarDataEntity> userCarList = (List<CarDataEntity>) sessionService.getObject(openId, "UserCarList");
				if(userCarList==null||userCarList.isEmpty()){
					//获取绑定车辆信息
					userCarList = giftCardService.loginGetCarInfo(username);
					//判断是否存在绑定车辆信息
					if (userCarList != null && !userCarList.isEmpty()) {
						//存在，则保存信息到session
						sessionService.setAttribute(openId, "UserCarList", userCarList);
						return true;
					} else {
						//不存在需要跳转至投保车辆关联页面
						writeResErrorMsg(response, "-03", "未关联投保车辆信息");
						return false;
					}
				}
			}
		}else{
			//openId为空
			writeResErrorMsg(response,"-01","openId参数不存在");
			return false;
		}
		return true;
	}
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		super.afterCompletion(request, response, handler, ex);
	}
	private void writeResErrorMsg(HttpServletResponse response,String loginErrorCode,String loginErrorMsg)throws IOException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("login_error_code",loginErrorCode);
		jsonObject.put("login_error_msg",loginErrorMsg);
		ServletOutputStream outputStream = response.getOutputStream();
		String characterEncoding = response.getCharacterEncoding();
		response.setContentType("text/html; charset="+characterEncoding);
		BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(outputStream,characterEncoding));
		bw.write(jsonObject.toJSONString());
		bw.close();
	}
	
	private boolean isExcludeMapping(HttpServletRequest request){
		boolean res=false;
		String reqUrl = request.getServletPath();
		if(this.excludeMappings!=null){
			PathMatcher matcher = new AntPathMatcher();
			for (String antPath : excludeMappings) {
				res=matcher.match(antPath, reqUrl);
				if(res){
					return res;
				}
			}
		}
		return res;
	}
}
