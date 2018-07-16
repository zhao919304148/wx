package com.wap.post.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sys.exception.EpiccException;
import com.wap.main.entity.UserEntity;
import com.wap.post.entity.CarPhotoEntity;
import com.wap.post.entity.PostOrderEntity;
import com.wap.post.entity.PostQueryEntity;
import com.wap.trans.entity.tr_1002.ReqTrans1002Entity;
import com.wap.trans.entity.tr_1002.ResTrans1002BasePartEntity;
import com.wap.trans.entity.tr_1002.ResTrans1002Entity;
import com.wap.trans.entity.tr_1002.ResTrans1002OrderDataEntity;
import com.wap.trans.entity.tr_1003.ReqTrans1003Entity;
import com.wap.trans.entity.tr_1003.ResTrans1003Entity;
import com.wap.trans.entity.tr_1003.ResTrans1003FileDataEntity;
import com.wap.trans.entity.tr_1003.ResTrans1003OrderInfoEntity;
import com.wap.trans.entity.tr_1004.ReqTrans1004Entity;
import com.wap.trans.entity.tr_1004.ResTrans1004Entity;
import com.wap.trans.entity.tr_1004.ResTrans1004OrderInfoEntity;
import com.wap.trans.entity.tr_1005.ReqTrans1005Entity;
import com.wap.trans.entity.tr_1005.ReqTrans1005FileEntity;
import com.wap.trans.entity.tr_1005.ResTrans1005Entity;
import com.wap.trans.service.TransService;
import com.wap.util.TransUtil;

