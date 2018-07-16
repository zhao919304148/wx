package com.wap.dzsw.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.informix.util.dateUtil;
import com.sun.org.apache.regexp.internal.recompile;
import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.ActivityCzj2017Gift;
import com.wap.dzsw.entity.ActivityGiftCardEntity;
import com.wap.dzsw.entity.ActivityGiftCardQueryEntity;
import com.wap.dzsw.entity.Czj2017GiftItemVo;
import com.wap.dzsw.entity.Czj2017GiftVo;
import com.wap.dzsw.entity.GiftCardEntity;
import com.wap.dzsw.entity.LimitTimeRobGetHis;
import com.wap.dzsw.entity.Page;
import com.wap.dzsw.entity.PanicBuyingRequest;
import com.wap.dzsw.entity.PerdayGetHis;
import com.wap.dzsw.entity.WashCarRecord;
import com.wap.trans.entity.tr_1021.ReqTrans1021Entity;
import com.wap.trans.entity.tr_1021.ResTrans1021BasePartEntity;
import com.wap.trans.entity.tr_1021.ResTrans1021CardDataEntity;
import com.wap.trans.entity.tr_1021.ResTrans1021Entity;
import com.wap.trans.entity.tr_1021.ResTrans1021Score;
import com.wap.trans.entity.tr_1044.ReqTrans1044Entity;
import com.wap.trans.entity.tr_1044.ResTrans1044Entity;
import com.wap.trans.entity.tr_1044.ResTrans1044RecordDataEntity;
import com.wap.trans.entity.tr_1046.ReqTrans1046Entity;
import com.wap.trans.entity.tr_1046.ResTrans1046BasePartEntity;
import com.wap.trans.entity.tr_1046.ResTrans1046CardDataEntity;
import com.wap.trans.entity.tr_1046.ResTrans1046Entity;
import com.wap.trans.service.TransService;
import com.wap.util.CommonUtils;
import com.wap.util.ConstantUtils;
import com.wap.util.DateUtils;
import com.wap.util.TransUtil;

@Service("activitieService")
public class ActivityService {
	@Autowired
	private SessionFactory sessionFactory;;
	@Autowired
	private JedisPool jedisPool;
	/**抢券活动队列**/
	@Autowired@Qualifier("limitTimeRobAmqpTemplate")
	private RabbitTemplate limitTimeRobAmqpTemplate;
	/**每日领取活动队列**/
	@Autowired@Qualifier("perdayGetAmqpTemplate")
	private RabbitTemplate perdayGetRobAmqpTemplate;
	/**本service层缓存Map**/
	private Map<String,Object> cacheMap = new HashMap<String,Object>();
	
	@Autowired(required = false)
	private TransService transService = null;

	public void setTransService(TransService transService) {
		this.transService = transService;
	}
	
