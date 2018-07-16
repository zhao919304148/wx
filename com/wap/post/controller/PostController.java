package com.wap.post.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sys.dic.SysDicHelper;
import com.sys.exception.EpiccException;
import com.sys.service.GetBussinessNoService;
import com.wap.main.entity.UserEntity;
import com.wap.post.entity.CarPhotoEntity;
import com.wap.post.entity.PostOrderEntity;
import com.wap.post.entity.PostQueryEntity;
import com.wap.post.service.PostService;
import com.wap.util.WxSDKUtil;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value={"/post"})
public class PostController {
	private static final Log logger = LogFactory.getLog(PostService.class);
	@Autowired(required = false)
	private PostService postService = null;
	
	public void setPostService(PostService postService) {
		this.postService = postService;
	}
	/**
	 * 
			* 描述:通过扫码或者订单号查询 订单详情
			* @param map
			* @param request
			* @param session
			* @param response
			* @author ZJM
	 */
	@RequestMapping(value={"/queryOrderInfo.do"})
	public ModelAndView queryOrderInfo(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String URI = session.getServletContext().getAttribute("httpBasePath")+request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);
		String querystr = request.getQueryString();
		if(querystr != null && !"".equals(querystr)){
			URI = URI + "?" + querystr;
		}
		Map<String,String> signMap = WxSDKUtil.getInstance().sign(URI);
		request.setAttribute("appId", signMap.get("appId"));
		request.setAttribute("nonceStr", signMap.get("nonceStr"));
		request.setAttribute("timestamp", signMap.get("timestamp"));
		request.setAttribute("signature", signMap.get("signature"));
		request.setAttribute("uri", URI);
		PostQueryEntity postQueryEntity = new PostQueryEntity();
		postQueryEntity.setOrderno(request.getParameter("orderno"));
		postQueryEntity.setBarcode(request.getParameter("barcode"));
		postQueryEntity.setQueryflag(request.getParameter("queryflag"));
		ModelAndView modelAndView = new ModelAndView("/post/detail");
		PostOrderEntity postOrderEntity = null;
		try {
			postOrderEntity = postService.queryOrderInfo(postQueryEntity);
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error(e.getErrMess());
		}
		modelAndView.addObject("order", postOrderEntity);
		modelAndView.addObject("photoUrl", SysDicHelper.getInstance().getValueByDicTypeAndDicId("PHOTOURL", "photoUrl"));
		modelAndView.addObject("onebackreasonMap", SysDicHelper.getInstance().getOnebackreasonMap());
		modelAndView.addObject("orderstatusMap", SysDicHelper.getInstance().getMapByDicType("ORDERSTATUS"));
		if(null!=postOrderEntity){
			if(null!=postOrderEntity.getOnebackreason()&&!"".equals(postOrderEntity.getOnebackreason().trim())){
				modelAndView.addObject("twobackreasonMap", SysDicHelper.getInstance().getTwobackreasonMap(postOrderEntity.getOnebackreason().trim()));
			}
			if(null!=postOrderEntity.getTwobackreason()&&!"".equals(postOrderEntity.getTwobackreason().trim())){
				modelAndView.addObject("thrbackreasonMap", SysDicHelper.getInstance().getThrbackreasonMap(postOrderEntity.getTwobackreason().trim()));
			}
		}
		return modelAndView;
	}
	
	/**
	 * 
			* 描述:通过条件查询订单列表
			* @param map
			* @param request
			* @param session
			* @param response
			* @author ZN
	 * @throws ServletException 
	 */
	@RequestMapping(value={"/queryOrderList.do"})
	public void queryOrderList(ModelMap map,HttpServletRequest request,HttpSession session,HttpServletResponse response) throws ServletException{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		PrintWriter out=null;
		PostQueryEntity postQueryEntity = new PostQueryEntity();
		UserEntity userEntity = (UserEntity) session.getAttribute("loginUser");
		if(userEntity != null){
			postQueryEntity.setUsercode(userEntity.getUsercode());
			postQueryEntity.setComcode(userEntity.getComcode());
			postQueryEntity.setUsertype(userEntity.getUsertype());
		}
		postQueryEntity.setOrderno(request.getParameter("orderno"));
		postQueryEntity.setOrderstatus(request.getParameter("orderstatus"));
		postQueryEntity.setChargedate(request.getParameter("chargedate"));
		postQueryEntity.setModifydate(request.getParameter("modifydate"));
		postQueryEntity.setLicenseno(request.getParameter("licenseno"));
		postQueryEntity.setQueryflag(request.getParameter("queryflag"));
		postQueryEntity.setBarcode(request.getParameter("barcode"));
		postQueryEntity.setPageno(Integer.parseInt(request.getParameter("pageno")));
		postQueryEntity.setPagesize(Integer.parseInt(request.getParameter("pagesize")));
		
		JSONObject json= new JSONObject();
		try {
			out = response.getWriter();
			Map<String, Object> orderMap = postService.queryOrderList(postQueryEntity);
			json = JSONObject.fromObject(orderMap);
			json.element("pagecount", orderMap.get("pagecount"));// 总页数
			json.element("pageno", orderMap.get("pageno"));// 页码
			json.element("totalcount", orderMap.get("totalcount"));// 数据总条数
		} catch (EpiccException e) {
			json.element("msg", "查询失败！"+e.getErrMess());
			json.element("res", 0);
			e.printStackTrace();
			logger.error(e.getErrMess());
		} catch (IOException e) {
			json.element("msg", "查询失败！");
			json.element("res", 0);
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	/**
	 * 
			* 描述:修改订单
			* @param map
			* @param request
			* @param postOrderEntity
			* @param response
			* @author ZN
	 */
	@RequestMapping(value={"/modifyPostInfo.do"})
	public void modifyPostInfo(ModelMap map,HttpSession session,HttpServletRequest request,PostOrderEntity order,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		UserEntity user = (UserEntity) session.getAttribute("loginUser");
		PrintWriter out=null;
		JSONObject json = new JSONObject();
		json.element("result", "0");
		try {
			out = response.getWriter();
			PostOrderEntity orderEntity = postService.modifyPostInfo(order,user);
			if(orderEntity!=null){
				json.element("msg", "修改成功");
				json.element("result", "1");
			}
		} catch (EpiccException e) {
			json.element("msg", "修改失败！"+e.getErrMess());
			e.printStackTrace();
			logger.error(e.getErrMess());
		} catch (IOException e) {
			json.element("msg", "修改失败！");
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
	 * 描述:反馈原因级联
	 * @param map
	 * @param request
	 * @param postOrderEntity
	 * @param response
	 * @author ZJM
	 */
	@RequestMapping(value={"/getBackreasons.do"})
	public void getBackreasons(ModelMap map,HttpServletRequest request,HttpServletResponse response){
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String flag = request.getParameter("flag");
		String backreasoncode = request.getParameter("backreasoncode");
		PrintWriter out=null;
		JSONObject json = new JSONObject();
		Map<String,String> map2 = new HashMap<String, String>();
		try {
			out = response.getWriter();
			if("2".equals(flag)){
				map2 = SysDicHelper.getInstance().getTwobackreasonMap(backreasoncode);
			}else if("3".equals(flag)){
				map2 = SysDicHelper.getInstance().getThrbackreasonMap(backreasoncode);
			}
			json = JSONObject.fromObject(map2);
			System.out.println(json);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	/**
	 * 
			* 描述:上传验车照片
			* @param map
			* @param session
			* @param request
			* @param response
			* @author 朱久满 2016年4月5日 上午9:57:49
	 */
	@RequestMapping(value={"/uploadCarPhotos.do"})
	public void uploadCarPhotos(ModelMap map,HttpSession session,HttpServletRequest request,HttpServletResponse response){
		JSONObject json = new JSONObject();
		PrintWriter out=null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			String photoids = request.getParameter("photoids");
			String serverIds = request.getParameter("serverIds");
			PostOrderEntity order = new PostOrderEntity();
			order.setOrderno(request.getParameter("orderno"));
			order.setIscarhurt(request.getParameter("iscarhurt"));
			List<CarPhotoEntity> list = new ArrayList<CarPhotoEntity>();
			//提取删除的照片
			if(photoids!=null && !"".equals(photoids.trim())){
				String[] ids = photoids.split(",");
				if(ids.length>0){
					for (String id : ids) {
						if(id != null && !"".equals(id.trim())){
							CarPhotoEntity carPhotoEntity = new CarPhotoEntity();
							carPhotoEntity.setId(id);
							list.add(carPhotoEntity);
						}
					}
				}
			}
			//提取新增的照片
			if(serverIds!=null && !"".equals(serverIds.trim())){
				String[] sids = serverIds.split(",");
				if(sids.length>0){
					for (String sid : sids) {
						if(sid != null && !"".equals(sid.trim())){
							CarPhotoEntity carPhotoEntity = new CarPhotoEntity();
							carPhotoEntity.setBase64code(WeixinUtil.downloadImage(sid));
							carPhotoEntity.setFilename(GetBussinessNoService.GetBussinessNo("CARPHOTO")+".jpg");
							list.add(carPhotoEntity);
						}
					}
				}
			}
			out = response.getWriter();
			postService.saveCarPhotos(list, order, (UserEntity)session.getAttribute("loginUser"));
			json.element("res", "1");
			json.element("msg", "上传成功！");
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("上传验车照片失败:"+e.getErrMess());
			json.element("res", "0");
			json.element("msg", "系统异常！");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("上传验车照片失败:"+e.getMessage());
			json.element("res", "0");
			json.element("msg", "系统异常！");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传验车照片失败:"+e.getMessage());
			json.element("res", "0");
			json.element("msg", "系统异常！");
		} finally {
			if(out!=null){
				out.println(json);
				out.flush();
				out.close();
			}
		}
	}
	
	
}
