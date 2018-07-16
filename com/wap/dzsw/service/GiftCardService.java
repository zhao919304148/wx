package com.wap.dzsw.service;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.bouncycastle.util.encoders.UrlBase64;
import org.hibernate.SQLQuery;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



























import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.informix.util.stringUtil;
import com.sys.dic.entity.BrandEntity;
import com.sys.dic.entity.NetWorkEntity;
import com.sys.dic.entity.YuYueEntity;
import com.sys.exception.EpiccException;
import com.wap.dzsw.entity.BaoYangCouponConsume;
import com.wap.dzsw.entity.BaoYangCouponRecord;
import com.wap.dzsw.entity.BaoYangNetworkEntity;
import com.wap.dzsw.entity.CancelOrder;
import com.wap.dzsw.entity.CarDataEntity;
import com.wap.dzsw.entity.CarPhotoEntity;
import com.wap.dzsw.entity.DrivingInfoVo;
import com.wap.dzsw.entity.FriendGetGiftVo;
import com.wap.dzsw.entity.FriendRecommendVo;
import com.wap.dzsw.entity.GiftCardEntity;
import com.wap.dzsw.entity.GiftCardQueryEntity;
import com.wap.dzsw.entity.GiftConsignVo;
import com.wap.dzsw.entity.IdentifyDateEntity;
import com.wap.dzsw.entity.NetworkEntity;
import com.wap.dzsw.entity.NetworkQueryVo;
import com.wap.dzsw.entity.NetworkVo;
import com.wap.dzsw.entity.Page;
import com.wap.dzsw.entity.PhoneModifyEntity;
import com.wap.dzsw.entity.SaveYuYueData;
import com.wap.dzsw.entity.Verification;
import com.wap.dzsw.entity.WashCarRecord;
import com.wap.dzsw.entity.YimeiGiftConSignVo;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1021.ReqTrans1021Entity;
import com.wap.trans.entity.tr_1021.ResTrans1021BasePartEntity;
import com.wap.trans.entity.tr_1021.ResTrans1021CardDataEntity;
import com.wap.trans.entity.tr_1021.ResTrans1021Entity;
import com.wap.trans.entity.tr_1021.ResTrans1021Score;
import com.wap.trans.entity.tr_1023.ReqTrans1023Entity;
import com.wap.trans.entity.tr_1023.ResTrans1023Entity;
import com.wap.trans.entity.tr_1025.ReqTrans1025Entity;
import com.wap.trans.entity.tr_1025.ResTrans1025Entity;
import com.wap.trans.entity.tr_1027.ReqTrans1027Entity;
import com.wap.trans.entity.tr_1027.ResTrans1027Entity;
import com.wap.trans.entity.tr_1031.ReqTrans1031Entity;
import com.wap.trans.entity.tr_1031.ResTrans1031BodyBasePart;
import com.wap.trans.entity.tr_1031.ResTrans1031BodyEntity;
import com.wap.trans.entity.tr_1031.ResTrans1031Entity;
import com.wap.trans.entity.tr_1031.ResTrans1031ScoreDeailEntity;
import com.wap.trans.entity.tr_1032.ReqTrans1032Entity;
import com.wap.trans.entity.tr_1032.ResTrans1032Entity;
import com.wap.trans.entity.tr_1033.ReqTrans1033Entity;
import com.wap.trans.entity.tr_1033.ResTrans1033Entity;
import com.wap.trans.entity.tr_1034.ReqTrans1034Entity;
import com.wap.trans.entity.tr_1034.ResTrans1034Entity;
import com.wap.trans.entity.tr_1035.ReqTrans1035Entity;
import com.wap.trans.entity.tr_1035.ResTrans1035Entity;
import com.wap.trans.entity.tr_1036.ReqTrans1036Entity;
import com.wap.trans.entity.tr_1036.ResTrans1036Entity;
import com.wap.trans.entity.tr_1037.ReqTrans1037Entity;
import com.wap.trans.entity.tr_1037.ReqTrans1037FileEntity;
import com.wap.trans.entity.tr_1037.ResTrans1037Entity;
import com.wap.trans.entity.tr_1038.ReqTrans1038Entity;
import com.wap.trans.entity.tr_1038.ResTrans1038CarDataEntity;
import com.wap.trans.entity.tr_1038.ResTrans1038Entity;
import com.wap.trans.entity.tr_1038.ResTrans1038IdentifyDateEntity;
import com.wap.trans.entity.tr_1039.ReqTrans1039Entity;
import com.wap.trans.entity.tr_1039.ResTrans1039Entity;
import com.wap.trans.entity.tr_1040.ReqTrans1040Entity;
import com.wap.trans.entity.tr_1040.ResTrans1040Entity;
import com.wap.trans.entity.tr_1040.ResTrans1040NetworkDataEntity;
import com.wap.trans.entity.tr_1041.ReqTrans1041Entity;
import com.wap.trans.entity.tr_1041.ResTrans1041Entity;
import com.wap.trans.entity.tr_1044.ReqTrans1044Entity;
import com.wap.trans.entity.tr_1044.ResTrans1044Entity;
import com.wap.trans.entity.tr_1044.ResTrans1044RecordDataEntity;
import com.wap.trans.entity.tr_1057.ReqTrans1057Entity;
import com.wap.trans.entity.tr_1057.ResTrans1057Entity;
import com.wap.trans.entity.tr_1058.ReqTrans1058Entity;
import com.wap.trans.entity.tr_1058.ResTrans1058CardDataEntity;
import com.wap.trans.entity.tr_1058.ResTrans1058Entity;
import com.wap.trans.entity.tr_1059.ReqTrans1059Entity;
import com.wap.trans.entity.tr_1059.ResTrans1059Entity;
import com.wap.trans.entity.tr_1059.ResTrans1059RecordListEntity;
import com.wap.trans.entity.tr_1061.ReqTrans1061Entity;
import com.wap.trans.entity.tr_1061.ResTrans1061CardDataEntity;
import com.wap.trans.entity.tr_1061.ResTrans1061Entity;
import com.wap.trans.entity.tr_1062.ReqTrans1062Entity;
import com.wap.trans.entity.tr_1062.ResTrans1062Entity;
import com.wap.trans.entity.tr_1068.ReqTrans1068Entity;
import com.wap.trans.entity.tr_1068.ResTrans1068Entity;
import com.wap.trans.entity.tr_1069.ReqTrans1069Entity;
import com.wap.trans.entity.tr_1069.ResTrans1069Entity;
import com.wap.trans.entity.tr_1070.ReqTrans1070Entity;
import com.wap.trans.entity.tr_1070.ResTrans1070Entity;
import com.wap.trans.entity.tr_1073.ReqTrans1073Entity;
import com.wap.trans.entity.tr_1073.ResTrans1073Entity;
import com.wap.trans.entity.tr_1074.ReqTrans1074Entity;
import com.wap.trans.entity.tr_1074.ResTrans1074Entity;
import com.wap.trans.entity.tr_1075.ReqTrans1075Entity;
import com.wap.trans.entity.tr_1075.ResTrans1075Entity;
import com.wap.trans.entity.tr_1078.ReqTrans1078Entity;
import com.wap.trans.entity.tr_1078.ResTrans1078Entity;
import com.wap.trans.service.TransService;
import com.wap.util.AESUtil;
import com.wap.util.CommonUtils;
import com.wap.util.ConstantUtils;
import com.wap.util.DateUtils;
import com.wap.util.DistanceUtil;
import com.wap.util.TransUtil;
import com.wap.wx_interface.utils.AESUtils;
import com.wx.util.ConfigUtil;
import com.wx.util.WeixinUtil;

import core.db.dao.IBaseService;

@Service("giftCardService")
public class GiftCardService {
	
	private static final Log logger = LogFactory.getLog(GiftCardService.class);
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
	
	@Autowired(required = false)
	private JdbcTemplate jdbcTemplate = null;
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	@Autowired
	private HibernateTemplate hibernateTemplate;
	
	@Autowired
	private JedisPool jedisPool;
	/**
	 * 
	 * 描述:礼品查询
	 * @param cardQueryEntity
	 * @return
	 * @author 权恩喜  2016年11月22日 上午10:51:59
	 * @throws EpiccException 
	 */
	public Map<String, Object> getGiftCardList(GiftCardQueryEntity cardQueryEntity) throws EpiccException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<GiftCardEntity> list = null;
		ReqTrans1021Entity reqTrans1021Entity = new ReqTrans1021Entity();
		TransUtil.copyObject(cardQueryEntity, reqTrans1021Entity);
		ResTrans1021Entity resTrans1021Entity = transService.executeTrans1021(reqTrans1021Entity);
		if(null == resTrans1021Entity){
			map.put("res", "0");
			map.put("msg", "礼品信息查询接口异常！");
			return map;
		}
		logger.info("executeTrans1021,message:"+resTrans1021Entity.getResuestHeadEntity().getResponse_message().trim());
		if("0".equals(resTrans1021Entity.getResuestHeadEntity().getResponse_code().trim())){
			map.put("res", "0");
			map.put("msg", resTrans1021Entity.getResuestHeadEntity().getResponse_message().trim());
			return map;
		}
		List<ResTrans1021CardDataEntity> resTrans1021CardDataEntityList = resTrans1021Entity.getResTrans1021BodyEntity().getResTrans1021CardDataEntityList();
		ResTrans1021Score resTrans1021Score = resTrans1021Entity.getResTrans1021BodyEntity().getResTrans1021Score();
		ResTrans1021BasePartEntity resTrans1021BasePartEntity = resTrans1021Entity.getResTrans1021BodyEntity().getResTrans1021BasePartEntity();
		if(resTrans1021CardDataEntityList != null && resTrans1021CardDataEntityList.size() > 0){
			list = new ArrayList<GiftCardEntity>();
			for (ResTrans1021CardDataEntity resTrans1021CardDataEntity : resTrans1021CardDataEntityList) {
				GiftCardEntity giftCardEntity = new GiftCardEntity();
				TransUtil.copyObject(resTrans1021CardDataEntity,giftCardEntity);
				//手机号要加密显示
				String phoneNumber = giftCardEntity.getPhoneNumber();
				String cardType = giftCardEntity.getCardType();
				if(StringUtils.hasText(phoneNumber) && !"1069".equals(cardType)){
					phoneNumber=CommonUtils.shieldPhoneNumber(phoneNumber.trim());
				}
				giftCardEntity.setPhoneNumber(phoneNumber);
				//卡号加密
				String cardNo =  giftCardEntity.getCardNo();
				if(StringUtils.hasText(cardNo)){
				 	giftCardEntity.setEncodeCardNo(CommonUtils.encodeCardNo(cardNo));
				}
				list.add(giftCardEntity);
			}
			Page page = new Page();
			TransUtil.copyObject(resTrans1021BasePartEntity,page);
			map.put("res", "1");
			map.put("msg","成功");
			map.put("giftCardList", list);
			map.put("page", page);

		}
		if(resTrans1021Score!=null){
			Page page = new Page();
			TransUtil.copyObject(resTrans1021BasePartEntity,page);
			map.put("score", resTrans1021Score);
			map.put("res", "1");
			map.put("msg","成功");
			map.put("page", page);
		}
		return map;
	}

	/**
	 * 
	 * 描述:品牌列表
	 * @return
	 * @author qex 2016年12月8日 上午10:25:54
	 */
