package com.wap.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 用于处理OBJ和其他对象之间的转换，如hashMap,XML,OBJ,还有SQL语句等。
 * @author XIEWIE create by 2010-08-04
 *
 */
public class TransUtil {
	private static final Log logger = LogFactory.getLog(TransUtil.class);

	/**
	 * 通过遍历MAP,将map里所有值赋值给对象
	 * @param map
	 * @param obj
	 * @return 
	 * @author xw by 2010-08-04
	 */
	public static void mapToObjByMap(HashMap<String,String> map,Object obj){
		if(map==null||map.isEmpty()){
			return ;
		}
		Class clazz=obj.getClass();
		
		/*获取map的key的游标*/
		Set set=map.keySet();
		Iterator it=set.iterator();
		while(it.hasNext()){
			String colstr=it.next().toString();
			try {
				Field field=clazz.getDeclaredField(colstr);
				Class type=field.getType();
				field.setAccessible(true); 
				if(type.equals(String.class)){
					field.set(obj, map.get(colstr)==null?"":map.get(colstr).toString());
				}else if(type.equals(int.class)){
					field.setInt(obj, map.get(colstr)==null?0:Integer.parseInt(map.get(colstr).toString()));
				}else if(type.equals(long.class)){
					field.setLong(obj, map.get(colstr)==null?0:Long.parseLong(map.get(colstr).toString()));
				}else if(type.equals(float.class)){
					field.setFloat(obj, map.get(colstr)==null?0.0f:Float.parseFloat(map.get(colstr).toString()));
				}else if(type.equals(double.class)){
					field.setDouble(obj, map.get(colstr)==null?0.0:Double.parseDouble(map.get(colstr).toString()));
				}else if(type.equals(boolean.class)){
					field.setBoolean(obj, map.get(colstr)==null?false:Boolean.parseBoolean(map.get(colstr).toString()));
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn("map转换为："+clazz.getName()+"对象时，"+colstr+"字段赋值失败！");
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn("map转换为："+clazz.getName()+"对象时，"+colstr+"字段赋值失败！");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn("map转换为："+clazz.getName()+"对象时，"+colstr+"字段赋值失败！");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn("map转换为："+clazz.getName()+"对象时，"+colstr+"字段赋值失败！");
			}
		}
	}
	/**
	 * 通过遍历OBJ将MAP里的值赋值给OBJ
	 * @param map
	 * @param obj
	 * @author XW by 2010-09-14
	 */
	public static void mapToObjByObj(HashMap<String,String> map,Object obj){
		if(map==null||map.isEmpty()){
			logger.info("map转换为对象时，map为空！");
		}
		Class clazz=obj.getClass();
		Field[] field=clazz.getDeclaredFields();//获取变量
		if(field.length==0){
			logger.info("map转换"+clazz.getName()+"对象时，对象没有字段！");
			return ;
		}
		/*获取map的key的游标*/
		
		for(int i=0;i<field.length;i++){
			String fidname=field[i].getName().toUpperCase();
			try {
				field[i].setAccessible(true);
				Class type=field[i].getType();
				String value=map.get(fidname)==null?"":map.get(fidname).toString();
				value=new String(value.getBytes("GBK"));
				if("".equals(value)){
					continue;
				}
				if(type.equals(String.class)){
					field[i].set(obj, value);
				}else if(type.equals(Date.class)){
					//SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //2011-8-26 时间改为24小时制
					frmt.parse(value);
					field[i].set(obj, frmt.parse(value));
				}else if(type.equals(int.class)){
					field[i].setInt(obj, Integer.parseInt(value));
				}else if(type.equals(double.class)){
					field[i].setDouble(obj, Double.parseDouble(value));
				}
			
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn("map转换为："+clazz.getName()+"对象时，"+fidname+"字段赋值失败！");
			}
		}
	}
	/**
	 * 将对象转换为INSERT语句
	 * @param obj
	 * @return 返回insert语句
	 * @author xw by 2010-08-04
	 */
	public static String objInsert(Object obj,String tbname){
		if(obj==null){
			return "";
		}
		Class c=obj.getClass();	//获取对象
		
		Field[] fid=c.getDeclaredFields();//获取变量
		if(fid.length==0){
			return "";
		}
		//String tbname=c.getName();//获取类名(全路径)
		//String[] tempname=tbname.split("\\.");
		//tbname=tempname[tempname.length-1];
		StringBuffer sqlfid=new StringBuffer();
		StringBuffer sqlvalue=new StringBuffer();
		for(int i=0;i<fid.length;i++){
			try {
				String fidname=fid[i].getName();	//获取变量名
				if(fidname.equals("serialVersionUID")){
					continue;
				}
				Class tp=fid[i].getType();	//获取变量类型
				fid[i].setAccessible(true);			//设置可访问性
				if(tp.equals(String.class)){		//类型比较
					String value=(String)fid[i].get(obj);//获取变量值
					if(value==null){
						continue;
					}
					sqlvalue.append("'"+value+"',");
				}else if(tp.equals(Date.class)){
					Date date=(Date)fid[i].get(obj);
					if(date==null){
						continue;
					}
					SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					//SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //2011-8-26 时间改为24小时制
					String value=frmt.format(date);
					//sqlvalue.append("to_date('"+value+"','yyyy-mm-dd hh24:mi:ss'),"); 为oracle
					sqlvalue.append("'"+value+"',");   //iformix
				}else if(tp.equals(int.class)){
					int value=fid[i].getInt(obj);
					sqlvalue.append(value+",");
				}else if(tp.equals(long.class)){
					long value=fid[i].getLong(obj);
					sqlvalue.append(value+",");
				}else if(tp.equals(double.class)){
					double value=fid[i].getDouble(obj);
					sqlvalue.append(value+",");
				}else{
					continue;
				}
				sqlfid.append(fidname+",");
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn(c.getName()+"对象转换为INSERT语句时：字段值获取失败！");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn(c.getName()+"对象转换为INSERT语句时：字段值获取失败！");
			} 
		}
		if("".equals(sqlfid.toString())){
			return "";
		}
		StringBuffer sql=new StringBuffer("insert into "+tbname+"(");
		sql.append(sqlfid.substring(0,sqlfid.length()-1));
		sql.append(") values(");
		sql.append(sqlvalue.substring(0, sqlvalue.length()-1));
		sql.append(")");
		
		return sql.toString();
	}
	/**
	 * 将对象中有值的生成select语句
	 * @param obj
	 * @param tbname
	 * @return 返回select语句
	 * @author xw by 2010-08-04
	 */
	public static String objSelect(Object obj,String tbname){
			if(obj==null){
				return "";
			}
			Class c=obj.getClass();	//获取对象
			
			Field[] fid=c.getDeclaredFields();//获取变量
			if(fid.length==0){
				return "";
			}
			//String tbname=c.getName();//获取类名(全路径)
			//String[] tempname=tbname.split("\\.");
			//tbname=tempname[tempname.length-1];
			//StringBuffer sqlfid=new StringBuffer();
			StringBuffer sqlvalue=new StringBuffer();
			for(int i=0;i<fid.length;i++){
				try {
					String fidname=fid[i].getName();	//获取变量名
					if(fidname.equals("serialVersionUID")){
						continue;
					}
					Class tp=fid[i].getType();	//获取变量类型
					fid[i].setAccessible(true);			//设置可访问性
					if(tp.equals(String.class)||tp.equals(Date.class)){		//类型比较
						String value=(String)fid[i].get(obj);//获取变量值
						if(value==null||"".equals(value.trim())){
							continue;
						}
						sqlvalue.append(fidname+"='"+value+"' and ");
					}
					
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.warn(c.getName()+"对象转换为SELECT语句时：字段值获取失败！");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.warn(c.getName()+"对象转换为SELECT语句时：字段值获取失败！");
				} 
			}
			StringBuffer sql=new StringBuffer("select * from "+tbname+" where ");
			sql.append(sqlvalue.substring(0, sqlvalue.length()-3));
			
			return sql.toString();
	}
	/**
	 * 将对象按照结构生成HASHMAP
	 * @param obj //对象支持其他对象类型、String、int、long、double、date.暂不支持其他类型，如果有需要自行添加
	 * @param map
	 * @author XW BY 2010-09-11
	 * @return
	 */
	public static HashMap<String,Object> ObjToMap(Object obj,HashMap<String,Object> map){
		System.out.println(obj);
		Class c=obj.getClass();	//获取对象
		System.out.println("obj:"+obj);
		Field[] fid=c.getDeclaredFields();//获取变量
		if(fid.length==0){
			return map;
		}
		for(int i=0;i<fid.length;i++){
			try {
				String fidname=fid[i].getName();	//获取变量名
				if(fidname.equals("serialVersionUID")){
					continue;
				}
				fidname=fidname.toUpperCase(); //将变量名变大写,用于HASHMAP
				Class tp=fid[i].getType();	//获取变量类型
				fid[i].setAccessible(true);			//设置可访问性
				if(tp.equals(String.class)){		//类型比较
					String value=(String)fid[i].get(obj);//获取变量值
					if(value==null){
						// continue; 根据需求 空的的传""
						value="";
					}
					map.put(fidname, value);
				}else if(tp.equals(Date.class)){
					Date date=(Date)fid[i].get(obj);
					String value="";
					if(date!=null){
						SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //根绝需求格式化DATE
					//	SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //2011-8-26 时间改为24小时制
						value=frmt.format(date);
					}
					map.put(fidname, value);
				}else if(tp.equals(int.class)){
					int value=fid[i].getInt(obj);
					map.put(fidname, value+"");
				}else if(tp.equals(long.class)){
					long value=fid[i].getLong(obj);
					map.put(fidname, value+"");
				}else if(tp.equals(double.class)){
					double value=fid[i].getDouble(obj);
					map.put(fidname, value+"");
				}else if(tp.equals(List.class)){
					List templist=(List)fid[i].get(obj);
					if(templist!=null){
						List<HashMap<String,Object>> maplist=new ArrayList<HashMap<String,Object>>();
						for(int j=0;j<templist.size();j++){
							Object tempobj=templist.get(j);
							HashMap<String,Object> tempmap=ObjToMap(tempobj,new HashMap<String,Object>());
							maplist.add(tempmap);
						}
						map.put(fidname, maplist);
					}
				}else{
					Object tempobj=fid[i].get(obj);
					map.put(fidname, ObjToMap(tempobj,map));
				}
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn(c.getName()+"对象转换为hashMap时：字段赋值失败！");
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				logger.warn(c.getName()+"对象转换为hashMap时：字段赋值失败！");
			} 
		}
		return map;
	}
	/**
	 * 将对象source中和target相同的字段值，赋值给target,目标对象字段全是String类型，本方法用于生成交易对象	
	 * @param source
	 * @param target
	 * @author XW
	 */
	public static void copyObject(Object source,Object target,String dateformat){
		Class s=source.getClass();	//获取源对象
		Field[] sfid=s.getDeclaredFields();//获取源变量
		Class t=target.getClass();	//获取目标对象
		Field[] tfid=t.getDeclaredFields();	//获取目标变量
		
		for(int i=0;i<tfid.length;i++){
			String tname=tfid[i].getName();	//字段名
			for(int j=0;j<sfid.length;j++){
				String sname=sfid[j].getName();
				if(tname.equals(sname)){
					tfid[i].setAccessible(true);
					sfid[j].setAccessible(true);
					String tmpvalue="";
					try {
						if(sfid[j].getType().equals(Date.class)){
							SimpleDateFormat frmt=new SimpleDateFormat(dateformat); //根绝需求格式化DATE
							Date date=(Date)sfid[j].get(source);
							if(date==null){
								continue;
							}else{
								tmpvalue=frmt.format(date);
							}
						}else if(sfid[j].getType().equals(double.class)){	//double类型统一转成小数点后两位
							tmpvalue=formatDoubleTo2(sfid[j].getDouble(source));
						}else if(sfid[j].getType().equals(float.class)){
							tmpvalue=formatDoubleTo2(sfid[j].getDouble(source)); //float类型统一转成小数点后两位
						}else if(sfid[j].getType().equals(int.class)){
							tmpvalue=sfid[j].getInt(source)+"";
						}else{
							if(sfid[j].get(source)==null||"".equals(sfid[j].get(source).toString())){
								continue;
							}else{
								tmpvalue=sfid[j].get(source).toString();
								
							}
						}
						tfid[i].set(target, tmpvalue);
						break;
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						logger.warn(s.getName()+"对象赋值"+t.getName()+"对象时："+sname+"字段赋值失败！");
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						logger.warn(s.getName()+"对象赋值"+t.getName()+"对象时："+sname+"字段赋值失败！");
					}
				}
			}
		}
		
	}
	/**
	 * 将源对象的值赋值给目标对象中，源对象全是String类型，目标对象为JPA对象，用于处理返回的消息
	 * @param source
	 * @param target
	 */
	public static void copyObject(Object source,Object target){
		Class s=source.getClass();	//获取源对象
		Field[] sfid=s.getDeclaredFields();//获取源变量
		Class t=target.getClass();	//获取目标对象
		Field[] tfid=t.getDeclaredFields();	//获取目标变量
		int n=0;
		for(int i=0;i<tfid.length;i++){
			n++;
			String tname=tfid[i].getName();	//字段名
			Class ttype=tfid[i].getType(); //字段类型
			for(int j=0;j<sfid.length;j++){
				String sname=sfid[j].getName();
				if(tname.equals(sname)){
					tfid[i].setAccessible(true);
					sfid[j].setAccessible(true);
					Object tmpvalue="";
					try {
						if(ttype.equals(Date.class)){ //时间格式转换
							String svalue=sfid[j].get(source)==null?"":sfid[j].get(source).toString().trim();
							if("".equals(svalue)){
								continue;
							}else if(svalue.length()==10){
								svalue=svalue+" 00:00:00";
							}else if(svalue.length()!=19){
								System.out.println("返回交易报文"+tname+"格式不正确");
							}
							SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"); //根绝需求格式化DATE
							//SimpleDateFormat frmt=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");        //2011-8-26 时间改为24小时制
							tmpvalue=frmt.parse(svalue);
						}else if(ttype.equals(double.class)){	//double类型
							String svalue=sfid[j].get(source)==null?"0.00":sfid[j].get(source).toString().trim();
							tmpvalue=Double.parseDouble(svalue);
						}else if(sfid[j].getType().equals(float.class)){ //float类型
							String svalue=sfid[j].get(source)==null?"0.00":sfid[j].get(source).toString().trim();
							tmpvalue=Float.parseFloat(svalue); 
						}else if(ttype.equals(int.class)){	//INT类型
							String svalue=sfid[j].get(source)==null?"0":sfid[j].get(source).toString().trim();
							tmpvalue=Integer.parseInt(svalue); 
						}else if(ttype.equals(long.class)){	//LONG类型
							String svalue=sfid[j].get(source)==null?"0":sfid[j].get(source).toString().trim();
							tmpvalue=Long.parseLong(svalue); 
						}else{		//字符串类型
							tmpvalue=sfid[j].get(source)==null?"":sfid[j].get(source).toString();
							
						}
						tfid[i].set(target, tmpvalue);
						break;
					} catch (ParseException e) {
						e.printStackTrace();
						logger.warn(s.getName()+"对象赋值"+t.getName()+"对象时："+sname+"字段赋值失败！");
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
						logger.warn(s.getName()+"对象赋值"+t.getName()+"对象时："+sname+"字段赋值失败！");
					} catch (IllegalAccessException e) {
						e.printStackTrace();
						logger.warn(s.getName()+"对象赋值"+t.getName()+"对象时："+sname+"字段赋值失败！");
					}
				}
			}
		}
	}
	/**
	 * 相同实体的对象之间赋值,将obj1的所有属性值赋给obj2的对应属性
	 * @param obj1
	 * @param obj2
	 * @author liuxd time :2014-08-25
	 * @return
	 */
	public static Object propertyCopy(Object obj1,Object obj2){
		 Field[] field = obj1.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
	        try {
	            for (int j = 0; j < field.length; j++) { // 遍历所有属性
	                String name = field[j].getName(); // 获取属性的名字
	                name = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
	                String type = field[j].getGenericType().toString(); // 获取属性的类型
	                if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
	                    Method m = obj1.getClass().getMethod("get" + name);
	                    String value = (String) m.invoke(obj1); // 调用getter方法获取属性值
	                    if (value != null) {
	                        m = obj1.getClass().getMethod("set"+name,String.class);
	                        m.invoke(obj2, value);
	                    }
	                }
	                if (type.equals("class java.lang.Integer")) {
	                    Method m = obj1.getClass().getMethod("get" + name);
	                    Integer value = (Integer) m.invoke(obj1);
	                    if (value != null) {
	                        m = obj1.getClass().getMethod("set"+name,Integer.class);
	                        m.invoke(obj2,value);
	                    }
	                }
	                if (type.equals("class java.lang.Boolean")) {
	                    Method m = obj1.getClass().getMethod("get" + name);
	                    Boolean value = (Boolean) m.invoke(obj1);
	                    if (value != null) {
	                        m = obj1.getClass().getMethod("set"+name,Boolean.class);
	                        m.invoke(obj2, value);
	                    }
	                }
	                if (type.equals("class java.util.Date")) {
	                    Method m = obj1.getClass().getMethod("get" + name);
	                    Date value = (Date) m.invoke(obj1);
	                    if (value != null) {
	                        m = obj1.getClass().getMethod("set"+name,Date.class);
	                        m.invoke(obj2, value);
	                    }
	                }
	            }
	        } catch (NoSuchMethodException e) {
	            e.printStackTrace();
	        } catch (SecurityException e) {
	            e.printStackTrace();
	        } catch (IllegalAccessException e) {
	            e.printStackTrace();
	        } catch (IllegalArgumentException e) {
	            e.printStackTrace();
	        } catch (InvocationTargetException e) {
	            e.printStackTrace();
	        }
	   return obj2;

	  }
	/**
	 * 将double类型变成小数点后两位的String 类型  change by XW 变成小数点后4位
	 * @param d
	 * @return
	 * @author XW
	 */
	private static String formatDoubleTo2(double d){
		String value=String.valueOf(d);
		if(value.indexOf(".")==-1){
			return value+".00";
		}else if(value.length()-value.indexOf(".")>5){
			value=value.substring(0,value.indexOf(".")+5);
		}
		while(value.length()-value.indexOf(".")<5){
			value=value+"0";
		}
		
		return value;
	}
	/**
	 * 对象深克隆
	 * @param obj  obj里面对象必须实现序列化接口
	 * @return
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @author XW
	 */
	public static Object deepClone(Object obj) throws IOException, ClassNotFoundException {   
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();   
			ObjectOutputStream out = new ObjectOutputStream(outStream);   
			out.writeObject(obj);   

			ByteArrayInputStream inStream = new ByteArrayInputStream(outStream.toByteArray());   
			ObjectInputStream in = new ObjectInputStream(inStream);   
			Object retList = (Object) in.readObject();   
			
			return retList;   
	} 

