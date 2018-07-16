package com.sys.entity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 *  简单的日期实体类
 * 
 */
@Service
public class DateEntity {
	
	/**
	 * 日期格式化器
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat();
	
	/**
	 * 日期实体实例
	 */
	private Calendar cal = null;
	
	/**
	 * 构造函数<br>
	 * 获取当前日期时间
	 *
	 */
	public DateEntity(){
		cal = GregorianCalendar.getInstance();
	}
	
	/**
	 * 构造函数<br>
	 * 使用指定日期构造实体<br>
	 * @param dateYMD - 日期 yyyymmdd格式
	 */
	public DateEntity(int dateYMD){
		cal = GregorianCalendar.getInstance();
		setDate(dateYMD);
	}

	/**
	 * 构造日期
	 * @param date
	 */
	public DateEntity(Date date){
		cal.setTime(date);
	}
	
	/**
	 * 构造函数<br>
	 * 使用指定时间日期构造实体<br>
	 * @param dateYMD - 日期
	 * @param timeHMS - 时间
	 */
	public DateEntity(int dateYMD,int timeHMS){
		cal = GregorianCalendar.getInstance();
		setDate(dateYMD,timeHMS);
	}
	
	/**
	 * 克隆方法
	 */
	public Object clone()
	{
		DateEntity dt = new DateEntity();
		dt.cal = (Calendar)cal.clone();
		return (Object)dt;
	}
	
	/**
	 * toString 方法
	 */
	public String toString(){
		
		return cal.toString();
	}

	/**
	 * 获取日期格式化串
	 * @return YYYYMMDD格式
	 */
	public String getDateStr(){
		try{
						
			String pattern = "yyyyMMdd";			
			sdf.applyPattern(pattern);
			String s = sdf.format(cal.getTime());			  				
			return s;
			  	
		}catch( Exception e){
			  	
			e.printStackTrace();
			return "19000101";		  	
		}		
	}
	
	/**
	 * 获取整数日期 YYYYMMDD
	 * @return
	 */
	public int getDate(){
		
		try{
			return Integer.parseInt( getDateStr() );
			
		}catch( Exception e){
			e.printStackTrace();
			return 19000101;		  	
		}
	}
	
	/**
	 * 获取时间格式化串 HHmmss
	 * @return 
	 */
	public String getTimeStr() {
		  
		try{
			
			String pattern = "HHmmss";			
			sdf.applyPattern(pattern);
			String s = sdf.format(cal.getTime());
			return s;
			
		}catch( Exception e){
							
		  e.printStackTrace();
		  return "000000";		  	
		}
	}
	
	/**
	 * 获取整数时间
	 * @return
	 */
	public int getTime() {
		  
		try{
			return Integer.parseInt( getTimeStr() );		
		  	
		}catch( Exception e){
							
		  e.printStackTrace();
		  return 0;		  	
		}			
	}
	
	/**
	 * 获取日期时间格式化串 YYYYMMDDHHmmss
	 * @return
	 */
	public String getDateTimeStr(){
		
		return getDateStr() + getTimeStr(); 
	}
	
	/**
	 * 获取包含毫秒的时间串
	 * @return
	 */
	public String getYMDHMSS(){
		
		String strYMDHMSS="";
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyyyMMddHHmmssSSS");
		strYMDHMSS= formatter.format(currentDateTime);
		return  strYMDHMSS; 
		
	}
	/**
	 * 获取YYMMDDHHmm格式的时间字符串
	 * @return
	 */
	public String getYYMMDDHHmmss(){
		Date currentDateTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat ("yyMMddHHmmss");
		String strYMDHM= formatter.format(currentDateTime);
		return strYMDHM;
	}
	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 获取整数日
	 * @return 整数日
	 */
	public int getDay(){  return cal.get( Calendar.DAY_OF_MONTH ); }
	
	/**
	 * 获取整数月 从 1 - 12 
	 * @return
	 */
	public int getMonth(){ return cal.get( Calendar.MONTH) + 1; }
	
	/**
	 * 获取整数年
	 * @return
	 */
	public int getYear(){  return cal.get( Calendar.YEAR); }
	
	/**
	 * 获取正式小时
	 * @return
	 */
	public int getHours(){ return cal.get( Calendar.HOUR_OF_DAY );  }
	
	/**
	 * 获取整数分钟
	 * @return
	 */
	public int getMinutes(){ return cal.get( Calendar.MINUTE );  }
	
	/**
	 * 获取整数秒
	 * @return
	 */
	public int getSeconds(){ return cal.get( Calendar.SECOND );  }
	
	/**
	 * 获取整数星期,周日 1, 周一2,周二3,...周六7 
	 * @return
	 */
	public int getDayOfWeek(){ return cal.get( Calendar.DAY_OF_WEEK ); }
	
	/////////////////////////////////////////////////////////////////////////////////////
	/**
	 * 修改日
	 * @param d - 整数日
	 */
	public void setDay( int d ){ cal.set(Calendar.DAY_OF_MONTH,d); }
	
