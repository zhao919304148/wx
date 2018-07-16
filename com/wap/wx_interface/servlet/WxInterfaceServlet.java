package com.wap.wx_interface.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import net.sf.json.JSONObject;

import com.wap.wx_interface.controller.WxController;

public class WxInterfaceServlet extends HttpServlet {
	private static final Log logger = LogFactory.getLog(WxInterfaceServlet.class);

	
			/** 
			* 描述：
			*/
		
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor of the object.
	 */
	public WxInterfaceServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getSession().getId());
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String target = request.getParameter("target");
		//防xss
		Assert.isTrue(StringEscapeUtils.escapeHtml(target).equals(target));
		PrintWriter out = null;
		if(target == null || "".equals(target.trim())){
			out = response.getWriter();
			JSONObject json = new JSONObject();
			json.element("retcode", "0001");
			json.element("retmsg", "请求参数有误");
			out.print(json);
			out.flush();
			out.close();
		}else{
			try {
				String jsondata = "";
				WxController controller = new WxController();
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(ServletInputStream) request.getInputStream(), "utf-8"));
				StringBuffer sb = new StringBuffer("");
				String temp;
				while ((temp = br.readLine()) != null) {
					sb.append(temp);
				}
				br.close();
				jsondata = URLDecoder.decode(sb.toString(), "UTF-8");
				Method method = controller.getClass().getMethod(target,HttpServletRequest.class, HttpServletResponse.class,String.class);
				method.invoke(controller, request, response, jsondata);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("接口出现异常:"+e.getMessage());
			}
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
