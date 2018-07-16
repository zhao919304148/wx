package com.wap.kfcenter.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.exception.EpiccException;
import com.wap.kfcenter.entity.ActiveReceiveNumEntity;
import com.wap.kfcenter.entity.ActiveReceiveNumQueryEntity;
import com.wap.kfcenter.entity.CzQueryResListEntity;
import com.wap.kfcenter.entity.CzReqListEntity;
import com.wap.kfcenter.entity.CzResListEntity;
import com.wap.kfcenter.entity.ExchangeQueryReqEntity;
import com.wap.kfcenter.entity.ExchangeQueryResEntity;
import com.wap.kfcenter.entity.ExchangeQueryResListEntity;
import com.wap.kfcenter.entity.ExchangeReqEntity;
import com.wap.kfcenter.entity.ExchangeReqListEntity;
import com.wap.kfcenter.entity.ExchangeResEntity;
import com.wap.kfcenter.entity.ExchangeResListEntity;
import com.wap.kfcenter.entity.PolicyEntity;
import com.wap.kfcenter.entity.PolicyQueryEntity;
import com.wap.lipei.entity.MaterialFileEntity;
import com.wap.trans.entity.tr_1012.ReqTrans1012FileEntity;
import com.wap.trans.entity.tr_1016.ReqTrans1016Entity;
import com.wap.trans.entity.tr_1016.ResTrans1016Entity;
import com.wap.trans.entity.tr_1016.ResTrans1016PolicyDataEntity;
import com.wap.trans.entity.tr_1017.ReqTrans1017CzEntity;
import com.wap.trans.entity.tr_1017.ReqTrans1017Entity;
import com.wap.trans.entity.tr_1017.ReqTrans1017ExchangeEntity;
import com.wap.trans.entity.tr_1017.ResTrans1017CzDataEntity;
import com.wap.trans.entity.tr_1017.ResTrans1017Entity;
import com.wap.trans.entity.tr_1017.ResTrans1017ExchangeDataEntity;
import com.wap.trans.entity.tr_1018.ReqTrans1018Entity;
import com.wap.trans.entity.tr_1018.ResTrans1018CzDataEntity;
import com.wap.trans.entity.tr_1018.ResTrans1018Entity;
import com.wap.trans.entity.tr_1018.ResTrans1018ExchangeDataEntity;
import com.wap.trans.entity.tr_1020.ReqTrans1020Entity;
import com.wap.trans.entity.tr_1020.ResTrans1020BasePartEntity;
import com.wap.trans.entity.tr_1020.ResTrans1020Entity;
import com.wap.trans.service.TransService;
import com.wap.util.TransUtil;

@Service("kfCenterService")
public class KfCenterService {
	
