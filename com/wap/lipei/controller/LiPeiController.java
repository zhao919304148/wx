package com.wap.lipei.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sys.dic.SysDicHelper;
import com.sys.exception.EpiccException;
import com.wap.lipei.entity.CaseInfoEntity;
import com.wap.lipei.entity.CaseQueryEntity;
import com.wap.lipei.entity.DeflossDetailPageEntity;
import com.wap.lipei.entity.DeflossDetailQueryEntity;
import com.wap.lipei.entity.MaterialEntity;
import com.wap.lipei.entity.MaterialFileEntity;
import com.wap.lipei.entity.NetWorkQueryEntity;
import com.wap.lipei.entity.ReportEntity;
import com.wap.lipei.service.LiPeiService;
import com.wap.trans.entity.tr_1015.ResTrans1015NetWorkEntity;
import com.wap.util.WxSDKUtil;
import com.wx.util.WeixinUtil;

import core.db.dao.IBaseService;

@Controller
@RequestMapping(value={"/lipei"})
public class LiPeiController {
	private static final Log logger = LogFactory.getLog(LiPeiController.class);

	@Autowired(required = false)
	private IBaseService baseService = null;

	public void setBaseService(IBaseService baseService) {
		this.baseService = baseService;
	}
	@Autowired(required = false)
	private LiPeiService liPeiService = null;
	
	public void setLiPeiService(LiPeiService liPeiService) {
		this.liPeiService = liPeiService;
	}

