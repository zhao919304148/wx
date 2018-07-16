package com.wx.util;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.xml.sax.InputSource;

public class WxCommonUtil {
	
	/**
	 * ??��???????��??�?�?
	 * @return
	 */
	
	
	/**
	 * ???�???��?????
	 * @param money
	 * @return
	 */
	public static String getMoney(String amount) {
		if(amount==null){
			return "";
		}
		// ???�?�????为�??为�??�?
		String currency =  amount.replaceAll("\\$|\\�?|\\,", "");  //�??????????, �? ??????$??????�?  
        int index = currency.indexOf(".");  
        int length = currency.length();  
        Long amLong = 0l;  
        if(index == -1){  
            amLong = Long.valueOf(currency+"00");  
        }else if(length - index >= 3){  
            amLong = Long.valueOf((currency.substring(0, index+3)).replace(".", ""));  
        }else if(length - index == 2){  
            amLong = Long.valueOf((currency.substring(0, index+2)).replace(".", "")+0);  
        }else{  
            amLong = Long.valueOf((currency.substring(0, index+1)).replace(".", "")+"00");  
        }  
        return amLong.toString(); 
	}
	 
	/**
	 *   �?map �???? url 
	 * @param map
	 * @return
	 */
	 public static String getUrlParamsByMap(Map<String, Object> map) {  
	     if (map == null) {  
	         return "";  
	     }  
	     StringBuffer sb = new StringBuffer();  
	     for (Map.Entry<String, Object> entry : map.entrySet()) {  
	    	 sb.append(entry.getKey().trim())
	    	   .append("=")
	    	   .append(entry.getValue()) ;
	         sb.append("&");  
	     }  
	     String s = sb.toString();  
	     if (s.endsWith("&")) {  
	         s = StringUtils.substringBeforeLast(s, "&");  
	     }  
	     return s;  
	 }
	 
	 

}