	/**
	 * 机构代码整合
	 * @param bch1 一级列表
	 * @param bch2 二级列表
	 * @param bch3 三级列表
	 * @return 返回机构代码
	 * @author XW
	 */
	public static String getBranchId(String bch1,String bch2,String bch3){
		if(bch1==null||"".equals(bch1.trim())){
			return "";
		}else if(bch2==null||"".equals(bch2.trim())){
			return bch1;
		}else if(bch3==null||"".equals(bch3.trim())){
			return bch2;
		}else{
			return bch3;
		}
	}
	/**
	 * 规范数字输出，默认格式为00.00,小数点不够自动加，多了4舍5入
	 * @param num 大于0的整数
	 * @return
	 */
	public static String setRightZero(String str,int num){
		if(str==null||"".equals(str.trim())){
			str="0.";
			return TransUtil.rightAppendChr(str, str.length()-(str.length()-str.indexOf('.'))+num+1);
		}
		if(str.indexOf('.')==-1){
			str+=".";
			str=TransUtil.rightAppendChr(str, str.length()+num);
			return str;
		}else if(str.length()-str.indexOf('.')<=num+1){
			return TransUtil.rightAppendChr(str, str.length()-(str.length()-str.indexOf('.'))+num+1);
		}else{
			double d=Double.parseDouble(str);
			d= BigDecimalUtil.round(d, num);
			DecimalFormat df = new DecimalFormat("0."+TransUtil.rightAppendChr("", num)); 
			String dStr = df.format(d); 
			return TransUtil.setRightZero(dStr+"", num);
		}
	}
	
