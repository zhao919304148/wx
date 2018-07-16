package com.wap.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRouteParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;



public class HttpUtil {

	
	/**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
	public static String httpGet(String url,String param){
		
		String result = "";
        BufferedReader in = null;
        
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        
        return result;
	 }
	
	
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
	  public static String HttpPost(String url, String param) {
	        PrintWriter out = null;
	        BufferedReader in = null;
	        String result = "";
	        try {
	            URL realUrl = new URL(url);
	            // 打开和URL之间的连接
	            URLConnection conn = realUrl.openConnection();
	            // 设置通用的请求属性
	            conn.setRequestProperty("accept", "*/*");
	            conn.setRequestProperty("connection", "Keep-Alive");
	            conn.setRequestProperty("user-agent",
	                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
	            // 发送POST请求必须设置如下两行
	            conn.setDoOutput(true);
	            conn.setDoInput(true);
	            // 获取URLConnection对象对应的输出流
	            out = new PrintWriter(conn.getOutputStream());
	            // 发送请求参数
	            out.print(param);
	            // flush输出流的缓冲
	            out.flush();
	            // 定义BufferedReader输入流来读取URL的响应
	            in = new BufferedReader(
	                    new InputStreamReader(conn.getInputStream()));
	            String line;
	            while ((line = in.readLine()) != null) {
	                result += line;
	            }
	        } catch (Exception e) {
	            System.out.println("发送 POST 请求出现异常！"+e);
	            e.printStackTrace();
	        }
	        //使用finally块来关闭输出流、输入流
	        finally{
	            try{
	                if(out!=null){
	                    out.close();
	                }
	                if(in!=null){
	                    in.close();
	                }
	            }
	            catch(IOException ex){
	                ex.printStackTrace();
	            }
	        }
	        return result;
	    } 
	  
	
	  
	  /**
	   * 
	  		* 描述:验证必填参数有效
	  		* @param jsoninfo
	  		* @param checkParam
	  		* @return
	  		* @author 李朝晖 2016年12月8日 上午10:52:58
	   */
	  public static String checkParam(String jsondata,String checkParam){
		 
		  String[] split = StringUtils.split(checkParam,",");
		  try {
			  if(split != null && split.length >0){
					JSONObject jsonObject = JSONObject.parseObject(jsondata);
					Set<String> keys = jsonObject.keySet();
					for (String splits : split) {
						if(!keys.contains(splits)){
							return "必填的请求参数有缺失";
						}
						Object objValue = jsonObject.get(splits);
						if(objValue == null || StringUtils.isBlank(objValue.toString())){
							return "必填的请求参数为空";
						}
					}	
			  }
			  return "";
		} catch (Exception e) {
			return "参数值异常";
		} catch (Throwable throwable) {
			throwable.printStackTrace();
			return "程序处理中数据有效性异常";
		}
		  
	  }
	//设置代理
	  public static HttpClient getHttpClient() {
		   DefaultHttpClient httpClient = new DefaultHttpClient();
		   String proxyHost = "12.1.37.247";
		   int proxyPort = 8080;
		   String userName = "";
		   String password = "";
		   httpClient.getCredentialsProvider().setCredentials(
		     new AuthScope(proxyHost, proxyPort),
		     new UsernamePasswordCredentials(userName, password));
		   HttpHost proxy = new HttpHost(proxyHost,proxyPort);
		   httpClient.getParams().setParameter(ConnRouteParams.DEFAULT_PROXY, proxy);
		   httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		   return httpClient;
	  }
	  
	  /**
	   * get代理
	  		* 描述:
	  		* @param url
	  		* @return
	  		* @author qex2016年12月29日 上午11:13:06
	   */
	  public static String httpGetProxy(String url){
		  StringBuffer sb = new StringBuffer();
		  //创建HttpClient实例
		  HttpClient client = getHttpClient();
		  //创建httpGet
		  HttpGet httpGet = new HttpGet(url);
		  //执行
		  try {
		   HttpResponse response = client.execute(httpGet);
		   HttpEntity entry = response.getEntity();
		   if(entry != null)
		   {
		    InputStreamReader is = new InputStreamReader(entry.getContent(),"utf-8");
		    BufferedReader br = new BufferedReader(is);
		    String str = null;
		    while((str = br.readLine()) != null)
		    {
		     sb.append(str.trim());
		    }
		    br.close();
		   }
		   
		  } catch (ClientProtocolException e) {
		   e.printStackTrace();
		  } catch (IOException e) {
		   e.printStackTrace();
		  }
		  return sb.toString();
	  }
	  /**
	   * 代理post
	  		* 描述:
	  		* @param url
	  		* @param json
	  		* @return
	  		* @author qex 2016年12月29日 上午11:11:52
	   */
	  public static String httpPostProxy(String url, String json){
		  StringBuffer sb = new StringBuffer();
		  //创建HttpClient实例
		  HttpClient client = getHttpClient();
		  //创建HttpPost
		  HttpPost httpPost = new  HttpPost(url);
		  //执行
		  try {
			   httpPost.setEntity(new StringEntity(json,"UTF-8"));
			   HttpResponse response = client.execute(httpPost);
			   HttpEntity entry = response.getEntity();
			   if(entry != null){
				    InputStreamReader is = new InputStreamReader(entry.getContent(),"utf-8");
				    BufferedReader br = new BufferedReader(is);
				    String str = null;
				    while((str = br.readLine()) != null){
					     sb.append(str.trim());
				    }
				    br.close();
			   }
		   
		  } catch (ClientProtocolException e) {
			  e.printStackTrace();
		  } catch (IOException e) {
			  e.printStackTrace();
		  }
		  return sb.toString();
	  }
	  
	  /**
	   * dopost
	  		* 描述:
	  		* @param url
	  		* @param requestBody
	  		* @return
	  		* @author qex 2016年12月29日 上午11:25:38
	   */
	  public static String doPostProxy(String url, String requestBody){
		  try {
				HttpPost post = new HttpPost(url);
				post.setEntity(new StringEntity(requestBody, "UTF-8"));
				HttpResponse response = getHttpClient().execute(post);
				int statusCode = response.getStatusLine().getStatusCode();
				return statusCode == 200 ? EntityUtils.toString(response.getEntity()) : ("" + statusCode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return "";
	  }
	  
	  public static void main(String[] args) {
		  String httpGetProxy = httpGetProxy("http://ic.api.anzexian.mofanggongchang.cn/InsurancePolicy/Push");
		  System.out.println(httpGetProxy);
		  
	}
}
