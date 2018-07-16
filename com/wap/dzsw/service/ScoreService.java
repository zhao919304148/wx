package com.wap.dzsw.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.ScoreConsume;
import com.wap.trans.entity.tr_1028.ReqTrans1028Entity;
import com.wap.trans.entity.tr_1028.ResTrans1028Entity;
import com.wap.trans.entity.tr_1029.ReqTrans1029Entity;
import com.wap.trans.entity.tr_1029.ResTrans1029Entity;
import com.wap.trans.entity.tr_1030.ReqTrans1030Entity;
import com.wap.trans.entity.tr_1030.ResTrans1030Entity;
import com.wap.trans.service.TransService;
import com.wap.util.AESCode;
import com.wap.util.DateUtils;
import com.wap.util.RSACode;
import com.wap.util.RandomUtil;
import com.wap.util.TransUtil;
import com.wap.util.WxCustomerMessageUtils;
import com.wx.util.WeixinUtil;

import core.db.dao.IBaseService;
import net.sf.json.JSONObject;

@Service("scoreService")
public class ScoreService {
	private static final Log logger = LogFactory.getLog(ScoreService.class);


	@Autowired(required = false)
	private TransService transService = null;

	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	@Autowired(required = false)
	private IBaseService baseService = null;
	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	public JSONObject queryBalanceById(String userId, String requestTime) throws EpiccException {
		ReqTrans1028Entity trans1028Entity = new ReqTrans1028Entity();
		trans1028Entity.setRequestTime(requestTime);
		trans1028Entity.setUserId(userId);
		ResTrans1028Entity executeTrans1028 = transService.executeTrans1028(trans1028Entity);
		JSONObject jsonObject = new JSONObject();
		if("1".equals(executeTrans1028.getResHeadEntity().getResponse_code())){
			jsonObject.put("scoreBalance", executeTrans1028.getResTrans1028BodyEntity().getResTrans1028BasePartEntity().getScoreBalance());
		}else{
			jsonObject.put("scoreBalance","");
		}
		jsonObject.put("msg", executeTrans1028.getResHeadEntity().getResponse_message());
		jsonObject.put("resCode", executeTrans1028.getResHeadEntity().getResponse_code());
		return jsonObject;
	}
	/**
	 * 
			* 描述:积分消费
			* @param scoreConsume
			* @return
			* @author qex 2017年1月19日 下午5:21:21
	 * @throws EpiccException 
	 */
	public JSONObject scoreConsume(ScoreConsume scoreConsume) throws EpiccException {
		//创建请求实体
		ReqTrans1029Entity reqTrans1029Entity = new ReqTrans1029Entity();
		reqTrans1029Entity.setAmount(scoreConsume.getAmount());
		String openId = scoreConsume.getOpenId();
		String userId = scoreConsume.getUserId();
		reqTrans1029Entity.setOpenId(openId);
		reqTrans1029Entity.setUserId(userId);
		reqTrans1029Entity.setOrderList(scoreConsume.getOrderList());
		System.out.println("executeTrans1029="+scoreConsume.toString());
		ResTrans1029Entity executeTrans1029 = transService.executeTrans1029(reqTrans1029Entity);
		//成功就推送微信信息
		if("1".equals(executeTrans1029.getResHeadEntity().getResponse_code().trim())){
			try{
				//微信消息推送,失败不影响流程
				JSONObject msg = new JSONObject();
				JSONObject content = new JSONObject();
				String jfUrl=generateJfStoreUrl(userId,openId,"myorder_1");
				String wxsms = WxCustomerMessageUtils.template(WxCustomerMessageUtils.TYPE_JF_CREATE_ORDER,jfUrl);
				content.put("content", wxsms);
				msg.put("touser", openId);
				msg.put("msgtype","text");
				msg.put("text", content.toString());
				System.out.println("sendCustomMessage:"+msg.toString());
				WeixinUtil.sendCustomMessage(msg.toString());
			}catch(Exception ex){
				ex.printStackTrace();
				logger.info("微信消息推送失败", ex);
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("msg", executeTrans1029.getResHeadEntity().getResponse_message());
		jsonObject.put("resCode", executeTrans1029.getResHeadEntity().getResponse_code());
		return jsonObject;
	}
	
	/**
	 * 
			* 描述:生成积分商城请求Url
			* @param userId
			* 			未加密的userId
			* @param openId
			* @return
			* @author 许宝众2017年5月23日 上午11:29:41
	 */
	private static String generateJfStoreUrl(String userId,String openId,String type){
		StringBuffer sb=new StringBuffer();
		sb.append("http://bjpicc.mocredit.cn/test/ssologin.ashx?type="+type);
		try{
			if("myorder_1".equals(type)||"myorder_4".equals(type)){
				userId=AESCode.Encrypt(userId, AESCode.AESKEY);
				JSONObject jsonObject = new JSONObject();
				JSONObject data = new JSONObject();
				data.put("userId", userId);
				data.put("openId", openId);
				data.put("dateTime",DateUtils.getYMDHMS());
				String key = RandomUtil.getRandomString(6);
				logger.info("data:"+data.toString()+",key:"+key);
				String dataStr = AESCode.Encrypt(data.toString(), key);
				String keyStr =Base64.encode(RSACode.encrypt(key));
				jsonObject.put("key", keyStr);
				jsonObject.put("data", dataStr);
				sb.append("&key="+jsonObject.getString("key"));
				sb.append("&data="+jsonObject.getString("data"));
			}
			return sb.toString().replaceAll("\\r+", "").replaceAll("\\n+", "");
		}catch(Exception ex){
			logger.info("积分商城登录链接生成失败 userid:"+userId+",opendId:"+openId);
			throw new RuntimeException(ex);
		}
		
	}
	public static void main(String[] args) throws Exception {
//		StringBuffer stringBuffer = new StringBuffer("http://bjpicc.mocredit.cn/test/ssologin.ashx?type=myorder_1&userId=");
//		stringBuffer.append(AESCode.Encrypt("70c89c243c4a781a11b3e1e4c888c4a4",AESCode.AESKEY)).
//		stringBuffer.append("70c89c243c4a781a11b3e1e4c888c4a4").
//		append("&openId=").
//		append("osX-mwB4Dj4l0Fs80O5ew92mVRsE");
//		System.out.println(stringBuffer.toString());
//		String url=generateJfStoreUrl("OQGz5/VPJRQp8XStyyGTmxpJ5RG1bhsd6jdmLUBSA9rqvMtuVnPrxS1w0CjrZl5C","osX-mwB4Dj4l0Fs80O5ew92mVRsE");
//		System.out.println(url);
	}
	/***
	 * 
			* 描述:积分撤销
			* @param orderNo
			* @return
			* @author qex 2017年1月19日 下午5:35:57
	 * @param requestTime 
	 * @throws EpiccException 
	 */
	public JSONObject scoreCancel(String orderNo, String requestTime, String userId, String openId) throws EpiccException {
		ReqTrans1030Entity reqTrans1030Entity = new ReqTrans1030Entity();
		reqTrans1030Entity.setOrderNo(orderNo);
		reqTrans1030Entity.setRequestTime(requestTime);
		ResTrans1030Entity executeTrans1030 = transService.executeTrans1030(reqTrans1030Entity);
		JSONObject jsonObject = new JSONObject();
		//成功就推送微信信息
		if("1".equals(executeTrans1030.getResHeadEntity().getResponse_code().trim())){
			try{
				//微信消息推送,失败不影响流程
				JSONObject msg = new JSONObject();
				JSONObject content = new JSONObject();
				String jfUrl=generateJfStoreUrl(userId,openId,"myorder_4");
				String wxsms = WxCustomerMessageUtils.template(WxCustomerMessageUtils.TYPE_JF_CANCEL_ORDER,jfUrl);
				content.put("content", wxsms);
				msg.put("touser", openId);
				msg.put("msgtype","text");
				msg.put("text", content.toString());
				System.out.println("sendCustomMessage:"+msg.toString());
				WeixinUtil.sendCustomMessage(msg.toString());
			}catch(Exception ex){
				ex.printStackTrace();
				logger.info("微信消息推送失败", ex);
			}
		}
		jsonObject.put("msg", executeTrans1030.getResHeadEntity().getResponse_message());
		jsonObject.put("resCode", executeTrans1030.getResHeadEntity().getResponse_code());
		return jsonObject;
	}
	
	
	
}