	/**
	 * 
			* 描述:
			* 根据日期和礼品类型得到 redis保存的礼品list的key
			* @param date
			* @param cardtype
			* @return
			* @author 许宝众 2017年9月20日 下午4:35:51
	 */
	private String getRobGiftSetKey(Date date,String cardtype){
		return getRobGiftListKey(DateUtils.format(date, "yyyy-MM-dd"),cardtype);
	}
	/**
	 * 
			* 描述:
			* 根据日期和礼品类型得到 redis保存的礼品list的key
			* @param date
			* 			日期格式：yyyy=MM-dd
			* @param cardtype
			* @return
			* @author 许宝众 2017年9月20日 下午4:35:51
	 */
	private String getRobGiftListKey(String date,String cardtype){
		return ConstantUtils.LimitTimeRobRabbitmqConstants.ACTIVITIES_2017_CZJ_ROB_GIFT_SET_PREFFIX+date+"-"+cardtype;
	}
	/**
	 * 
			* 描述:
			* 每日领取活动礼品 redis list key
			* @param cardtype
			* @return
			* @author  2017年9月20日 下午4:49:51
	 */
	private String getPerdayGiftSetKey(String cardtype){
		return ConstantUtils.PerdayGetRabbitmqConstants.ACTIVITIES_2017_CZJ_PERDAY_GIFT_SET_PREFFIX+cardtype;
	}
	/**
	 * 
			* 描述:
			* 限时抢购礼品券
			* @param panicBuyingRequest
			* @return
			* @author 2017年9月13日 下午1:03:44
	 */
	public JSONObject getLimitTimeRobGift(PanicBuyingRequest panicBuyingRequest) {
		JSONObject resJson = null;
		String cardtype = panicBuyingRequest.getCardtype();
		Date now = new Date();
		if(isValidRobGiftType(cardtype, now)){
			//判断当前是否有库存(redis list pop一个卡号)，不空则有库存，否则无库存
			Jedis jedis = null;
			String cardno = null;
			boolean isCardNoRecycle = false;//是否需要回收卡号
			boolean isRecordUserRobSatusKey = false;//是否记录用户抢购标识key
			String openid = panicBuyingRequest.getOpenid();
			String username = panicBuyingRequest.getUsername();
			String cardNoSetKey = this.getRobGiftSetKey(now, cardtype);
			String giftStatusKey = ConstantUtils.LimitTimeRobRabbitmqConstants.ACTIVITIES_2017_CZJ_LIMITE_TIME_PARTICIPANT+DateUtils.format(now, "yyyy-MM-dd")+"-"+openid;
			LimitTimeRobGetHis limitTimeRobGetHis = null;
			try{
				jedis = jedisPool.getResource();
				cardno = jedis.spop(cardNoSetKey);
				if(cardno!=null){
					//先查redis有没有这用户今天的
					String giftStatus = jedis.get(giftStatusKey);
					if(giftStatus!=null){
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "-3");
						resJson.put("errMsg", "每天只允许抢购一张礼券");
						//回收卡号
						isCardNoRecycle = true;
						return resJson;
					}
					//尝试保存，数据库约束判断：是否今天已经抢到过券
					try {
						Date today = DateUtils.getNowDate(now, "yyyy-MM-dd");
						//限制不能多次抢购(使用前端数据库唯一约束)
						limitTimeRobGetHis = new LimitTimeRobGetHis();
						limitTimeRobGetHis.setGetdate(today);
						limitTimeRobGetHis.setOpenid(openid);
						limitTimeRobGetHis.setCardtype(cardtype);
						limitTimeRobGetHis.setCardno(cardno);
						limitTimeRobGetHis.setInserttimeforhis(now);
						this.saveLimitTimeRobHis(limitTimeRobGetHis);
					} catch (Exception e) {
						e.printStackTrace();
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "-3");
						resJson.put("errMsg", "每天只允许抢购一张礼券");
						//回收卡号
						isCardNoRecycle = true;
						return resJson;
					}
					try{
						//入队列
						com.alibaba.fastjson.JSONObject queueJson = new com.alibaba.fastjson.JSONObject();
						//标识操作类型：limit_time_rob-->限时抢购
						queueJson.put("operatetype", "limit-time-rob");
						queueJson.put("username", username);
						queueJson.put("openid", openid);
						queueJson.put("cardtype", cardtype);
						queueJson.put("cardno", cardno);
						queueJson.put("in_queue_time",DateUtils.format(now, "yyyy-MM-dd HH:mm:ss.SSS"));
						//入队列逻辑
						limitTimeRobAmqpTemplate.convertAndSend(ConstantUtils.LimitTimeRobRabbitmqConstants.TEMPLATE_DEFAULT_ROUTE_KEY,queueJson,new CorrelationData(limitTimeRobGetHis.getId().toString()));
						//返回成功消息
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "1");
						//查询当前库存
						Long remain = jedis.scard(cardNoSetKey);
						com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
						//用户可以通过此key查询抢购礼品的当前处理状态
						dataJson.put("remain", remain);
						resJson.put("data", dataJson);
					}catch(Exception e){
						e.printStackTrace();
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "-4");
						resJson.put("errMsg", "抢券成功【"+cardno+"】，推送队列失败，请联系客服人员");
						return resJson;
					}finally{
						isRecordUserRobSatusKey = true;//redis中需记录用户抢券标识
					}
				}else{//卡号为空，说明礼券已经抢完了
					resJson = new com.alibaba.fastjson.JSONObject();
					resJson.put("resCode", "-5");
					resJson.put("errMsg", "无库存");
				}	
			}catch(Exception e){
				resJson = new com.alibaba.fastjson.JSONObject();
				resJson.put("resCode", "-99");
				resJson.put("errMsg", "服务异常，请稍后重试");
			}finally{
				if(isRecordUserRobSatusKey){//redis记录用户抢购抢券，防止一天内同一个用户多次抢券，减少数据库操作
					jedis.set(giftStatusKey, "0");//0 等待处理
					//设置key过期时间：明天凌晨
					Calendar tomorrowCal = Calendar.getInstance();
					tomorrowCal.add(Calendar.DATE, 1);
					tomorrowCal.set(Calendar.HOUR_OF_DAY, 0);
					tomorrowCal.set(Calendar.MINUTE, 0);
					tomorrowCal.set(Calendar.SECOND, 0);
					tomorrowCal.set(Calendar.MILLISECOND, 0);
					long unixTime = tomorrowCal.getTimeInMillis()/1000;
					jedis.expireAt(giftStatusKey, unixTime);
				}
				//卡号回收且卡号不为空
				if(isCardNoRecycle&&org.apache.commons.lang.StringUtils.isNotBlank(cardno)){
					jedis.sadd(cardNoSetKey, cardno);
				}
				if(jedis!=null){
					jedis.close();
				}
			}
		}else{
			resJson = new com.alibaba.fastjson.JSONObject();
			resJson.put("resCode", "-6");
			resJson.put("errMsg", "礼品不在活动时间");
		}
		return resJson;
	}
	
	/**
	 * 
			* 描述:
			* 保存抢券成功历史轨迹 
			* @param limitTimeRobGetHis
			* @throws Exception
			* @author 许宝众 2017年9月19日 下午2:02:37
	 */
	private void saveLimitTimeRobHis(LimitTimeRobGetHis limitTimeRobGetHis) throws Exception{
		//开启新事务 保存轨迹
		Session newSession = sessionFactory.openSession();
		Transaction tx = newSession.beginTransaction();
		try{
			tx.begin();
			newSession.save(limitTimeRobGetHis);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw e;
		}finally{
			newSession.close();
		}
	}
	
	/**
	 * 
			* 描述:
			* 保存每日领取活动礼品轨迹
			* @param perdayGetHis
			* @throws Exception
			* @author 许宝众2017年9月19日 下午2:31:17
	 */
	private void savePerdayGetHis(PerdayGetHis perdayGetHis) throws Exception{
		//开启新事务 保存轨迹
		Session newSession = sessionFactory.openSession();
		Transaction tx = newSession.beginTransaction();
		try{
			tx.begin();
			newSession.save(perdayGetHis);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw e;
		}finally{
			newSession.close();
		}
	}
	/**
	 * 
			* 描述:
			* 		2017车主节 活动期间每日领取礼品处理
			* @param panicBuyingRequest
			* @return
			* @author 许宝众 2017年9月19日 下午2:11:14
	 */
	public JSONObject getPerdayGift(PanicBuyingRequest panicBuyingRequest) {
		JSONObject resJson = null;
		String cardtype = panicBuyingRequest.getCardtype();
		if(isValidPerdayGiftType(cardtype)){
			//判断当前是否有库存(redis list pop一个卡号)，不空则有库存，否则无库存
			Jedis jedis = null;
			String cardno = null;
			boolean isCardNoRecycle = false;//是否需要回收卡号
			String openid = panicBuyingRequest.getOpenid();
			String username = panicBuyingRequest.getUsername();
			String cardNoSetKey = this.getPerdayGiftSetKey(cardtype);
			PerdayGetHis perdayGetHis = null;
			try{
				jedis = jedisPool.getResource();
				cardno = jedis.spop(cardNoSetKey);
				if(cardno!=null){
					//尝试保存，数据库约束判断：每个用户不能重复领取一种礼品
					try {
						Date now = new Date();
						perdayGetHis = new PerdayGetHis();
						perdayGetHis.setGetdate(now);
						perdayGetHis.setOpenid(openid);
						perdayGetHis.setCardtype(cardtype);
						perdayGetHis.setCardno(cardno);
						perdayGetHis.setInserttimeforhis(now);
						this.savePerdayGetHis(perdayGetHis );
					} catch (Exception e) {
						e.printStackTrace();
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "-3");
						resJson.put("errMsg", "每人最多领取一个同种类型的礼品");
						//回收卡号
						isCardNoRecycle = true;
						return resJson;
					}
					try{
						//入队列
						com.alibaba.fastjson.JSONObject queueJson = new com.alibaba.fastjson.JSONObject();
						//标识操作类型：perday_get-->每日领取
						queueJson.put("operatetype", "perday-get");
						queueJson.put("username", username);
						queueJson.put("openid", openid);
						queueJson.put("cardtype", cardtype);
						queueJson.put("cardno", cardno);
						queueJson.put("in_queue_time",DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss.SSS"));
						//入队列逻辑
						perdayGetRobAmqpTemplate.convertAndSend(ConstantUtils.PerdayGetRabbitmqConstants.TEMPLATE_DEFAULT_ROUTE_KEY,queueJson,new CorrelationData(perdayGetHis.getId().toString()));
						//返回成功消息
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "1");
						//查询当前库存
						Long remain = jedis.scard(cardNoSetKey);
						com.alibaba.fastjson.JSONObject dataJson = new com.alibaba.fastjson.JSONObject();
						//用户可以通过此key查询抢购礼品的当前处理状态
						dataJson.put("remain", remain);
						resJson.put("data", dataJson);
					}catch(Exception e){
						resJson = new com.alibaba.fastjson.JSONObject();
						resJson.put("resCode", "-4");
						resJson.put("errMsg", "抢券成功【"+cardno+"】，推送队列失败，请联系客服人员");
						return resJson;
					}
				}else{//卡号为空，说明礼券已经领取完
					resJson = new com.alibaba.fastjson.JSONObject();
					resJson.put("resCode", "-5");
					resJson.put("errMsg", "无库存");
				}	
			}catch(Exception e){
				resJson = new com.alibaba.fastjson.JSONObject();
				resJson.put("resCode", "-99");
				resJson.put("errMsg", "服务异常，请稍后重试");
			}finally{
				//卡号回收且卡号不为空
				if(isCardNoRecycle&&org.apache.commons.lang.StringUtils.isNotBlank(cardno)){
					jedis.sadd(cardNoSetKey, cardno);
				}
				if(jedis!=null){
					jedis.close();
				}
			}	
		}else{
			resJson = new com.alibaba.fastjson.JSONObject();
			resJson.put("resCode", "-6");
			resJson.put("errMsg", "礼品类型非法");
		}
		
		return resJson;
	}
	/***
	 * 
			* 描述:
			* 	判断cardtype是否为每日领取活动礼品
			* @param cardType
			* @return
			* @author 许宝众 2017年9月25日 下午3:13:44
	 */
	private boolean isValidPerdayGiftType(String cardType){
		List<ActivityCzj2017Gift> allPerdayActivityGiftList = getAllPerdayActivityGiftList();
		if(allPerdayActivityGiftList!=null&&allPerdayActivityGiftList.size()>0){
			for (ActivityCzj2017Gift activityCzj2017Gift : allPerdayActivityGiftList) {
				if(cardType.equals(activityCzj2017Gift.getCardType())){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
			* 描述:
			* 判断cardtype在指定时间点内是否正在进行活动
			* @param cardType
			* @param specialDate
			* @return
			* @author 许宝众2017年9月25日 下午3:00:37
	 */
	private boolean isValidRobGiftType(String cardType,Date specialDate){
		List<ActivityCzj2017Gift> allRobActivityGiftList = this.getAllRobActivityGiftList();
		if(allRobActivityGiftList!=null&&allRobActivityGiftList.size()>0){
			for (int i = 0; i < allRobActivityGiftList.size(); i++) {
				ActivityCzj2017Gift activityCzj2017Gift = allRobActivityGiftList.get(i);
				Date endTime = activityCzj2017Gift.getEndTime();
				Date beginTime = activityCzj2017Gift.getBeginTime();
				if((specialDate.getTime()>=beginTime.getTime()&&specialDate.getTime()<=endTime.getTime())&&cardType.equals(activityCzj2017Gift.getCardType())){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 
			* 描述:
			* 得到指定时间内活动信息Vo,包括每日领取、每日抢
			* @param specialDate
			* 			指定日期
			* @param openid
			* 			微信openid
			* @return
			* @author 许宝众 2017年9月20日 下午2:19:36
	 */
	public Czj2017GiftVo getSpecailDateCzj2017ActivityGiftList(Date specialDate,String openid){
		Czj2017GiftVo resVo = new Czj2017GiftVo();
		ArrayList<Czj2017GiftItemVo> perdayList = new ArrayList<Czj2017GiftItemVo>();
		ArrayList<Czj2017GiftItemVo> robList = new ArrayList<Czj2017GiftItemVo>();
		resVo.setPerdayGifts(perdayList);
		resVo.setRobGifts(robList);
		List<PerdayGetHis> perydayHisList = getPerydayHisList(openid);
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			List<ActivityCzj2017Gift> allPerdayActivityGiftList = getAllPerdayActivityGiftList();
			//每日领取 活动信息
			if(allPerdayActivityGiftList!=null){
				for (int i = 0; i < allPerdayActivityGiftList.size(); i++) {
					ActivityCzj2017Gift activityCzj2017Gift = allPerdayActivityGiftList.get(i);
					if(specialDate.getTime()>=activityCzj2017Gift.getBeginTime().getTime()&&specialDate.getTime()<=activityCzj2017Gift.getEndTime().getTime()){
						Czj2017GiftItemVo item = new Czj2017GiftItemVo();
						String cardType = activityCzj2017Gift.getCardType();
						item.setCardType(cardType);
						item.setCardTypeName(activityCzj2017Gift.getCardTypeName());
						String key = this.getPerdayGiftSetKey(cardType);
						item.setAmount(activityCzj2017Gift.getAmount());
						item.setRemain(jedis.scard(key).intValue());
						item.setActivityStatus("1");
						if(perydayHisList!=null){
							for (PerdayGetHis perydayHis : perydayHisList) {
								if(perydayHis.getCardtype().equals(cardType)){
									item.setAcquired(true);
									break;
								}
							}
						}
						perdayList.add(item);
					}
				}
			}
			//每日抢 活动信息:取当前最匹配的两轮活动
			List<ActivityCzj2017Gift> allRobActivityGiftList = this.getAllRobActivityGiftList();
			
			if(allRobActivityGiftList!=null){
				Date firstTime = null;
				Date secondTime = null;
				for (int i = 0; i < allRobActivityGiftList.size(); i++) {
					ActivityCzj2017Gift activityCzj2017Gift = allRobActivityGiftList.get(i);
					Date endTime = activityCzj2017Gift.getEndTime();
					long recu = endTime.getTime()-specialDate.getTime();
					if(recu>=0){
						if(firstTime==null){
							firstTime = endTime;
						}else if(firstTime!=null&&firstTime.getTime()!=endTime.getTime()){
							secondTime = endTime;
							break;
						}
					}
				}
				for (int i = 0; i < allRobActivityGiftList.size(); i++) {
					ActivityCzj2017Gift activityCzj2017Gift = allRobActivityGiftList.get(i);
					Date beginTime = activityCzj2017Gift.getBeginTime();
					Date endTime = activityCzj2017Gift.getEndTime();
					if((firstTime!=null&&endTime.getTime()==firstTime.getTime())||(secondTime!=null&&endTime.getTime()==secondTime.getTime())){
						//判断礼品信息是否为活动进行中礼品
						Czj2017GiftItemVo item = new Czj2017GiftItemVo();
						String cardType = activityCzj2017Gift.getCardType();
						item.setCardType(cardType);
						item.setCardTypeName(activityCzj2017Gift.getCardTypeName());
						item.setBeginTime(DateUtils.format(beginTime, "yyyy-MM-dd HH:mm:ss"));
						item.setEndTime(DateUtils.format(endTime, "yyyy-MM-dd HH:mm:ss"));
						item.setAmount(activityCzj2017Gift.getAmount());
						if(specialDate.getTime()>=beginTime.getTime()&&specialDate.getTime()<endTime.getTime()){
							String key = this.getRobGiftSetKey(specialDate, cardType);;
							item.setRemain(jedis.scard(key).intValue());
							item.setActivityStatus("1");//活动进行中
							//是否已领取
							String giftStatusKey = ConstantUtils.LimitTimeRobRabbitmqConstants.ACTIVITIES_2017_CZJ_LIMITE_TIME_PARTICIPANT+DateUtils.format(specialDate, "yyyy-MM-dd")+"-"+openid;
							item.setAcquired(jedis.exists(giftStatusKey));
						}else{
							item.setRemain(activityCzj2017Gift.getAmount());
							item.setActivityStatus("0");//活动未开始
						}
						robList.add(item);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
		return resVo;
	}
	/**
	 * 
			* 描述:
			* 	获取用户所有的领取记录
			* @param openid
			* @return
			* @author 许宝众2017年9月20日 下午5:13:41
	 */
	private List<PerdayGetHis> getPerydayHisList(String openid){
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("from PerdayGetHis where openid = ? ");
		query.setParameter(0, openid);
		return query.list();
	}
	/**
	 * 
			* 描述:
			* 得到得到2017车主节活动 所有的每日领取礼品列表
			* @return
			* @author 许宝众 2017年9月20日 下午2:05:10
	 */
	public List<ActivityCzj2017Gift> getAllPerdayActivityGiftList(){
		String key = "getAllPerdayActivityGiftList";
		List<ActivityCzj2017Gift> allPerdayActivityGiftList = (List<ActivityCzj2017Gift>) cacheMap.get(key);
		if(allPerdayActivityGiftList !=null){
			return allPerdayActivityGiftList;
		}else{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from ActivityCzj2017Gift where activityType = '0' order by beginTime");
			allPerdayActivityGiftList=query.list();
			if(allPerdayActivityGiftList!=null){
				cacheMap.put(key, allPerdayActivityGiftList);
			}
			return allPerdayActivityGiftList;
		}
	}
	/**
	 * 
			* 描述:
			* 得到2017车主节活动 所有天天抢的活动礼品列表
			* @return
			* @author 许宝众 2017年9月20日 上午9:23:00
	 */
	public List<ActivityCzj2017Gift> getAllRobActivityGiftList(){
		String key = "getAllRobActivityGiftList";
		List<ActivityCzj2017Gift> allRobActivityGiftList = (List<ActivityCzj2017Gift>) cacheMap.get(key);
		if(allRobActivityGiftList !=null){
			return allRobActivityGiftList;
		}else{
			Session session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("from ActivityCzj2017Gift where activityType = '1' order by begintime");
			allRobActivityGiftList=query.list();
			if(allRobActivityGiftList!=null){
				cacheMap.put(key, allRobActivityGiftList);
			}
			return allRobActivityGiftList;
		}
	}
	/**
	 * 
			* 描述:
			* 		清除本service的缓存
			* @author 许宝众 2017年9月27日 下午4:03:41
	 */
	public void clearCacheMap(){
		this.cacheMap.clear();
	}
	
	/**
	 * 
			* 描述:查询车主领到的礼品
			* @param ActivityGiftCardQueryEntity
			* @return
			* @throws EpiccException
			* @author zs 2017年9月28日 下午10:44:00
	 */
	public JSONObject queryOwnerGiftList(ActivityGiftCardQueryEntity activitygiftcardqueryentity) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1046Entity reqTrans1046Entity = new ReqTrans1046Entity();
		TransUtil.copyObject(activitygiftcardqueryentity, reqTrans1046Entity);
		ResTrans1046Entity resTrans1046Entity = transService.executeTrans1046(reqTrans1046Entity);
		List<ActivityGiftCardEntity> list = null;
		if ("0".equals(resTrans1046Entity.getResuestHeadEntity().getResponse_code().trim())) {
			String errMsg = resTrans1046Entity.getResuestHeadEntity().getResponse_message();
			json.put("resCode", "1");
			json.put("errMsg", errMsg);
		} else {
			
			 List<ResTrans1046CardDataEntity> resTrans1046CardDataEntityList = resTrans1046Entity.getResTrans1046BodyEntity().getResTrans1046CardDataEntityList();
			 ResTrans1046BasePartEntity resTrans1046Score = resTrans1046Entity.getResTrans1046BodyEntity().getResTrans1046BasePartEntity();
			 if(resTrans1046CardDataEntityList != null && resTrans1046CardDataEntityList.size() > 0){
					list = new ArrayList<ActivityGiftCardEntity>();
					for (ResTrans1046CardDataEntity restrans1046carddataentity : resTrans1046CardDataEntityList) {
						ActivityGiftCardEntity activitygiftcardentity = new ActivityGiftCardEntity();
						TransUtil.copyObject(restrans1046carddataentity,activitygiftcardentity);
						list.add(activitygiftcardentity);
					}
					Page page = new Page();
					TransUtil.copyObject(resTrans1046Score,page);
					json.put("resCode", "0");
					json.put("msg","成功");
					json.put("data", list);
					json.put("page", resTrans1046Score);

				}else{
					Page page = new Page();
					TransUtil.copyObject(resTrans1046Score,page);
					json.put("resCode", "1");
					json.put("msg","没有数据");
					json.put("data", list);
					json.put("page", resTrans1046Score);
				}
		}
		return json;
	}
}
