package com.wx.util;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
	/**
	 * ����cookie
	 * @param response
	 * @param name  cookie����
	 * @param value cookieֵ
	 * @param maxAge cookie��������  ����Ϊ��λ
	 */
	public static void addCookie(HttpServletResponse response,String name,String value,int maxAge){
		try{
			//value = URLEncoder.encode(value,"UTF-8");  
		    Cookie cookie = new Cookie(name,value);
		    cookie.setPath("/");
		    if(maxAge==0){
		    	cookie.setMaxAge(0);
		    }else{
		    	  cookie.setMaxAge(5*365*24*60*60);
		    }
		  
		    response.addCookie(cookie);
		}catch(Exception e){
			e.printStackTrace() ;
		}
		
	}
	/**
	 * �������ֻ�ȡcookie
	 * @param request
	 * @param name cookie����
	 * @return
	 */
	public static Cookie getCookieByName(HttpServletRequest request,String name){
	    Map<String,Cookie> cookieMap = ReadCookieMap(request);
	    if(cookieMap.containsKey(name)){
	        Cookie cookie = (Cookie)cookieMap.get(name);
	        return cookie;
	    }else{
	        return null;
	    }   
	}
	 
	 
	 
	/**
	 * ��cookie��װ��Map����
	 * @param request
	 * @return
	 */
	private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){  
	    Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
	    Cookie[] cookies = request.getCookies();
	    if(null!=cookies){
	        for(Cookie cookie : cookies){
	            cookieMap.put(cookie.getName(), cookie);
	        }
	    }
	    return cookieMap;
	}
}