	private static final Log logger = LogFactory.getLog(KfCenterService.class);
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	
	/**
	 * 描述:根据条件查询保单列表
	 * @param policyQueryEntity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮2016年08月05日 下午13:46:43
	 */
	public Map getPolicyList(PolicyQueryEntity policyQueryEntity) throws EpiccException{
		Map map = new HashMap();
		if(null == policyQueryEntity){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空！");
			return map;
		}
		/*调用抽数接口*/
		List<PolicyEntity> list = null;
		ReqTrans1016Entity reqTrans1016Entity = new ReqTrans1016Entity();
		TransUtil.copyObject(policyQueryEntity, reqTrans1016Entity);
		ResTrans1016Entity resTrans1016Entity = transService.executeTrans1016(reqTrans1016Entity);
		if(null == resTrans1016Entity){
			map.put("res", "0");
			map.put("msg", "保单信息查询接口异常！");
			return map;
		}
		if("0".equals(resTrans1016Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1016Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
			//throw new EpiccException("1016接口查询异常！"+resTrans1016Entity.getResuestHeadEntity().getResponse_message());
		}
		List<ResTrans1016PolicyDataEntity> resTrans1016PolicyDataEntities = resTrans1016Entity.getResTrans1016BodyEntity().getResTrans1016PolicyDataEntityList();
		if(resTrans1016PolicyDataEntities != null && resTrans1016PolicyDataEntities.size() > 0){
			list = new ArrayList<PolicyEntity>();
			for (ResTrans1016PolicyDataEntity resTrans1016PolicyDataEntity : resTrans1016PolicyDataEntities) {
				PolicyEntity policyEntity = new PolicyEntity();
				TransUtil.copyObject(resTrans1016PolicyDataEntity,policyEntity);
				list.add(policyEntity);
			}
			map.put("res", "1");
			map.put("msg","成功");
			map.put("policylist", list);
		}
		return map;
	}
	
	/**
	 * 描述:根据条件获取兑换券及充值链接列表
	 * @param exchangeReqEntity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮2016年08月05日 下午13:46:43
	 */
	public Map getExchangeList(ExchangeReqEntity exchangeReqEntity) throws EpiccException{
		Map map = new HashMap();
		if(null == exchangeReqEntity){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空！");
			return map;
		}
		
		ExchangeResEntity exchangeResEntity = new ExchangeResEntity();
		ReqTrans1017Entity reqTrans1017Entity = new ReqTrans1017Entity();
		TransUtil.copyObject(exchangeReqEntity, reqTrans1017Entity);
		
		List<ExchangeReqListEntity> exchangelist = exchangeReqEntity.getExchangeReqList();
		List<CzReqListEntity> czlist = exchangeReqEntity.getCzReqList();
		List<ReqTrans1017ExchangeEntity> reqTrans1017ExchangeEntityList = new ArrayList<ReqTrans1017ExchangeEntity>();
		List<ReqTrans1017CzEntity> reqTrans1017CzEntityList = new ArrayList<ReqTrans1017CzEntity>();
		
		if(null != exchangelist){
			for (ExchangeReqListEntity exchange : exchangelist) {
				ReqTrans1017ExchangeEntity entity = new ReqTrans1017ExchangeEntity();
				TransUtil.copyObject(exchange, entity);
				reqTrans1017ExchangeEntityList.add(entity);
			}
		}
		if(null != czlist){
			for (CzReqListEntity cz : czlist) {
				ReqTrans1017CzEntity entity1 = new ReqTrans1017CzEntity();
				TransUtil.copyObject(cz, entity1);
				reqTrans1017CzEntityList.add(entity1);
			}
		}
		reqTrans1017Entity.setReqTrans1017ExchangeEntityList(reqTrans1017ExchangeEntityList);
		reqTrans1017Entity.setReqTrans1017CzEntityList(reqTrans1017CzEntityList);
		
		ResTrans1017Entity resTrans1017Entity = transService.executeTrans1017(reqTrans1017Entity);
		if(null == resTrans1017Entity){
			map.put("res", "0");
			map.put("msg", "礼品兑换接口异常！");
			return map;
			//throw new EpiccException("1017接口查询异常！");
		}
		if("0".equals(resTrans1017Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1017Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
			//throw new EpiccException("1017接口查询异常！"+resTrans1017Entity.getResuestHeadEntity().getResponse_message());
		}
		List<ResTrans1017ExchangeDataEntity> resTrans1017ExchangeDataEntities = resTrans1017Entity.getResTrans1017BodyEntity().getResTrans1017ExchangeDataEntityList();
		List<ResTrans1017CzDataEntity> resTrans1017CzDataEntities = resTrans1017Entity.getResTrans1017BodyEntity().getResTrans1017CzDataEntityList();
		
		ExchangeResListEntity exchangeResListEntity = null;
		List<ExchangeResListEntity> exchangeResList = new ArrayList<ExchangeResListEntity>();
		CzResListEntity czResListEntity = null;
		List<CzResListEntity> czResList = new ArrayList<CzResListEntity>();
		
		if(resTrans1017ExchangeDataEntities != null){
			for (ResTrans1017ExchangeDataEntity resTrans1017ExchangeDataEntity : resTrans1017ExchangeDataEntities) {
				exchangeResListEntity = new ExchangeResListEntity();
				TransUtil.copyObject(resTrans1017ExchangeDataEntity,exchangeResListEntity);
				exchangeResList.add(exchangeResListEntity);
			}
		}
		if(resTrans1017CzDataEntities != null){
			for (ResTrans1017CzDataEntity resTrans1017CzDataEntity : resTrans1017CzDataEntities) {
				czResListEntity = new CzResListEntity();
				TransUtil.copyObject(resTrans1017CzDataEntity,czResListEntity);
				czResList.add(czResListEntity);
			}
		}
		exchangeResEntity.setExchangeResList(exchangeResList);
		exchangeResEntity.setCzResList(czResList);
		map.put("res", "1");
		map.put("msg","成功");
		map.put("exchangeinfo", exchangeResEntity);
		return map;
	}
	
	/**
	 * 
			* 描述:根据保单号查询兑换券信息及充值卡信息
			* @param exchangeQueryReqEntity
			* @return
			* @throws EpiccException
			* @author 朱久满 2016年8月22日 下午6:18:40
	 */
	public Map getExchangeQryList(ExchangeQueryReqEntity exchangeQueryReqEntity) throws EpiccException{
		Map map = new HashMap();
		if(null == exchangeQueryReqEntity){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空！");
			return map;
		}
		
		ExchangeQueryResEntity exchangeQueryResEntity = new ExchangeQueryResEntity();
		ReqTrans1018Entity reqTrans1018Entity = new ReqTrans1018Entity();
		TransUtil.copyObject(exchangeQueryReqEntity, reqTrans1018Entity);
		
		List<ReqTrans1017ExchangeEntity> reqTrans1017ExchangeEntityList = new ArrayList<ReqTrans1017ExchangeEntity>();
		List<ReqTrans1017CzEntity> reqTrans1017CzEntityList = new ArrayList<ReqTrans1017CzEntity>();
		
		ResTrans1018Entity resTrans1018Entity = transService.executeTrans1018(reqTrans1018Entity);
		if(null == resTrans1018Entity){
			map.put("res", "0");
			map.put("msg", "查询礼品兑换接口异常！");
			return map;
		}
		if("0".equals(resTrans1018Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1018Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		List<ResTrans1018ExchangeDataEntity> resTrans1018ExchangeDataEntities = resTrans1018Entity.getResTrans1018BodyEntity().getResTrans1018ExchangeDataEntityList();
		List<ResTrans1018CzDataEntity> resTrans1018CzDataEntities = resTrans1018Entity.getResTrans1018BodyEntity().getResTrans1018CzDataEntityList();
		
		ExchangeQueryResListEntity exchangeQueryResListEntity = null;
		List<ExchangeQueryResListEntity> exchangeQueryResList = new ArrayList<ExchangeQueryResListEntity>();
		CzQueryResListEntity czQueryResListEntity = null;
		List<CzQueryResListEntity> czQueryResList = new ArrayList<CzQueryResListEntity>();
		
		if(resTrans1018ExchangeDataEntities != null){
			for (ResTrans1018ExchangeDataEntity resTrans1018ExchangeDataEntity : resTrans1018ExchangeDataEntities) {
				exchangeQueryResListEntity = new ExchangeQueryResListEntity();
				TransUtil.copyObject(resTrans1018ExchangeDataEntity,exchangeQueryResListEntity);
				exchangeQueryResList.add(exchangeQueryResListEntity);
			}
		}
		if(resTrans1018CzDataEntities != null){
			for (ResTrans1018CzDataEntity resTrans1018CzDataEntity : resTrans1018CzDataEntities) {
				czQueryResListEntity = new CzQueryResListEntity();
				TransUtil.copyObject(resTrans1018CzDataEntity,czQueryResListEntity);
				czQueryResList.add(czQueryResListEntity);
			}
		}
		exchangeQueryResEntity.setExchangeResList(exchangeQueryResList);
		exchangeQueryResEntity.setCzResList(czQueryResList);
		map.put("res", "1");
		map.put("msg","成功");
		map.put("exchangelistinfo", exchangeQueryResEntity);
		return map;
	}
	
	/**
	 * 描述:根据条件获取兑换券及充值链接列表
	 * 该接口主要实现绑定有礼功能
	 * @param exchangeReqEntity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮2016年08月05日 下午13:46:43
	 */
	public Map getBoundCzcodeList(ExchangeReqEntity exchangeReqEntity) throws EpiccException{
		Map map = new HashMap();
		if(null == exchangeReqEntity){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空！");
			return map;
		}
		
		ExchangeResEntity exchangeResEntity = new ExchangeResEntity();
		ReqTrans1017Entity reqTrans1017Entity = new ReqTrans1017Entity();
		TransUtil.copyObject(exchangeReqEntity, reqTrans1017Entity);
		
		List<ExchangeReqListEntity> exchangelist = exchangeReqEntity.getExchangeReqList();
		List<CzReqListEntity> czlist = exchangeReqEntity.getCzReqList();
		List<ReqTrans1017ExchangeEntity> reqTrans1017ExchangeEntityList = new ArrayList<ReqTrans1017ExchangeEntity>();
		List<ReqTrans1017CzEntity> reqTrans1017CzEntityList = new ArrayList<ReqTrans1017CzEntity>();
		
		if(null != exchangelist){
			for (ExchangeReqListEntity exchange : exchangelist) {
				ReqTrans1017ExchangeEntity entity = new ReqTrans1017ExchangeEntity();
				TransUtil.copyObject(exchange, entity);
				reqTrans1017ExchangeEntityList.add(entity);
			}
		}
		if(null != czlist){
			for (CzReqListEntity cz : czlist) {
				ReqTrans1017CzEntity entity1 = new ReqTrans1017CzEntity();
				TransUtil.copyObject(cz, entity1);
				reqTrans1017CzEntityList.add(entity1);
			}
		}
		reqTrans1017Entity.setReqTrans1017ExchangeEntityList(reqTrans1017ExchangeEntityList);
		reqTrans1017Entity.setReqTrans1017CzEntityList(reqTrans1017CzEntityList);
		//与前面的getExchangeList方法，只修改了下面这句代码
		ResTrans1017Entity resTrans1017Entity = transService.executeTransBoundGetGift(reqTrans1017Entity);
		if(null == resTrans1017Entity){
			map.put("res", "0");
			map.put("msg", "礼品兑换接口异常！");
			return map;
			//throw new EpiccException("1017接口查询异常！");
		}
		if("0".equals(resTrans1017Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1017Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
			//throw new EpiccException("1017接口查询异常！"+resTrans1017Entity.getResuestHeadEntity().getResponse_message());
		}
		List<ResTrans1017ExchangeDataEntity> resTrans1017ExchangeDataEntities = resTrans1017Entity.getResTrans1017BodyEntity().getResTrans1017ExchangeDataEntityList();
		List<ResTrans1017CzDataEntity> resTrans1017CzDataEntities = resTrans1017Entity.getResTrans1017BodyEntity().getResTrans1017CzDataEntityList();
		
		ExchangeResListEntity exchangeResListEntity = null;
		List<ExchangeResListEntity> exchangeResList = new ArrayList<ExchangeResListEntity>();
		CzResListEntity czResListEntity = null;
		List<CzResListEntity> czResList = new ArrayList<CzResListEntity>();
		
		if(resTrans1017ExchangeDataEntities != null){
			for (ResTrans1017ExchangeDataEntity resTrans1017ExchangeDataEntity : resTrans1017ExchangeDataEntities) {
				exchangeResListEntity = new ExchangeResListEntity();
				TransUtil.copyObject(resTrans1017ExchangeDataEntity,exchangeResListEntity);
				exchangeResList.add(exchangeResListEntity);
			}
		}
		if(resTrans1017CzDataEntities != null){
			for (ResTrans1017CzDataEntity resTrans1017CzDataEntity : resTrans1017CzDataEntities) {
				czResListEntity = new CzResListEntity();
				TransUtil.copyObject(resTrans1017CzDataEntity,czResListEntity);
				czResList.add(czResListEntity);
			}
		}
		exchangeResEntity.setExchangeResList(exchangeResList);
		exchangeResEntity.setCzResList(czResList);
		map.put("res", "1");
		map.put("msg","成功");
		map.put("exchangeinfo", exchangeResEntity);
		return map;
	}
	
	/**
	 * 描述:根据条件查询保单列表
	 * @param policyQueryEntity
	 * @return
	 * @throws EpiccException
	 * @author 吕亮2016年08月05日 下午13:46:43
	 */
	public Map getActiveReceiveNum(ActiveReceiveNumQueryEntity activeReceiveNumQueryEntity) throws EpiccException{
		Map map = new HashMap();
		if(null == activeReceiveNumQueryEntity){
			map.put("res", "0");
			map.put("msg", "请求参数不能为空！");
			return map;
		}
		/*调用抽数接口*/
		List<PolicyEntity> list = null;
		ReqTrans1020Entity reqTrans1020Entity = new ReqTrans1020Entity();
		TransUtil.copyObject(activeReceiveNumQueryEntity, reqTrans1020Entity);
		ResTrans1020Entity resTrans1020Entity = transService.executeTrans1020(reqTrans1020Entity);
		if(null == resTrans1020Entity){
			map.put("res", "0");
			map.put("msg", "未查询到有效数据");
			return map;
		}
		if("0".equals(resTrans1020Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1020Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		ResTrans1020BasePartEntity resTrans1020BasePartEntity = resTrans1020Entity.getResTrans1020BodyEntity().getResTrans1020BasePartEntity();
		if(resTrans1020BasePartEntity != null){
			ActiveReceiveNumEntity activeReceiveNumEntity = new ActiveReceiveNumEntity();
			TransUtil.copyObject(resTrans1020BasePartEntity,activeReceiveNumEntity);
			map.put("res", "1");
			map.put("msg","成功");
			map.put("receiveNumEntity", activeReceiveNumEntity);
		}else{
			map.put("res", "0");
			map.put("msg","未查询到有效数据");
		}
		return map;
	}

}