	/**
	 * 修改月
	 * @param m - 整数月
	 */
	public void setMonth( int m){	cal.set(Calendar.MONTH,m - 1);}
	
	/**
	 * 修改年
	 * @param y - 整数年
	 */
	public void setYear( int y){	cal.set(Calendar.YEAR,y);}
	
	/**
	 * 修改小时
	 * @param h - 整数小时
	 */
	public void setHour( int h ){ cal.set(Calendar.HOUR_OF_DAY,h); }
	
	/**
	 * 修改分钟
	 * @param m - 整数分钟
	 */
	public void setMinute( int m ){ cal.set(Calendar.MINUTE,m); }
	
	/**
	 * 修改秒
	 * @param s - 整数秒
	 */
	public void setSecond( int s ){ cal.set(Calendar.SECOND,s); }
	
	////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 修改日期
	 * @param dateYMD  整数日期
	 */
	private void setDate( int dateYMD ){
		int day = dateYMD % 100;
		int month = (dateYMD % 10000) / 100 - 1;
		month = (month>=0)?month:0;
		int year = dateYMD / 10000;
		cal.set(year,month,day);
	}
	
	/**
	 * 设置日期时间
	 * @param dateYMD - 整数日期
	 * @param timeHMS - 整数时间
	 */
	private void setDate( int dateYMD ,int timeHMS){
		int day = dateYMD % 100;
		int month = (dateYMD % 10000) / 100 - 1;
		month = (month>=0)?month:0;
		int year = dateYMD / 10000;

		int s = timeHMS % 100;
		int m = (timeHMS % 10000) / 100;
		int h = timeHMS / 10000;
		
		cal.set(year,month,day,h,m,s);
	}
	
	//////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * 日期计算，增加天数
	 * @param n - 天数  可以是负数
	 */
	public void addDay(int n){ cal.add(Calendar.DATE, n);}
	
	/**
	 * 当前日期 加years年days天
	 * @param inputTimeStr 需计算的日期 yyyy-mm-dd
	 * @param days
	 * @param years
	 * @return String yyyy-mm-dd
	 */
	public String dateAdd(String inputTimeStr,int days,int years){
		Date now = new Date() ;
		if(inputTimeStr != ""){
				if(inputTimeStr.substring(5,1)=="0"){
					if(inputTimeStr.substring(8,1)=="0"){
						now.setDate(Integer.parseInt(inputTimeStr.substring(0,4)));
						now.setMonth(Integer.parseInt(inputTimeStr.substring(6,1))-1);
						now.setYear(Integer.parseInt(inputTimeStr.substring(9,1)));
					}else{
						now.setDate(Integer.parseInt(inputTimeStr.substring(0,4)));
						now.setMonth(Integer.parseInt(inputTimeStr.substring(6,1))-1);
						now.setYear(Integer.parseInt(inputTimeStr.substring(8,2)));
					}
				}else if(inputTimeStr.substring(8,1)=="0"){
					now.setDate(Integer.parseInt(inputTimeStr.substring(0,4)));
					now.setMonth(Integer.parseInt(inputTimeStr.substring(5,2))-1);
					now.setYear(Integer.parseInt(inputTimeStr.substring(9,1)));
			 	}else{
			 		now.setDate(Integer.parseInt(inputTimeStr.substring(0,4)));
					now.setMonth(Integer.parseInt(inputTimeStr.substring(5,2))-1);
					now.setYear(Integer.parseInt(inputTimeStr.substring(8,2)));
			 	}
		}
		now.setYear(now.getYear() + years);
		now.setDate(now.getDate() + days);
		String m = "";
		String d = "";
		int yy = now.getYear();
	    int mm = now.getMonth()+1; 
		if(mm  <10){
			m = "0" + Integer.toString(mm);
		}else{
			m = Integer.toString(mm);
		}
		int dd = now.getDate();
		if(dd <10){
			d = "0" +Integer.toString(dd);;
		}else{
			d = Integer.toString(dd);
		}
		String calDate = Integer.toString(yy) + "-" + m +"-"+d;
		return calDate;
		
	}
	/**
	 * 时间计算，增加秒数量
	 * @param n 时间秒，可以负数
	 */
	public void addSeconds(int n){ cal.add(Calendar.SECOND, n);}
	

	/**
	 * 获取两个日期间隔天数,不包括起始日期
	 * @param strBeginDate String 起始日期,格式为yyyyMMdd
	 * @param strEndDate String 终止日期,格式为yyyyMMdd
	 * @return long 间隔天数
	 */
	public static long compreDay(String strBeginDate,String strEndDate) throws Exception{
	  long dateCount = 0;
  	
	  SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");	  
	  Date date1 = sdf.parse(strBeginDate);
	  Date date2 = sdf.parse(strEndDate);
	  dateCount = (date2.getTime() - date1.getTime()) / (24*60*60*1000);
      	
	  return dateCount;
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		try{		
		
			DateEntity de = new DateEntity();
			de.addDay( 5 );
		}catch(Exception e){}
	}
}