@Service("postService")
public class PostService {
	@Autowired(required = false)
	private TransService transService = null;
	
	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	
	/**
	 * 
			* 描述:
			* @param postQueryEntity 查询条件
			* @return postOrderEntityList  查询列表
			* @throws EpiccException
			* @author ZN 2015-11-23 下午3:51:42
	 */
	public Map<String,Object> queryOrderList(PostQueryEntity postQueryEntity) throws EpiccException{
		if(null == postQueryEntity){
			return null;
		}
		ReqTrans1002Entity reqTrans1002Entity = new ReqTrans1002Entity();
		TransUtil.copyObject(postQueryEntity, reqTrans1002Entity);
		ResTrans1002Entity resTrans1002Entity = transService.executeTrans1002(reqTrans1002Entity);
		if(null == resTrans1002Entity){
			throw new EpiccException("1002接口查询异常！");
		}
		if("0".equals(resTrans1002Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1002接口查询异常！"+resTrans1002Entity.getResuestHeadEntity().getResponse_message());
		}
		List<ResTrans1002OrderDataEntity> resTrans1002Entities =  resTrans1002Entity.getResTrans1002BodyEntity().getResTrans1002OrderDataEntityList();
		ResTrans1002BasePartEntity resTrans1002BasePartEntity = resTrans1002Entity.getResTrans1002BodyEntity().getResTrans1002BasePartEntity();
		
		List<PostOrderEntity> postOrderEntityList = new ArrayList<PostOrderEntity>();
		for (ResTrans1002OrderDataEntity resTrans1002OrderDataEntity : resTrans1002Entities) {
			PostOrderEntity postOrderEntity = new PostOrderEntity();
			TransUtil.copyObject(resTrans1002OrderDataEntity, postOrderEntity);
			postOrderEntityList.add(postOrderEntity);
		}	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("orderList",postOrderEntityList);
		map.put("pageno", resTrans1002BasePartEntity.getPageno());
		map.put("pagecount", resTrans1002BasePartEntity.getPagecount());
		map.put("queryflag", resTrans1002BasePartEntity.getQueryflag());
		map.put("totalcount", resTrans1002BasePartEntity.getTotalcount());
		return map;
		
		
	}
	
	/**
	 * 
		* 描述:
		* @param postQueryEntity 查询条件
		* @return PostOrderEntity 配送订单详情
		* @throws EpiccException
		* @author ZJM
	 */
	public PostOrderEntity queryOrderInfo(PostQueryEntity postQueryEntity) throws EpiccException{
		if(null == postQueryEntity){
			return null;
		}
		String orderno = postQueryEntity.getOrderno();
		String barcode = postQueryEntity.getBarcode();
		String queryflag = postQueryEntity.getQueryflag();
		if((null == orderno || "".equals(orderno.trim())) 
				&& (null == barcode || "".equals(barcode.trim()))){
			return null;
		}
		if(null == queryflag){
			return null;
		}
		ReqTrans1003Entity reqTrans1003Entity = new ReqTrans1003Entity();
		TransUtil.copyObject(postQueryEntity, reqTrans1003Entity);
		ResTrans1003Entity resTrans1003Entity = transService.executeTrans1003(reqTrans1003Entity);
		if(null == resTrans1003Entity){
			throw new EpiccException("1003接口查询异常！");
		}
		if("0".equals(resTrans1003Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1003接口查询异常！"+resTrans1003Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1003OrderInfoEntity resTrans1003OrderInfoEntity = resTrans1003Entity.getResTrans1003BodyEntity().getResTrans1003OrderInfoEntity();
		PostOrderEntity postOrderEntity = new PostOrderEntity();
		TransUtil.copyObject(resTrans1003OrderInfoEntity, postOrderEntity);
		List<ResTrans1003FileDataEntity> list = resTrans1003Entity.getResTrans1003BodyEntity().getResTrans1003FileDataEntityList();
		List<CarPhotoEntity> photoList = new ArrayList<CarPhotoEntity>();
		if(null != list){
			for (ResTrans1003FileDataEntity fileData : list) {
				CarPhotoEntity photo = new CarPhotoEntity();
				TransUtil.copyObject(fileData, photo);
				photoList.add(photo);
			}
		}
		postOrderEntity.setCarphotos(photoList);
		return postOrderEntity;
	}
	
	/**
	 * 
			* 描述:
			* @param postOrderEntity 修改订单
			* @return
			* @throws EpiccException
			* @author ZN 2015-11-23 下午4:11:48
	 */
	public PostOrderEntity modifyPostInfo(PostOrderEntity postOrderEntity,UserEntity user) throws EpiccException{
		PostOrderEntity orderEntity = new PostOrderEntity();
		if(null == postOrderEntity || null == user){
			return null;
		}
		ReqTrans1004Entity reqTrans1004Entity = new ReqTrans1004Entity();
		TransUtil.copyObject(postOrderEntity, reqTrans1004Entity);
		reqTrans1004Entity.setUsercode(user.getUsercode());
		reqTrans1004Entity.setUsername(user.getUsername());
		reqTrans1004Entity.setComcode(user.getComname());
		ResTrans1004Entity resTrans1004Entity = transService.executeTrans1004(reqTrans1004Entity);
		if(null == resTrans1004Entity){
			throw new EpiccException("1004接口查询异常！");
		}
		if("0".equals(resTrans1004Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException("1004接口查询异常！"+resTrans1004Entity.getResuestHeadEntity().getResponse_message());
		}
		String response_code = resTrans1004Entity.getResuestHeadEntity().getResponse_code();
		if(!"1".equals(response_code)){
			throw new EpiccException(resTrans1004Entity.getResuestHeadEntity().getResponse_message());
		}
		ResTrans1004OrderInfoEntity resTrans1004OrderInfoEntity = resTrans1004Entity.getResTrans1004BodyEntity().getResTrans1004OrderInfoEntity();
		TransUtil.copyObject(resTrans1004OrderInfoEntity, orderEntity);
		return orderEntity;
		
	}
	/**
	 * 
			* 描述: 保存验车照片
			* @param files 图片文件
			* @param orderno 订单号
			* @param usercode 操作员工号
			* @return
			* @author ZJM 2015年11月25日 下午4:33:18
	 * @throws EpiccException 
	 */
	public boolean saveCarPhotos(List<CarPhotoEntity> files,PostOrderEntity order,UserEntity user) throws EpiccException{
		ReqTrans1005Entity reqTrans1005Entity = new ReqTrans1005Entity();
		List<ReqTrans1005FileEntity> reqTrans1005FileEntityList = new ArrayList<ReqTrans1005FileEntity>();
		for (CarPhotoEntity carPhotoEntity : files) {
			ReqTrans1005FileEntity reqTrans1005FileEntity = new ReqTrans1005FileEntity();
				TransUtil.copyObject(carPhotoEntity, reqTrans1005FileEntity);
				reqTrans1005FileEntityList.add(reqTrans1005FileEntity);
		}
		reqTrans1005Entity.setOrderno(order.getOrderno());
		reqTrans1005Entity.setUsercode(user.getUsercode());
		reqTrans1005Entity.setComcode(user.getComcode());
		reqTrans1005Entity.setIscarhurt(order.getIscarhurt());
		reqTrans1005Entity.setReqTrans1005FileEntityList(reqTrans1005FileEntityList);
		ResTrans1005Entity resTrans1005Entity = transService.executeTrans1005(reqTrans1005Entity);
		if(null == resTrans1005Entity){
			throw new EpiccException("1005接口处理异常！");
		}
		if("0".equals(resTrans1005Entity.getResuestHeadEntity().getResponse_code().trim())){
			throw new EpiccException(resTrans1005Entity.getResuestHeadEntity().getResponse_message());
		}
		return true;
	}
}
