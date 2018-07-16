package com.wx.util;


import javax.servlet.http.HttpServletResponse;

public class PromptUtil {
		public static String palert(HttpServletResponse response,Object message){
			if(message!=null){
				return getDiv(message.toString());
			}else{
				return "";
			}
				
		}
		public static String getDiv(String message){
			StringBuffer sb=new StringBuffer();
			sb.append("<div class=\"dmsg\" id=\"dmsg\">");
			sb.append(message);
			sb.append("</div>");
			sb.append("<div class=\"mask-pop\" style=\"display:block\"></div>  ");
			return sb.toString();
		}
}