	/**
	 * 
			* 描述: 判断用户是否绑定
			* @param request
			* @param response
			* @author wangjinhua 2015年12月18日 上午11:10:59
	 */
	@RequestMapping(value={"/isBoundUser.do"})
	public ModelAndView isBoundUser(HttpServletRequest request,	HttpServletResponse response, HttpSession session) {
		String openId = request.getParameter("openId");
		ModelAndView view = new ModelAndView("/lipei/caselist");
		try {
			if (null != openId && !"".equals(openId)) {
				List<Map<String, String>> list = WeixinUtil.getCustBindInfoBound(openId);
				if(list==null){
					view.addObject("isBound", "0");
				}else{
					String identifyNumber = "";
					if(list.size()>0){
						identifyNumber = list.get(0).get("identifyNumber");
					}
					for (int i = 1; i < list.size(); i++) {
						if(!identifyNumber.contains(list.get(i).get("identifyNumber"))){
							identifyNumber += ","+ list.get(i).get("identifyNumber");
						}
					}
					session.setAttribute("identifyNumber", identifyNumber);
					view.addObject("identifyNumber", identifyNumber);
					if("".equals(identifyNumber)){
						view.addObject("isBound", "0");
					}else{
						view.addObject("isBound", "1");
					}
				}
			}else{
				view.addObject("isBound", "0");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	/**
	 * 
			* 描述:用户绑定
			* @param request
			* @param response
			* @author 朱久满 2015年12月30日 下午4:07:43
	 */
	@RequestMapping(value={"/bound.do"})
	public void bound(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String openid = request.getParameter("openId");
		String comcode = request.getParameter("comcode");
		String cardno = request.getParameter("cardno");
		String plateno = request.getParameter("plateno");
		String identifytype = request.getParameter("identifytype");
		Map<String, String> map = new HashMap<String, String>();
		map.put("openid", openid);
		map.put("comcode", comcode);
		map.put("cardno", cardno);
		map.put("plateno", plateno);
		map.put("identifytype", identifytype);
		Map<String, String> resmap = WeixinUtil.bound(map);
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		json.element("res","0");
		try {
			out = response.getWriter();
			if(resmap!=null){
				if("0".equals(resmap.get("res"))){
					json.element("msg","绑定成功！");
					json.element("res","1");
				}else{
					json.element("msg","绑定失败！"+resmap.get("msg"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("绑定失败:" + e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
			* 描述:报案列表查询
			* @param request
			* @param response
			* @author 朱久满 2015年12月31日 下午1:50:58
	 */
	@RequestMapping(value={"/queryCaseList.do"})
	public void queryCaseList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		CaseQueryEntity queryEntity = new CaseQueryEntity();
		String identifynumber = request.getParameter("identifynumber");
		String registno = request.getParameter("registno");
		String reportormobile = request.getParameter("reportormobile");
		String licenseno = request.getParameter("licenseno");
		queryEntity.setIdentifynumber(identifynumber);// 身份证号码
		queryEntity.setRegistno(registno);// 报案号(后6位)
		queryEntity.setReportormobile(reportormobile);// 手机号
		queryEntity.setLicenseno(licenseno);// 车牌号
		queryEntity.setCasestatus(request.getParameter("casestatus"));// 案件状态
		PrintWriter out = null;
		JSONArray json = new JSONArray();
		try {
			out = response.getWriter();
			if((identifynumber!=null && !"".equals(identifynumber.trim()))
					|| (registno!=null && !"".equals(registno.trim())
					&& licenseno!=null && !"".equals(licenseno.trim()))){
				List<ReportEntity> list = liPeiService.queryCaseList(queryEntity);
				json = JSONArray.fromObject(list);
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("报案列表查询失败:" + e.getErrMess());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("报案列表查询失败:" + e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
			* 描述:查看案件详情
			* @param request
			* @param response
			* @author 朱久满 2015年12月31日 下午1:52:50
	 */
	@RequestMapping(value={"/queryCaseDetail.do"})
	public ModelAndView queryCaseDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String registno = request.getParameter("registno");
		ModelAndView modelAndView = new ModelAndView("/lipei/casedetail");
		try {
			CaseInfoEntity caseInfo = liPeiService.queryCaseDetail(registno);
			modelAndView.addObject("case", caseInfo);
			modelAndView.addObject("res", "1");
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("报案详情查询失败:" + e.getErrMess());
			modelAndView.addObject("res", "0");
			modelAndView.addObject("msg", e.getErrMess());
		}
		return modelAndView;
	}
	
	/**
	 * 
			* 描述:定损明细查看
			* @param request
			* @param response
			* @author 朱久满 2016年1月4日 上午8:58:39
	 */
	@RequestMapping(value={"/queryDeflossDetail.do"})
	public void queryDeflossDetail(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		DeflossDetailQueryEntity deflossDetailQueryEntity = new DeflossDetailQueryEntity();
		deflossDetailQueryEntity.setPrpldeflossmainid(request.getParameter("prpldeflossmainid"));
		deflossDetailQueryEntity.setPageno(request.getParameter("pageno"));
		deflossDetailQueryEntity.setPagesize(request.getParameter("pagesize"));
		String queryflag = request.getParameter("queryflag");//1:换件清单 2:修理费用清单 3:辅料清单 4:待检测零部件清单
		DeflossDetailPageEntity detail = null;
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			out = response.getWriter();
			if ("1".equals(queryflag)) {
				detail = liPeiService.queryComponentDetailList(deflossDetailQueryEntity);
			} else if ("2".equals(queryflag)) {
				detail = liPeiService.queryRepairDetailList(deflossDetailQueryEntity);
			} else if ("3".equals(queryflag)) {
				detail = liPeiService.queryMaterialDetailList(deflossDetailQueryEntity);
			} else if ("4".equals(queryflag)) {
				detail = liPeiService.queryCheckDetailList(deflossDetailQueryEntity);
			}
			if (null != detail) {
				json.element("res", "1");
				json = JSONObject.fromObject(detail);
				json.element("queryflag", queryflag);
			} else {
				json.element("res", "0");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("定损清单查询失败:" + e.getErrMess());
			json.element("res", "0");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("系统异常:" + e.getMessage());
			json.element("res", "0");
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
			* 描述:上传索赔材料
			* @param request
			* @param response
			* @author 朱久满 2016年1月4日 上午9:04:58
			* 20161221去掉上传索赔材料功能
	 */
	@RequestMapping(value={"/uploadMaterial_old20161221.do"})
	public void uploadMaterial_old20161221(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		MaterialEntity materialEntity = new MaterialEntity();
		String access_token = WeixinUtil.getaccessToken_fromPICC().get("access_token");
		String photoids = request.getParameter("photoids");
		String serverIds = request.getParameter("serverIds");
		materialEntity.setAccess_token(access_token);
		materialEntity.setBankno(request.getParameter("bankno"));
		materialEntity.setLicenseno(request.getParameter("licenseno"));
		materialEntity.setOpenid(request.getParameter("openId"));
		materialEntity.setPayeename(request.getParameter("payeename"));
		materialEntity.setRegistno(request.getParameter("registno"));
		materialEntity.setBankname(request.getParameter("bankname"));
		materialEntity.setBanknamezh(request.getParameter("banknamezh"));
		materialEntity.setBanknameflc(request.getParameter("banknameflc"));
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		List<MaterialFileEntity> materialFileList = new ArrayList<MaterialFileEntity>();
		//提取删除的照片
		if(photoids!=null && !"".equals(photoids.trim())){
			String[] ids = photoids.split(",");
			if(ids.length>0){
				for (String id : ids) {
					if(id != null && !"".equals(id.trim())){
						MaterialFileEntity materialFileEntity = new MaterialFileEntity();
						materialFileEntity.setId(id);
						materialFileList.add(materialFileEntity);
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
						MaterialFileEntity materialFileEntity = new MaterialFileEntity();
						materialFileEntity.setMedia_id(sid.split(":")[0]);
						materialFileEntity.setPhototype(sid.split(":")[1]);
						materialFileList.add(materialFileEntity);
					}
				}
			}
		}
		materialEntity.setMaterialFileList(materialFileList);
		try {
			out = response.getWriter();
			materialEntity = liPeiService.uploadMaterial(materialEntity);
			json.element("res", "1");
			json.element("msg", "提交成功！");
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("资料照片上传失败:" + e.getErrMess());
			json.element("msg", "系统繁忙，请稍后再试！");
			json.element("res", "0");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常:" + e.getMessage());
			json.element("msg", "系统繁忙，请稍后再试！");
			json.element("res", "0");
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 
			* 描述:加载 提交索赔材料页面
			* @param request
			* @param response
			* @author 朱久满 2016年1月4日 上午10:15:31
			* 20161221去掉上传索赔材料功能
	 */
	@RequestMapping(value={"/loadMaterial_old20161221.do"})
	public ModelAndView loadMaterial_old20161221(HttpServletRequest request, HttpSession session, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		String URI = session.getServletContext().getAttribute("httpBasePath")+request.getRequestURI().substring(request.getRequestURI().indexOf("/", 1)+1);
		String photoUrl = SysDicHelper.getInstance().getValueByDicTypeAndDicId("PHOTOURL", "MATERIAL_URL");
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
		request.setAttribute("photoUrl", photoUrl);
		ModelAndView modelAndView = new ModelAndView("/lipei/submitdata");
		modelAndView.addObject("res", "0");
		modelAndView.addObject("hurtflag", request.getParameter("hurtflag"));
		modelAndView.addObject("casestatus", request.getParameter("casestatus"));
		String registno = request.getParameter("registno");
		try {
			if(registno!=null && !"".equals(registno.trim())){
				MaterialEntity material = liPeiService.queryMaterial(registno);
				if(material==null){
					material = new MaterialEntity();
				}
				if(material.getRegistno()==null || "".equals(material.getRegistno().trim())){
					material.setLicenseno(registno);
				}
				if(material.getLicenseno()==null || "".equals(material.getLicenseno().trim())){
					material.setLicenseno(request.getParameter("licenseno"));
				}
				modelAndView.addObject("material", material);
				modelAndView.addObject("res", "1");
			}
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("加载索赔材料失败:" + e.getErrMess());
		}catch (Exception e) {
			e.printStackTrace();
			logger.error("系统异常:" + e.getMessage());
		}
		return modelAndView;
	}

	/**
	 * 
			* 描述:服务网点查询
			* @param request
			* @param response
			* @author 朱久满 2016年3月22日 下午2:49:24
	 */
	@RequestMapping(value={"/queryNetWorkList.do"})
	public void queryNetWorkList(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		NetWorkQueryEntity netWorkQueryEntity = new NetWorkQueryEntity();
		netWorkQueryEntity.setAreacode(request.getParameter("areacode"));
		netWorkQueryEntity.setDistance(request.getParameter("distance"));
		netWorkQueryEntity.setLatitude(request.getParameter("latitude"));
		netWorkQueryEntity.setLongitude(request.getParameter("longitude"));
		netWorkQueryEntity.setType(request.getParameter("type"));
		PrintWriter out = null;
		JSONObject json = new JSONObject();
		try {
			JSONArray jsonArray = new JSONArray();
			out = response.getWriter();
			List<ResTrans1015NetWorkEntity> list = liPeiService.queryNetWorkList(netWorkQueryEntity);
			jsonArray = JSONArray.fromObject(list);
			json.element("list", jsonArray);
			json.element("res", "1");
		} catch (EpiccException e) {
			e.printStackTrace();
			logger.error("网点信息获取失败:" + e.getErrMess());
			json.element("res", "0");
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("系统异常:" + e.getMessage());
			json.element("res", "0");
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}
	
}