//	public PageSplitUtil<NetWorkEntity> getBrand(int pageNo, int pageSize) {
//		baseService.evictObject(PictureEntity.class);
//		PageSplitUtil<NetWorkEntity> pageSplitUtil = baseService.queryForHqlPage("from BrandEntity b order by b.initial ASC",pageNo,pageSize);
//		return pageSplitUtil;
//	}
	/**
	 * 品牌网点
	 * 描述:
	 * @param brandId
	 * @param latitude
	 * @param longitude
	 * @param pageNo
	 * @return
	 * @author qex 2016年12月8日 下午2:49:55
	 * @param cardType 
	 */
	@SuppressWarnings("unchecked")
	public Page getNetworkByBrandId(String brandId, String latitude, String longitude,  Page page,String networkType, String cardType) throws Exception{
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setBrandId(brandId);
		queryVo.setNetworkType(cardType);
		List<NetworkVo> items = getNetwork(queryVo);
		logger.info("size:"+items.size());
		for (NetworkVo networkVo : items) {
			String distance = DistanceUtil.getDistance( latitude, longitude, networkVo.getLatitude(), networkVo.getLongitude());
			networkVo.setDistance(Double.parseDouble(distance));
		}
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator("distance"), false);
		Collections.sort(items,comparatorChain);
		List<NetworkVo> list2 = pagination(items,Integer.parseInt(page.getPageNo()),10);
		int pageCount = items.size()%10==0?items.size()/10:(items.size()/10+1);
		logger.info("pageCount="+pageCount);
		page.setList(list2);
		page.setPageCount(pageCount+"");
		return page;
	}
	/**
	 * 
	 * 描述:list分页工具
	 * @param objList
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @author qex2017年2月10日 下午4:52:53
	 */
	private <T> List pagination(List<T> objList, int pageNo, int pageSize) {
		if (pageNo <= 0)
			pageNo = 1;

		if (pageSize == 0)
			pageSize = 10;

		List objArray = new ArrayList();
		int startIndex = (pageNo - 1) * pageSize;
		int endIndex = pageNo * pageSize;
		if (objList != null && objList.size() > 0) {
			if (objList.size() < endIndex) {
				endIndex = objList.size();
			}
			for (int i = startIndex; i < endIndex; ++i) {
				objArray.add(objList.get(i));
			}
		}
		return objArray;
	}
	/**
	 * 未来90天的预约数据
	 * 描述:
	 * @return
	 * @author qex 2016年12月8日 下午5:00:48
	 */
	public JSONObject getYuYueData(String networkJobId) {
		//往后预约天数
		String maxDays = ConfigUtil.getString("maxDays");
		//预约开始日期：两天后的日期
		String nowDate = DateUtils.format(DateUtils.addDay(new Date(),2),DateUtils.FORMAT_INT);
		//90天后的日期	maxDays
		String after90Day = DateUtils.format(DateUtils.addDay(new Date(), Integer.parseInt(maxDays)),DateUtils.FORMAT_INT);
		//得到有效期内的预约数据
		baseService.evictObject(YuYueEntity.class);
		String hql = "from YuYueEntity where yuyueDate between ? and ? and networkJobid = ?";
		List<YuYueEntity> list = hibernateTemplate.find(hql,nowDate,after90Day,networkJobId);
		//该网点的最大预约数
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkJobid(networkJobId);
		List<NetworkVo> entityList = getNetwork(queryVo);
		NetworkVo findObject = (entityList!=null&&entityList.size()>0)?entityList.get(0):null;
		Map<String,Integer> map = new HashMap<String,Integer>();
		logger.info("List<YuYueEntity>.size:"+list.size());
		//将预约数据存放到map方便根据日期获取
		for (YuYueEntity yuYueEntity : list) {
//			logger.info("key=yuYueEntity.getYuyueDate()"+yuYueEntity.getYuyueDate());
			map.put(yuYueEntity.getYuyueDate(), yuYueEntity.getYuyueCount());
		}
//		logger.info("map.to=="+map.toString());
		//最大预约数
		String maxYuYueCount = findObject.getMaxCount();
//		logger.info("maxYuYueCount="+maxYuYueCount);
		//有效预约时间内的数据
		JSONObject rilidata = rili(Integer.parseInt(maxDays),map,nowDate,Integer.parseInt(maxYuYueCount));
		return rilidata;
	}

	/**
	 * 
	 * 描述:日历
	 * @param year当前年
	 * @param month当前月
	 * @param day当前几号
	 * @param count往后多少天
	 * @author qex 2016年12月12日 下午3:17:00
	 */
	public static JSONObject rili(int count, Map<String, Integer> map, String today, int max){
		int year = Integer.parseInt(today.substring(0, 4));
		int month = Integer.parseInt(today.substring(4,6));
		int day = Integer.parseInt(today.substring(6, 8));

		logger.info("年:"+year);
		logger.info("月:"+month);
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("maxCount", max);
		int m=0;
		for (int i = 0; i < count; i++) {
			int days = DateUtils.getDaysByYearMonth(year, month);
			int [] dayArray = new  int[days+1];
			days = days-day+1;
			for (int j = 0; j < days; j++) {

				Date addDay = DateUtils.addDay(DateUtils.parse(today, DateUtils.FORMAT_INT), m);
				String key = DateUtils.format(addDay, DateUtils.FORMAT_INT);
//				logger.info("map.get(key)="+map.get(key));
//				logger.info("key="+key);
				dayArray[day] = map.get(key)==null?0:map.get(key);
//				logger.info(day);
				day++;
				m++;
				if(m==count){
					jsonObject.put(month, dayArray);
					jsonObject.put("month", month);
					jsonObject.put("maxdate", day-1);
					return jsonObject;
				}
			}
			jsonObject.put(month, dayArray);
			jsonObject.put("month", month);
			month++;
			day=1;
			if(month==13){
				month = 1;
				year ++;
				logger.info("年:"+year+",月:"+month);
			}
			logger.info("月："+month);
			logger.info("m"+m);
		}
		return jsonObject;

	}
	/**
	 * 
			* 描述:
			* 		查询服务网点指定日期的预约信息
			* @param networkjobid
			* @param fwType
			* @param fwDate
			* 			预约日期 ，格式yyyyMMdd
			* @return
			* @author 许宝众2017年8月25日 下午3:37:24
	 */
	@SuppressWarnings("unused")
	private YuYueEntity getYuyueInfo(String networkjobid,String fwType,String fwDate){
		String hql = "from YuYueEntity  where networktype = ? and networkjobid = ? and yuyuedate = ? ";
		List<YuYueEntity> yuYueList = hibernateTemplate.find(hql,fwType,networkjobid,fwDate);
		return (yuYueList!=null&&yuYueList.size()>0)?yuYueList.get(0):null;
	}
	/**
	 * 
			* 描述:获取最大预约量
			* @return
			* @author 许宝众2017年8月25日 下午3:44:41
	 */
	private NetworkVo getMaxYuyueCount(String networkJobId,String networkType){
		Assert.isTrue(org.apache.commons.lang.StringUtils.isNotBlank(networkType)&&org.apache.commons.lang.StringUtils.isNotBlank(networkJobId));
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkJobid(networkJobId);
		queryVo.setNetworkType(networkType);
		List<NetworkVo> list = getNetwork(queryVo);
		return (list!=null&&list.size()>0)?list.get(0):null;
	}
	/**
	 * 保存预约数据
	 * 描述:
	 * @param pojo
	 * @return
	 * @author qex2016年12月14日 下午2:16:28
	 * @throws EpiccException 
	 */
	public JSONObject saveYuYueDate(SaveYuYueData pojo) throws EpiccException {
		JSONObject json = new JSONObject();
		String networkType = pojo.getFwType();
		String networkJobId = pojo.getNetworkJobId();
		String fwDate = pojo.getFwDate();
		//服务类型翻译
		if("1004".equals(networkType)){
			pojo.setFwType("1");
		}else if("1010".equals(networkType)){
			pojo.setFwType("2");
		}else if("1009".equals(networkType)){
			pojo.setFwType("4");
		}else{
			pojo.setFwType("3");
		}
		String fwType = pojo.getFwType();
		//查询网点信息
		NetworkVo  network= this.getNetWorkById(networkJobId,networkType);
		TransUtil.copyObject(network, pojo);
		logger.info(pojo.toString());
		//如果list为空添加一条记录，如果不为空就修改预约数
		baseService.evictObject(YuYueEntity.class);
		YuYueEntity yuYueInfo = this.getYuyueInfo(networkJobId, fwType, fwDate);
		//预约网点的最大预约数
		NetworkVo networkVo = this.getMaxYuyueCount(networkJobId, networkType);
		//如果已经预约过，便增加一条记录，否则更新记录的预约数
		if(networkVo!=null){
			if(yuYueInfo!=null){
				if(Integer.parseInt(networkVo.getMaxCount())<=yuYueInfo.getYuyueCount()){
					json.put("resCode", "1");
					json.put("msg", "今日预约已满");
					return json;
				}
				ReqTrans1023Entity reqTrans1023Entity = new ReqTrans1023Entity();
				logger.info(fwDate);
				pojo.setFwDate(DateUtils.format(DateUtils.parse(fwDate, DateUtils.FORMAT_INT), DateUtils.FORMAT_SHORT));
				TransUtil.copyObject(pojo, reqTrans1023Entity);
				reqTrans1023Entity.setUsername((String)getCurrentRequest().getAttribute((ConstantUtils.USER_NAME)));
				reqTrans1023Entity.setSendType("0");//接口保存数据
				reqTrans1023Entity.setNetworkPhone(pojo.getRecvmsgPhone());
				logger.info("reqTrans1023Entity:"+reqTrans1023Entity.toString());
				ResTrans1023Entity executeTrans1023 = transService.executeTrans1023(reqTrans1023Entity);
				if("0".equals(executeTrans1023.getResuestHeadEntity().getResponse_code().trim())){
					String responseMessage = executeTrans1023.getResuestHeadEntity().getResponse_message().trim();
					logger.error(responseMessage);
					json.put("resCode", "4");
					json.put("msg", responseMessage);
				}else{
					String updateSql = "update s_dic_yuyue set yuyuecount = yuyuecount+1 where networktype = ? and networkjobid= ? and yuyuedate= ? ";
					Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
					SQLQuery sqlQuery = currentSession.createSQLQuery(updateSql);
					sqlQuery.setParameter(0, fwType);
					sqlQuery.setParameter(1, networkJobId);
					sqlQuery.setParameter(2, fwDate);
					sqlQuery.executeUpdate();
					json.put("resCode", "0");
					json.put("msg", "成功");
				}
			}else{
				ReqTrans1023Entity reqTrans1023Entity = new ReqTrans1023Entity();
				TransUtil.copyObject(pojo, reqTrans1023Entity);
				reqTrans1023Entity.setSendType("0");//接口保存数据
				reqTrans1023Entity.setNetworkPhone(pojo.getRecvmsgPhone());
				reqTrans1023Entity.setUsername((String)getCurrentRequest().getAttribute((ConstantUtils.USER_NAME)));
				reqTrans1023Entity.setFwDate(DateUtils.format(DateUtils.parse(reqTrans1023Entity.getFwDate(),DateUtils.FORMAT_INT),DateUtils.FORMAT_SHORT));
				logger.info("reqTrans1023Entity:"+reqTrans1023Entity.toString());
				ResTrans1023Entity executeTrans1023 = transService.executeTrans1023(reqTrans1023Entity);
				logger.info("executeTrans1023.getResuestHeadEntity().getResponse_code().trim()="+executeTrans1023.getResuestHeadEntity().getResponse_code().trim());
				if("0".equals(executeTrans1023.getResuestHeadEntity().getResponse_code().trim())){
					String responseMessage = executeTrans1023.getResuestHeadEntity().getResponse_message().trim();
					logger.error(responseMessage);
					json.put("resCode", "4");
					json.put("msg", responseMessage);
				}else{
//					String sql = "INSERT INTO s_dic_yuyue(id, networkjobid, networktype, yuyuedate, yuyuecount)"
//							+ "VALUES (yuyue_sequence.NEXTVAL, '"+networkJobId+"', '"+fwType+"', '"+fwDate+"', 1)";
//					baseService.updateJDBC(sql);
					String sql = "INSERT INTO s_dic_yuyue(id, networkjobid, networktype, yuyuedate, yuyuecount)"
							+ "VALUES (yuyue_sequence.NEXTVAL, ? , ? , ? , 1)";
					Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
					SQLQuery sqlQuery = currentSession.createSQLQuery(sql);
					sqlQuery.setParameter(0, networkJobId);
					sqlQuery.setParameter(1, fwType);
					sqlQuery.setParameter(2, fwDate);
					sqlQuery.executeUpdate();
					
					json.put("resCode", "0");
					json.put("msg", "成功");
				}
			}
		}else{
			json.put("resCode", "4");
			json.put("msg", "未找到网点信息");
		}
		return json;
	}

	/**
	 * 
	 * 描述:查询网点信息
	 * @param networkJobId
	 * @param networkType
	 * @return
	 * @author qex2017年1月9日 下午3:11:35
	 */
	public NetworkVo getNetWorkById(String networkJobId,String networkType) {
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkJobid(networkJobId);
		queryVo.setNetworkType(networkType);
		List<NetworkVo> list =getNetwork(queryVo);
		return (list!=null&&list.size()>0)?list.get(0):null;
	}

	/**
	 * 
	 * 描述:玻璃修复网点
	 * @param pageNo
	 * @param longitude
	 * @param latitude
	 * @return
	 * @author qex 2017年1月4日 下午5:51:51
	 */
	@SuppressWarnings("unchecked")
	public Page getNetworkBoli(String networkType, String pageNo, String longitude, String latitude) {
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkType(networkType);
		List<NetworkVo> list = getNetwork(queryVo);
		for (NetworkVo networkVo : list) {
			String distance = DistanceUtil.getDistance(longitude, latitude, networkVo.getLongitude() , networkVo.getLatitude());
			networkVo.setDistance(Double.parseDouble(distance));
		}
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator("distance"), false);
		Collections.sort(list,comparatorChain);
		List<NetworkVo> list2 = pagination(list,Integer.parseInt(pageNo),10);
		int pageCount = list.size()%10==0?list.size()/10:list.size()/10+1;
		logger.info("pageCount="+pageCount);
		Page page = new Page();
		page.setList(list2);
		page.setPageCount(pageCount+"");
		return page;
	}
	/**
	 * 喷漆玻璃取消订单
	 * 描述:
	 * @param cancel
	 * @return
	 * @author qex 2017年1月5日 下午2:47:23
	 * @param cancel 
	 * @throws EpiccException 
	 */
	public JSONObject cancelOrder(CancelOrder cancel) throws EpiccException {
		ReqTrans1025Entity reqTrans1025Entity = new ReqTrans1025Entity();
		String fwType = cancel.getFwType();
		String networkJobId = cancel.getNetworkJobId();
		String yuYueDate = cancel.getYuYueDate();
		JSONObject json = new JSONObject();//BLXF00001
		if(org.apache.commons.lang.StringUtils.isNotBlank(fwType)
				&&org.apache.commons.lang.StringUtils.isNotBlank(networkJobId)
				&&org.apache.commons.lang.StringUtils.isNotBlank(yuYueDate)){
			TransUtil.copyObject(cancel, reqTrans1025Entity);
			ResTrans1025Entity executeTrans1025 = transService.executeTrans1025(reqTrans1025Entity);
			if("1".equals(executeTrans1025.getResuestHeadEntity().getResponse_code().trim())){
				String sql = "update s_dic_yuyue set yuyuecount = yuyuecount -1 where networkjobid = ? and yuyuedate = ? and networktype = ?";
				Session currentSession = hibernateTemplate.getSessionFactory().getCurrentSession();
				SQLQuery sqlQuery = currentSession.createSQLQuery(sql);
				sqlQuery.setParameter(0, networkJobId);
				sqlQuery.setParameter(1, yuYueDate);
				sqlQuery.setParameter(2, fwType);
				sqlQuery.executeUpdate();
				
				json.put("resCode", "0");//成功
				json.put("msg", "已取消，请重新预约！");
			}else{
				json.put("resCode", "1");//1失败
				json.put("msg", executeTrans1025.getResuestHeadEntity().getResponse_message().trim());
			}
		}else{
			json.put("resCode", "1");//1失败
			json.put("msg", "参数错误");
		}
		return json;
	}

	/**
	 * 
	 * 描述:积分详情查询
	 * @param identifyno
	 * @param identifytype
	 * @author qex 2017年2月5日 下午2:28:43
	 */
	public Page getScoreDeail(String identifyno, String identifytype,int pageno) {
		Page page = new Page();
		ReqTrans1031Entity reqTrans1031Entity = new ReqTrans1031Entity();
		reqTrans1031Entity.setIdentifyno(identifyno);
		reqTrans1031Entity.setIdentifytype(identifytype);
		reqTrans1031Entity.setPageno(pageno+"");
		reqTrans1031Entity.setPagesize("10");
		try {
			ResTrans1031Entity executeTrans1031 = transService.executeTrans1031(reqTrans1031Entity);
			ResTrans1031BodyEntity resTrans1031BodyEntity = executeTrans1031.getResTrans1031BodyEntity();
			if(resTrans1031BodyEntity!=null){
				List<ResTrans1031ScoreDeailEntity> scoreList = resTrans1031BodyEntity.getScoreList();
				ResTrans1031BodyBasePart basePart = resTrans1031BodyEntity.getBasePart();
				page.setPageCount(basePart.getPagecount());
				page.setPageNo(basePart.getPageno());
				page.setPageSize(basePart.getPagesize());
				page.setList(scoreList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error:"+e.getMessage());
		}
		return page;
	}
	
	/**
	 * 
			* 描述:爱车保养
			* @param networkType
			* @param longitude
			* @param latitude
			* @param pageNo
			* @return
			* @author qex 2017年2月21日 上午11:40:44
	 */
	public Page getNetworkByType(String networkType, String longitude, String latitude, String pageNo) {
		NetworkQueryVo queryVo = new NetworkQueryVo();
		queryVo.setNetworkType(networkType);
		List<NetworkVo> list = getNetwork(queryVo);
		for (NetworkVo networkVo : list) {
			String distance = DistanceUtil.getDistance(longitude, latitude, networkVo.getLongitude() , networkVo.getLatitude());
			networkVo.setDistance(Double.parseDouble(distance));
		}
		ComparatorChain comparatorChain = new ComparatorChain();
		comparatorChain.addComparator(new BeanComparator("distance"), false);
		Collections.sort(list,comparatorChain);
		List<NetworkVo> list2 = pagination(list,Integer.parseInt(pageNo),10);
		int pageCount = list.size()%10==0?list.size()/10:list.size()/10+1;
		logger.info("pageCount="+pageCount);
		Page page = new Page();
		page.setList(list2);
		page.setPageCount(pageCount+"");
		return page;
		
	}
	
	/**
	 * 
	* 描述:请求发送短信验证码
	* @param phoneNumber
	* 				接收短信的手机号
	* @param operateType
	* 				操作类型<br>
	* 				01:油卡充值 验证码
	* @return
	* @author 许宝众2017年4月24日 下午1:39:51
	 * @throws EpiccException 
	 */
	public JSONObject sendPhoneValidateCode(Verification verification){
		JSONObject resJson=new JSONObject();
		ReqTrans1027Entity reqTrans1027Entity = new ReqTrans1027Entity();
		TransUtil.copyObject(verification, reqTrans1027Entity);
		logger.info("reqTrans1027Entity:"+reqTrans1027Entity.toString());
		ResTrans1027Entity executeTrans1027=null;
		try {
			executeTrans1027 = transService.executeTrans1027(reqTrans1027Entity);
			if("0".equals(executeTrans1027.getResuestHeadEntity().getResponse_code().trim())){
				String errMsg = executeTrans1027.getResuestHeadEntity().getResponse_message();
				resJson.put("resCode", "0");//失败返回
				resJson.put("errMsg", errMsg);//详细错误信息
			}else{
				resJson.put("resCode", "1");//成功返回
			}
		} catch (EpiccException e) {
			logger.info("1027交易服务异常：",e);
			resJson.put("resCode", "0");//失败返回
			resJson.put("errMsg", e.getErrMess());//详细错误信息
		}
		return resJson;
	}
	/**
	 * 
	* 描述:
	* 	请求发送礼品卡密短信通知到用户手机
	* @param goodsSeqNumber
	* 				礼品序列号
	* @param validateCode 
	* 				用户录入验证码
	* @return
	* @throws EpiccException
	* @author 许宝众 2017年4月21日 下午12:34:06
	 */
	public JSONObject requestSendGiftMsgNotify(String goodsSeqNumber, String validateCode)throws EpiccException{
		JSONObject json=new JSONObject();
		ReqTrans1032Entity reqTrans1032Entity = new ReqTrans1032Entity();
		reqTrans1032Entity.setGoodsSeqNumber(goodsSeqNumber);
		reqTrans1032Entity.setValidateCode(validateCode);
		logger.info("reqTrans1032Entity:"+reqTrans1032Entity.toString());
		ResTrans1032Entity executeTrans1032 = transService.executeTrans1032(reqTrans1032Entity);
		if("0".equals(executeTrans1032.getResHeadEntity().getResponse_code().trim())){
			String errMsg = executeTrans1032.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 
			* 描述:关联投保人车辆，获取投保人手机号
			* @param identifyType
			* @param identifyNo
			* @param licenseNo
			* @return JSONObject（resCode 、 phoneNumber）
			* @author 骆利锋 2017年6月2日 上午10:06:01
	 */
	public JSONObject getPhoneNumByLicenseNo(String identifyType, String identifyNo, String licenseNo) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1033Entity reqTrans1033Entity = new ReqTrans1033Entity();
		reqTrans1033Entity.setIdentifyType(identifyType);
		reqTrans1033Entity.setIdentifyNo(identifyNo);
		reqTrans1033Entity.setLicenseNo(licenseNo);
		logger.info("reqTrans1033Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1033Entity));
		ResTrans1033Entity executeTrans1033 = transService.executeTrans1033(reqTrans1033Entity);
		if ("1".equals(executeTrans1033.getResHeadEntity().getResponse_code().trim())) {
			String phone = executeTrans1033.getResTrans1033BodyEntity().getBasePart().getPhoneNumber(); //手机号
			if(StringUtils.hasText(phone)){
				json.put("resCode", "1");//成功返回
				json.put("phoneNumber", CommonUtils.shieldPhoneNumber(phone.trim()));
			}else{
				json.put("resCode", "-1");//失败返回
				json.put("errMsg", "系统未识别到您的投保车辆");//详细错误信息
			}
		}else{
			String errMsg = executeTrans1033.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}
		return json;
	}
	
	/**
	 * 
			* 描述:获取手机校验码
			* @param operateType
			* @param reqData(JSON串)
			* @return JSONObject(resCode)
			* @author 骆利锋 2017年6月2日 下午4:54:15
	 */
	public JSONObject getPhoneVerifyCode(String operateType,String reqData) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1034Entity reqTrans1034Entity = new ReqTrans1034Entity();
		reqTrans1034Entity.setOperateType(operateType);
		reqTrans1034Entity.setReqData(reqData);
		logger.info("reqTrans1034Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1034Entity));
		ResTrans1034Entity executeTrans1034 = transService.executeTrans1034(reqTrans1034Entity);
		if ("0".equals(executeTrans1034.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1034.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 
			* 描述:关联投保车辆，绑定（提交）用户信息
			* @param identifyType
			* @param identifyNo
			* @param licenseNo
			* @param userName
			* @param valiDateCode
			* @return JSONObject(resCode)
			* @author 骆利锋 2017年6月5日 下午4:54:15
	 */
	public JSONObject bindCarUserInfo(String identifyType,String identifyNo,String licenseNo,String userName,String valiDateCode) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1035Entity reqTrans1035Entity = new ReqTrans1035Entity();
		reqTrans1035Entity.setIdentifyType(identifyType);
		reqTrans1035Entity.setIdentifyNo(identifyNo);
		reqTrans1035Entity.setLicenseNo(licenseNo);
		reqTrans1035Entity.setUserName(userName);
		reqTrans1035Entity.setValiDateCode(valiDateCode);
		reqTrans1035Entity.setOpenId((String)getCurrentRequest().getAttribute(ConstantUtils.OPEN_ID));
		logger.info("reqTrans1035Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1035Entity));
		ResTrans1035Entity executeTrans1035 = transService.executeTrans1035(reqTrans1035Entity);
		if ("0".equals(executeTrans1035.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1035.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 
			* 描述:投保手机号变更（原手机号可用）
			* @param identifyType
			* @param identifyNo
			* @param licenseNo
			* @param userName
			* @param valiDateCode
			* @param modifyPhoneNumber
			* @return JSONObject(resCode)
			* @author 骆利锋 2017年6月5日 下午4:54:15
	 */
	public JSONObject changePhone(String openId,String identifyType,String identifyNo,String licenseNo,String userName,String valiDateCode,String modifyPhoneNumber) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1036Entity reqTrans1036Entity = new ReqTrans1036Entity();
		reqTrans1036Entity.setIdentifyType(identifyType);
		reqTrans1036Entity.setIdentifyNo(identifyNo);
		reqTrans1036Entity.setLicenseNo(licenseNo);
		reqTrans1036Entity.setUserName(userName);
		reqTrans1036Entity.setValiDateCode(valiDateCode);
		reqTrans1036Entity.setModifyPhoneNumber(modifyPhoneNumber);
		reqTrans1036Entity.setOpenId(openId);
		logger.info("reqTrans1036Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1036Entity));
		ResTrans1036Entity executeTrans1036 = transService.executeTrans1036(reqTrans1036Entity);
		if ("0".equals(executeTrans1036.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1036.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 
			* 描述:投保手机号变更（提交资料审核）
			* @param files 车辆上传照片文件
			* @param phoneModifyEntity 手机号更新实体
			* @return JSONObject(resCode)
			* @author 骆利锋 2017年6月5日 下午4:54:15
	 */
	public JSONObject changePhoneAudit(List<CarPhotoEntity> files,PhoneModifyEntity phoneModifyEntity) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1037Entity reqTrans1037Entity = new ReqTrans1037Entity();
		reqTrans1037Entity.setIdentifyType(phoneModifyEntity.getIdentifyType());
		reqTrans1037Entity.setIdentifyNo(phoneModifyEntity.getIdentifyNo());
		reqTrans1037Entity.setLicenseNo(phoneModifyEntity.getLicenseNo());
		reqTrans1037Entity.setUserName(phoneModifyEntity.getUserName());
		reqTrans1037Entity.setModifyPhoneNumber(phoneModifyEntity.getModifyPhoneNumber());
		List<ReqTrans1037FileEntity> reqTrans1037FileEntityList = new ArrayList<ReqTrans1037FileEntity>();
		for(CarPhotoEntity file : files){
			ReqTrans1037FileEntity reqTrans1037FileEntity = new ReqTrans1037FileEntity();
			TransUtil.copyObject(file, reqTrans1037FileEntity);
			reqTrans1037FileEntityList.add(reqTrans1037FileEntity);
			logger.info("reqTrans1037FileEntity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1037FileEntity));
		}
		reqTrans1037Entity.setReqTrans1037FileEntityList(reqTrans1037FileEntityList);
		logger.info("reqTrans1037Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1037Entity));
		ResTrans1037Entity executeTrans1037 = transService.executeTrans1037(reqTrans1037Entity);
		if ("0".equals(executeTrans1037.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1037.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 
			* 描述:电商福袋登陆，获取用户基本信息
			* @param openid
			* @return Map
			* @author 骆利锋 2017年6月5日 下午4:54:15
	 */
	public List<CarDataEntity> loginGetCarInfo(String openid) throws EpiccException{
		ReqTrans1038Entity reqTrans1038Entity = new ReqTrans1038Entity();
		reqTrans1038Entity.setOpenid(openid);
		logger.info("reqTrans1038Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1038Entity));
		List<CarDataEntity> carDataEntityList = new ArrayList<CarDataEntity>();
		ResTrans1038Entity executeTrans1038 = transService.executeTrans1038(reqTrans1038Entity);
		List<ResTrans1038CarDataEntity> resTrans1038CarDataEntityList = executeTrans1038.getResTrans1038BodyEntity().getResTrans1038CarDataEntityList();
		if(resTrans1038CarDataEntityList != null){
			for (ResTrans1038CarDataEntity car : resTrans1038CarDataEntityList) {
				CarDataEntity carDataEntity = new CarDataEntity();
				List<IdentifyDateEntity> identifyDateEntitylist =  new ArrayList<IdentifyDateEntity>();
				for(ResTrans1038IdentifyDateEntity IdentifyDateEntity : car.getResTrans1038IdentifyDateEntityList()){
					IdentifyDateEntity identify = new IdentifyDateEntity();
					TransUtil.copyObject(IdentifyDateEntity ,identify);
					identifyDateEntitylist.add(identify);
				}
				carDataEntity.setIdentifyDateEntityList(identifyDateEntitylist);
				carDataEntity.setLicenseNo(car.getLicenseNo());
				carDataEntityList.add(carDataEntity);
			}
		}
		return carDataEntityList;
	}
	/**
	 * 
			* 描述:
			* 解绑用户车辆
			* @param username
			* 			总公司注册用户
			* @param licenseNoList
			* 				车牌列表，多个用英文逗号分隔
			* @return
			* @author 许宝众2017年6月27日 下午2:39:32
	 */
	public com.alibaba.fastjson.JSONObject unbindCar(String openId,String userName,String licenseNoList)throws EpiccException{
		com.alibaba.fastjson.JSONObject json = new com.alibaba.fastjson.JSONObject();
		json.put("resCode", "1");//成功返回
		ReqTrans1039Entity reqTrans1039Entity = new ReqTrans1039Entity();
		reqTrans1039Entity.setOpenId(openId);
		reqTrans1039Entity.setUserName(userName);
		reqTrans1039Entity.setLicenseNoList(licenseNoList);
		ResTrans1039Entity executeTrans1039 = transService.executeTrans1039(reqTrans1039Entity);
		if ("0".equals(executeTrans1039.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1039.getResHeadEntity().getResponse_message();
			json.put("resCode", "0");//失败返回
			json.put("errMsg", errMsg);//详细错误信息
		}else{
			json.put("resCode", "1");//成功返回
		}
		return json;
	}
	
	/**
	 * 洗车网点查询
			* 描述:
			* @param queryType
			* @param queryContent
			* @param longitude
			* @param latitude
			* @param networkType
			* @return JSONObject
			* @author 骆利锋 2017年7月10日 下午2:34:15
	 */
	public JSONObject queryWashCarNetWork(String queryType,String queryContent,String longitude,String latitude,String networkType) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1040Entity reqTrans1040Entity = new ReqTrans1040Entity();
		reqTrans1040Entity.setQueryType(queryType);
		reqTrans1040Entity.setQueryContent(queryContent);
		reqTrans1040Entity.setLongitude(longitude);
		reqTrans1040Entity.setLatitude(latitude);
		reqTrans1040Entity.setNetworkType(networkType);
		logger.info("reqTrans1040Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1040Entity));
		List<NetworkEntity> networkEntityList = new ArrayList<NetworkEntity>();
		ResTrans1040Entity executeTrans1040 = transService.executeTrans1040(reqTrans1040Entity);
		if ("0".equals(executeTrans1040.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1040.getResHeadEntity().getResponse_message();
			json.put("resCode", 0);
			json.put("errMsg", errMsg);
		} else {
			List<ResTrans1040NetworkDataEntity> resTrans1040NetworkDataEntityList = executeTrans1040.getResTrans1040BodyEntity().getResTrans1040BasePartEntity().getResTrans1040NetworkDataEntityList();
			if(resTrans1040NetworkDataEntityList != null){
				for (ResTrans1040NetworkDataEntity networkData : resTrans1040NetworkDataEntityList) {
					NetworkEntity networkEntity = new NetworkEntity();
					TransUtil.copyObject(networkData, networkEntity);
					networkEntityList.add(networkEntity);
				}
			}
			json.put("resCode", 1);
			json.put("list", networkEntityList);
		}
		return json;
	}
	
	/**
	 * 生成洗车随机码
			* 描述:
			* @param goodsSeqNumber
			* @return 
			* @author 骆利锋 2017年7月10日 下午2:34:15
	 */
	public JSONObject generateWashCarCode(String goodsSeqNumber,String openId) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1041Entity reqTrans1041Entity = new ReqTrans1041Entity();
		reqTrans1041Entity.setGoodsSeqNumber(goodsSeqNumber);
		reqTrans1041Entity.setOpenId(openId);
		logger.info("reqTrans1041Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1041Entity));
		ResTrans1041Entity executeTrans1041 = transService.executeTrans1041(reqTrans1041Entity);
		if("1".equals(executeTrans1041.getResHeadEntity().getResponse_code().trim())){
			String washCarCode = executeTrans1041.getResTrans1041BodyEntity()
					.getResTrans1041BasePartEntity().getWashCarCode();
			json.put("resCode", 1);
			json.put("washCarCode", washCarCode);
		} else {
			String errMsg = executeTrans1041.getResHeadEntity().getResponse_message();
			json.put("errMsg", errMsg);
			json.put("resCode", 0);
		}
		return json;
	}
	private HttpServletRequest getCurrentRequest(){
		return ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
	}
	
	/**
	 * 
			* 描述:洗车记录查询
			* @param goodsSeqNumber
			* @return
			* @throws EpiccException
			* @author 朱久满 2017年7月24日 下午1:14:48
	 */
	public JSONObject queryWashCarRecord(String goodsSeqNumber) throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1044Entity reqTrans1044Entity = new ReqTrans1044Entity();
		reqTrans1044Entity.setGoodsSeqNumber(goodsSeqNumber);
		logger.info("reqTrans1044Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1044Entity));
		List<WashCarRecord> recordList = new ArrayList<WashCarRecord>();
		ResTrans1044Entity executeTrans1044 = transService.executeTrans1044(reqTrans1044Entity);
		if ("0".equals(executeTrans1044.getResHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1044.getResHeadEntity().getResponse_message();
			json.put("resCode", 0);
			json.put("errMsg", errMsg);
		} else {
			List<ResTrans1044RecordDataEntity> resTrans1044RecordDataEntityList = executeTrans1044.getResTrans1044BodyEntity().getResTrans1044BasePartEntity().getResTrans1044RecordDataEntityList();
			if(resTrans1044RecordDataEntityList != null){
				for (ResTrans1044RecordDataEntity recordData : resTrans1044RecordDataEntityList) {
					WashCarRecord washCarRecord = new WashCarRecord();
					TransUtil.copyObject(recordData, washCarRecord);
					recordList.add(washCarRecord);
				}
			}
			json.put("resCode", 1);
			json.put("list", recordList);
		}
		return json;
	}
	
	/**
	 * 
			* 描述:获取全年洗车记录
			* @param month
			* @param goodsSeqNumber
			* @return
			* @throws EpiccException
			* @author lxp 2017年12月12日 下午2:07:51
	 */
	public JSONObject getAllYearWashConsumeRecord(String month, String goodsSeqNumber)  throws EpiccException{
		JSONObject json = new JSONObject();
		ReqTrans1059Entity reqTrans1059Entity = new ReqTrans1059Entity();
		reqTrans1059Entity.setMonth(month);
		reqTrans1059Entity.setGoodsSeqNumber(goodsSeqNumber);
		logger.info("reqTrans1059Entity:"+com.alibaba.fastjson.JSON.toJSONString(reqTrans1059Entity));
		ResTrans1059Entity executeTrans1059 = transService.executeTrans1059(reqTrans1059Entity);
		List<WashCarRecord> recordList = new ArrayList<WashCarRecord>();
		if ("0".equals(executeTrans1059.getResuestHeadEntity().getResponse_code().trim())) {
			String errMsg = executeTrans1059.getResuestHeadEntity().getResponse_message();
			json.put("resCode", 0);
			json.put("errMsg", errMsg);
		} else {
			List<ResTrans1059RecordListEntity> resTrans1059RecordDataEntityList = executeTrans1059.getResTrans1059BodyEntity().getResTrans1059RecordListEntityList();
			if(resTrans1059RecordDataEntityList != null){
				for (ResTrans1059RecordListEntity recordData : resTrans1059RecordDataEntityList) {
					WashCarRecord washCarRecord = new WashCarRecord();
					TransUtil.copyObject(recordData, washCarRecord);
					recordList.add(washCarRecord);
				}
			}
			json.put("resCode", 1);
			json.put("list", recordList);
		}
		logger.info("json="+json);
		return json;
	}
	
	
	/**
	 * 
			* 描述:根据条件查询维修保养代金劵网点
			* @param queryType
			* @param queryContent
			* @param longitude
			* @param latitude
			* @param networkType
			* @return JSONObject
			* @throws EpiccException
			* @author 赵硕  2017年12月13日 上午9:23:39
	 */
	public JSONObject queryBaoYangCouponNetWork(String queryType,String queryContent,String longitude,String latitude,String networkType ,String brandId) throws EpiccException{
		JSONObject json = new JSONObject();
		logger.info("根据条件查询维修保养代金劵网点："+queryType+" "+queryContent+" "+networkType);
		if("1".equals(queryType)){//按网点名称查询
			StringBuffer sql = new StringBuffer();
			List valueList = new ArrayList();
			sql.append(" SELECT * FROM S_DIC_BRAND_NETWORK  BRAND LEFT JOIN S_DIC_NETWORKALL  NETWORK ON BRAND.NETWORKID = NETWORK.ID  WHERE NETWORK.FLAG='1' ");
			if( org.apache.commons.lang.StringUtils.isNotBlank(queryContent)){
				sql.append(" AND NETWORK.NETWORKNAME LIKE ?");
			    valueList.add("%" + queryContent + "%");
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(networkType)){
				sql.append(" AND BRAND.NETWORKTYPE = ? ");
			    valueList.add(networkType);
			}
			if(org.apache.commons.lang.StringUtils.isNotBlank(brandId)){
				sql.append(" AND BRAND.BRANDID = ? ");
			    valueList.add(brandId);
			}
			sql.append("and network.flag='1'");
			logger.info("按网点名称查询SQL："+sql.toString());
			System.out.println("按网点名称查询SQL："+sql.toString());
			List<BaoYangNetworkEntity> list = jdbcTemplate.query(sql.toString(),valueList.toArray(),new RowMapper<BaoYangNetworkEntity>(){
				public BaoYangNetworkEntity mapRow(ResultSet rs, int arg1) throws SQLException {
					BaoYangNetworkEntity baoYangNetWorkEntity = new BaoYangNetworkEntity();
					baoYangNetWorkEntity.setNetworkJobId(rs.getString("networkjobid"));//网点工号
					baoYangNetWorkEntity.setLatitude(rs.getString("latitude")); //纬度
					baoYangNetWorkEntity.setLongitude(rs.getString("longitude")); //经度
					baoYangNetWorkEntity.setNetworkAddress(rs.getString("networkaddress")); //网点地址
					baoYangNetWorkEntity.setNetworkPhone(rs.getString("networkphone"));//网点手机号
					baoYangNetWorkEntity.setNetworkName(rs.getString("networkname")); //网点名称
					return baoYangNetWorkEntity;
				}
			});
			json.put("resCode", 1);
			json.put("list", list);
		}else if("2".equals(queryType)){//按汽车品牌查询
			StringBuffer sql = new StringBuffer();
			List valueList = new ArrayList();
			if( org.apache.commons.lang.StringUtils.isNotBlank(queryContent)){
				sql.append("SELECT * FROM S_DIC_BRAND_NETWORK BRAND LEFT JOIN S_DIC_NETWORKALL NETWORK ON BRAND.NETWORKID = NETWORK.ID WHERE NETWORK.FLAG='1'");
				sql.append(" AND BRAND.NETWORKTYPE = ?");
				sql.append(" AND BRAND.BRANDID IN (SELECT BRANDID FROM S_DIC_BRAND WHERE BRANDNAME LIKE ?)");
			    valueList.add(networkType);
			    valueList.add("%" + queryContent + "%");
			    logger.info("按汽车品牌查询SQL："+sql.toString());
			    System.out.println("按汽车品牌查询SQL："+sql.toString());
			    List<BaoYangNetworkEntity> list = jdbcTemplate.query(sql.toString(),valueList.toArray(),new RowMapper<BaoYangNetworkEntity>(){
					public BaoYangNetworkEntity mapRow(ResultSet rs, int arg1) throws SQLException {
						BaoYangNetworkEntity baoYangnetWorkEntity = new BaoYangNetworkEntity();
						baoYangnetWorkEntity.setNetworkJobId(rs.getString("networkjobid"));//网点工号
						baoYangnetWorkEntity.setLatitude(rs.getString("latitude")); //纬度
						baoYangnetWorkEntity.setLongitude(rs.getString("longitude")); //经度
						baoYangnetWorkEntity.setNetworkAddress(rs.getString("networkaddress")); //网点地址
						baoYangnetWorkEntity.setNetworkPhone(rs.getString("networkphone"));//网点手机号
						baoYangnetWorkEntity.setNetworkName(rs.getString("networkname")); //网点名称
						return baoYangnetWorkEntity;
					}
				});		  
				json.put("resCode", 1);
				json.put("list", list);
			}else{
				json.put("resCode",0);
				json.put("errMsg", "汽车品牌错误");
			}
			
		}else{
			json.put("resCode",0);
			json.put("errMsg", "参数错误");
		}
	
		return json;
	}
	
	/**
	 * 
	 * 描述:维修保养代金券多次消费
	 * @param baoYangCouponConsume
	 * @return
	 * @throws EpiccException
	 * @author 赵硕  2017年12月8日 上午10:42:06
	 */
	public JSONObject consumeBaoYangCoupon(BaoYangCouponConsume baoYangCouponConsume)throws EpiccException{
		JSONObject json = new JSONObject(); 
		if(baoYangCouponConsume != null){
			List<NetWorkEntity> entityList = hibernateTemplate.find("from NetWorkEntity where networkJobid = ? and networkName = ? and flag='1' ", baoYangCouponConsume.getNetworkJobid(),baoYangCouponConsume.getNetworkName());
			if(entityList != null && entityList.size()>0){
				baoYangCouponConsume.setNetworkAddress(entityList.get(0).getNetworkAddress());
				baoYangCouponConsume.setNetworkPhone(entityList.get(0).getNetworkPhone());
				ReqTrans1057Entity reqTrans1057Entity = new ReqTrans1057Entity();
				TransUtil.copyObject(baoYangCouponConsume, reqTrans1057Entity);
				logger.info("reqTrans1057FileEntity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1057Entity));
				ResTrans1057Entity resTrans1057Entity = transService.executeTrans1057(reqTrans1057Entity);
				logger.info("落地系统返回reqTrans1057FileEntity="+com.alibaba.fastjson.JSON.toJSONString(resTrans1057Entity));
				if ("1".equals(resTrans1057Entity.getResuestHeadEntity().getResponse_code().trim())) {
					json.put("resCode", 1);
					json.put("data", resTrans1057Entity.getResTrans1057BodyEntity().getResTrans1057BasePartEntity());
				}else{
					String errMsg = resTrans1057Entity.getResuestHeadEntity().getResponse_message();
					json.put("resCode", 0);
					json.put("errMsg", errMsg);
				}
			}else{
				json.put("resCode", 0);
				json.put("errMsg", "网点不存在");
			}
			
			
			
		}	
		return json;
	}
	
	/**
	 * 
	 * 描述:维修保养代金卷使用记录查询
	 * @return Map
	 * @author 赵硕 2017年12月7日 下午9:51:48
	 */
	public Map<String, Object> queryBaoYangCouponRecord(String licenseNo ,String openId) throws EpiccException{
		Map<String, Object> map = new HashMap<String, Object>();
		ReqTrans1058Entity reqTrans1058Entity = new ReqTrans1058Entity();
		reqTrans1058Entity.setLicenseNo(licenseNo);
		reqTrans1058Entity.setOpenId(openId);
		logger.info("reqTrans1058FileEntity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1058Entity));
		List<BaoYangCouponRecord> list = new ArrayList<BaoYangCouponRecord>();
		ResTrans1058Entity resTrans1058Entity = transService.executeTrans1058(reqTrans1058Entity);
		if ("1".equals(resTrans1058Entity.getResuestHeadEntity().getResponse_code().trim())) {
			List<ResTrans1058CardDataEntity> resTrans1058CardDataEntityList = resTrans1058Entity.getResTrans1058BodyEntity().getResTrans1058CardDataEntityList();
			if(resTrans1058CardDataEntityList != null){
				for (ResTrans1058CardDataEntity resTrans1058CardDataEntity : resTrans1058CardDataEntityList) {
					BaoYangCouponRecord baoYangCouponRecord = new BaoYangCouponRecord();
					List<NetWorkEntity> entityList = hibernateTemplate.find("from NetWorkEntity where networkName = ? and networkType = '1064' and flag='1' order by maxcount desc", resTrans1058CardDataEntity.getNetWorkName());
					NetWorkEntity netWorkEntity  = 	entityList.get(0);				
					baoYangCouponRecord.setConsumeAmount(resTrans1058CardDataEntity.getConsumeAmount());
					baoYangCouponRecord.setConsumeTime(resTrans1058CardDataEntity.getConsumeTime());
					baoYangCouponRecord.setNetworkName(resTrans1058CardDataEntity.getNetWorkName());
					baoYangCouponRecord.setNetworkJobid(netWorkEntity.getNetworkJobid());
					baoYangCouponRecord.setNetworkPhone(netWorkEntity.getNetworkPhone());
					baoYangCouponRecord.setNetworkAddress(netWorkEntity.getNetworkAddress());
					list.add(baoYangCouponRecord);
				}
			}
			String remainAmount = resTrans1058Entity.getResTrans1058BodyEntity().getRsTrans1058BasePartEntity().getRemainAmount();
			map.put("resCode", 1);
			map.put("remainAmount", remainAmount);
			map.put("list", list);
		}else{
			String errMsg = resTrans1058Entity.getResuestHeadEntity().getResponse_message();
			map.put("resCode", 0);
			map.put("errMsg", errMsg);
		}
		return  map;
	}
	/**
	 * 
			* 描述:全年保养劵使用记录查询
			* @param cardNo
			* @return
			* @throws EpiccException
			* @author 赵硕 2017年12月18日 下午1:35:27
	 */
	public JSONObject queryAllYearBaoYangCouponRecord(String cardNo) throws EpiccException{
		JSONObject json  = new JSONObject();
		Map<String, Object> map = new HashMap<String, Object>();
		ReqTrans1061Entity reqTrans1061Entity = new ReqTrans1061Entity();
		reqTrans1061Entity.setCardId(cardNo);
		logger.info("reqTrans1061FileEntity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1061Entity));
		ResTrans1061Entity resTrans1061Entity = transService.executeTrans1061(reqTrans1061Entity);
		if ("1".equals(resTrans1061Entity.getResuestHeadEntity().getResponse_code().trim())) {
			List<ResTrans1061CardDataEntity> resTrans1061CardDataEntityList = resTrans1061Entity.getResTrans1061BodyEntity().getResTrans1061CardDataEntityList();
			json.put("resCode", 1);
			json.put("list", resTrans1061CardDataEntityList);
		}else {
			String errMsg = resTrans1061Entity.getResuestHeadEntity().getResponse_message();
			json.put("resCode", 0);
			json.put("errMsg", errMsg);
		}
		return  json;
	}
	
	/**
	 * 
			* 描述:取消全年保养劵订单
			* @param orderNo
			* @param openId
			* @return
			* @throws EpiccException
			* @author 赵硕  2017年12月20日 下午3:56:51
	 */
	public JSONObject allYearBaoYangOrderCancel(String orderNo ,String openId)throws Exception,EpiccException{
		JSONObject json = new JSONObject(); 
		ReqTrans1062Entity reqTrans1062Entity = new ReqTrans1062Entity();
		reqTrans1062Entity.setOpenId(openId);
		reqTrans1062Entity.setOrderNo(orderNo);
		logger.info("取消全年保养劵订单请求reqTrans1062Entity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1062Entity));
		System.out.println("取消全年保养劵订单请求reqTrans1062Entity="+com.alibaba.fastjson.JSON.toJSONString(reqTrans1062Entity));
		ResTrans1062Entity resTrans1062Entity = transService.executeTrans1062(reqTrans1062Entity);
		logger.info("落地系统返回resTrans1062Entity="+com.alibaba.fastjson.JSON.toJSONString(resTrans1062Entity));
		System.out.println("落地系统返回resTrans1062Entity="+com.alibaba.fastjson.JSON.toJSONString(resTrans1062Entity));
		if ("1".equals(resTrans1062Entity.getResuestHeadEntity().getResponse_code().trim())) {
			json.put("resCode", 1);
			json.put("okMsg","取消订单成功");
		}else{
			String errMsg = resTrans1062Entity.getResuestHeadEntity().getResponse_message();
			json.put("resCode", 0);
			json.put("errMsg", errMsg);
		}
		return json;	
	}
	
	
	/**
	 * 
	 * 描述:查询品牌
	 * @param networkType
	 * @return List<BrandEntity>
	 * @author 赵硕2018年1月16日 下午4:27:47
	 */
	public List<BrandEntity> queryBrand(String networkType) {
		String sql = "select * from s_dic_brand s where exists (select 1 from s_dic_brand_network  where s.brandid = brandid and networktype = ? )  order by bfirstletter ";
		List<BrandEntity> list = jdbcTemplate.query(sql,new Object[]{networkType},new RowMapper<BrandEntity>(){
			public BrandEntity mapRow(ResultSet rs, int arg1) throws SQLException {
				BrandEntity brandEntity = new BrandEntity();
				brandEntity.setBrandid(rs.getString("brandid"));
				brandEntity.setBfirstletter(rs.getString("bfirstletter"));
				brandEntity.setBrandName(rs.getString("brandName"));	
				return brandEntity;
			}
		});
		return list;
	}
	
	/**
	 * 
			* 描述:查询2018版洗车卡当日洗车次数是否超过限制
			* @param licenseNo
			* @author 朱久满 2018年2月13日 下午4:36:38
	 */
	public void washCardPaymentNumQry(String licenseNo,JSONObject resJson)throws Exception{
		SimpleDateFormat sim = new SimpleDateFormat("yyyy_MM_dd");
		Date nowDate = new Date();
		String washCardKey = "RBXC_LIMIT_WASH_COUNT_"+sim.format(nowDate)+"_"+licenseNo;
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String num = jedis.get(washCardKey) == null ? "0" : jedis.get(washCardKey);
			if(org.apache.commons.lang.StringUtils.isNotBlank(num)){
				Integer washCardNum = Integer.valueOf(num);
				if(washCardNum<4){
					resJson.put("resCode", "1");
					resJson.put("errMsg", "当日洗车次数没有超过上限");
				}else{
					resJson.put("errMsg", "当日洗车卡洗车次数超过限制");
				}	
			}else{
				resJson.put("errMsg", "获取当日洗车次数失败");
			}
		}catch(Exception e){
			e.printStackTrace();
			resJson.put("errMsg", "获取当日洗车次数失败");
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	/**
	 * 
	 * 描述:数据库中获取主页升级提示文字内容
	 * @return
	 * @author 赵硕 2018年4月10日 上午10:40:47
	 */
	public String getUpgradeMessage(){
		List<String> list = new ArrayList<String>();
		String sql = "select * from s_dic_content where dictypeid = 'UPGRADE_MESSAGE'";
		list = jdbcTemplate.query(sql,new Object[]{},new RowMapper<String>(){
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				return rs.getString("idvalue");
			}
		});
	 return list.get(0)==null? "" : list.get(0);
	}
	/**
	 * 
	 * 描述:赠送礼品1070
	 * @author 赵硕 2018年4月12日 上午11:25:18
	 */
	public void giveGiftService(String promotionNumber,String openId,JSONObject resjson,String licenseNo,String giftSourceFlag)throws Exception{
		ReqTrans1070Entity req1070 = new ReqTrans1070Entity();
		req1070.setPromotionNumber(promotionNumber);
		req1070.setOpenId(openId);
		req1070.setLicenseNo(licenseNo);
		req1070.setGiftSourceFlag(giftSourceFlag);
		ResTrans1070Entity res1070 =  (ResTrans1070Entity)transService.genericExecuteTrans("1070", req1070, ResTrans1070Entity.class);
		ResponseHeadEntity resHead = res1070.getResuestHeadEntity();
		String responseCode= resHead.getResponse_code();
		if("1".equals(responseCode)){
			String id = res1070.getBody().getBasePart().getId();
			String wxUserName =  WeixinUtil.getWxUserName(openId);
			if(StringUtils.hasText(id)){
				resjson.put("resCode", "1");
				resjson.put("id", CommonUtils.encodeCardNo(id));
		        resjson.put("promotionNumber",CommonUtils.encodeCardNo(promotionNumber));
		        resjson.put("shareUserName",wxUserName);//微信昵称
		        resjson.put("shareOpenId",CommonUtils.encodeCardNo(openId));//分享人openid
		        resjson.put("giftSourceFlag",giftSourceFlag);//礼品来源
			}else{
				resjson.put("errMsg", "返回参数有误");
			}
			
		}else{
			String responseMessage= resHead.getResponse_message();
			resjson.put("errMsg", responseMessage);
		}
	}
	/**
	 * 
	 * 描述:赠送礼品确认
	 * @param  id
	 * @param  shareStatus
	 * @param  resjson
	 * @param  promotionNumber
	 * @param  licenseNo
	 * @param  openId
	 * @throws EpiccException
	 * @author 赵硕 2018年6月4日 下午6:16:37
	 */
	public void giveGiftAgree(String id,String shareStatus,JSONObject resjson,String promotionNumber,String licenseNo,String giftSourceFlag,String openId) throws EpiccException{
		ReqTrans1073Entity req1073 = new ReqTrans1073Entity();
		req1073.setId(id);
		req1073.setShareStatus(shareStatus);
		req1073.setGiftSourceFlag(giftSourceFlag);
		req1073.setOpenId(openId);
		req1073.setPromotionNumber(promotionNumber);
		req1073.setLicenseNo(licenseNo);
		ResTrans1073Entity res1073 =  (ResTrans1073Entity)transService.genericExecuteTrans("1073", req1073, ResTrans1073Entity.class);
		ResponseHeadEntity resHead = res1073.getResuestHeadEntity();
		String responseCode= resHead.getResponse_code();
		if("1".equals(responseCode)){
			resjson.put("resCode", "1");//默认失败
		}else{
			String responseMessage= resHead.getResponse_message();
			resjson.put("errMsg", responseMessage);
		}
	}
	
	
	/**
	 * 
			* 描述:校验礼品分享
			* @param openId
			* @param id
			* @return
			* @author 朱久满 2018年4月13日 下午3:55:33
	 */
	public HashMap<String, Object> verifyReceiveGifts(String openId,String id)throws Exception{
		HashMap<String , Object> map = new HashMap<String , Object>();
		boolean flag = false;
		String resMsg = null;
		ReqTrans1068Entity req1068 = new ReqTrans1068Entity();
     	req1068.setId(id);
     	ResTrans1068Entity res1068 = (ResTrans1068Entity)transService.genericExecuteTrans("1068",req1068,ResTrans1068Entity.class);
     	ResponseHeadEntity head = res1068.getResuestHeadEntity();
     	String resCode  = head.getResponse_code();
     	resMsg =  head.getResponse_message();
     	map.put("resCode", resCode);
		map.put("resMsg", resMsg);
		return map;
	}
	
	/**
	 * 
	 * 描述:好友领取礼品
	 * @param resjson
	 * @param vo
	 * @throws Exception
	 * @author 朱久满 2018年4月16日 下午3:44:44
	 */
	public void getGiftService(JSONObject resjson , FriendGetGiftVo vo)throws Exception{
		ReqTrans1069Entity req1069 = new ReqTrans1069Entity();
		TransUtil.copyObject(vo, req1069);
		ResTrans1069Entity res1069 =  (ResTrans1069Entity)transService.genericExecuteTrans("1069", req1069, ResTrans1069Entity.class);
		ResponseHeadEntity resHead = res1069.getResuestHeadEntity();
		String responseCode= resHead.getResponse_code();
		if("1".equals(responseCode)){
			resjson.put("resCode", "1");
		}else{
			String responseMessage= resHead.getResponse_message();
			resjson.put("resMsg", responseMessage);
		}
	}
	
	/**
	 * 
			* 描述:根据条件获取网点信息
			* @param networkQueryVo
			* @return
			* @author 朱久满 2018年3月28日 上午11:05:32
	 */
	public List<NetworkVo> getNetwork(NetworkQueryVo networkQueryVo){
		StringBuffer sql  = new StringBuffer("SELECT * FROM S_DIC_BRAND_NETWORK  BRAND LEFT JOIN S_DIC_NETWORKALL  NETWORK ON BRAND.NETWORKID = NETWORK.ID  WHERE 1 = 1");
		List<Object> list = new ArrayList<Object>();
		String brandId = networkQueryVo.getBrandId();   //品牌id
		if(org.apache.commons.lang.StringUtils.isNotBlank(brandId)){
			sql.append("AND BRAND.BRANDID = ?");
			list.add(brandId);
		}
		String brandType = networkQueryVo.getBrandType();   //服务品牌类型  --1 专修品牌  --0 兼修品牌
		if(org.apache.commons.lang.StringUtils.isNotBlank(brandType)){
			sql.append("AND BRAND.BRANDTYPE = ?");
			list.add(brandType);
		}	
		String parentNetworkJobid = networkQueryVo.getParentNetworkJobid();  //上级工号
		if(org.apache.commons.lang.StringUtils.isNotBlank(parentNetworkJobid)){
			sql.append("AND NETWORK.PARENTNETWORKJOBID = ?");
			list.add(parentNetworkJobid);
		}
			
		String networkJobid = networkQueryVo.getNetworkJobid();   //网点工号
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkJobid)){
			sql.append("AND NETWORK.NETWORKJOBID= ?");
			list.add(networkJobid);
		}	
		String networkName = networkQueryVo.getNetworkName();   //网点名称
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkName)){
			sql.append("AND NETWORK.NETWORKNAME= ?");
			list.add(networkName);
		}
		String networkPhone = networkQueryVo.getNetworkPhone();   //服务电话
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkPhone)){
			sql.append("AND NETWORK.NETWORKPHONE= ?");
			list.add(networkPhone);
		}	
		String recvmsgPhone = networkQueryVo.getRecvmsgPhone();    //短信接收人
		if(org.apache.commons.lang.StringUtils.isNotBlank(recvmsgPhone)){
			sql.append("AND NETWORK.RECVMSGPHONE= ?");
			list.add(recvmsgPhone);
		}	
		String networkAddress = networkQueryVo.getNetworkAddress();   //店址
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkAddress)){
			sql.append("AND NETWORK.NETWORKADDRESS= ?");
			list.add(networkAddress);
		}
		String networkType = networkQueryVo.getNetworkType();   //网点类型
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkType)){
			sql.append("AND BRAND.NETWORKTYPE= ?");
			list.add(networkType);
		}	
		String networkTypeName = networkQueryVo.getNetworkTypeName(); //网点类型名称
		if(org.apache.commons.lang.StringUtils.isNotBlank(networkTypeName)){
			sql.append("AND BRAND.NETWORKTYPENAME= ?");
			list.add(networkTypeName);
		}	
		String maxCount = networkQueryVo.getMaxCount();     //最大预约数
		if(org.apache.commons.lang.StringUtils.isNotBlank(maxCount)){
			sql.append("AND NETWORK.MAXCOUNT= ?");
			list.add(maxCount);
		}	
		Integer is4S = networkQueryVo.getIs4S();     //是否是4s店  0 是4S店，1 修理厂
		if(is4S!=null){
			sql.append("AND NETWORK.IS4S= ?");
			list.add(is4S);
		}
		String flag = networkQueryVo.getFlag();  // 
		if(org.apache.commons.lang.StringUtils.isNotBlank(flag)){
			sql.append("AND NETWORK.FLAG= ?");
			list.add(flag);
		}else{
			sql.append("AND NETWORK.FLAG= '1'");
		}
		sql.append("ORDER BY IS4S ASC,BRANDTYPE DESC");
		List<NetworkVo> items = new ArrayList<NetworkVo>();
		logger.info("sql:"+sql.toString());
		items = jdbcTemplate.query(sql.toString(),list.toArray(),new RowMapper<NetworkVo>(){
			public NetworkVo mapRow(ResultSet rs, int arg1) throws SQLException {
				NetworkVo networkVo = new NetworkVo();
				networkVo.setBrandId(rs.getString("brandid"));
				networkVo.setBrandType(rs.getString("brandType"));
				networkVo.setLatitude(rs.getString("latitude"));
				networkVo.setLongitude(rs.getString("longitude"));
				networkVo.setMaxCount(rs.getString("maxcount"));
				networkVo.setNetworkAddress(rs.getString("networkaddress"));
				networkVo.setNetworkJobid(rs.getString("networkjobid"));
				networkVo.setNetworkPhone(rs.getString("networkphone"));
				networkVo.setNetworkType(rs.getString("networktype"));
				networkVo.setNetworkName(rs.getString("networkName"));
				networkVo.setParentNetworkJobid(rs.getString("parentnetworkjobid"));
				networkVo.setRecvmsgPhone(rs.getString("recvmsgphone"));
				networkVo.setNetworkName(rs.getString("networkname"));
				networkVo.setIs4S(rs.getInt("is4s"));
				return networkVo;
			}
		});
		return items;
	}
	/**
	 * 
	 * 描述:礼品寄卖（1074）
	 * @param resjson
	 * @param vo
	 * @throws Exception
	 * @author 赵硕 2018年6月6日 下午2:03:23
	 */
		public void consignGift(JSONObject resjson , GiftConsignVo giftConsignVo)throws Exception{
		ReqTrans1074Entity req1074 = new ReqTrans1074Entity();
		TransUtil.copyObject(giftConsignVo, req1074);
		String cardNo = req1074.getCardId();
		String cardPass = req1074.getCardPass();
		ResTrans1074Entity res1074 =  (ResTrans1074Entity)transService.genericExecuteTrans("1074", req1074, ResTrans1074Entity.class);
		ResponseHeadEntity resHead = res1074.getResuestHeadEntity();
		String responseCode= resHead.getResponse_code();
		if("1".equals(responseCode)){
			
			YimeiGiftConSignVo yiMei= new YimeiGiftConSignVo();
			yiMei.setOrderCode(res1074.getResTrans1074BodyEntity().getResTrans1072BasePartEntity().getOrderId());
			yiMei.setPartner("renbao1");
			yiMei.setGiftCode(req1074.getPromotionNumber());
			yiMei.setGiftName(req1074.getGiftName());
			yiMei.setGiftType(req1074.getGiftType());
			yiMei.setCustomerId("");
			yiMei.setMobile("");
			yiMei.setCount("1");
			yiMei.setCode(cardNo+":"+cardPass);
			yiMei.setAmount(req1074.getConsignPrice());
			yiMei.setDiscount("");
			yiMei.setOpenid( CommonUtils.encodeOpenId(giftConsignVo.getOpenId()));
			yiMei.setSign("");
			JSONObject yiMeiJson = JSONObject.fromObject(yiMei);
			String encodeAesContent = AESUtil.aesEncryptToBytes(yiMeiJson.toString(),  Base64.encodeBase64String("17212223710f4560".getBytes()));
			String urlBase64EncodeAesContent = new String(UrlBase64.encode(org.bouncycastle.util.encoders.Base64.decode(encodeAesContent.getBytes("UTF-8"))),"UTF-8");
			resjson.put("resCode", "1");
			resjson.put("resMsg", urlBase64EncodeAesContent);
			
		}else{
			String responseMessage= resHead.getResponse_message();
			resjson.put("resMsg", responseMessage);
		}
	}
	/**
	 * 
			* 描述:好友测算(1075)
			* @throws Exception
			* @author 赵硕 2018年6月21日 下午5:05:33
	 */
		public void friendRecommendSer(JSONObject resjson , FriendRecommendVo friendRecommendVo)throws Exception{
			ReqTrans1075Entity req1075 = new ReqTrans1075Entity();
			TransUtil.copyObject(friendRecommendVo, req1075);
			
			ResTrans1075Entity res1075 =  (ResTrans1075Entity)transService.genericExecuteTrans("1075", req1075, ResTrans1075Entity.class);
			ResponseHeadEntity resHead = res1075.getResuestHeadEntity();
			String responseCode= resHead.getResponse_code();
			if("1".equals(responseCode)){
				resjson.put("resCode", "1");
			}else{
				String responseMessage= resHead.getResponse_message();
				resjson.put("resMsg", responseMessage);
			}
		}
		/**
		 * 
				* 描述:非会员好友测算(1078)
				* @throws Exception
				* @author 赵硕 2018年6月21日 下午5:05:33
		 */
			public void noNmemberRecommendSer(JSONObject resjson , DrivingInfoVo drivingInfoVo)throws Exception{
				ReqTrans1078Entity req1078 = new ReqTrans1078Entity();
				TransUtil.copyObject(drivingInfoVo, req1078);
				
				ResTrans1078Entity res1078 =  (ResTrans1078Entity)transService.genericExecuteTrans("1078", req1078, ResTrans1078Entity.class);
				ResponseHeadEntity resHead = res1078.getResuestHeadEntity();
				String responseCode= resHead.getResponse_code();
				if("1".equals(responseCode)){
					resjson.put("resCode", "1");
				}else{
					String responseMessage= resHead.getResponse_message();
					resjson.put("resMsg", responseMessage);
				}
			}
			
}

