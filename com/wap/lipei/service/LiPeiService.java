package com.wap.lipei.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.exception.EpiccException;
import com.wap.lipei.entity.CaseInfoEntity;
import com.wap.lipei.entity.CaseQueryEntity;
import com.wap.lipei.entity.DeflossDetailEntity;
import com.wap.lipei.entity.DeflossDetailPageEntity;
import com.wap.lipei.entity.DeflossDetailQueryEntity;
import com.wap.lipei.entity.DeflossEntity;
import com.wap.lipei.entity.MaterialEntity;
import com.wap.lipei.entity.MaterialFileEntity;
import com.wap.lipei.entity.NetWorkQueryEntity;
import com.wap.lipei.entity.PayfeeEntity;
import com.wap.lipei.entity.ReportEntity;
import com.wap.trans.entity.tr_1006.ReqTrans1006Entity;
import com.wap.trans.entity.tr_1006.ResTrans1006Entity;
import com.wap.trans.entity.tr_1006.ResTrans1006ReportDataEntity;
import com.wap.trans.entity.tr_1007.ReqTrans1007Entity;
import com.wap.trans.entity.tr_1007.ResTrans1007DeflossDataEntity;
import com.wap.trans.entity.tr_1007.ResTrans1007Entity;
import com.wap.trans.entity.tr_1007.ResTrans1007PayfeeDataEntity;
import com.wap.trans.entity.tr_1008.ReqTrans1008Entity;
import com.wap.trans.entity.tr_1008.ResTrans1008BasePartEntity;
import com.wap.trans.entity.tr_1008.ResTrans1008ComponentDataEntity;
import com.wap.trans.entity.tr_1008.ResTrans1008Entity;
import com.wap.trans.entity.tr_1009.ReqTrans1009Entity;
import com.wap.trans.entity.tr_1009.ResTrans1009BasePartEntity;
import com.wap.trans.entity.tr_1009.ResTrans1009Entity;
import com.wap.trans.entity.tr_1009.ResTrans1009RepairDataEntity;
import com.wap.trans.entity.tr_1010.ReqTrans1010Entity;
import com.wap.trans.entity.tr_1010.ResTrans1010BasePartEntity;
import com.wap.trans.entity.tr_1010.ResTrans1010Entity;
import com.wap.trans.entity.tr_1010.ResTrans1010MaterialDataEntity;
import com.wap.trans.entity.tr_1011.ReqTrans1011Entity;
import com.wap.trans.entity.tr_1011.ResTrans1011BasePartEntity;
import com.wap.trans.entity.tr_1011.ResTrans1011ComponentDataEntity;
import com.wap.trans.entity.tr_1011.ResTrans1011Entity;
import com.wap.trans.entity.tr_1012.ReqTrans1012Entity;
import com.wap.trans.entity.tr_1012.ReqTrans1012FileEntity;
import com.wap.trans.entity.tr_1012.ResTrans1012Entity;
import com.wap.trans.entity.tr_1012.ResTrans1012FileDataEntity;
import com.wap.trans.entity.tr_1013.ReqTrans1013Entity;
import com.wap.trans.entity.tr_1013.ResTrans1013Entity;
import com.wap.trans.entity.tr_1013.ResTrans1013FileDataEntity;
import com.wap.trans.entity.tr_1014.ReqTrans1014Entity;
import com.wap.trans.entity.tr_1015.ReqTrans1015Entity;
import com.wap.trans.entity.tr_1015.ResTrans1015Entity;
import com.wap.trans.entity.tr_1015.ResTrans1015NetWorkEntity;
import com.wap.trans.service.TransService;
import com.wap.util.TransUtil;

