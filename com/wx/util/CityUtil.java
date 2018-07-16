package com.wx.util;

import java.io.IOException;
import java.text.ParseException;

import net.sf.json.JSONObject;

public class CityUtil {
		public static String getCityId(String cityName){
			String cityId=new String();
			try {
				JSONObject object = XlsUtil.readXlsByCityName(cityName);
				if(object!=null){
					cityId= object.getString("cityId").toString();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cityId;
		}
		public static String getCityName(String cityId){
			String cityName=new String();
			try {
				JSONObject object=XlsUtil.readXlsById(cityId);
				cityName=object!=null?object.getString("cityName"):null;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return cityName;
		}
}
