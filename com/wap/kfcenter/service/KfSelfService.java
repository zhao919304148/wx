package com.wap.kfcenter.service;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;

import com.wap.dzsw.entity.BrandNetwork;
import com.wap.kfcenter.entity.DbServicelistEntity;
import com.wap.kfcenter.model.ZzkfWxSelfHelp;
import com.sys.exception.EpiccException;
import com.wap.trans.entity.tr_1047.ReqTrans1047Entity;
import com.wap.trans.entity.tr_1047.ResTrans1047BasePartEntity;
import com.wap.trans.entity.tr_1047.ResTrans1047Entity;
import com.wap.trans.entity.tr_1048.ReqTrans1048Entity;
import com.wap.trans.entity.tr_1048.ResTrans1048Entity;
import com.wap.trans.entity.tr_1049.ReqTrans1049Entity;
import com.wap.trans.entity.tr_1049.ResTrans1049Entity;
import com.wap.trans.entity.tr_1049.ResTrans1049ServiceDataEntity;
import com.wap.trans.entity.tr_1050.ReqTrans1050Entity;
import com.wap.trans.entity.tr_1050.ResTrans1050Entity;
import com.wap.trans.entity.tr_1051.ReqTrans1051Entity;
import com.wap.trans.entity.tr_1051.ResTrans1051Entity;
import com.wap.trans.entity.tr_1052.ReqTrans1052Entity;
import com.wap.trans.entity.tr_1052.ResTrans1052CusserviceDataEntity;
import com.wap.trans.entity.tr_1052.ResTrans1052Entity;
import com.wap.trans.entity.tr_1053.ReqTrans1053Entity;
import com.wap.trans.entity.tr_1053.ResTrans1053Entity;
import com.wap.trans.entity.tr_1054.ReqTrans1054BodyEntity;
import com.wap.trans.entity.tr_1054.ResTrans1054Entity;
import com.wap.trans.entity.tr_1055.ReqTrans1055BodyEntity;
import com.wap.trans.entity.tr_1055.ResTrans1055Entity;
import com.wap.trans.entity.tr_1055.ResTrans1055MovecarMainDataEntity;
import com.wap.trans.entity.tr_1056.ReqTrans1056BodyEntity;
import com.wap.trans.entity.tr_1056.ResTrans1056Entity;
import com.wap.trans.service.TransService;
import com.wap.util.CommonUtils;
import com.wap.util.TransUtil;

import core.db.dao.IBaseService;

@Service("kfSelfService")
public class KfSelfService {
	
	@Autowired(required = false)
	private TransService transService = null;
	