@Service("liPeiService")
public class LiPeiService {
	private static final Log logger = LogFactory.getLog(LiPeiService.class);
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	
	/**
	 * 
			* 描述:根据条件查询案件列表
			* @param caseQueryEntity
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午3:46:43
	 */
	public List<ReportEntity> queryCaseList(CaseQueryEntity caseQueryEntity) throws EpiccException{
		if(null == caseQueryEntity){
			return null;
		}
		/*调用抽数接口*/
		try {
			ReqTrans1014Entity reqTrans1014Entity = new ReqTrans1014Entity();
			TransUtil.copyObject(caseQueryEntity, reqTrans1014Entity);
			transService.executeTrans1014(reqTrans1014Entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("1014同步报案数据失败:"+e.getMessage());
		}
		List<ReportEntity> list = new ArrayList<ReportEntity>();
		ReqTrans1006Entity reqTrans1006Entity = new ReqTrans1006Entity();
		TransUtil.copyObject(caseQueryEntity, reqTrans1006Entity);
		ResTrans1006Entity resTrans1006Entity = transService.executeTrans1006(reqTrans1006Entity);
		if(null == resTrans1006Entity){
			throw new EpiccException("1006接口查询异常！");
		}
		if("0".equals(resTrans1006Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1006接口查询异常！"+resTrans1006Entity.getResuestHeadEntity().getResponse_message());
		}
		List<ResTrans1006ReportDataEntity> resTrans1006ReportDataEntities = resTrans1006Entity.getResTrans1006BodyEntity().getResTrans1006ReportDataEntityList();
		for (ResTrans1006ReportDataEntity resTrans1006ReportDataEntity : resTrans1006ReportDataEntities) {
			ReportEntity reportEntity = new ReportEntity();
			TransUtil.copyObject(resTrans1006ReportDataEntity,reportEntity);
			list.add(reportEntity);
		}
		return list;
	}
	
	/**
	 * 
			* 描述:根据报案号查询案件详情
			* @param registno
			* @return
			* @throws EpiccException
			* @author 朱久满 2015年12月29日 下午4:52:36
	 */
	public CaseInfoEntity queryCaseDetail(String registno) throws EpiccException{
		if(null == registno || "".equals(registno.trim())){
			return null;
		}
		/*调用抽数接口*/
		try {
			ReqTrans1014Entity reqTrans1014Entity = new ReqTrans1014Entity();
			reqTrans1014Entity.setRegistno(registno);
			transService.executeTrans1014(reqTrans1014Entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("1014同步报案数据失败:"+e.getMessage());
		}
		ReqTrans1007Entity reqTrans1007Entity = new ReqTrans1007Entity();
		reqTrans1007Entity.setRegistno(registno);
		ResTrans1007Entity resTrans1007Entity = transService.executeTrans1007(reqTrans1007Entity);
		if(null == resTrans1007Entity){
			throw new EpiccException("1007接口查询异常！");
		}
		if("0".equals(resTrans1007Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1007接口查询异常！"+resTrans1007Entity.getResuestHeadEntity().getResponse_message());
		}
		CaseInfoEntity caseInfoEntity = new CaseInfoEntity();
		TransUtil.copyObject(resTrans1007Entity.getResTrans1007BodyEntity().getResTrans1007ReportInfoEntity(),caseInfoEntity);
		List<PayfeeEntity> payfeeEntities = new ArrayList<PayfeeEntity>();
		List<DeflossEntity> deflossEntities = new ArrayList<DeflossEntity>();
		List<ResTrans1007PayfeeDataEntity> payfeeDataEntities = resTrans1007Entity.getResTrans1007BodyEntity().getResTrans1007PayfeeDataEntityList();
		List<ResTrans1007DeflossDataEntity> deflossDataEntities = resTrans1007Entity.getResTrans1007BodyEntity().getResTrans1007DeflossDataEntityList();
		if(null != payfeeDataEntities){
			for (ResTrans1007PayfeeDataEntity resTrans1007PayfeeDataEntity : payfeeDataEntities) {
				PayfeeEntity payfeeEntity = new PayfeeEntity();
				TransUtil.copyObject(resTrans1007PayfeeDataEntity, payfeeEntity);
				payfeeEntities.add(payfeeEntity);
			}
			caseInfoEntity.setPayfeeList(payfeeEntities);
		}
		if(null != deflossDataEntities){
			for (ResTrans1007DeflossDataEntity resTrans1007DeflossDataEntity : deflossDataEntities) {
				DeflossEntity deflossEntity = new DeflossEntity();
				TransUtil.copyObject(resTrans1007DeflossDataEntity, deflossEntity);
				deflossEntities.add(deflossEntity);
			}
			caseInfoEntity.setDeflossList(deflossEntities);
		}
		return caseInfoEntity;
	}
	
	/**
	 * 
			* 描述:查询定损明细-换件清单列表(分页)
			* @param deflossDetailQueryEntity
			* @return
			* @author 朱久满 2015年12月30日 上午10:37:54
	 * @throws EpiccException 
	 */
	public DeflossDetailPageEntity queryComponentDetailList(DeflossDetailQueryEntity deflossDetailQueryEntity) throws EpiccException{
		if(null == deflossDetailQueryEntity){
			return null;
		}
		DeflossDetailPageEntity deflossDetailPageEntity = new DeflossDetailPageEntity();
		ReqTrans1008Entity reqTrans1008Entity = new ReqTrans1008Entity();
		TransUtil.copyObject(deflossDetailQueryEntity, reqTrans1008Entity);
		ResTrans1008Entity resTrans1008Entity = transService.executeTrans1008(reqTrans1008Entity);
		if(null == resTrans1008Entity){
			throw new EpiccException("1008接口查询异常！");
		}
		if("0".equals(resTrans1008Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1008接口查询异常！"+resTrans1008Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1008BasePartEntity resTrans1008BasePartEntity = resTrans1008Entity.getResTrans1008BodyEntity().getResTrans1008BasePartEntity();
		TransUtil.copyObject(resTrans1008BasePartEntity, deflossDetailPageEntity);
		List<ResTrans1008ComponentDataEntity> dataList = resTrans1008Entity.getResTrans1008BodyEntity().getResTrans1008ComponentDataEntityList();
		List<DeflossDetailEntity> list = new ArrayList<DeflossDetailEntity>();
		if(null != dataList){
			for (ResTrans1008ComponentDataEntity data : dataList) {
				DeflossDetailEntity deflossDetailEntity = new DeflossDetailEntity();
				TransUtil.copyObject(data, deflossDetailEntity);
				list.add(deflossDetailEntity);
			}
		}
		deflossDetailPageEntity.setDeflossDetailList(list);
		return deflossDetailPageEntity;
	}
	/**
	 * 
	 * 描述:查询定损明细-修理费用清单列表(分页)
	 * @param deflossDetailQueryEntity
	 * @return
	 * @author 朱久满 2015年12月30日 上午10:37:54
	 * @throws EpiccException 
	 */
	public DeflossDetailPageEntity queryRepairDetailList(DeflossDetailQueryEntity deflossDetailQueryEntity) throws EpiccException{
		if(null == deflossDetailQueryEntity){
			return null;
		}
		DeflossDetailPageEntity deflossDetailPageEntity = new DeflossDetailPageEntity();
		ReqTrans1009Entity reqTrans1009Entity = new ReqTrans1009Entity();
		TransUtil.copyObject(deflossDetailQueryEntity, reqTrans1009Entity);
		ResTrans1009Entity resTrans1009Entity = transService.executeTrans1009(reqTrans1009Entity);
		if(null == resTrans1009Entity){
			throw new EpiccException("1009接口查询异常！");
		}
		if("0".equals(resTrans1009Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1009接口查询异常！"+resTrans1009Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1009BasePartEntity resTrans1009BasePartEntity = resTrans1009Entity.getResTrans1009BodyEntity().getResTrans1009BasePartEntity();
		TransUtil.copyObject(resTrans1009BasePartEntity, deflossDetailPageEntity);
		List<ResTrans1009RepairDataEntity> dataList = resTrans1009Entity.getResTrans1009BodyEntity().getResTrans1009RepairDataEntityList();
		List<DeflossDetailEntity> list = new ArrayList<DeflossDetailEntity>();
		if(null != dataList){
			for (ResTrans1009RepairDataEntity data : dataList) {
				DeflossDetailEntity deflossDetailEntity = new DeflossDetailEntity();
				TransUtil.copyObject(data, deflossDetailEntity);
				list.add(deflossDetailEntity);
			}
		}
		deflossDetailPageEntity.setDeflossDetailList(list);
		return deflossDetailPageEntity;
	}
	/**
	 * 
	 * 描述:查询定损明细-辅料清单列表(分页)
	 * @param deflossDetailQueryEntity
	 * @return
	 * @author 朱久满 2015年12月30日 上午10:37:54
	 * @throws EpiccException 
	 */
	public DeflossDetailPageEntity queryMaterialDetailList(DeflossDetailQueryEntity deflossDetailQueryEntity) throws EpiccException{
		if(null == deflossDetailQueryEntity){
			return null;
		}
		DeflossDetailPageEntity deflossDetailPageEntity = new DeflossDetailPageEntity();
		ReqTrans1010Entity reqTrans1010Entity = new ReqTrans1010Entity();
		TransUtil.copyObject(deflossDetailQueryEntity, reqTrans1010Entity);
		ResTrans1010Entity resTrans1010Entity = transService.executeTrans1010(reqTrans1010Entity);
		if(null == resTrans1010Entity){
			throw new EpiccException("1010接口查询异常！");
		}
		if("0".equals(resTrans1010Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1010接口查询异常！"+resTrans1010Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1010BasePartEntity resTrans1010BasePartEntity = resTrans1010Entity.getResTrans1010BodyEntity().getResTrans1010BasePartEntity();
		TransUtil.copyObject(resTrans1010BasePartEntity, deflossDetailPageEntity);
		List<ResTrans1010MaterialDataEntity> dataList = resTrans1010Entity.getResTrans1010BodyEntity().getResTrans1010MaterialDataEntityList();
		List<DeflossDetailEntity> list = new ArrayList<DeflossDetailEntity>();
		if(null != dataList){
			for (ResTrans1010MaterialDataEntity data : dataList) {
				DeflossDetailEntity deflossDetailEntity = new DeflossDetailEntity();
				TransUtil.copyObject(data, deflossDetailEntity);
				list.add(deflossDetailEntity);
			}
		}
		deflossDetailPageEntity.setDeflossDetailList(list);
		return deflossDetailPageEntity;
	}
	/**
	 * 
	 * 描述:查询定损明细-待检测零部件清单列表(分页)
	 * @param deflossDetailQueryEntity
	 * @return
	 * @author 朱久满 2015年12月30日 上午10:37:54
	 * @throws EpiccException 
	 */
	public DeflossDetailPageEntity queryCheckDetailList(DeflossDetailQueryEntity deflossDetailQueryEntity) throws EpiccException{
		if(null == deflossDetailQueryEntity){
			return null;
		}
		DeflossDetailPageEntity deflossDetailPageEntity = new DeflossDetailPageEntity();
		ReqTrans1011Entity reqTrans1011Entity = new ReqTrans1011Entity();
		TransUtil.copyObject(deflossDetailQueryEntity, reqTrans1011Entity);
		ResTrans1011Entity resTrans1011Entity = transService.executeTrans1011(reqTrans1011Entity);
		if(null == resTrans1011Entity){
			throw new EpiccException("1011接口查询异常！");
		}
		if("0".equals(resTrans1011Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1011接口查询异常！"+resTrans1011Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1011BasePartEntity resTrans1011BasePartEntity = resTrans1011Entity.getResTrans1011BodyEntity().getResTrans1011BasePartEntity();
		TransUtil.copyObject(resTrans1011BasePartEntity, deflossDetailPageEntity);
		List<ResTrans1011ComponentDataEntity> dataList = resTrans1011Entity.getResTrans1011BodyEntity().getResTrans1011ComponentDataEntityList();
		List<DeflossDetailEntity> list = new ArrayList<DeflossDetailEntity>();
		if(null != dataList){
			for (ResTrans1011ComponentDataEntity data : dataList) {
				DeflossDetailEntity deflossDetailEntity = new DeflossDetailEntity();
				TransUtil.copyObject(data, deflossDetailEntity);
				list.add(deflossDetailEntity);
			}
		}
		deflossDetailPageEntity.setDeflossDetailList(list);
		return deflossDetailPageEntity;
	}
	
	/**
	 * 
			* 描述:上传索赔材料
			* @param materialEntity
			* @return
			* @author 朱久满 2015年12月30日 下午3:23:29
	 * @throws EpiccException 
	 */

	public MaterialEntity uploadMaterial(MaterialEntity materialEntity) throws EpiccException{
		if(null == materialEntity){
			return null;
		}
		ReqTrans1012Entity reqTrans1012Entity = new ReqTrans1012Entity();
		TransUtil.copyObject(materialEntity, reqTrans1012Entity);
		List<ReqTrans1012FileEntity> reqTrans1012FileEntityList = new ArrayList<ReqTrans1012FileEntity>();
		List<MaterialFileEntity> list  = materialEntity.getMaterialFileList();
		if(null != list){
			for (MaterialFileEntity file : list) {
				ReqTrans1012FileEntity entity = new ReqTrans1012FileEntity();
				TransUtil.copyObject(file, entity);
				reqTrans1012FileEntityList.add(entity);
			}
		}
		reqTrans1012Entity.setReqTrans1012FileEntityList(reqTrans1012FileEntityList);
		ResTrans1012Entity resTrans1012Entity = transService.executeTrans1012(reqTrans1012Entity);
		if(null == resTrans1012Entity){
			throw new EpiccException("1012接口异常！");
		}
		if("0".equals(resTrans1012Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1012接口异常！"+resTrans1012Entity.getResuestHeadEntity().getResponse_message());
		}
		materialEntity = new MaterialEntity();
		TransUtil.copyObject(resTrans1012Entity.getResTrans1012BodyEntity().getResTrans1012BasePartEntity(), materialEntity);
		List<MaterialFileEntity> fileList = new ArrayList<MaterialFileEntity>();
		List<ResTrans1012FileDataEntity> fileDataEntities = resTrans1012Entity.getResTrans1012BodyEntity().getResTrans1012FileDataEntityList();
		if(null != fileDataEntities){
			for (ResTrans1012FileDataEntity data : fileDataEntities) {
				MaterialFileEntity fileDate = new MaterialFileEntity();
				TransUtil.copyObject(data, fileDate);
				fileList.add(fileDate);
			}
		}
		return materialEntity;
	}
	
	/**
	 * 
	 * 描述:查询索赔材料
	 * @param materialEntity
	 * @return
	 * @author 朱久满 2015年12月30日 下午3:23:29
	 * @throws EpiccException 
	 */
	public MaterialEntity queryMaterial(String registno) throws EpiccException{
		if(null == registno){
			return null;
		}
		ReqTrans1013Entity reqTrans1013Entity = new ReqTrans1013Entity();
		reqTrans1013Entity.setRegistno(registno);
		ResTrans1013Entity resTrans1013Entity = transService.executeTrans1013(reqTrans1013Entity);
		if(null == resTrans1013Entity){
			throw new EpiccException("1013接口查询异常！");
		}
		if("0".equals(resTrans1013Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1013接口查询异常！"+resTrans1013Entity.getResuestHeadEntity().getResponse_message());
		}
		MaterialEntity materialEntity = new MaterialEntity();
		TransUtil.copyObject(resTrans1013Entity.getResTrans1013BodyEntity().getResTrans1013BasePartEntity(), materialEntity);
		List<MaterialFileEntity> fileList = new ArrayList<MaterialFileEntity>();
		List<ResTrans1013FileDataEntity> fileDataEntities = resTrans1013Entity.getResTrans1013BodyEntity().getResTrans1013FileDataEntityList();
		if(null != fileDataEntities){
			for (ResTrans1013FileDataEntity data : fileDataEntities) {
				MaterialFileEntity fileDate = new MaterialFileEntity();
				TransUtil.copyObject(data, fileDate);
				fileList.add(fileDate);
			}
		}
		materialEntity.setMaterialFileList(fileList);
		return materialEntity;
	}
	
	/**
	 * 
			* 描述:服务网点查询
			* @param registno
			* @return
			* @throws EpiccException
			* @author 朱久满 2016年3月22日 下午2:48:22
	 */
	public List<ResTrans1015NetWorkEntity> queryNetWorkList(NetWorkQueryEntity netWorkQueryEntity) throws EpiccException{
		if(null == netWorkQueryEntity){
			return null;
		}
		ReqTrans1015Entity reqTrans1015Entity = new ReqTrans1015Entity();
		TransUtil.copyObject(netWorkQueryEntity,reqTrans1015Entity);
		ResTrans1015Entity resTrans1015Entity = transService.executeTrans1015(reqTrans1015Entity);
		if(null == resTrans1015Entity){
			throw new EpiccException("1015接口查询异常！");
		}
		if("0".equals(resTrans1015Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1015接口查询异常！"+resTrans1015Entity.getResuestHeadEntity().getResponse_message());
		}
		return resTrans1015Entity.getResTrans1015BodyEntity().getResTrans1015NetWorkEntityList();
	}
	
}
