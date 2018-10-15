package com.sys.dic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.alibaba.fastjson.JSONObject;
import com.sys.dic.entity.BrandEntity;
import com.sys.dic.entity.CrsBrandEntity;
import com.sys.dic.entity.CrsModelEntity;
import com.sys.dic.entity.DicContentEntity;
import com.sys.dic.entity.DicContentTEntity;
import com.sys.dic.entity.PictureEntity;
import com.wap.util.ConstantUtils;

import core.db.dao.IBaseService;
import core.db.query.HQLQuery;

public class SysDicHelper {
	private static final Log logger = LogFactory.getLog(SysDicHelper.class);
	@Autowired(required = false)
	private static  IBaseService baseService = null;

    private static List<DicContentEntity> sysDicList = new ArrayList<DicContentEntity>(); //系统级字典
    
    private static List<PictureEntity> pList = new ArrayList<PictureEntity>();//图片集
    //private static List<NetWorkEntity> ntwList = new ArrayList<NetWorkEntity>();
    private static List<BrandEntity> brandList = new ArrayList<BrandEntity>();
    private static List<CrsBrandEntity> crsBrandList = new ArrayList<CrsBrandEntity>();
    private static List<CrsModelEntity> crsModelList = new ArrayList<CrsModelEntity>();
    private static HashMap<String,ArrayList<DicContentEntity>> dbhashEntity= new HashMap<String,ArrayList<DicContentEntity>>();
    private static List<Map<String,String>> backreasonMap = new ArrayList<Map<String,String>>();
    private static ApplicationContext ctx = null ;
    private static Map<String,String> pangdaGoods = new HashMap<String,String>();//庞大礼品
    private static Map<String,String> kaladingGoods = new HashMap<String,String>();//卡拉丁礼品
    private static Map<String,String> luodiGoods = new HashMap<String,String>();//落地系统
    /**redis中微信常量**/
    private static Map<String,Object> redisWxMap = new HashMap<String,Object>();
	/**
	 * 唯一实例
	 */
	private static SysDicHelper instance=null;

	/**
	 * 防止外部构造
	 *
	 */
	private SysDicHelper(){
	}
	/**
	 * 获取唯一实例 
	 * @return
	 */
	public static SysDicHelper getInstance(){
		
		if( instance == null ){
			instance = new SysDicHelper();
		}
		return instance;
	}
    