	@Autowired(required = false)
	private IBaseService baseService = null;
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	/**
	 * 
			* 描述:受邀有礼
			* @param reqTrans1047Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月19日 下午1:26:16
	 */
	public com.alibaba.fastjson.JSONObject getSYYLInfo(ReqTrans1047Entity reqTrans1047Entity)throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1047Entity resTrans1047Entity = transService.executeTrans1047(reqTrans1047Entity);
		if ("1".equals(resTrans1047Entity.getResponseHeadEntity().getResponse_code().trim())) {
			ResTrans1047BasePartEntity resTrans1047BasePartEntity = resTrans1047Entity.getResTrans1047BodyEntity().getResTrans1047BasePartEntity();
			if (resTrans1047BasePartEntity != null) {
				json.put("retcode", "1");
				json.put("info", resTrans1047BasePartEntity);
			} else {
				json.put("retcode", "0");
				json.put("retmsg", "请确认您是再保客户");//待修改
			}
		} else {
			String errMsg = resTrans1047Entity.getResponseHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:受邀有礼注册
			* @param reqTrans1048Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月19日 下午5:05:32
	 */
	public com.alibaba.fastjson.JSONObject syylSubmit(ReqTrans1048Entity reqTrans1048Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1048Entity resTrans1048Entity = transService.executeTrans1048(reqTrans1048Entity);
		if(resTrans1048Entity != null){
			if ("1".equals(CommonUtils.trim(resTrans1048Entity.getResponseHeadEntity().getResponse_code()))) {
				json.put("retcode", "1");
				json.put("retmsg", "注册成功");
			} else {
				String errMsg = resTrans1048Entity.getResponseHeadEntity().getResponse_message();
				json.put("retcode", "0");
				json.put("retmsg", errMsg);
			}
		}else{
			json.put("retcode", "0");
			json.put("retmsg", "注册失败");
		}
		return json;
	}
	/**
	 * 
			* 描述:车主秘书注册信息查询
			* @param reqTrans1049Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午2:11:00
	 */
	public com.alibaba.fastjson.JSONObject czmsRegisterQuery(ReqTrans1049Entity reqTrans1049Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1049Entity resTrans1049Entity = transService.executeTrans1049(reqTrans1049Entity);
		if ("1".equals(resTrans1049Entity.getResuestHeadEntity().getResponse_code().trim())) {
			List<ResTrans1049ServiceDataEntity> resTrans1049ServiceDataEntities = resTrans1049Entity.getResTrans1049BodyEntity().getResTrans1049ServiceDataList();
			if (null != resTrans1049ServiceDataEntities) {
				json.put("retcode", "1");
				json.put("registerInfo", resTrans1049ServiceDataEntities);
			}else {
				json.put("retcode", "0");
				json.put("retmsg", "获取信息失败");
			}
		} else {
			String errMsg = resTrans1049Entity.getResuestHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:车主秘书注册、修改
			* @param reqTrans1050Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午2:28:28
	 */
	public com.alibaba.fastjson.JSONObject czmsRegisterOrModify(ReqTrans1050Entity reqTrans1050Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1050Entity resTrans1050Entity = transService.executeTrans1050(reqTrans1050Entity);
		if ("1".equals(resTrans1050Entity.getResponseHeadEntity().getResponse_code().trim())) {
			json.put("retcode", "1");
			if ("1".equals(reqTrans1050Entity.getOperatetype())) {
				json.put("retmsg", "注册成功");
			}else {
				json.put("retmsg", "修改成功");
			}
		} else {
			String errMsg = resTrans1050Entity.getResponseHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:事故救援预约登记信息保存
			* @param reqTrans1051Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午2:32:02
	 */
	public com.alibaba.fastjson.JSONObject sgjyInfoSave(ReqTrans1051Entity reqTrans1051Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1051Entity resTrans1051Entity = transService.executeTrans1051(reqTrans1051Entity);
		if ("1".equals(resTrans1051Entity.getResponseHeadEntity().getResponse_code().trim())) {
			json.put("retcode", "1");
			json.put("retmsg", "保存成功");
		} else {
			String errMsg = resTrans1051Entity.getResponseHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:服务预约信息查询
			* @param reqTrans1052Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午2:44:46
	 */
	public com.alibaba.fastjson.JSONObject fwyyInfoQuery(ReqTrans1052Entity reqTrans1052Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1052Entity resTrans1052Entity = transService.executeTrans1052(reqTrans1052Entity);
		if ("1".equals(resTrans1052Entity.getResuestHeadEntity().getResponse_code().trim())) {
			json.put("retcode", "1");
			List<ResTrans1052CusserviceDataEntity> resTrans1052CusserviceDataEntities = resTrans1052Entity.getResTrans1052BodyEntity().getResTrans1052CusserviceDataList();
			if (null != resTrans1052CusserviceDataEntities && resTrans1052CusserviceDataEntities.size()>0) {
				json.put("retcode", "1");
				json.put("servicelist", resTrans1052CusserviceDataEntities);
			}else {
				json.put("retcode", "0");
				json.put("retmsg", "您没有此项服务");
			}
		} else {
			String errMsg = resTrans1052Entity.getResuestHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:服务预约信息注册
			* @param reqTrans1053Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年10月20日 下午2:47:54
	 */
	public com.alibaba.fastjson.JSONObject fwyyInfoRegister(ReqTrans1053Entity reqTrans1053Entity) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1053Entity resTrans1053Entity = transService.executeTrans1053(reqTrans1053Entity);
		if ("1".equals(resTrans1053Entity.getResponseHeadEntity().getResponse_code().trim())) {
			json.put("retcode", "1");
			json.put("retmsg", "注册成功");
		} else {
			String errMsg = resTrans1053Entity.getResponseHeadEntity().getResponse_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	
	/**
	 * 
			* 描述:自助客服--根据类型搜索问题
			* @param questionType
			* @return
			* @author zhangjian2017年11月8日 上午10:50:43
	 */
	public List<ZzkfWxSelfHelp> getQuestionList(String questionType) {
		baseService.evictObject(ZzkfWxSelfHelp.class);
		List<ZzkfWxSelfHelp> list = hibernateTemplate.find("from ZzkfWxSelfHelp where validFlag = ? and questionType = ? ","1",questionType);
		return list;
	}
	/**
	 * 
			* 描述:自助客服--根据搜索内容搜索
			* @param searchContent
			* @return
			* @author zhangjian2017年11月8日 上午10:50:47
	 */
	public List<ZzkfWxSelfHelp> searchQuestionList(String searchContent){
		baseService.evictObject(ZzkfWxSelfHelp.class);
		List<ZzkfWxSelfHelp> list = hibernateTemplate.find("from ZzkfWxSelfHelp where validFlag = ? and question like ? ","1","%"+searchContent+"%");
		return list;
	}
	/**
	 * 
			* 描述:自助客服--根据问题id搜索答案
			* @param questionId
			* @return
			* @author zhangjian2017年11月8日 上午10:50:50
	 */
	public List<ZzkfWxSelfHelp> searchAnswerList(String questionId){
		baseService.evictObject(ZzkfWxSelfHelp.class);
		List<ZzkfWxSelfHelp> list = hibernateTemplate.find("from ZzkfWxSelfHelp where validFlag = ? and id = ? ","1",questionId);
		return list;
	}
	
	/**
	 * 
			* 描述:挪车服务注册
			* @param reqTrans1054Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午3:01:11
	 */
	public com.alibaba.fastjson.JSONObject carServiceRegister(ReqTrans1054BodyEntity reqBody) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1054Entity resTrans1054Entity = transService.executeTrans1054(reqBody);
		if ("0000".equals(resTrans1054Entity.getResponseHead95518().getResponse_code().trim())) {
			json.put("retcode", "1");
			json.put("retmsg", "注册成功");
		} else {
			String errMsg = resTrans1054Entity.getResponseHead95518().getError_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}

	/**
	 * 
			* 描述:查询已配置挪车服务
			* @param reqTrans1055Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午3:06:18
	 */
	public com.alibaba.fastjson.JSONObject queryCarServiceList(ReqTrans1055BodyEntity reqBody) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1055Entity resTrans1055Entity = transService.executeTrans1055(reqBody);
		if ("0000".equals(resTrans1055Entity.getResuestHead95518().getResponse_code().trim())) {
			json.put("retcode", "1");
			List<ResTrans1055MovecarMainDataEntity> resTrans1055MovecarMainDataEntities = resTrans1055Entity.getResTrans1055BodyEntity().getResTrans1055MovecarMainDataEntities();
			json.put("carlist", resTrans1055MovecarMainDataEntities);
		} else {
			String errMsg = resTrans1055Entity.getResuestHead95518().getError_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	/**
	 * 
			* 描述:获取可使用分机号
			* @param reqTrans1056Entity
			* @return
			* @throws EpiccException
			* @author zhangjian2017年11月28日 下午3:12:50
	 */
	public com.alibaba.fastjson.JSONObject getExtensionNumber(ReqTrans1056BodyEntity reqBody) throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		ResTrans1056Entity resTrans1056Entity = transService.executeTrans1056(reqBody);
		if ("0000".equals(resTrans1056Entity.getResuestHead95518().getResponse_code().trim())) {
			json.put("retcode", "1");
			json.put("extensionno", resTrans1056Entity.getResTrans1056BodyEntity().getExtensionno());
		} else {
			String errMsg = resTrans1056Entity.getResuestHead95518().getError_message();
			json.put("retcode", "0");
			json.put("retmsg", errMsg);
		}
		return json;
	}
	
	
}

	
