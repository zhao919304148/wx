package com.sys.redis;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Response;
import redis.clients.jedis.Transaction;

import com.alibaba.fastjson.JSONObject;
import com.wap.dzsw.entity.CarDataEntity;
import com.wap.util.ConstantUtils;

/**
 * Redis实现http的Session机制
 * @author xubaozhong
 *
 */
public class RedisSessionService extends RedisDaoSupport{
	/**首次创建session时需要指定一个key value**/
	public static final String SESSION_DEFAULT_KEY ="null";
	/**首次创建session时需要指定一个key value**/
	public static final String SESSION_DEFAULT_VALUE ="null";
	/** session过期是时间 单位：秒（s）<br>
	 *	非正数则永久保存<br>
	 *	默认保存30分钟
	 */
	private int expire=30*60;
	
	public int getExpire() {
		return expire;
	}
	public void setExpire(int expire) {
		this.expire = expire;
	}
	public RedisSessionService(JedisPool jedisPool) {
		super(jedisPool);
	}
	/**
	 * 延长session过期时间<br>
	 * 该方法，应该在每次请求时调用
	 * @param sessionId
	 */
	public void extendSession(final String sessionId){
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			@Override
			public Object doCommands(Transaction tx) {
				
				return null;
			}
		});
	}
	/**
	 * 模拟session保存数据<br>
	 * 复杂对象将转换json保存
	 * @param sessionId
	 * 			sessionId类似http协议JSESSIONID。用于获取session对象。这里需要我们自定义<br>
	 * @param key
	 * 			session的key
	 * @param value
	 * 			key对应的value
	 * 			
	 */
	public void setAttribute(final String sessionId,final String key,Object value){
		final String valStr=RedisUtils.encodingData(value);
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			@Override
			public Object doCommands(Transaction tx) {
				tx.hset(sessionId, key, valStr);
				return null;
			}
		});
	}
	/**
	 * 取值<br>
	 * 同 方法<code>getString</code>
	 * 
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public String getAtrribute(String sessionId,String key){
		return getString(sessionId, key);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Boolean getBoolean(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
		return (Boolean) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Boolean.valueOf(res):false;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Byte getByte(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Byte.valueOf(str);
		return (Byte) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Byte.valueOf(res):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Short getShort(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Short.valueOf(str);
		return (Short) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Short.valueOf(res):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Character getChar(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return str.charAt(0);
		return (Character) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?res.charAt(0):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Integer getInt(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Integer.valueOf(str);
		return (Integer) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Integer.valueOf(res):null;
			}
		},false);
		
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Long getLong(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Long.valueOf(str);
		return (Long) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Long.valueOf(res):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Float getFloat(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Float.valueOf(str);
		return (Float) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Float.valueOf(res):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Double getDouble(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return Double.valueOf(str);
		return (Double) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?Double.valueOf(res):null;
			}
		},false);
	}
	/**
	 * 取值
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public String getString(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return String.valueOf(str);
		return (String) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				return response.get();
			}
		},false);
	}
	/**
	 * 
			* 描述:
			* 	取List<Vo>数据
			* @param sessionId
			* @param key
			* @param voClazz
			* 		
			* @return
			* @author xbz2017年6月21日 上午11:15:41
	 */
	public Object getListObject(final String sessionId,final String key,Class<?> voClazz){
		Object object = getObject(sessionId, key);
		List resList = null;
		if(object!=null){
			try{
				List<JSONObject> list=(List<JSONObject>) object;
				if(!list.isEmpty()){
					resList = new ArrayList(list.size());
					for (JSONObject jsonObject : list) {
						Object parseObject = JSONObject.parseObject(jsonObject.toJSONString(), voClazz);
						resList.add(parseObject);
					}
				}
			}catch(Exception e){
				e.printStackTrace();
				throw new RuntimeException("List数据取出失败");
			}
		}
		return resList;
	}
	/**
	 * 取值<br>
	 * 注意：
	 * 	<p>1.如果保存的数据类型是自定义的VO类型，那个可以直接取数据</p>
	 * 	<p>2.如果保存一个List<VO>类型，那么返回的将是一个List的JSONObject数据，需要手动转换</p>
	 * @param sessionId
	 * @param key
	 * @return
	 */
	public Object getObject(final String sessionId,final String key){
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		Response<String> response = tx.hget(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
//		String str = response.get();
//		if(str == null){
//			return null;
//		}
//		return JSON.parse(str);
		return (Object) super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				Response<String> response = tx.hget(sessionId, key);
				tx.exec();
				String res = response.get();
				return (res!=null)?JSONObject.parse(res):null;
			}
		},false);
	}
	public static void main(String[] args) {
		String jsonStr="[{'identifyNo':'111111','identifyType':'01','licenseNo':'xe4xbaxacJH2510','phoneNumber':'18518633758'},{'identifyNo':'222222','identifyType':'01','licenseNo':'xe4xbaxacX12345','phoneNumber':'18701100183'}]";
		
		List<CarDataEntity> userCarList = (List<CarDataEntity>)JSONObject.parse(jsonStr);
		System.out.println(userCarList);
	}
	/***
	 * 移除session中的某个key
	 * @param sessionId
	 * @param key
	 */
	public void removeAttribute(final String sessionId,final String key) {
//		jedis.watch(sessionId);
//		Transaction tx = jedis.multi();
//		jedis.hdel(sessionId, key);
//		tx.exec();
//		jedis.unwatch();
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				tx.hdel(sessionId, key);
				return null;
			}
		});
	}
	/**
	 * session失效
	 * @param sessionId
	 */
	public void invalidate(final String sessionId) {
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			
			@Override
			public Object doCommands(Transaction tx) {
				tx.del(sessionId);
				return null;
			}
		});
	}
	/**
	 * 设置session过期时间,同时会设置openId-->sessionId的过期时间
	 * @param sessionId
	 */
	public void setSessionExpire(final String sessionId) {
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			@Override
			public Object doCommands(Transaction tx) {
				tx.expire(sessionId, expire);
				return null;
			}
		});
	}
	
	/**
	 * 
			* 描述:
			* 判断sessionId是否已存在
			* @param sessionId
			* @return
			* @author 许宝众 2017年6月19日 下午12:30:34
	 */
	public Boolean exists(final String sessionId){
		return getCurrentJedis().exists(sessionId);
	}
	/**
	 * 
	* 描述:
	* 	创建sessionId,sessionId就是openid
	* @return
	* 		返回sessionId
	* @author 许宝众 2017年6月19日 下午12:19:52
	 */
	public String createSession(final String sessionId){
		super.executeCommand(new RedisCommand(new String[]{sessionId}) {
			@Override
			public Object doCommands(Transaction tx) {
				tx.hsetnx(sessionId,SESSION_DEFAULT_KEY,SESSION_DEFAULT_VALUE );
				tx.expire(sessionId, expire);
				return null;
			}
		});
		return sessionId;
	}
}
