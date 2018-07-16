package com.sys.entity;

/**
 * 服务器节点，用于区分不同的电销服务器，从jdbc.properties中读取赋值
 * @author xw
 *
 */
public class ServerNodeBean {
	
	private static  String servernode;

	private static String serverType;

	public static String getServernode() {
		return servernode;
	}
	public void setServernode(String servernode){
		if(servernode==null||"".equals(servernode.trim())){
			servernode="9";
		}
		if(servernode.trim().length()>1){
			servernode=servernode.trim().substring(0,1);
		}
		this.servernode=servernode.trim();
	}
	
	public static String getServerType() {
		return serverType;
	}
	public  void setServerType(String serverType) {
		if(serverType!=null && "flexsupt".equals(serverType)){
			serverType="SUPT";
		}else{
			serverType="ITM";
		}
		this.serverType = serverType;
	}
	public static void main(String args[]){
		System.out.println(ServerNodeBean.getServernode());
	}
}

