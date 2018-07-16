package com.wap.main.controller;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sys.dic.SysDicHelper;
import com.sys.exception.EpiccException;
import com.sys.service.GetBussinessNoService;
import com.wap.dzsw.service.ActivityService;
import com.wap.main.entity.OperationLogEntity;
import com.wap.main.entity.UserEntity;
import com.wap.main.service.MainService;
import com.wap.trans.service.TransService;

import core.db.dao.IBaseService;

@Controller
@RequestMapping(value={"/main"})
public class MainController {
	private static final Log logger = LogFactory.getLog(MainController.class);

	@Autowired(required = false)
	private IBaseService baseService = null;

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	@Autowired(required = false)
	private MainService mainService = null;

	public void setMainService(MainService mainService) {
		this.mainService = mainService;
	}
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	@Autowired
	private ActivityService activityService;
	@Autowired
	private CacheManager cacheManager;
	/**
	 * 
			* 描述:登录电商配送
			* @param map
			* @param request
			* @param session
			* @param response
			* @author 朱久满 2016年3月1日 上午8:58:24
	 */
	@RequestMapping(value={"/doLogin"})
	public void doLogin(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out=null;
		UserEntity userEntity = new UserEntity();
		String openId = request.getParameter("openId");
		userEntity.setUsercode(request.getParameter("usercode"));
		userEntity.setPassword(request.getParameter("password"));
		logger.error("登录时,请求中的session:"+request.getParameter("JSESSIONID"));
		JSONObject json=new JSONObject();
		try {
			out = response.getWriter();
			if (null == openId || "".equals(openId)) {
				throw new Exception();
			}
			userEntity = mainService.login(userEntity);
			if (userEntity != null) {
				if (null != userEntity.getUsercode()
						&& !"".equals(userEntity.getUsercode().trim())
						&& null != userEntity.getPassword()
						&& !"".equals(userEntity.getPassword().trim())) {
					json.element("result", 1);
					session.setAttribute("loginUser", userEntity);
					session.setAttribute("openId", openId);
					json.element("msg", "登录成功");
					logger.info("登录成功:" + session.getAttribute("loginUser"));
					logger.info("登录成功JSESSIONID:" + session.getId());
					json.element("usercode", userEntity.getUsercode());
					json.element("password", userEntity.getPassword());
					mainService.loginReport(userEntity, session);
					logger.info("登录结束============:");
				} else {
					json.element("result", 0);
					json.element("msg", "登录异常！");
				}
			} else {
				json.element("result", 0);
				json.element("msg", "登录异常！");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("登录异常:"+e.getErrMess());
			json.element("result",0);
			json.element("msg", e.getErrMess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("登录异常:"+e.getMessage());
			json.element("result",0);
			json.element("msg", "登录异常！");
			if(null == openId || "".equals(openId)){
				json.element("msg", "请在微信公众号内进入系统！");
			}
		}
		logger.info("========登录json:"+json);
		out.println(json);
		out.flush();
		out.close();
	}
	/**
	 * 
			* 描述:刷新缓存
			* @param map
			* @param request
			* @param session
			* @param response
			* @author 朱久满 2016年3月1日 上午8:58:08
	 */
	@RequestMapping(value={"/refresh"})
	public void doRefresh(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out=null;
		JSONObject json=new JSONObject();
		try {
			out = response.getWriter();
			SysDicHelper.getInstance().reLoadDic();
			activityService.clearCacheMap();
			Collection<String> cacheNames = cacheManager.getCacheNames();
			if(cacheNames!=null){
				for (String cacheName : cacheNames) {
					Cache cache = cacheManager.getCache(cacheName);
					cache.clear();
				}
			}
			session.getServletContext().setAttribute("httpBasePath", SysDicHelper.getInstance().getDicContentByDicTypeAndDicId("HTTPBASEPATH", "picc01").getIdValue());
			json.element("msg","刷新成功！");
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			json.element("msg","系统异常！");
		}
		out.println(json);
		out.flush();
		out.close();
	}
	/**
	 * 
			* 描述:注销
			* @param map
			* @param request
			* @param session
			* @param response
			* @author 朱久满 2016年3月1日 上午8:57:54
	 */
	@RequestMapping(value={"/doExit"})
	public void doExit(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		String openId = request.getParameter("openId");
		try {
			mainService.logoutReport(session,openId);//记录系统登出时间
			UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
			if(userEntity != null){
				session.removeAttribute("loginUser");
			}
			response.sendRedirect("../login.jsp?openId="+openId);
		} catch (Exception e) {
			logger.error("注销失败:"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * 
			* 描述:操作轨迹
			* @param map
			* @param request
			* @param session
			* @param response
			* @author 朱久满 2016年3月1日 上午8:58:46
	 */
	@RequestMapping(value={"/operationLog"})
	public void operationLog(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		String titlecode = request.getParameter("titlecode");
		String openId = request.getParameter("openId");
		String title = SysDicHelper.getInstance().getValueByDicTypeAndDicId("OPERATION_LOG", titlecode);
		if (title != null && !"".equals(title.trim())) {
			OperationLogEntity logEntity = new OperationLogEntity();
			logEntity.setIds(GetBussinessNoService.GetBussinessNo("LINSHI"));
			logEntity.setCreatetime(new Date());
			logEntity.setOpenid(openId);
			logEntity.setTitle(title);
			try {
				baseService.save(logEntity);
				logger.error("openId:" + openId + " 执行【" + title + "】操作");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("保存【" + title + "】操作记录异常:" + e.getMessage() + "【openId:" + openId + "】");
			}
		}
	}
	
}
