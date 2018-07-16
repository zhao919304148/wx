/**
* 
*  Copyright 2005 FounderOrder Co.,LTD.
*  All right reserved.
*  Date          Author     Changes
*  2006/01/02    ken        Created  
*
*/
package com.sys.run;

import java.util.List;

/**
 * 电话营销系统配置
 * @Author huanghui
 * @CreateDate 2008-6-2
 * @Descript:
 *    1. 
 */
/**
 * 定时FTP到cti服务器取电话日志,并保存到电销系统  	    	

 */


/**
 * 系统运行状态参数<br>
 * 例如 - 调试状态
 * @author ken
 *
 */
public class SystemParameter {
	
	private String SystemHomePath ;

	/**
	 * 发送阳光中间业务地址<车险>
	 */
	private String carCoreHttpUrl;
	/**
	 * 本服务器ip 15位
	 */
	private String localSvcIp ;	
	
	// <!-- 通讯管理子系统配置 -->
	/**
	 * 本地监听配置
	 */
	private int serverNo = 1 ;      //web服务的序号,系统中流水号用
	private int listenPort = 2502;  //socket监听端口
	private int poolSocketSize = 2 ; //socket通讯线程数
	private int poolTaskSize = 2 ;   //定时作业线程数
	
	private int queueSize;			 //交易排队长度限制
	private String tradeFile ;       //交易文件配置文件
	private String commFile ;        //交易通讯区配置
	
	/**
	 * 是否要检查模式权限
	 */
	private  boolean debug = false;

	/**
	 * cit服务器列
	 */
	private List ctiSvrList = null ;
    /**
     * 本系统DB配置,用于jdbc
     */
    private String sunnyTMDBUrl ;
    
    private String sunnyTMDBUser ;
    
    private String sunnyTMPwd ;
    
	/**
	 * 唯一实例
	 */
	private static SystemParameter instance=null;

	/**
	 * 防止外部构造
	 *
	 */
	private SystemParameter(){
		
	}
	
	/**
	 * 获取唯一实例
	 * @return
	 */
	public static SystemParameter getInstance(){
		
		if( instance == null ){
			instance = new SystemParameter();
		}
		return instance;
	}
	
	
	/**
	 * 判断是否系统处于调试状态
	 * @return debug
	 */
	public  boolean getDebug(){
		
		return this.debug;
	}
	
	/**
	 * 设置检查模式
	 * @param checked
	 */
	public synchronized  void setDebug( boolean debug ){
		
		this.debug = debug;
	}


	public static void setInstance(SystemParameter instance) {
		SystemParameter.instance = instance;
	}

	public String getSunnyTMDBUrl() {
		return sunnyTMDBUrl;
	}

	public String getCarCoreHttpUrl() {
		return carCoreHttpUrl;
	}

	public void setCarCoreHttpUrl(String carCoreHttpUrl) {
		this.carCoreHttpUrl = carCoreHttpUrl;
	}

	public List getCtiSvrList() {
		return ctiSvrList;
	}

	public void setCtiSvrList(List ctiSvrList) {
		this.ctiSvrList = ctiSvrList;
	}

	
	public void setSunnyTMDBUrl(String sunnyTMDBUrl) {
		this.sunnyTMDBUrl = sunnyTMDBUrl;
	}

	public String getSunnyTMDBUser() {
		return sunnyTMDBUser;
	}

	public void setSunnyTMDBUser(String sunnyTMDBUser) {
		this.sunnyTMDBUser = sunnyTMDBUser;
	}

	public String getSunnyTMPwd() {
		return sunnyTMPwd;
	}

	public void setSunnyTMPwd(String sunnyTMPwd) {
		this.sunnyTMPwd = sunnyTMPwd;
	}

	public String getSystemHomePath() {
		return SystemHomePath;
	}

	public void setSystemHomePath(String systemHomePath) {
		SystemHomePath = systemHomePath;
	}

	public String getLocalSvcIp() {
		return localSvcIp;
	}


	public void setLocalSvcIp(String localSvcIp) {
		while (localSvcIp.length()<15) {
			localSvcIp +=" ";			
		}
		this.localSvcIp = localSvcIp;
	}
	
	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}

	public int getPoolSocketSize() {
		return poolSocketSize;
	}

	public void setPoolSocketSize(int poolSocketSize) {
		this.poolSocketSize = poolSocketSize;
	}

	public int getPoolTaskSize() {
		return poolTaskSize;
	}

	public void setPoolTaskSize(int poolTaskSize) {
		this.poolTaskSize = poolTaskSize;
	}

	public String getTradeFile() {
		return tradeFile;
	}

	public void setTradeFile(String tradeFile) {
		this.tradeFile = tradeFile;
	}

	public String getCommFile() {
		return commFile;
	}

	public void setCommFile(String commFile) {
		this.commFile = commFile;
	}

	public int getServerNo() {
		return serverNo;
	}

	public void setServerNo(int serverNo) {
		this.serverNo = serverNo;
	}

	public int getQueueSize() {
		return queueSize;
	}

	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}

}