	  /**
	 * 左边补0到指定长度的函数
	 * @param s
	 * @param length
	 * @param chr
	 * @return
	 */
	public	static  String rightAppendChr(String s,int length ){
			
			if( s == null ){
				s="";
			}		
			if( s.length() >length ){
				
				return s.substring(0,length);
			}		
			while( s.length() < length ){
				
				s = s+"0";
			}		
			return s;
	}
	/**
	 * 险别初始化
	 * @param source
	 * @param startStr
	 * @return
	 */
	public static String[] setNewDefault(Object source,String startStr){
		Class s=source.getClass();	//获取源对象
		Field[] sfid=s.getDeclaredFields();//获取源变量
		String[] str=new String[sfid.length];
		for(int j=0;j<sfid.length;j++){
			String sname=sfid[j].getName();
			if(sname.startsWith(startStr)){
				sfid[j].setAccessible(true);
				Object tmpvalue="";
				try {
					tmpvalue=sfid[j].get(source)==null?"":sfid[j].get(source).toString();
					if(!"".equals(tmpvalue)){
						str[j]=sname.substring(2,sname.length())+tmpvalue;
					}
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.warn(s.getName()+"险种初始化赋值"+sname+"字段赋值失败！");
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					//e.printStackTrace();
					logger.warn(s.getName()+"险种初始化赋值"+sname+"字段赋值失败！");
				}
			}
		}
		return str;
	} 
	 public static String translate(double num){
	        String[] upChinese={"零","壹","贰","叁","肆","伍","陆","柒","捌","玖",};
	        String[] upChinese2={"分","角","圆","拾","佰","仟","萬","拾","佰","仟","亿","拾","佰","仟","兆"};
	        StringBuffer result=new StringBuffer();
	        int count=0;
	        int zeroflag=0;
	        boolean mantissa=false;
	        if(num<0){                      //输入值小于零
	            return "输入金额不能为负数！";
	        }
	        if(num==0){                     //输入值等于零
	            return "零";
	        }
	        if(String.valueOf(num).indexOf('E')!=-1){ //输入值过大转为科学计数法本方法无法转换
	            return "您输入的金额过大";
	        }
	        int tem=(int)(num*100);
	        if(tem%100==0){                           //金额为整时
	            if(tem==0)return "";         //输入额为e:0.0012小于分计量单位时  
	            result.insert(0, "整");
	            tem=tem/100;
	            count=2;
	            mantissa=true;
	        }
	        while(tem>0){
	              
	            int t=(int)tem%10;                    //取得最后一位
	            if(t!=0){                             //最后一位不为零时  
	                if(zeroflag>=1){                  //对该位前的单个或多个零位进行处理  
	                    if(((!mantissa)&&count==1)){                    //不是整数金额且分为为零
	                        
	                    }else if(count>2&&count-zeroflag<2){            //输入金额为400.04小数点前后都有零
	                        
	                            result.insert(1,"零");
	                       
	                    }else if(count>6&&count-zeroflag<6&&count<10){  //万位后为零且万位为零
	                        if(count-zeroflag==2){                      //输入值如400000
	                            result.insert(0,"萬");
	                        }else{
	                            result.insert(0,"萬零");                 //输入值如400101 
	                        }
	                    }else if(count>10&&count-zeroflag<10){
	                        if(count-zeroflag==2){
	                            result.insert(0,"亿");
	                        }else{
	                            result.insert(0,"亿零");
	                        }
	                        
	           
	                    }else if(((count-zeroflag)==2)){                //个位为零
	                        
	                    }else if(count>6&&count-zeroflag==6&&count<10){ //以万位开始出现零如4001000
	                        result.insert(0,"萬");
	                    }else if(count==11&&zeroflag==1){               
	                        result.insert(0,"亿");
	                    }else{
	                        result.insert(0,"零");
	                    }
	                    
	                }           
	                result.insert(0,upChinese[t]+ upChinese2[count]); 
	                zeroflag=0;
	                
	            }else{
	                if(count==2){
	                    result.insert(0,upChinese2[count]);             //个位为零补上"圆"字
	                }
	                zeroflag++;
	            }
	            tem/=10;
	           // System.out.println("count="+count+"---zero="+zeroflag+"----"+result.toString());
	            count++;
	            
	            if(count>20)break;      
	        }
	        return result.toString();
	        
	    }
	 
	 /**
	  * 将Json数据转换成对应的对象
	  * @param json
	  * @author 吕亮
	  * @date 2018-05-08
	  * @return obj
	  */
	 public static <T,R> R genericJsonToObj(String json,Class<R> resClazz){
		 if(StringUtils.isEmpty(json)){
			 return null;
		 }
		 R res = com.alibaba.fastjson.JSONObject.parseObject(json, resClazz);
		 return res;
	 }
	 
	 public static void main(String args[]){
		 System.out.println(TransUtil.setRightZero(null, 2));
		 System.out.println(TransUtil.setRightZero("1211.1291", 2));
		 System.out.println(TransUtil.setRightZero("1211.10",2));
		 System.out.println(TransUtil.setRightZero("121.00000900", 5));
	 }
	
}

