package com.wap.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class GetServerIPUtils {
	
	private static final Log logger = LogFactory.getLog(GetServerIPUtils.class);
	
	private static String serverIPs;
	private static String serverIp;
	
	public void setServerIPs(String serverIPs) {
		this.serverIPs = serverIPs;
	}


	
	public static String getServerIP() {
		if(null==serverIp||"".equals(serverIp)){
			serverIp= getIp();
		}
		return serverIp;
	}
	public static String getIp(){
		String serverIP="";
		String[] ips=serverIPs.split(",");
		try {
			Enumeration allNetInterfaces;
			
		    allNetInterfaces = NetworkInterface.getNetworkInterfaces();
			
			InetAddress ip = null;
			while (allNetInterfaces.hasMoreElements()) {
				NetworkInterface netInterface = (NetworkInterface) allNetInterfaces
						.nextElement();
				// 网卡名称eth0等
				// System.out.println(netInterface.getName());
				Enumeration addresses = netInterface.getInetAddresses();
				// 因为一个网卡可能有IPv4和IPv6两个IP地址，导致获取的地址不正确
				while (addresses.hasMoreElements()) {
					ip = (InetAddress) addresses.nextElement();
					// Inet4Address和Inet6Address是InetAddress的子类
					if (ip != null && ip instanceof Inet4Address) { // 只获取IPv4地址
						for (int i = 0; i < ips.length; i++) {
							if(ip.getHostAddress().equals(ips[i])){
								serverIP=ip.getHostAddress();
								//System.out.println("本机的IP = " + ip.getHostAddress());
								break;
							}
						}
					}
				}
			}
			return serverIP;
		} catch (Exception e) {
			logger.error("获取本地IP失败:"+e.getMessage());
			return "";
		}
	}



	public static String getServerIp() {
		return serverIp;
	}



	public static void setServerIp(String serverIp) {
		GetServerIPUtils.serverIp = serverIp;
	}
	

}