	/**
	 * 
	 * @param ctx
	 */
	public void setApplicationContext(ApplicationContext ctx){
		this.ctx = ctx ;
		this.baseService = (IBaseService) ctx.getBean("baseService"); 
		initSysDIC();
		initDbDicList();
		initPicList();
//		initNetworkList();
		intiBrandList();
		loadRedisWxMap();
	}
	/**
	 * 加载微信在redis中的常量信息 
	 * @author 许宝众 
	 * @since 20170913 10:59:00
	 **/
	private void loadRedisWxMap(){
		JedisPool jedisPool = (JedisPool) ctx.getBean("jedisPool"); 
		Jedis jedis = null;
		try{
			jedis = jedisPool.getResource();
			String maxLimit = jedis.get(ConstantUtils.SERVER_REQUEST_MAX_LIMIT);
			if(StringUtils.isBlank(maxLimit)||NumberUtils.isDigits(maxLimit)){
				maxLimit = "50";
			}
			redisWxMap.put(ConstantUtils.SERVER_REQUEST_MAX_LIMIT, Integer.valueOf(maxLimit));
			System.out.println("redis常量数据已加载："+JSONObject.toJSONString(redisWxMap));
		}catch(Exception e){
			logger.info("加载redis常量数据失败：",e);
			e.printStackTrace();
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
	}
	/**
			* 描述:
			* 取redis常量
			* @param key
			* @return
			* @author 许宝众 2017年9月13日 上午10:59:54
	 */
	public Object getRedisConstants(String key){
		return redisWxMap.get(key);
	}
	private void initSysDIC(){
		
	}
	/**
	 * 重新加载字典，刷新缓存(页面直接调用，用于刷新字典缓存)
	 */
	public void reLoadDic()
	{
		this.initDbDicList();
		this.initPicList();
		this.intiBrandList();
		this.intiCrsBrandList();
		this.loadRedisWxMap();
	}
	
	private void intiBrandList() {
		try {
			baseService.evictObject(BrandEntity.class);
			String sql="from BrandEntity order by bfirstletter ";
			List<BrandEntity> brandList1 = baseService.findObjects(new HQLQuery(sql));
			if(brandList1 != null){
				brandList = brandList1;
			}
		} catch (RuntimeException e) {
			logger.fatal("品牌始化失败：");
			logger.fatal("  "+e.getMessage());
		}
	}
	
	private void intiCrsBrandList() {
		try {
			baseService.evictObject(CrsBrandEntity.class);
			String sql="from CrsBrandEntity order by brandname ";
			List<CrsBrandEntity> brandList1 = baseService.findObjects(new HQLQuery(sql));
			if(brandList1 != null){
				crsBrandList = brandList1;
			}
		} catch (RuntimeException e) {
			logger.fatal("品牌始化失败：");
			logger.fatal("  "+e.getMessage());
		}
	}
	
	private void intiCrsModelList(String brandname) {
		try {
			baseService.evictObject(CrsModelEntity.class);
			StringBuffer sql= new StringBuffer();
			sql.append("from CrsModelEntity where 1=1 ");
			if(brandname != null && !"".equals(brandname)){
				sql.append(" and brandname = '").append(brandname).append("' ");
			}
			sql.append("order by modelname ");
			List<CrsModelEntity> modelList1 = baseService.findObjects(new HQLQuery(sql.toString()));
			if(modelList1 != null){
				crsModelList = modelList1;
			}
		} catch (RuntimeException e) {
			logger.fatal("品牌始化失败：");
			logger.fatal("  "+e.getMessage());
		}
	}
	
	/**
	 * 		初始化喷漆和玻璃修复网点列表
			* 描述:
			* @author qex 2016年12月5日 下午5:24:44
	 */
//	private void initNetworkList() {
//		try {
//			baseService.evictObject(NetWorkEntity.class);
//			String sql="from NetWorkEntity where flag='1'";
//			List<NetWorkEntity> networkList = baseService.findObjects(new HQLQuery(sql));
//			if(networkList != null){
//				ntwList = networkList;
//			}
//		} catch (RuntimeException e) {
//			logger.fatal("喷漆和玻璃修复网点始化失败：");
//			logger.fatal("  "+e.getMessage());
//		}
//		
//	}
	
	/**
	 * 初始化图片集
	 */
	private void initPicList(){
		try {
			baseService.evictObject(PictureEntity.class);
			String sql="from PictureEntity p where p.validflag='1' order by p.indexNum";
			List<PictureEntity> tmpPicList = baseService.findObjects(new HQLQuery(sql));
			if(tmpPicList != null){
				pList = tmpPicList;
			}
		} catch (RuntimeException e) {
			logger.fatal("图片初始化失败：");
			logger.fatal("  "+e.getMessage());
		}
		
	}
	
	/**
	 * 初始化应用级字典集
	 */
	private void initDbDicList(){
		try {
			baseService.evictObject(DicContentTEntity.class);
			String sql="from DicContentTEntity dic where dic.validFlag='1' order by dic.dicTypeId,dic.displayIndex,dic.dicId";
			List<DicContentTEntity> tmpDicList = baseService.findObjects(new HQLQuery(sql));			
			if(tmpDicList != null){
				HashMap<String,ArrayList<DicContentEntity>> tempMap=new HashMap<String,ArrayList<DicContentEntity>>();
				ArrayList<DicContentEntity> tempList=new ArrayList<DicContentEntity>();
				String tempDicType="";
				for(DicContentTEntity t:tmpDicList){
					if("".equals(tempDicType)){
						tempDicType=t.getDicTypeId().toUpperCase();
						tempList.add(this.changeDicTEntity(t));
					}else if(tempDicType.equals(t.getDicTypeId().toUpperCase())){
						tempList.add(this.changeDicTEntity(t));
					}else{
						tempMap.put(tempDicType, tempList);
						tempList=new ArrayList<DicContentEntity>();
						tempDicType=t.getDicTypeId().toUpperCase();
						tempList.add(this.changeDicTEntity(t));
					}
				}
				if(tempList.size()>0&&!"".equals(tempDicType)){
					tempMap.put(tempDicType, tempList);
				}
				dbhashEntity=tempMap;
			}
			//加载反馈原因字典
			sql = "SELECT * FROM LDZH_DDFKREASON WHERE FLAG='1' ORDER BY FIRREASONCODE,SECREASONCODE,THRREASONCODE";
			backreasonMap = baseService.queryForJDBCList(sql);
		} catch (RuntimeException e) {
			logger.fatal("DB级字典初始化失败：");
			logger.fatal("   "+e.getMessage());
		}
	}
	
	private DicContentEntity changeDicTEntity(DicContentTEntity t){
		DicContentEntity m=new DicContentEntity();
		if(t!=null){
			m.setDicId(t.getDicId());
			m.setIdValue(t.getIdValue());
			m.setIdValue2(t.getIdValue2());
		}
		return m;
	}
	
    /**
     * 根据字典类型与id获取具体值
     * @param dicTypeId
     * @param dicId
     * @return
     */
	public String getValueByDicTypeAndDicId(String dicType,String dicId) {
		if(dicType==null||dicId==null){
			return "";
		}
		 //从字典集中查询字典 
		int maxNum=sysDicList.size();
		for(int i=0;i<maxNum;i++){
			DicContentEntity dic=sysDicList.get(i);
			if(dicType.toUpperCase().equals(dic.getDicTypeId())&&dicId.equals(dic.getDicId())){
				return dic.getIdValue();
			}
		}
        //系统不存在，从db集中查询字典
		List<DicContentEntity> t=dbhashEntity.get(dicType.toUpperCase());
		if(t==null||t.size()==0){
			return "";
		}
		for(int i=0;i<t.size();i++){
			DicContentEntity dic=t.get(i);
			if(dicId.equals(dic.getDicId())){
				return dic.getIdValue();
			}
		}
		return "" ;
	}
	/**
	 * 根据字典类型与id获取 字典对象
	 * @param dicTypeId
	 * @param dicId
	 * @return
	 */
	public DicContentEntity getDicContentByDicTypeAndDicId(String dicType,String dicId) {
		if(dicType==null||dicId==null){
			return null;
		}
		//从字典集中查询字典 
		int maxNum=sysDicList.size();
		for(int i=0;i<maxNum;i++){
			DicContentEntity dic=sysDicList.get(i);
			if(dicType.toUpperCase().equals(dic.getDicTypeId())&&dicId.equals(dic.getDicId())){
				return dic;
			}
		}
		//系统不存在，从db集中查询字典
		List<DicContentEntity> t=dbhashEntity.get(dicType.toUpperCase());
		if(t==null||t.size()==0){
			return null;
		}
		for(int i=0;i<t.size();i++){
			DicContentEntity dic=t.get(i);
			if(dicId.equals(dic.getDicId())){
				return dic;
			}
		}
		return null ;
	}
	/**
	 * 
			* 描述:获取一级反馈原因列表
			* @return
			* @author 朱久满 2015年12月1日 下午3:16:49
	 */
	public Map<String,String> getOnebackreasonMap(){
		Map<String, String> tmpMap = new HashMap<String, String>();;
		String firreasoncode = "";
		for (Map<String,String> map : backreasonMap) {
			if(!firreasoncode.equals(map.get("FIRREASONCODE"))){
				tmpMap.put(map.get("FIRREASONCODE"), map.get("FIRREASONNAME"));
			}
			firreasoncode = map.get("FIRREASONCODE");
		}
		return tmpMap;
	}
	/**
	 * 
	 * 描述:根据一级反馈原因获取二级列表
	 * @return
	 * @author 朱久满 2015年12月1日 下午3:16:49
	 */
	public Map<String,String> getTwobackreasonMap(String firreasoncode){
		Map<String, String> tmpMap = new HashMap<String, String>();
		String secreasoncode = "";
		for (Map<String,String> map : backreasonMap) {
			if(firreasoncode.equals(map.get("FIRREASONCODE"))){
				if(!secreasoncode.equals(map.get("SECREASONCODE"))){
					tmpMap.put(map.get("SECREASONCODE"), map.get("SECREASONNAME"));
				}
				secreasoncode = map.get("SECREASONCODE");
			}
		}
		return tmpMap;
	}
	/**
	 * 
	 * 描述:根据二级反馈原因获取三级列表
	 * @return
	 * @author 朱久满 2015年12月1日 下午3:16:49
	 */
	public Map<String,String> getThrbackreasonMap(String secreasoncode){
		Map<String, String> tmpMap = new HashMap<String, String>();
		String thrreasoncode = "";
		for (Map<String,String> map : backreasonMap) {
			if(secreasoncode.equals(map.get("SECREASONCODE"))){
				if(!thrreasoncode.equals(map.get("THRREASONCODE"))){
					tmpMap.put(map.get("THRREASONCODE"), map.get("THRREASONNAME"));
				}
				thrreasoncode = map.get("THRREASONCODE");
			}
		}
		return tmpMap;
	}
	
	/**
	 * 
			* 描述:
			* @param dicType
			* @return
			* @author 朱久满 2015年12月2日 上午10:16:44
	 */
	public Map<String,String> getMapByDicType(String dicType){
		LinkedHashMap<String,String> map=new LinkedHashMap<String,String>();
    	if(dicType==null||"".equals(dicType.trim())){
    		return map;
    	}
    	dicType=dicType.toUpperCase();
    	int maxNum=sysDicList.size();
    	//查询出的结果是根据dicType排序的
    	for(int i=0;i<maxNum;i++){
    		DicContentEntity dic=sysDicList.get(i);
			if(dicType.equals(dic.getDicTypeId())){
				if(dic.getDicId()==null){
					dic.setDicId("1");
				}
				map.put(dic.getDicId(), dic.getIdValue());
			}
		}
		//系统级中存在则直接从这里提取
		if(map.size()>0){
			return map ;
		}

		ArrayList<DicContentEntity> t=dbhashEntity.get(dicType);
		for(int i=0;i<t.size();i++){
			DicContentEntity d=t.get(i);
			map.put(d.getDicId(), d.getIdValue());
		}
		
		//系统级中存在则直接从这里提取
		//if(CollectionUtils.EMPTY_COLLECTION != resDics)
		if(map.size()>0){
			return map ;	
		}
		
		logger.error("注意：系统中不存在类型为["+dicType+"]的字典，请与开发人员联系！");
		//都不存在，赋一个初始值防止应用异常
		return map;
    }
	
	/**
	 * 获取相关页面的图集
	 */
	public List<PictureEntity> getIndexPicList(String dicType){
		List<PictureEntity> picList = new ArrayList<PictureEntity>();
		if(dicType == null){
			return null;
		}
		//从图集中查询
		int maxNum = pList.size();
		for(int i=0;i<maxNum;i++){
			PictureEntity pic = pList.get(i);
			if(dicType.toUpperCase().equals(pic.getDicTypeId().toUpperCase())){
				picList.add(pic);
			}
		}
		
		return picList;
	}
	
	/**
	 * 返回品牌列表
			* 描述:
			* @return
			* @author qex 2016年12月21日 下午3:17:20
	 */
	public List<BrandEntity> getBrandList(){
		if(brandList==null||crsBrandList.size()<=0){
			intiBrandList();
		}
		return brandList;
	}
	
	/**
	 * 返回品牌、车型关联表中品牌列表
	 * 描述:
	 * @return
	 * @author fenghairui 
	 * @date 2017年11月7日 下午13:17:20
	 */
	public List<CrsBrandEntity> getCrsBrandList(){
		if(crsBrandList==null||crsBrandList.size()<=0){
			intiCrsBrandList();
		}
		return crsBrandList;
	}
	
	/**
	 * 返回品牌、车型关联表中车型列表
	 * 描述:
	 * @return
	 * @author fenghairui 
	 * @date 2017年11月7日 下午13:17:20
	 */
	public List<CrsModelEntity> getCrsModelList(String brandname){
		if(crsModelList==null||crsModelList.size()<=0){
			intiCrsModelList(brandname);
		}else{
			String reBrandname = crsModelList.get(0).getBrandname();
			if(!brandname.equals(reBrandname)){
				intiCrsModelList(brandname);
			}
		}
		return crsModelList;
	}
	

}
