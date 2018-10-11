

package com.commman.init;


 /**
 * @ClassName  : ConstDefine.java
 * @Brief      : [FlexServer交易代码常量定义]
 * @Author     : huanghui
 * @CreateDate : Nov 4, 2008
 * @CopyRight  : 
 * @Descript   :[class struct descript] 
 *        No.1 :     
 */
public final class ConstDefine 
{
   /**
    * @roseuid 48CC63B802EE
    */
	
	/**
	 * 报表服务器外部交易清单
	 *  业务需求 ：
	 *      可以控制生成报表/读取报表权限分离
	 *      1。操作员只能读取报表，不能生成
	 *         a。检查报表是否存在，不在就推出
	 *         b。读取报表（没有则生成），权限在前面拦截了 。
	 *      
	 *      如果以后需要确认用户权限/终端权限则在请求报文中直接加入   
	 *      2。	前台约束：
	 *          严格控制报表访问次数，客户没有导出权限直接通过页面进行察看/打印 ；
	 *           有导出权限的客户，不输出页面而直接产生xsl减轻服务段压力
	 *           
	 *         如果报表数据不具有稳定性则不存放到数据库  
	 *         
	 *  1. 检查报表是否已经生成 ；
	 *  
	 */
	
	/**
	 * 报表系统交易码段 ： 1100 ~ 1299
	 *     
	 */
	   /**
	    * 检查报表是否存在
	    */
	   public static final int r_rpt_exist = 1000 ; 
	
	   /**
	    * 取业务编号
	    */
	   public static final int APP_GETBUSSNO = 1001;
	   
	   /**
	    * 取销售机会
	    */
	   public static final int APP_GETSALECHANCE = 1002;
	   	
	   /**
	    * 3.2.1.	配送单打印（单笔）
	    */
	   public static final int RPT_PRINTONEPOST = 1110;   
	   /**
	    * 3.2.1.	配送单打印（单笔）
	    */
	   public static final int RPT_REPORTUSE = 1101;   
	   /**
	    * 3.2.1.	配送单打印（多笔）批量
	    */
	   public static final int RPT_PRINTMULTIPOST = 1002;
	   /**
	    * 
	    */
	 
	   
	   /**
	    * 返回码定义
	    */
	   public static final String ERRCODE_SUCC = "0";
	   public static final String ERRMSG_SUCC = "交易成功！";
        	   
	   public static final String ERRCODE_PACKERR = "9901";
	   public static final String ERRMSG_PACKERR = "打包错误";
	   
	   public static final String ERRCODE_UNPACKERR = "9901";
	   public static final String ERRMSG_UNPACKERR = "解包错误";

	   public static final String ERRCODE_TRADEEXCEPT = "9902";
	   public static final String ERRMSG_TRADEEXCEPT = "交易处理异常";
	   
	   public static final String ERRCODE_TRCODENOTEXIST = "9001";
	   public static final String ERRMSG_TRCODENOTEXIST = "交易不存在，请与管理员联系！";

	   public static final String ERRCODE_TRANSNOTEXIST = "9002";
	   public static final String ERRMSG_TRANSNOTEXIST = "交易通讯节点不存在，请与管理员联系！";
	   
	   public static final String ERRCODE_TRANSEXCEPT = "9002";
	   public static final String ERRMSG_TRANSEXCEPT = "通讯故障";
	   
	   /**
	    * 没有生成报表
	    */
	   public static final String ERRCODE_RPTDATANOTEXIST = "9002";
	   public static final String ERRMG_RPTDATANOTEXIST   = "报表数据不存在，请先生成报表！";
	   /**
	    * 报表统计时间未到，不能创建报表
	    */
	   public static final String ERRCODE_RPTDATEUNDUE = "9003";
	   public static final String ERRMSG_RPTDATEUNDUE = "报表统计时间未到，不能创建报表！";
	   
	   
	   /**
	    * 取交易码对应的中文交易名称
	    * @param trCode
	    * @return
	    */
	   public static String getMsgByTrCode(String trCode){
			String msg = "未知";
			switch(Integer.parseInt(trCode)){
			case	RPT_PRINTONEPOST	:  	    msg="配送单打印（单笔）";	break;
			case	RPT_PRINTMULTIPOST	:  	    msg="配送单打印（多笔）批量";	break;
			}
			return msg ;
		}
	   
	   
}

