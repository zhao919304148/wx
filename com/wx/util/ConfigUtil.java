package com.wx.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

public class ConfigUtil {
	
	
	private static final String ORDER_CONFIG_FILE = "cblwx.properties";
	
	public static PropertiesConfiguration getConfig(String file) {
		PropertiesConfiguration config = null;
		try {
			config = new PropertiesConfiguration();
			config.setEncoding("utf-8");
			config.load(file);
			config.setAutoSave(true);
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			throw new RuntimeException( e
					.getLocalizedMessage());
		}
		return config;
	}
	
	public static String getString(String key) {
		return getConfig(ORDER_CONFIG_FILE).getString(key);
	}
	
	public static List<String> getList(String key) {
		return getConfig(ORDER_CONFIG_FILE).getList(key);
	}
	
	public static void updateProp(String key, String value) {
		getConfig(ORDER_CONFIG_FILE).setProperty(key, value);
	}
	
	public static Double getDouble(String key) {
		return getConfig(ORDER_CONFIG_FILE).getDouble(key);
	}
	
	public static BigDecimal getBigDecimal(String key) {
		return getConfig(ORDER_CONFIG_FILE).getBigDecimal(key);
	}
	public static String getDistance(String distance){
		String result="";
        String []d=distance.split("[.]");
        if(d.length>=2){
        	distance=d[0];
        }
        DecimalFormat df=new DecimalFormat(".##");
        if(distance!=null){
        	int intdistance=Integer.parseInt(distance);
        	if(intdistance>1000){
        		result=df.format(Double.parseDouble(distance)*0.001)+"公里";
        	}else{
        		if(intdistance==1){
        			result="无法定位";
        		}else{
        			result=intdistance+"米";
        		}
        		
        	}
        }
		return result;
	}
	public static String getDay(String star,String end){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    Date date1 = null, date2 = null;
	    try {
	      date1 = format.parse(star);
	      date2 = format.parse(end);
	    } catch (ParseException e) {
	      e.printStackTrace();
	    }

	    long diff = date1.getTime() - date2.getTime();
	    long days = diff / (24 * 60 * 60 * 1000);
	    return days+"";
	}
//	public static int setRandom(int min, int max) {
//		//int r = getRandom(min, max);
//		return r + min;
//	}
	/**
	 * 判断字符是否是中文
	 *
	 * @param c 字符
	 * @return 是否是中文
	 */
	public static boolean isChinese(char c) {
	    Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
	    if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
	            || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
	            || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
	            || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
	            || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
	        return true;
	    }
	    return false;
	}
	 
	
	
	public static String substr(String str,int length){
		if(str.length()>length){
			str=str.substring(0, length)+"...";
		}
		return str;
	}
	public static String substr(String str,int length,String type){
		if(str.length()>length){
			str=str.substring(0, length);
		}
		return str;
	}
	
	public static String getJf(HttpServletRequest request){
		Cookie cookieUser= CookieUtil.getCookieByName(request, "user");
		String jf="0";
		JSONObject jsonUser=new JSONObject();
		if(cookieUser!=null){
			jsonUser=JSONObject.fromObject(cookieUser.getValue());
			String cardNumber=jsonUser.getString("userAccount");
			JSONObject jsonNo=new JSONObject();
			String insService=ConfigUtil.getString("insService");
			jsonNo.element("cardNumber", cardNumber);
			String data="type=1&servletType=1&methodName=getCardInfo&paramStr="+jsonNo;
			String result=HttpRequestUtil.sendHttpPostRequest(insService, data);
			JSONObject jsonResult=JSONObject.fromObject(result);
			if(jsonResult.getString("result").equals("true")){
				jf= jsonResult.getString("jf");
			}
		}
		
		
		return jf;
	}
	public static boolean theTime(String mydate){
		boolean result=false;
		Date nowdate=new Date(); 
		String myString = mydate;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date d;
		try {
			d = sdf.parse(myString);
			boolean flag = d.before(nowdate);
			if(!flag)
			result=true;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(result);
		return result;
	}
	/**
	 * 判断字符串是否是乱码
	 *
	 * @param strName 字符串
	 * @return 是否是乱码
	 */
	public static boolean isMessyCode(String strName) {
	    Pattern p = Pattern.compile("\\s*|\t*|\r*|\n*");
	    Matcher m = p.matcher(strName);
	    String after = m.replaceAll("");
	    String temp = after.replaceAll("\\p{P}", "");
	    char[] ch = temp.trim().toCharArray();
	    float chLength = ch.length;
	    float count = 0;
	    for (int i = 0; i < ch.length; i++) {
	        char c = ch[i];
	        if (!Character.isLetterOrDigit(c)) {
	            if (!isChinese(c)) {
	                count = count + 1;
	            }
	        }
	    }
	    float result = count / chLength;
	    if (result > 0.4) {
	        return true;
	    } else {
	        return false;
	    }
	 
	}
	
	public static String formatDate1(String time){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		try {
			Date d = formatter.parse(time);
			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
			time = format.format(d); 
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return time;
	}
	
}
