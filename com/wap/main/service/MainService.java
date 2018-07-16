package com.wap.main.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.sys.exception.EpiccException;
import com.wap.main.controller.MainController;
import com.wap.main.entity.LoginReportEntity;
import com.wap.main.entity.UserEntity;
import com.wap.trans.entity.tr_1001.ReqTrans1001Entity;
import com.wap.trans.entity.tr_1001.ResTrans1001Entity;
import com.wap.trans.service.TransService;
import com.wap.util.DateUtils;
import com.wap.util.TransUtil;

import core.db.dao.IBaseService;

@Service("mainService")
public class MainService {
	private static final Log logger = LogFactory.getLog(MainService.class);
	@Autowired(required = false)
	private IBaseService baseService = null;

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	@Autowired(required = false)
	private MainController mainController = null;
	
	public void setMainController(MainController mainController) {
		this.mainController = mainController;
	}
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	public UserEntity login(UserEntity userEntity) throws EpiccException{
		ReqTrans1001Entity reqTrans1001Entity = new ReqTrans1001Entity();
		TransUtil.copyObject(userEntity, reqTrans1001Entity);
		ResTrans1001Entity resTrans1001Entity = transService.executeTrans1001(reqTrans1001Entity);
		if("0".equals(resTrans1001Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("登录失败："+resTrans1001Entity.getResuestHeadEntity().getResponse_message());
		}
		TransUtil.copyObject(resTrans1001Entity.getResTrans1001BodyEntity().getResTrans1001UserInfoEntity(), userEntity);
		return userEntity;
	}
	/**
	 * 
			* 描述:记录系统登入信息
			* @param userEntity
			* @param session
			* @author ZN
	 */
	public void loginReport(UserEntity userEntity,HttpSession session){
		LoginReportEntity loginReportEntity = new LoginReportEntity();
		TransUtil.copyObject(userEntity,loginReportEntity);
		Object openId = session.getAttribute("openId");
		if(null != openId){
			loginReportEntity.setOpenid(openId.toString());
		}
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
        String strCurrentDateTime= formatter.format(new Date());//获取当前系统时间  年月日时分秒
		try {
			loginReportEntity.setLogintime(formatter.parse(strCurrentDateTime));//登入时间
		} catch (Exception e) {
			e.printStackTrace();
		}
		String uid = DateUtils.stringToNumber(strCurrentDateTime);//yyyyMMddHHmmss
		loginReportEntity.setCid(uid);//主键
		Object obj = session.getAttribute("openId");
		if(null != obj){
			loginReportEntity.setOpenid(obj.toString());
		}
		baseService.save(loginReportEntity);
		session.setAttribute("loginReportEntity", loginReportEntity);
	//	session.setMaxInactiveInterval(60*60);
	}
	/**
	 * 
			* 描述:记录系统登出信息
			* @param session
			* @author ZN
	 * @throws ParseException 
	 */
	public void logoutReport(HttpSession session,String openId) throws ParseException{
//		LoginReportEntity loginReportEntity  = (LoginReportEntity) session.getAttribute("loginReportEntity");
		LoginReportEntity loginReportEntity  = this.getHisLoginUserByOpenId(openId);
		if(loginReportEntity != null){
			SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");
			String strCurrentDateTime= formatter.format(new Date());//获取当前系统时间  年月日时分秒
			loginReportEntity.setValidflag("0");
			loginReportEntity.setLogouttime(formatter.parse(strCurrentDateTime));//登出时间
			baseService.update(loginReportEntity);
		}
	}

	public LoginReportEntity getHisLoginUserByOpenId(String openId) {
		LoginReportEntity loginReportEntity = null;
		try {
		String sql = "select first 1 cid,usercode,password,openid,comcode,logintime,logouttime,validflag from S_RBAC_LOGINREPORT where openid= ? order by logintime desc";
		List list = jdbcTemplate.queryForList(sql, openId);
		if(null != list && list.size()>0){
			Map<String,Object> map = (Map<String, Object>) list.get(0);
			loginReportEntity = new LoginReportEntity();
			loginReportEntity.setCid(map.get("CID").toString());
			loginReportEntity.setUsercode(map.get("USERCODE").toString());
			loginReportEntity.setPassword(map.get("PASSWORD").toString());
			loginReportEntity.setOpenid(map.get("OPENID").toString());
			loginReportEntity.setComcode(map.get("COMCODE").toString());
			loginReportEntity.setLogintime((java.sql.Timestamp)map.get("LOGINTIME"));
			loginReportEntity.setLogouttime((java.sql.Timestamp)map.get("LOGOUTTIME"));
			loginReportEntity.setValidflag(String.valueOf(map.get("VALIDFLAG")));
		}
		} catch (Exception e) {
			logger.error("getHisLoginUserByOpenId方法异常:"+e.getMessage());
		}
		return loginReportEntity;
	}
}
