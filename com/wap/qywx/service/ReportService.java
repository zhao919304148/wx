package com.wap.qywx.service;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.exception.EpiccException;
import com.wap.trans.entity.tr_1071.ReqTrans1071Entity;
import com.wap.trans.entity.tr_1071.ResTrans1071Entity;
import com.wap.trans.entity.tr_1072.ReqTrans1072Entity;
import com.wap.trans.entity.tr_1072.ResTrans1072BasePartEntity;
import com.wap.trans.entity.tr_1072.ResTrans1072BodyEntity;
import com.wap.trans.entity.tr_1072.ResTrans1072Entity;
import com.wap.trans.service.TransService;
import com.wap.util.CommonUtils;
import com.wap.util.TransUtil;

@Service("reportService")
public class ReportService {
	private static final Log logger = LogFactory.getLog(ReportService.class);
	@Autowired(required = false)
	private TransService transService = null;

	/**
	 * 描述:企业微信用户权限校验
	 * @param reqTrans1071Entity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮 2018年05月08日 上午10:26:16
	 */
	public com.alibaba.fastjson.JSONObject qywxUserAuth(ReqTrans1071Entity reqTrans1071Entity)throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1071Entity resTrans1071Entity = transService.genericExecuteTrans("1071", reqTrans1071Entity, ResTrans1071Entity.class);
		if(resTrans1071Entity != null){
			if ("1".equals(resTrans1071Entity.getResponseHeadEntity().getResponse_code().trim())) {
				json.put("retcode", "1");
				json.put("retmsg", "成功");
			} else {
				json.put("retcode", "0");
				json.put("retmsg", resTrans1071Entity.getResponseHeadEntity().getResponse_message());
			}
		}else{
			json.put("retcode", "0");
			json.put("retmsg", "权限验证失败");
		}
		return json;
	}
	/**
	 * 描述:企业微信获取业务数据
	 * @param reqTrans1072Entity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮 2018年05月08日 上午10:20:32
	 */
	public com.alibaba.fastjson.JSONObject getReportData(ReqTrans1072Entity reqTrans1072Entity,Class resClazz) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1072Entity resTrans1072Entity = transService.genericExecuteTrans("1072", reqTrans1072Entity, ResTrans1072Entity.class);
		String report_data = "";
		ResTrans1072BodyEntity body = null;
		ResTrans1072BasePartEntity basePart = null;
		if(resTrans1072Entity != null){
			if ("1".equals(CommonUtils.trim(resTrans1072Entity.getResponseHeadEntity().getResponse_code()))) {
				//如果返回成功，取report_data数据，转换成对象
				body = resTrans1072Entity.getResTrans1072BodyEntity();
				if(body != null){
					basePart = body.getResTrans1072BasePartEntity();
					if(basePart != null){
						report_data = basePart.getReportData();
						logger.info("获取实时报表数据report_data=" + report_data);
						if(StringUtils.isNotBlank(report_data)){
							//将Json转换成对象
							json.put("retcode", "1");
							json.put("info", TransUtil.genericJsonToObj(report_data,resClazz));
							json.put("retmsg", "成功");
							logger.info("获取实时报表数据Json转换对象结束");
						}else{
							//失败
							json.put("retcode", "0");
							json.put("retmsg", "报表数据获取错误");
						}
					}else{
						//失败
						json.put("retcode", "0");
						json.put("retmsg", "报表数据为空");
					}
				}else{
					//失败
					json.put("retcode", "0");
					json.put("retmsg", "报表数据为空");
				}
			} else {
				String errMsg = resTrans1072Entity.getResponseHeadEntity().getResponse_message();
				json.put("retcode", "0");
				json.put("retmsg", errMsg);
			}
		}else{
			json.put("retcode", "0");
			json.put("retmsg", "获取报表数据异常");
		}
		return json;
	}
}
