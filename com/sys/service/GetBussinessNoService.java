package com.sys.service;

import java.sql.CallableStatement;
import java.sql.Types;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;

import com.wap.util.GetServerIPUtils;

import core.db.dao.IBaseDAO;
import core.db.dao.IBaseJdbcDAO;
import core.db.dao.impl.BaseDAO;

import com.sys.entity.ServerNodeBean;
import com.sys.entity.DateEntity;

/** * 
 * 
 * Class GetBussinessNoService * 
 * @author XW
 * 获取业务编号 BussinessNo
 * 风险：同一机构同一业务产品每分钟最大上限是99999
 *      服务出现异常时，一分钟内服务恢复将可能导致流水重复
 *      只能体现分钟时间内某一业务的数量
 */
public class GetBussinessNoService {
	
	private static IBaseJdbcDAO baseJdbcDAO;
	@Autowired(required = false)
	public void setBaseJdbcDAO(IBaseJdbcDAO baseJdbcDAO){
		this.baseJdbcDAO=baseJdbcDAO;
	}
	private static IBaseDAO baseDAO=null;
	@Autowired(required = false)
	public void setBaseDAO(BaseDAO baseDAO){
		this.baseDAO=baseDAO;
	}
	
	//获取服务器ip
	private static String serverIP = GetServerIPUtils.getServerIP();

	/*为了提高效率，保证每种业务使用不使用相同的时间变量，从而不共用同一锁*/
	/*为了提高效率，保证每个业务获取编号时不使用同一个锁给每个业务定义了个专门的锁*/
	
	private  static volatile long jiaoyiNo=0  ; 	  //异步交易流水 -JY
	private  static volatile String JYDate="";  //当前的系统时间
	private  static final Lock jylock=new ReentrantLock(); //JY锁对象
	
	private static volatile long linshiNo=0;		//统一的临时编号 共用的
	private static volatile String linshiDate="";//当前的系统时间
	private static final Lock linshilock=new ReentrantLock();
	
	private  static String servernode=ServerNodeBean.getServernode(); //服务器节点号·1位0-9
	
	/**
	 * 从JAVA中获取业务流水号  两位业务+一位服务器节点+YYMMDDHHMI+五位流水 =18位
	 * @param  bussType业务类型 
	 * @return bussinessNo 业务编号
	 */
	public static String GetBussinessNo(String bussType){
		//系统当前时间YYMMDDHHMI
		String bussinessNo = "";
		if("JIAOYI".equalsIgnoreCase(bussType)){  /**交易编号**/
			bussinessNo = new StringBuffer("JY").append(getJYNo()).toString();
		}else if("LINSHI".equalsIgnoreCase(bussType)){  /**临时编号**/
			bussinessNo = new StringBuffer("LS").append(getLSNo()).toString();
		}else if("CARPHOTO".equalsIgnoreCase(bussType)){  /**临时编号**/
			bussinessNo = new StringBuffer("CP").append(getLSNo()).toString();
		}else if("".equalsIgnoreCase(bussType)){
			bussinessNo = "";
		}
		
		return bussinessNo ;
	}
	
	/**
	 * 从数据库中获取业务流水号 业务类型（2位）+服务器节点（2为）+ 年月日时分（10位）+流水号(5位)
	 * @param bussType 业务类型 ipStr-服务器ip地址 serverType-服务器类型 
	 * @param 流水号如果长度不够会自动填充0,过长会自动截取
	 * @return bussinessNo业务编号
	 */
/*	public static String GetBussinessNo(String bussType) {

		String ipStr=serverIP;
		//对ip做校验
		System.out.println("获取服务器ip："+ipStr);
		if(ipStr==null||ipStr.trim().length()==0){
			return "";
		}
		//项目类型 EPICC 微信项目
		String serverType ="EPICC";
		//对业务类型做校验
		if("JIAOYI".equalsIgnoreCase(bussType)){  //交易编号
			bussType = "JY";
		}else if("LINSHI".equalsIgnoreCase(bussType)){  //临时编号
			bussType = "LS";
		}else if("PHOTO".equalsIgnoreCase(bussType)){  //图片名称编号
			bussType = "P";
		}else if("".equalsIgnoreCase(bussType)){
			bussType = "";
		}
		Vector<Object> in=new Vector<Object>();
		in.add(ipStr);
		in.add(serverType);
		in.add(bussType);
		Vector<Integer> out=new Vector<Integer>();
		out.add(Types.VARCHAR);
		out.add(Types.VARCHAR);
		final Vector<Object> in1=in;
		Vector<Object> ret=baseDAO.executeProcedure("SP_DIC_GETSERVERNO", in1,out);
		if(!"N".equals(ret.get(1).toString())){
			System.out.println("流水号："+ret.get(0).toString());
			return ret.get(0).toString();
		} else {
			return "";
		}
	}
*/	
	/**
	 * 获取交易编号 按时间重新递增
	 * @param datestr 
	 * @return
	 */
	private static String getJYNo(){
		String tempno="99999";
		try {
			jylock.lock();
			String datestr=new DateEntity().getYYMMDDHHmmss();
			if(datestr.equals(JYDate)){
				jiaoyiNo++;
			}else if("".equals(JYDate)){
				JYDate=datestr;
				jiaoyiNo=0;
			}else if(Long.parseLong(JYDate)>=Long.parseLong(datestr)){
				jiaoyiNo++;		
			}else{
				JYDate=datestr;
				jiaoyiNo=0;
			}
			tempno=JYDate+GetBussinessNoService.leftAppendChr(jiaoyiNo+"", 5);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			jylock.unlock();
		}
		return tempno;
	}
	
	/**
	 * 获取临时编号 按时间重新递增
	 * @param datestr
	 * @return
	 */
	private static  String getLSNo(){
		String tempno="99999";
		try {
			linshilock.lock();
			String datestr=new DateEntity().getYYMMDDHHmmss();
			if(datestr.equals(linshiDate)){
				linshiNo++;
			}else if("".equals(linshiDate)){
				linshiDate=datestr;
				linshiNo=0;
			}else if(Long.parseLong(linshiDate)>=Long.parseLong(datestr)){
				linshiNo++;
			}else{
				linshiDate=datestr;
				linshiNo=0;
			}
			tempno=linshiDate+GetBussinessNoService.leftAppendChr(linshiNo+"", 5);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}finally{
			linshilock.unlock();
		}
		return tempno;
	}

	/**
	 * 左边补0到指定长度的函数
	 * @param s
	 * @param length
	 * @param chr
	 * @return
	 */
	public	static  String leftAppendChr(String s,int length ){
			
			if( s == null ){
				s="";
			}		
			if( s.length() >length ){
				
				return s.substring(0,length);
			}		
			while( s.length() < length ){
				
				s = "0"+s;
			}		
			return s;
		}
}

