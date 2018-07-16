package com.wap.util;

public class ConstantUtils {
	/**访问票据：用于取session**/
	public static final String _ACCESS_TICKET = "_access_token";
	/**票据签名：防止票据被篡改**/
	public static final String _TICKET_SIGN = "_access_sign";
	/**微信openId**/
	public static final String OPEN_ID="openId";
	/**用户在总公司的注册账号**/
	public static final String USER_NAME="UserName";
	/**总公司用户绑定证件类型**/
	public static final String CENTRAL_IDENTIFY_TYPE="CentralIdentifyType";
	/**总公司用户绑定证件号码**/
	public static final String CENTRAL_IDENTIFY_NO="CentralIdentifyNo";
	/**用户绑定车辆列表**/
	public static final String USER_CAR_LIST="UserCarList";
	/**登录票据，redis中key前缀**/
	public static final String _ACCESS_TICKET_PREFFIX="ticket-";
	/**亿美固定HMACSHA256KEY 32位**/
	public static final String YM_HMACSHA256_KEY="f27d755a520f421e888540d6d2dd333c";
	/**亿美AESKey 16位**/
	public static final String YM_AESKEY="17212223710f4560";
	/**单server活动请求处理最大并发数量限制**/
	public static final String SERVER_REQUEST_MAX_LIMIT="bjwxplat-activities-request-max-limit";
	/** 企业微信token放到redis中设置的key值 **/
	public static final String QYWX_ACCESS_TOKEN_REDIS_KEY = "bjwxplat-qywx-redis-access-token-key001";
	/** 企业微信访问票据 **/
	public static final String QYWX_ACCESS_TICKET = "_qywx_access_ticket";
	/** 企业微信访问票据 **/
	public static final String QYWX_ACCESS_SIGN = "_qywx_access_sign";
	/** 企业微信访问票据 **/
	public static final String QYWX_TICKET_ID = "_qywx_ticket_id";
	
	/**限时抢购常量类**/
	public static class LimitTimeRobRabbitmqConstants{
		/**车主节每日抢活动礼品 Redis list集合key前缀，key规则：前缀-[yyyy-MM-dd]-[礼品类型]**/
		public static final String ACTIVITIES_2017_CZJ_ROB_GIFT_SET_PREFFIX="bjwxplat-czj2017-rob-gift-set-";
		/**redis中保存限时活动参与者的礼品获取状态key 前缀***/
		public static final String ACTIVITIES_2017_CZJ_LIMITE_TIME_PARTICIPANT="bjwxplat-czj2017-rob-gift-participant-";
		/**限时抢购rabbitmqTemplate模板默认路由，与applicationContext-rabbit.xml保持一致**/
		public static final String TEMPLATE_DEFAULT_ROUTE_KEY="rob-route-key";
		
	}
	/**每日领取常量类**/
	public static class PerdayGetRabbitmqConstants{
		public static final String TEMPLATE_DEFAULT_ROUTE_KEY="perday-route-key";
		/**车主节每日领取动礼品 Redis list集合key前缀，key规则：前缀-[礼品类型]**/
		public static final String ACTIVITIES_2017_CZJ_PERDAY_GIFT_SET_PREFFIX="bjwxplat-czj2017-perday-gift-set-";
		
	
	
	
	}
}
