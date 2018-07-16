package com.wap.util;

import java.text.DecimalFormat;

import org.apache.commons.lang.StringUtils;

public class DistanceUtil {
	private static double EARTH_RADIUS = 6378.137; 
	   
    private static double rad(double d) { 
        return d * Math.PI / 180.0; 
    }
     
    /**
     * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
     * 参数为String类型	longitude：经度，latitude：纬度
     * @param lat1 用户纬度
     * @param lng1 用户经度
     * @param lat2 商家纬度
     * @param lng2 商家经度
     * @return
     */
    public static String getDistance(String lat1Str, String lng1Str, String lat2Str, String lng2Str) {
    	if(StringUtils.isBlank(lat2Str) || StringUtils.isBlank(lng2Str) || StringUtils.isBlank(lat1Str) || StringUtils.isBlank(lng1Str)){
    		return "0";
    	}
        Double lat1 = Double.parseDouble(lat1Str);
        Double lng1 = Double.parseDouble(lng1Str);
        Double lat2 = Double.parseDouble(lat2Str);
        Double lng2 = Double.parseDouble(lng2Str);
         
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double difference = radLat1 - radLat2;
        double mdifference = rad(lng1) - rad(lng2);
        double distance = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(difference / 2), 2)+ Math.cos(radLat1) * Math.cos(radLat2)* Math.pow(Math.sin(mdifference / 2), 2)));
        distance = distance * EARTH_RADIUS*1000;
        distance = Math.round(distance * 10000) / 10000;
        distance = distance/1000;
        DecimalFormat df = new DecimalFormat("0.00");//格式化
        String distanceStr = df.format(distance);
        return distanceStr;
    }
    public static void main(String[] args) {
//    	var pointA = new BMap.Point(106.486654,29.490295);  // 创建点坐标A--大渡口区latitude=39.91488908,longitude=116.40387397
//route('http://weidx.piccnet.com.cn/WebTransNet4/$bjadmin/bjwxplat/',116.354392,39.872501)
    	//a:40.019736,b:116.486677 latitude=39.91488908,longitude=116.40387397
    	//route('http://weidx.piccnet.com.cn/WebTransNet4/$bjadmin/bjwxplat/',116.354392,39.872501)
//    	System.out.println(getDistance("39.872501","116.354392", "39.91488908","116.40387397"));39.91488908,longitude=116.40387397
    	System.out.println(getDistance("39.815507","116.512597", "39.91488908","116.40387397"));
    	System.out.println(getDistance("39.888539","116.312617", "39.91488908","116.40387397"));
         
    }
     
}
