package com.sys.redis;

import com.alibaba.fastjson.JSON;

public class RedisUtils {
	/**
	 * 编码入Redis数据格式<br>
	 * 原则：
	 * <p>8种基本类型+String转换为Sting</p>
	 * <p>其他类型json保存</p>
	 * @return
	 */
	public static String encodingData(Object data){
		if(data instanceof String){
			return (String) data;
		}else if(data instanceof Short){
			return Short.toString((Short)data);
		}else if(data instanceof Character){
			return Character.toString((Character)data);
		}else if(data instanceof Integer){
			return Integer.toString((Integer)data);
		}else if(data instanceof Long){
			return Long.toString((Long)data);
		}else if(data instanceof Float){
			return Float.toString((Float)data);
		}else if(data instanceof Double){
			return Double.toString((Double)data);
		}else if(data instanceof Boolean){
			return Boolean.toString((Boolean)data);
		}else if(data instanceof Byte){
			return Byte.toString((Byte)data);
		}else{
			return JSON.toJSONString(data);
		}
	}
	
}
