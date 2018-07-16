package com.wap.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * 
 */
public final class DateUtils {
	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";
	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";
	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";
	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";
    
    /**
     * 数字类型的日期YYYYMMdd
     */
    public static String FORMAT_INTEGER="YYYYMMdd";
    
    public static String FORMAT_INT="yyyyMMdd";
    
	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_LONG;
	}

	public static Date getNowDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		Date result = null;
		try {
			result = sdf.parse(sdf.format(date));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据预设格式返回当前日期
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parseShort(String strDate) {
		SimpleDateFormat df = new SimpleDateFormat(DateUtils.FORMAT_SHORT);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * 
	 * @Title: transToStar
	 * @Description: TODO(将分数转换为星星的个数)
	 * @author Administrator
	 * @param score
	 * @return
	 * @return int
	 * @throws
	 * @date 2014-2-19 下午03:25:13
	 */
	public static int transToStar(int score) {
		int oneStar = 15;
		int twoStar = 40;
		int threeStar = 75;
		int fourStar = 120;

		int result = 0;
		if (score <= oneStar) {
			result = 1;
		} else if (score > oneStar && score <= twoStar) {
			result = 2;
		} else if (score > twoStar && score <= threeStar) {
			result = 3;
		} else if (score > threeStar && score <= fourStar) {
			result = 4;
		} else if (score > fourStar) {
			result = 5;
		}
		return result;
	}

	/**
	 * 得到系统当前年月日小时分秒，格式 yyyyMMddHHmmss
	 * 
	 * @return
	 */
	public static String getYMDHMS() {

		String strYMDHMS = "";
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		strYMDHMS = formatter.format(currentDateTime);
		return strYMDHMS;
	}

	/**
	 * 得到系统当前年月日小时分秒，格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getDateTime() {

		String strCurrentDateTime = "";
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		strCurrentDateTime = formatter.format(currentDateTime);
		return strCurrentDateTime;
	}

	/**
	 * 日期格式转换从yyyy-MM-dd HH:mm:ss 到 yyyyMMddHHmmss
	 * 
	 * @param strDateTime
	 * @return
	 */
	public static String stringToNumber(String strDateTime) {

		String strYMDHMS = "";
		if (strDateTime == null) {

			return "";
		}
		strDateTime = strDateTime.trim();
		if (strDateTime.length() == 10) {

			strYMDHMS = strDateTime.substring(0, 4)
					+ strDateTime.substring(5, 7)
					+ strDateTime.substring(8, 10);
			strYMDHMS += "000000";
			return strYMDHMS;
		}
		if (strDateTime.length() == 18) {
			int i = strDateTime.indexOf(" ");
			int ii = strDateTime.indexOf(":");
			String tempStr = strDateTime.substring(i + 1, ii);
			tempStr = "0" + tempStr;
			strYMDHMS = strDateTime.substring(0, 4)
					+ strDateTime.substring(5, 7)
					+ strDateTime.substring(8, 10) + tempStr
					+ strDateTime.substring(13, 15)
					+ strDateTime.substring(16, 18);
			return strYMDHMS;
		}
		if (strDateTime.length() == 19) {
			int i = strDateTime.indexOf(" ");
			int j = strDateTime.lastIndexOf(" ");
			int l = strDateTime.length();
			String temp = strDateTime;
			if (i != j) {
				temp = strDateTime.substring(0, j) + "0"
						+ strDateTime.substring(j + 1, l);
			}
			strYMDHMS = temp.substring(0, 4) + temp.substring(5, 7)
					+ temp.substring(8, 10) + temp.substring(11, 13)
					+ temp.substring(14, 16) + temp.substring(17, 19);
			return strYMDHMS;
		}
		return "";
	}

	/**
	 * 得到系统当前年月日小时分秒毫秒，格式 yyyyMMddHHmmssSSS
	 * 
	 * @return
	 */
	public static String getYMDHMSS() {

		String strYMDHMSS = "";
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		strYMDHMSS = formatter.format(currentDateTime);
		return strYMDHMSS;
	}

	/**
	 * 
	 * 描述:得到date类型的日期格式
	 * 
	 * @param parseStr
	 * @return
	 * @throws Exception
	 * @author ZN
	 */
	public Date parseDate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date now = new Date();
		String myDate = sdf.format(now).toString();
		try {
			return sdf.parse(myDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 描述:比较两个日期、时间的大小
	 * @param startdate
	 * @param enddate
	 * @param format
	 * @return startdate>enddate返回-1；startdate=enddate返回0；startdate<enddate返回1.
	 * @author 2016年12月2日 下午4:58:32
	 */
	public static int compareDate(String startdate, String enddate,
			String format) {
		int result = 0;
		Date sdate = null;
		Date edate = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			sdate = sdf.parse(startdate);
			edate = sdf.parse(enddate);
			result = edate.compareTo(sdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}
     /** 
      * 根据年 月 获取对应的月份 天数 
      * */  
     public static int getDaysByYearMonth(int year, int month) {  
           
         Calendar a = Calendar.getInstance();  
         a.set(Calendar.YEAR, year);  
         a.set(Calendar.MONTH, month - 1);  
         a.set(Calendar.DATE, 1);  
         a.roll(Calendar.DATE, -1);  
         int maxDate = a.get(Calendar.DATE);  
         return maxDate;  
     }  
     
     public static void main(String[] args) {
    	 
    	 //System.out.println(DateUtils.format(DateUtils.addDay(new Date(), 24), "YYYYMMdd"));
     }
}