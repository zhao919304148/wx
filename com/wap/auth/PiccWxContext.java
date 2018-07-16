//package com.wap.auth;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * 
//		
// * 描述:
// *	线程内变量存储上下文
// * @author 许宝众
// * @version 1.0
// * @since 2017年6月20日 上午9:53:38
// */
//public class PiccWxContext {
//	private static ThreadLocal<Map<String,Object>> ctx = new ThreadLocal<>();
//	public static Map<String,Object> getMap(){
//		Map<String, Object> map = ctx.get();
//		if(map==null){
//			map = new HashMap<String,Object>();
//			ctx.set(map);
//		}
//		return map;
//	}
//	public static void put(String key,Object value){
//		getMap().put(key, value);
//	}
//	
//	public static Object get(String key){
//		return getMap().get(key);
//	}
//	
//	public static void remove(String key){
//		getMap().remove(key);
//	}
//	
//	public static boolean containsKey(String key){
//		return getMap().containsKey(key);
//	}
//	
//	public static boolean containsValue(Object value){
//		return getMap().containsValue(value);
//	}
//	
//	public static Map<String,Object> getContext(){
//		return getMap();
//	}
//	
//}
