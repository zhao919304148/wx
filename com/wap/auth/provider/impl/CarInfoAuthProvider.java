package com.wap.auth.provider.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.alibaba.fastjson.JSONObject;
import com.sys.exception.EpiccException;
import com.sys.redis.RedisSessionService;
import com.wap.auth.exception.AuthenticationException;
import com.wap.auth.provider.AbstractAuthProvider;
import com.wap.dzsw.entity.CarDataEntity;
import com.wap.dzsw.service.GiftCardService;
import com.wap.util.ConstantUtils;
import com.wx.util.WeixinUtil;

public class CarInfoAuthProvider extends AbstractAuthProvider {
	public static final Log logger=LogFactory.getLog(CarInfoAuthProvider.class);
	@Autowired
	private RedisSessionService sessionService;
	@Autowired
	private GiftCardService giftCardService;
	/**即使未绑定车辆也不会抛出【-03：未关联投保车辆信息】错误信息的url**/
	private List<String> excludeMappings;
	
	public List<String> getExcludeMappings() {
		return excludeMappings;
	}
	public void setExcludeMappings(List<String> excludeMappings) {
		this.excludeMappings = excludeMappings;
	}
	@Override
	public boolean doAuthenticate(HttpServletRequest request,HttpServletResponse response) throws AuthenticationException {
		logger.info("[车辆信息拦截]");
		boolean isAjax = StringUtils.isNotBlank(request.getHeader("X-Requested-With"));
		String servletPath = request.getServletPath();
		String accessJsp = "dzsw/dszh/main.jsp";
		boolean isFreshJsp = servletPath.startsWith("/"+accessJsp);
		boolean isMyCardJSP = servletPath.startsWith("/dzsw/myCard.jsp");
		String reqEnCoding = request.getCharacterEncoding();
		String username = null;
		String centralIdentifyType = null;
		String centralIdentifyNo = null;
		List<CarDataEntity> userCarList = null;
		response.setCharacterEncoding(reqEnCoding);
		String sessionId = (String) request.getAttribute(ConstantUtils._ACCESS_TICKET);
		String openId = sessionService.getAtrribute(sessionId , ConstantUtils.OPEN_ID);
		if(StringUtils.isNotBlank(openId)){
			//设置session有效时间
			sessionService.setSessionExpire(sessionId);
			//获取username
			username = sessionService.getString(sessionId, ConstantUtils.USER_NAME);
			centralIdentifyType = sessionService.getString(sessionId, ConstantUtils.CENTRAL_IDENTIFY_TYPE);
			centralIdentifyNo = sessionService.getString(sessionId, ConstantUtils.CENTRAL_IDENTIFY_NO);
			if(StringUtils.isBlank(username)||isFreshJsp){
				//得到总公司username,保存至session
				Map<String, String> resMap = WeixinUtil.getUserNameFormPICC(openId);
				String retcode = resMap.get("retcode");
				username = resMap.get("username");
				logger.info("调用总公司接口获取用户信息："+resMap.toString());
				if("0".equals(retcode)){
					if(StringUtils.isNotBlank(username)){
						centralIdentifyType=resMap.get("identifytype");
						centralIdentifyNo=resMap.get("identifyno");
						sessionService.setAttribute(sessionId, ConstantUtils.USER_NAME, username);
						sessionService.setAttribute(sessionId, ConstantUtils.CENTRAL_IDENTIFY_TYPE, centralIdentifyType);
						sessionService.setAttribute(sessionId, ConstantUtils.CENTRAL_IDENTIFY_NO, centralIdentifyNo);
					}else{
						if(isAjax){
							throw new AuthenticationException("-02","非注册用户");
						}else if(isFreshJsp){
							request.setAttribute("state", "-08");
							return true;
						}else{
							//弹窗提示跳转至总公司登录页面
							logger.info("跳转至总公司登录页面");
							try {
								request.getRequestDispatcher("/dzsw/toCentralLogin.jsp").forward(request, response);
							} catch (Exception e) {
								e.printStackTrace();
							}
							return false;
						}
					}
				}else{
					if(isAjax){
						throw new AuthenticationException("-08","检查登录状态失败，请重新登录");
					}else if(isFreshJsp){
						request.setAttribute("state", "-08");
						return true;
					}else{
						//弹窗提示跳转至总公司登录页面
						try {
							request.getRequestDispatcher("/dzsw/toCentralLogin.jsp").forward(request, response);
						} catch (Exception e) {
							e.printStackTrace();
						}
						return false;
					}
				}
			}
			userCarList = (List<CarDataEntity>) sessionService.getListObject(sessionId, ConstantUtils.USER_CAR_LIST,CarDataEntity.class);
			logger.info("获取redis session中用户车辆信息-[openid:"+openId+"]");
			if(userCarList==null||userCarList.isEmpty()||isMyCardJSP||isFreshJsp){
				//获取绑定车辆信息
				try {
					userCarList = giftCardService.loginGetCarInfo(openId);
				} catch (EpiccException e) {
					throw new AuthenticationException("-09","获取车辆信息失败，请稍后重试");
				}
				//刷新redis缓存
				sessionService.setAttribute(sessionId, ConstantUtils.USER_CAR_LIST, userCarList);
				//判断是否存在绑定车辆信息
				if (userCarList == null || userCarList.isEmpty()) {
					if(!isExcludeMapping(request)){
						if(isAjax){
							//不存在车辆信息 :需要跳转至投保车辆关联页面
							throw new AuthenticationException("-03", "未关联投保车辆信息");
						}else{
							String httpBasePath = (String) request.getSession().getServletContext().getAttribute("httpBasePath");
							String accessSign=(String) request.getAttribute(ConstantUtils._TICKET_SIGN);
//							String url = httpBasePath +"dzsw/loginGiftCard.jsp?"+ConstantUtils._ACCESS_TICKET+"="+sessionId+"&"+ConstantUtils._TICKET_SIGN+"="+accessSign;
							String url = httpBasePath +accessJsp+"?"+ConstantUtils._ACCESS_TICKET+"="+sessionId+"&"+ConstantUtils._TICKET_SIGN+"="+accessSign;
							this.redirectRquest(request, response, url);
							return false;
						}
					}
				}
			}
		}else{
			//openId为空
			throw new AuthenticationException("-01","openId参数不存在");
		}
		//request scope保存常用数据
		request.setAttribute(ConstantUtils.USER_NAME, username);
		request.setAttribute(ConstantUtils.USER_CAR_LIST, userCarList);
		request.setAttribute(ConstantUtils.CENTRAL_IDENTIFY_TYPE, centralIdentifyType);
		request.setAttribute(ConstantUtils.CENTRAL_IDENTIFY_NO, centralIdentifyNo);
		logger.info("当前线程变量：[ticket:"+sessionId+",openid:"+openId+",username:"+username+",CentralIdentifyType:"+centralIdentifyType+",CentralIdentifyNo:"+centralIdentifyNo+",userCarList:"+JSONObject.toJSONString(userCarList)+"]");
		return true;
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
}
