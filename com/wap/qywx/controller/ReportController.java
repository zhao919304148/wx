package com.wap.qywx.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.wap.qywx.entity.FgsBfsrAllData;
import com.wap.qywx.entity.ReportTimeData;
import com.wap.qywx.service.ReportService;
import com.wap.trans.entity.tr_1072.ReqTrans1072Entity;
import com.wap.util.ConstantUtils;

@Controller
@RequestMapping(value = { "/qywx/report" })
public class ReportController {
	
	private static final Log logger = LogFactory.getLog(ReportController.class);
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = { "/report1.do" })
	public ModelAndView fcomBfsrReport(HttpServletRequest request, HttpServletResponse response, HttpSession session,String ppcode) {
		Jedis jedis = null;
		String userid = "";
		ModelAndView czmsView = new ModelAndView("/qywx/report/fgsSsbf");
		String ticket_id = (String)request.getAttribute(ConstantUtils.QYWX_TICKET_ID);
		ReqTrans1072Entity reqTrans1072Entity = null;
		com.alibaba.fastjson.JSONObject res = null;
		ReportTimeData reportTimeData = null;
		String retcode = "";
		try{
			jedis = jedisPool.getResource();
			if(jedis != null){
				if(StringUtils.isNotBlank(ticket_id)){
					userid = jedis.get(ticket_id);
					if(StringUtils.isNotBlank(userid)){
						//调用接口获取数据，进行展示
						reqTrans1072Entity = new ReqTrans1072Entity();
						reqTrans1072Entity.setUserid(userid);
						reqTrans1072Entity.setRptmethod("qdbfTimeReport");
						if(StringUtils.isBlank(ppcode)){
							//默认是各个经营单位的实时数据
							ppcode = "PPAA";
						}
						reqTrans1072Entity.setPpcode(ppcode);
						res = reportService.getReportData(reqTrans1072Entity,ReportTimeData.class);
						if(res != null){
							retcode = (String)res.get("retcode");
							if(StringUtils.isNotBlank(retcode) && "1".equals(retcode)){
								reportTimeData = (ReportTimeData)res.get("info");
								if(reportTimeData != null){
									czmsView.addObject("res", res.get("retcode"));
									czmsView.addObject("info", reportTimeData.getReportdata());
									czmsView.addObject("updatetime", reportTimeData.getUpdatetime());
									czmsView.addObject("msg", res.get("retmsg"));
								}
							}else{
								czmsView.addObject("res", res.get("retcode"));
								czmsView.addObject("msg", res.get("retmsg"));
							}
						}else{
							czmsView.addObject("res", "0");
							czmsView.addObject("msg", "获取数据为空");
						}
					}else{
						//本次访问失效或您没有访问该报表权限
						czmsView.addObject("res", "0");
						czmsView.addObject("msg", "本次访问失效或您没有访问该报表权限");
					}
				}else{
					czmsView.addObject("res", "0");
					czmsView.addObject("msg", "本次访问票据已失效");
				}
			}else{
				//程序异常
				czmsView.addObject("res", "0");
				czmsView.addObject("msg", "本次访问已失效，请重新访问本页面");
			}
		}catch(Exception e){
			e.printStackTrace();
			czmsView.addObject("res", "0");
			czmsView.addObject("msg", "程序异常，请联系系统管理人员");
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		czmsView.addObject("ppcode",ppcode);
		return czmsView;
	}
	
	@RequestMapping(value = { "/rptIndex.do" })
	public ModelAndView fgsBf(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView indexView = new ModelAndView("/qywx/report/rptIndex");
		Jedis jedis = null;
		String userid = "";
		String ticket_id = (String)request.getAttribute(ConstantUtils.QYWX_TICKET_ID);
		ReqTrans1072Entity reqTrans1072Entity = null;
		com.alibaba.fastjson.JSONObject res = null;
		FgsBfsrAllData fgsBfsrAllData = null;
		String retcode = "";
		try{
			jedis = jedisPool.getResource();
			if(jedis != null){
				userid = jedis.get(ticket_id);
				if(StringUtils.isNotBlank(userid)){
					//调用接口获取数据，进行展示
					reqTrans1072Entity = new ReqTrans1072Entity();
					reqTrans1072Entity.setUserid(userid);
					reqTrans1072Entity.setRptmethod("fgsBfsrAllReport");
					res = reportService.getReportData(reqTrans1072Entity,FgsBfsrAllData.class);
					if(res != null){
						retcode = (String)res.get("retcode");
						if(StringUtils.isNotBlank(retcode) && "1".equals(retcode)){
							fgsBfsrAllData = (FgsBfsrAllData)res.get("info");
							if(fgsBfsrAllData != null){
								indexView.addObject("res", res.get("retcode"));
								indexView.addObject("info", fgsBfsrAllData);
								indexView.addObject("msg", res.get("retmsg"));
							}
						}else{
							indexView.addObject("res", res.get("retcode"));
							indexView.addObject("msg", res.get("retmsg"));
						}
					}else{
						indexView.addObject("res", "0");
						indexView.addObject("msg", "获取数据为空");
					}
				}else{
					//本次访问失效或您没有访问该报表权限
					indexView.addObject("res", "0");
					indexView.addObject("msg", "本次访问失效或您没有访问该报表权限");
				}
			}else{
				//程序异常
				indexView.addObject("res", "0");
				indexView.addObject("msg", "本次访问已失效，请重新访问本页面");
			}
		}catch(Exception e){
			e.printStackTrace();
			indexView.addObject("res", "0");
			indexView.addObject("msg", "程序异常，请联系系统管理人员");
		}finally{
			if(jedis != null){
				jedis.close();
			}
		}
		indexView.addObject("operateid", userid);
		return indexView;
	}
}
