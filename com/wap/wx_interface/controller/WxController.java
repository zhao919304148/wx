package com.wap.wx_interface.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.informix.util.stringUtil;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import com.sys.dic.SysDicHelper;
import com.sys.exception.EpiccException;
import com.thoughtworks.xstream.core.util.Base64Encoder;
import com.wap.dzsw.entity.ScoreConsume;
import com.wap.dzsw.entity.UpdateOrder;
import com.wap.dzsw.service.FuWuService;
import com.wap.dzsw.service.GiftCardService;
import com.wap.dzsw.service.ScoreService;
import com.wap.trans.entity.ResponseHeadEntity;
import com.wap.trans.entity.tr_1066.ReqTrans1066Entity;
import com.wap.trans.entity.tr_1066.ResTrans1066BasePartEntity;
import com.wap.trans.entity.tr_1066.ResTrans1066BodyEntity;
import com.wap.trans.entity.tr_1066.ResTrans1066Entity;
import com.wap.trans.service.TransService;
import com.wap.util.AESCode;
import com.wap.util.CommonUtils;
import com.wap.util.ConstantUtils;
import com.wap.util.HttpUtil;
import com.wap.util.RSACode;
import com.wap.wx_interface.entity.custom.DzmRegistVo;
import com.wap.wx_interface.entity.custom.MaintainOrderRequest;
import com.wap.wx_interface.entity.custom.ValidateWashCodeRequestMessageVo;
import com.wap.wx_interface.entity.custom.ValidateWashCodeRequestVo;
import com.wap.wx_interface.entity.custom.YmOrderVo;
import com.wap.wx_interface.utils.AESUtils;
import com.wap.wx_interface.utils.EncryptUtil;
import com.wap.wx_interface.utils.HMACUtil;
import com.wx.util.ConfigUtil;
import com.wx.util.Md5Util;
import com.wx.util.WeixinUtil;

@Controller
@RequestMapping(value = { "/wx_interface" })
public class WxController {
	private static final Logger logger = Logger.getLogger(WxController.class);

	@Autowired(required = false)
	private FuWuService fuWuService;
	@Autowired
	private TransService transService;

	public void setFuWuService(FuWuService fuWuService) {
		this.fuWuService = fuWuService;
	}

	@Autowired(required = false)
	private ScoreService scoreService;
	@Autowired(required = true)
	private CacheManager cacheManager;
	@Autowired
	private GiftCardService giftCardService;
	public void setScoreService(ScoreService scoreService) {
		this.scoreService = scoreService;
	}

	/**
	 * 
	 * 描述:发送索赔材料审核结果(未通过时)
	 * 
	 * @param request
	 * @param response
	 * @author 朱久满 2016年1月25日 下午2:58:26
	 */
	@RequestMapping(value = { "/sendAuditRes.do" })
	public void sendAuditRes(HttpServletRequest request,
			HttpServletResponse response, String jsondata) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		logger.info("审核不通过通知消息请求报文:" + jsondata);
		PrintWriter out = null;
		JSONObject json = JSONObject.fromObject(jsondata);
		try {
			out = response.getWriter();
			// 获取模板id
			String templateid = SysDicHelper.getInstance()
					.getValueByDicTypeAndDicId("TEMPLATE_ID", "sendAuditRes");
			String url = SysDicHelper.getInstance().getValueByDicTypeAndDicId(
					"WEIXINUTIL", "WX_URL");
			// 组装报文
			Map<String, Object> jsonmap = new HashMap<String, Object>();
			Map<String, Object> datamap = new HashMap<String, Object>();
			Iterator it = json.keys();
			while (it.hasNext()) {
				String key = it.next().toString();
				if ("openid".equals(key)) {
					jsonmap.put("touser", json.get("openid"));
				} else {
					Map<String, Object> parammap = new HashMap<String, Object>();
					parammap.put("value", json.get(key));
					parammap.put("color", "#173177");
					datamap.put(key, parammap);
				}
			}
			jsonmap.put("data", datamap);
			jsonmap.put("url", url);
			jsonmap.put("template_id", templateid);
			json = WeixinUtil.sendTemplateMessage(JSONObject
					.fromObject(jsonmap).toString());
		} catch (Exception e) {
			json = new JSONObject();
			json.element("retcode", "0002");
			json.element("retmsg", "系统异常");
			logger.error("发送索赔材料审核信息失败:" + e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}

	/**
	 * 
	 * 描述:发送文本客服消息
	 * 
	 * @param request
	 * @param response
	 * @param jsondata
	 * @author 朱久满 2016年3月8日 上午10:30:43
	 */
	@RequestMapping(value = { "/sendCustomMessage.do" })
	public void sendCustomMessage(HttpServletRequest request,
			HttpServletResponse response, String jsondata) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		logger.info("发送文本客服消息请求报文:" + jsondata);
		PrintWriter out = null;
		JSONObject json = JSONObject.fromObject(jsondata);
		try {
			out = response.getWriter();
			// 组装报文
			Map<String, Object> jsonmap = new HashMap<String, Object>();
			Map<String, Object> textmap = new HashMap<String, Object>();
			textmap.put("content", json.get("content"));
			jsonmap.put("touser", json.get("openid"));
			jsonmap.put("msgtype", "text");
			jsonmap.put("text", textmap);
			json = WeixinUtil.sendCustomMessage(JSONObject.fromObject(jsonmap)
					.toString());
		} catch (Exception e) {
			json = new JSONObject();
			json.element("retcode", "0002");
			json.element("retmsg", "系统异常");
			logger.error("发送文本客服消息失败:" + e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}

	// 庞大
	@ResponseBody
	@RequestMapping(value = { "/updateOrderMseeage.do" })
	public void updateOrderMseeage(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		JSONObject json = new JSONObject();
		String jsondata = "";
		PrintWriter out = null;
		Map map = null;
		String resCode = "";
		String msg = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			jsondata = URLDecoder.decode(sb.toString(), "UTF-8");
			logger.info("庞大请求参数：" + jsondata);
			logger.info("请求参数：" + jsondata);
			// 验证参数是否有效
			String result = HttpUtil.checkParam(jsondata,
					"cardNo,orderStatus,target,sign");
			if (!result.equals("")) {
				logger.info(result);
				json.put("resCode", "400");
				json.put("msg", result);
			}
			com.alibaba.fastjson.JSONObject fromObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
					.parse(jsondata);
			out = response.getWriter();
			JSONObject huoqu = new JSONObject();
			huoqu.put("cardNo", fromObject.get("cardNo"));
			huoqu.put("cardStatus", fromObject.get("orderStatus"));
			UpdateOrder updateOrder = com.alibaba.fastjson.JSONObject
					.parseObject(huoqu.toString(), UpdateOrder.class);
			String param = "cardno=" + fromObject.get("cardNo")
					+ "&orderstatus=" + fromObject.get("orderStatus")
					+ "&target=" + fromObject.get("target")
					+ ConfigUtil.getString("pangdakey");
			logger.info("param=" + param);
			String md5 = Md5Util.MD5(param);
			logger.info("md5:" + md5);
			logger.info("sign:" + fromObject.get("sign"));
			if (!fromObject.get("sign").equals(md5)) {
				logger.info("验签失败");
				json.put("resCode", "400");
				json.put("msg", "验签失败");
			} else {
				String string = updateOrder.getCardNo().split(",")[0];
				updateOrder.setCardNo(string);
				map = fuWuService.getupdateOrder(updateOrder);
				resCode = (String) map.get("res");
				msg = (String) map.get("msg");
				if ("1".equals(resCode)) {
					json.put("resCode", "200");
					json.put("msg", "操作成功");
				} else {
					json.put("resCode", "400");
					json.put("msg", msg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			json.put("resCode", "400");
			json.put(
					"msg",
					StringUtils.isBlank(e.getMessage()) ? "请求异常！" : e
							.getMessage());
			logger.error("error:" + e.getMessage());
		} finally {
			logger.info("返回信息：" + json.toString());
			out.println(json);
			out.flush();
			out.close();
		}

	}

	// 卡拉丁
	@ResponseBody
	@RequestMapping(value = { "/updateOrderMseeageKlaDing.do" })
	public void updateOrderMseeageKlaDing(HttpServletRequest request,
			HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		JSONObject json = new JSONObject();
		String jsondata = "";
		PrintWriter out = null;
		Map map = null;
		String resCode = "";
		String msg = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			jsondata = URLDecoder.decode(sb.toString(), "UTF-8");
			logger.info("请求参数：" + jsondata);
			// 验证参数是否有效
			String result = HttpUtil.checkParam(jsondata,
					"cardNo,orderStatus,target,sign");
			if (!result.equals("")) {
				logger.info(result);
				json.put("resCode", "400");
				json.put("msg", result);
			}
			com.alibaba.fastjson.JSONObject fromObject = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
					.parse(jsondata);
			out = response.getWriter();
			JSONObject huoqu = new JSONObject();
			huoqu.put("cardNo", fromObject.get("cardNo"));
			huoqu.put("cardStatus", fromObject.get("orderStatus"));
			UpdateOrder updateOrder = com.alibaba.fastjson.JSONObject
					.parseObject(huoqu.toString(), UpdateOrder.class);
			// 拼接字符串
			String param = "cardno=" + fromObject.get("cardNo")
					+ "&orderstatus=" + fromObject.get("orderStatus")
					+ "&target=" + fromObject.get("target")
					+ ConfigUtil.getString("kaladingkey");
			logger.info("param=" + param);
			String md5 = Md5Util.MD5(param);
			logger.info("md5:" + md5);
			logger.info("sign:" + fromObject.get("sign"));
			if (!fromObject.get("sign").equals(md5)) {
				json.put("resCode", "400");
				json.put("msg", "验签失败");
			} else {
				logger.info("进行服务运行");
				logger.info("updateOrder=" + updateOrder.toString());
				map = fuWuService.getupdateOrder(updateOrder);
				resCode = (String) map.get("res");
				msg = (String) map.get("msg");
				if ("1".equals(resCode)) {
					json.put("resCode", "200");
					json.put("msg", "操作成功");
				} else {
					json.put("resCode", "400");
					json.put("msg", "操作失败");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error==" + e.getMessage());
		} finally {
			out.println(json);
			out.flush();
			out.close();
		}
	}

	/**
	 * 
	 * 描述:积分余额查询
	 * 
	 * @param key
	 * @param data
	 * @author qex2017年1月19日 下午3:08:08
	 */
	@ResponseBody
	@RequestMapping(value = { "/scoreBalaceQuery.do" })
	public String scoreBalaceQuery(String key, String data,
			HttpServletRequest request) {
		logger.info("key:" + key + ",data:" + data);
		JSONObject res = new JSONObject();
		String dataParam = "";
		String aeskey = "";
		// key =
		// "oQeOEVFbMRKmO6rl//inUOv7lGMS2R8wh1wBzzA6AwkelEfseyg/MA8HOh8QG8IAr+1bEvbgRVN6XTLYjoXeiTOtdXlJQ36ryAU4SFMaTqc2HnJ957sRBR4xbSe1xk3ELRo6CTTv2R7OzAEANp4vj9hOgEeFLJmmj0WkStazu+k=";
		// Ze9Qgob5dIpusnquCfCsXE PjHfarR2Lc8EfPt03MVTAz
		// Y9sg1NzQ8Hq2gJw68P3NewX1jMM0YMBiji0MHOn7cg3jKyBwtkR8iO4TF2fZgYIRimZpk6MRatcKZ3jFafKlIpRJMlPeVKwDtfPzdwqG9W
		// m5AoshwCSPNRfQf864=
		try {
			if (!"".equals(key) && !"".equals(data)) {
				logger.info("=========解密开始==========");
				key = key.replaceAll(" ", "+");
				data = data.replaceAll(" ", "+");
				logger.info("key=" + key);
				// 解密key
				aeskey = new String(RSACode.Dencrypt(Base64.decode(key)));
				logger.info("aeskey:" + aeskey);
				// 解密data
				dataParam = AESCode.Decrypt(data, aeskey);
				logger.info("key:" + aeskey + ",dataParam:" + dataParam);
			}
			// 校验参数准确
			// {"userId":"12345678","requestTime":"2017-01-22 11:24:42"}
			String param = HttpUtil.checkParam(dataParam, "userId,requestTime");
			logger.info("参数校验param:" + param);
			if (!"".equals(param)) {
				res.put("resCode", "0");
				res.put("msg", param);
				res.put("scoreBalance", "0");
				return AESCode.Encrypt(res.toString(), aeskey);
			}
			com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
					.parse(dataParam);
			// 解密userId;
			String userId = (String) json.get("userId");
			logger.info("userId=" + userId.replaceAll(" ", "+"));
			userId = AESCode.Decrypt(userId.replaceAll(" ", "+"),
					AESCode.AESKEY);
			String requestTime = (String) json.get("requestTime");
			// 查询余额
			res = scoreService.queryBalanceById(userId, requestTime);
			logger.info("余额查询接口返回res:" + res);
			return AESCode.Encrypt(res.toString(), aeskey);
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("error:" + e.getMessage());
			res.put("resCode", "0");
			res.put("msg", "程序异常");
			res.put("scoreBalance", "0");
			try {
				return AESCode.Encrypt(res.toString(), aeskey);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.info("error=" + e1.getMessage());
				return "error : soft exception";
			}
		}
	}

	/**
	 * 
	 * 描述:积分消费，订单推送
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @author qex 2017年1月19日 下午4:56:13
	 */
	@ResponseBody
	@RequestMapping(value = { "/scoreConsume.do" })
	public String scoreConsume(String key, String data) {
		logger.info("请求密文：key=" + key + ";data=" + data);
		JSONObject res = new JSONObject();
		String dataParam = "";
		String aeskey = "";
		try {
			if (!"".equals(key) && !"".equals(data)) {
				key = key.replaceAll(" ", "+");
				data = data.replaceAll(" ", "+");
				logger.info("key=" + key);
				// 解密key
				aeskey = new String(RSACode.Dencrypt(Base64.decode(key)));
				// 解密data
				dataParam = AESCode.Decrypt(data, aeskey);
				logger.info("key:" + aeskey + ",dataParam:" + dataParam);
			}

			// 校验参数准确
			String param = HttpUtil.checkParam(dataParam,
					"userId,amount,openId,orderList");
			logger.info("参数校验param:" + param);
			if (!"".equals(param)) {
				res.put("resCode", "0");
				res.put("msg", param);
				return AESCode.Encrypt(res.toString(), aeskey);
			}
			logger.info("===============参数校验==============");
			ScoreConsume scoreConsume = com.alibaba.fastjson.JSONObject
					.parseObject(dataParam, ScoreConsume.class);
			logger.info("请求参数：" + scoreConsume.toString());
			logger.info("解密openId:" + scoreConsume.getOpenId());
			scoreConsume.setOpenId(AESCode.Decrypt(scoreConsume.getOpenId(),
					AESCode.AESKEY));
			// 解密userId;
			String userId = AESCode.Decrypt(scoreConsume.getUserId()
					.replaceAll(" ", "+"), AESCode.AESKEY);
			logger.info("userId:" + userId);
			scoreConsume.setUserId(userId);
			res = scoreService.scoreConsume(scoreConsume);
			return AESCode.Encrypt(res.toString(), aeskey);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("resCode", "0");
			res.put("msg", "程序异常");
			try {
				return AESCode.Encrypt(res.toString(), aeskey);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.info("error:" + e1.getMessage());
				return "error:soft exception";
			}
		}
	}

	/**
	 * 
	 * 描述:积分消费撤销
	 * 
	 * @param key
	 * @param data
	 * @return
	 * @author qex 2017年1月19日 下午5:33:53
	 */
	@ResponseBody
	@RequestMapping(value = { "/scoreCancel.do" })
	public String scoreCancel(String key, String data) {
		logger.info("请求密文：key=" + key + ";data=" + data);
		JSONObject res = new JSONObject();
		String dataParam = "";
		String aeskey = "";
		try {
			if (!"".equals(key) && !"".equals(data)) {
				key = key.replaceAll(" ", "+");
				data = data.replaceAll(" ", "+");
				logger.info("key=" + key);
				// 解密key
				aeskey = new String(RSACode.Dencrypt(Base64.decode(key)));
				// 解密data
				dataParam = AESCode.Decrypt(data, aeskey);
				logger.info("key:" + aeskey + ",dataParam:" + dataParam);
			}
			// 校验参数准确
			String param = HttpUtil
					.checkParam(dataParam, "orderNo,requestTime");
			logger.info("参数校验param:" + param);
			if (!"".equals(param)) {
				res.put("resCode", "0");
				res.put("msg", param);
				return AESCode.Encrypt(res.toString(), aeskey);
			}
			com.alibaba.fastjson.JSONObject json = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
					.parse(dataParam);
			String orderNo = (String) json.get("orderNo");
			String requestTime = (String) json.get("requestTime");
			String userId = (String) json.get("userId");
			String openId = (String) json.get("openId");
			res = scoreService
					.scoreCancel(orderNo, requestTime, userId, openId);
			return AESCode.Encrypt(res.toString(), aeskey);
		} catch (Exception e) {
			e.printStackTrace();
			res.put("resCode", "0");
			res.put("msg", "程序异常");
			try {
				return AESCode.Encrypt(res.toString(), aeskey);
			} catch (Exception e1) {
				e1.printStackTrace();
				logger.info("error:" + e1.getMessage());
				return "error:soft exception";
			}
		}
	}

	/**
	 * 
	 * 描述: 洗车码验证对接接口 1042 1 验证通过 -01 报文解析错误 -02 参数校验不通过 -03 码已过期或者失效 -04
	 * 礼品已无可扣减次数 -05 请求超时，不予处理 -06 签名校验失败 -99 未知错误
	 * 
	 * @return
	 * @author 许宝众 2017年7月11日 上午9:36:56
	 */
	@RequestMapping(value = { "/validateWashCarCode.do" })
	public void validateWashCarCode(HttpServletRequest request,
			HttpServletResponse response) {
		Date now = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		logger.info("验证洗车码接口调用时间：" + sdf.format(now));
		String res = null;
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		String resCode = "resCode";
		String errMsg = "errMsg";
		// 默认未知错误
		resJson.put(resCode, "-99");
		resJson.put(errMsg, "未知错误");
		String requestBody = null;
		StringBuffer sb = new StringBuffer();
		List<String> readLines;
		try {
			ServletInputStream in = request.getInputStream();
			readLines = IOUtils.readLines(in);
			if (readLines != null) {
				for (String oneLine : readLines) {
					sb.append(oneLine);
				}
			}
			requestBody = sb.toString();
			logger.info("校验洗车码接口，原始请求报文：" + requestBody);
			if (StringUtils.isNotBlank(requestBody)) {
				// 校验请求报文的合法性
				ValidateWashCodeRequestVo req = JSON.parseObject(requestBody,
						ValidateWashCodeRequestVo.class);
				Assert.notNull(req);
				String message = req.getMessage();
				String account = req.getAccount();
				Assert.isTrue(StringUtils.isNotBlank(account), "account不能为空");
				Assert.isTrue(StringUtils.isNotBlank(message), "message不能为空");
				String token = AESUtils
						.generateWashCarCodeTokeByAccount(account);
				// 解密请求报文
				String decrypt = AESUtils.decrypt(message, token);
				logger.info("校验洗车码接口，解密后message：" + decrypt);
				com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
						.parse(decrypt);
				String washCarCode = (String) jsonObj.get("washCarCode");
				String networkName = (String) jsonObj.get("networkName");
				String networkAddress = (String) jsonObj.get("networkAddress");
				String transportTime = (String) jsonObj.get("transportTime");
				String thirdOrderId = (String) jsonObj.get("thirdOrderId");
				String sign = (String) jsonObj.get("sign");
				// 校验必传参数
				if (StringUtils.isNotBlank(washCarCode)
						&& StringUtils.isNotBlank(networkName)
						&& StringUtils.isNotBlank(networkAddress)
						&& StringUtils.isNotBlank(transportTime)
						&& StringUtils.isNotBlank(thirdOrderId)
						&& StringUtils.isNotBlank(sign)) {
					// 校验签名
					String s = String
							.format("washCarCode=%s&networkName=%s&networkAddress=%s&transportTime=%s&thirdOrderId=%s&token=%s",
									washCarCode, networkName, networkAddress,
									transportTime, thirdOrderId, token);
					String targetSign = null;
					try {
						targetSign = DigestUtils.md5Hex(s.getBytes("utf-8"))
								.toUpperCase();
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					if (targetSign.equals(sign)) {
						// 调用落地接口，验证洗车码
						ValidateWashCodeRequestMessageVo messageVo = new ValidateWashCodeRequestMessageVo();
						messageVo.setCompanyCode(account);
						messageVo.setWashCarCode(washCarCode);
						messageVo.setNetworkName(networkName);
						messageVo.setNetworkAddress(networkAddress);
						messageVo.setTransportTime(transportTime);
						messageVo.setThirdOrderId(thirdOrderId);
						messageVo.setSign(targetSign);
						resJson = fuWuService.validateWashCarCode(messageVo);
					} else {
						resJson.put(resCode, "-06");
						resJson.put(errMsg, "签名校验失败");
					}
				} else {
					resJson.put(resCode, "-02");// -02
					resJson.put(errMsg, "参数校验不通过");
				}
				res = AESUtils.encrypt(resJson.toString(), token);
				logger.info("校验洗车码接口，响应报文[加密前：" + resJson.toString() + "][加密后："
						+ res + "]");
			} else {
				res = resJson.toString();
			}
		} catch (IOException e1) {
			logger.info("校验洗车码接口，读取异常：", e1);
		} finally {
			String characterEncoding = request.getCharacterEncoding();
			response.setCharacterEncoding(characterEncoding);
			writeResponseMsg(response, res);
		}
	}

	@RequestMapping(value = "/getWashCarCodeOrder.do")
	public void getWashCarCodeOrder(HttpServletRequest request,
			HttpServletResponse response) {
		String res = null;
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		String resCode = "resCode";
		String errMsg = "errMsg";
		// 默认未知错误
		resJson.put(resCode, "-99");
		resJson.put(errMsg, "未知错误");
		String requestBody = null;
		StringBuffer sb = new StringBuffer();
		List<String> readLines;
		try {
			ServletInputStream in = request.getInputStream();
			readLines = IOUtils.readLines(in);
			if (readLines != null) {
				for (String oneLine : readLines) {
					sb.append(oneLine);
				}
			}
		} catch (IOException e1) {
			logger.info("校验洗车码接口，读取异常：", e1);
		}
		requestBody = sb.toString();
		logger.info("洗车码验证订单查询接口，原始请求报文：" + requestBody);
		if (StringUtils.isNotBlank(requestBody)) {
			// 校验请求报文的合法性
			ValidateWashCodeRequestVo reqvo = JSON.parseObject(requestBody,
					ValidateWashCodeRequestVo.class);
			Assert.notNull(reqvo);
			String message = reqvo.getMessage();
			String account = reqvo.getAccount();
			Assert.isTrue(StringUtils.isNotBlank(account), "account不能为空");
			Assert.isTrue(StringUtils.isNotBlank(message), "message不能为空");
			String token = AESUtils.generateWashCarCodeTokeByAccount(account);
			// 解密请求报文
			String decrypt = AESUtils.decrypt(message, token);
			logger.info("洗车码验证订单查询接口，解密后message：" + decrypt);
			com.alibaba.fastjson.JSONObject jsonObj = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject
					.parse(decrypt);
			String thirdOrderId = (String) jsonObj.get("thirdOrderId");
			String sign = (String) jsonObj.get("sign");
			// 校验必传参数
			if (StringUtils.isNotBlank(sign)) {
				// 校验签名
				String s = String.format("thirdOrderId=%s&token=%s",
						thirdOrderId, token);
				String targetSign = null;
				try {
					targetSign = DigestUtils.md5Hex(s.getBytes("utf-8"))
							.toUpperCase();
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				if (targetSign.equals(sign)) {
					// 调用落地接口
					ValidateWashCodeRequestMessageVo messageVo = new ValidateWashCodeRequestMessageVo();
					messageVo.setCompanyCode(account);
					messageVo.setThirdOrderId(thirdOrderId);
					messageVo.setSign(targetSign);
					resJson = fuWuService.getWashCarOrder(messageVo);
				} else {
					resJson.put(resCode, "-06");
					resJson.put(errMsg, "签名校验失败");
				}
			} else {
				resJson.put(resCode, "-05");
				resJson.put(errMsg, "请求参数错误");
			}
			res = AESUtils.decrypt(resJson.toString(), token);
			logger.info("洗车码验证订单查询接口，响应报文（加密前）：" + res);
		} else {
			res = resJson.toString();
		}
		String characterEncoding = request.getCharacterEncoding();
		response.setCharacterEncoding(characterEncoding);
		writeResponseMsg(response, res);
	}

	private static void writeResponseMsg(HttpServletResponse response,
			String msg) {
		BufferedWriter bw = null;
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			String characterEncoding = response.getCharacterEncoding();
			response.setContentType("text/html; charset=" + characterEncoding);
			bw = new BufferedWriter(new OutputStreamWriter(outputStream,
					characterEncoding));
			bw.write(msg);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			try {
				bw.close();
				outputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 亿美礼品兑换同步卡号状态 描述:亿美礼品兑换同步卡号状态
	 * 
	 * @param request
	 * @param response
	 * @author 朱久满 2017年7月30日 上午9:21:22
	 */
	@RequestMapping("updateYmOrderStatus.do")
	public void updateYmOrderStatus(HttpServletRequest request,
			HttpServletResponse response) {
		String res = null;
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		// 默认未知错误
		resJson.put("result", false);
		resJson.put("returnParam", "未知错误");
		try {
			String requestBody = null;
			StringBuffer sb = new StringBuffer();
			List<String> readLines;
			ServletInputStream in = request.getInputStream();
			readLines = IOUtils.readLines(in);
			if (readLines != null) {
				for (String oneLine : readLines) {
					sb.append(oneLine);
				}
			}
			requestBody = sb.toString();
			logger.info("亿美礼品兑换同步卡号状态接口，原始请求报文：" + requestBody);
			// 请求参数:【随机三位数字+请求参数Json(AES加密, CBC ,PKCS7)+IV】
			String reqJson = requestBody
					.substring(3, requestBody.length() - 16);
			// 对请求参数Json 进行解密
			String iv = requestBody.substring(requestBody.length() - 16);
			logger.info("随机16位IV=" + iv);
			reqJson = EncryptUtil.aesDecrypt(reqJson, ConstantUtils.YM_AESKEY,
					iv);
			logger.info("原始请求报文解密后的值=" + reqJson);
			com.alibaba.fastjson.JSONObject data = com.alibaba.fastjson.JSONObject
					.parseObject(reqJson);
			String mobile = data.getString("mobile");
			String giftType = data.getString("giftType");
			String cardNo = data.getString("cardNo");
			String cardStatus = data.getString("cardStatus");
			String ymOrderNo = data.getString("ymOrderNo");
			String orderAmount = data.getString("orderAmount");
			String timestamp = data.getString("timestamp");
			String sign = data.getString("sign");
			if (StringUtils.isNotBlank(mobile)
					&& StringUtils.isNotBlank(giftType)
					&& StringUtils.isNotBlank(cardNo)
					&& StringUtils.isNotBlank(cardStatus)
					&& StringUtils.isNotBlank(ymOrderNo)
					&& StringUtils.isNotBlank(orderAmount)
					&& StringUtils.isNotBlank(timestamp)) {
				// 校验签名是否一致
				String signStr = mobile + giftType + cardNo + cardStatus
						+ ymOrderNo + orderAmount + timestamp;
				String encSign = HMACUtil.encryptHMAC(signStr,
						ConstantUtils.YM_HMACSHA256_KEY);
				if (sign.equals(encSign)) {
					// 调用落地系统接口
					YmOrderVo ymOrderVo = new YmOrderVo();
					ymOrderVo.setMobile(mobile);
					ymOrderVo.setGiftType(giftType);
					ymOrderVo.setCardNo(cardNo);
					ymOrderVo.setCardStatus(cardStatus);
					ymOrderVo.setYmOrderNo(ymOrderNo);
					ymOrderVo.setOrderAmount(orderAmount);
					com.alibaba.fastjson.JSONObject json = fuWuService
							.updateYmOrderStatus(ymOrderVo);
					if ("1".equals(json.getString("resCode"))) {
						resJson.put("result", true);
						resJson.put("returnParam", json.getString("errMsg"));
					} else {
						resJson.put("result", false);
						resJson.put("returnParam", json.getString("errMsg"));
					}
				} else {
					resJson.put("result", false);
					resJson.put("returnParam", "签名错误");
				}
			} else {
				resJson.put("result", false);
				resJson.put("returnParam", "请求参数错误");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 对返回信息进行加密
			Base64Encoder base64Encoder = new Base64Encoder();
			try {
				res = base64Encoder.encode(resJson.toJSONString().getBytes(
						"UTF-8"));
				res = URLEncoder.encode(res, "UTF-8");
				logger.info("接口返回加密后的数据：" + res);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			String characterEncoding = request.getCharacterEncoding();
			response.setCharacterEncoding(characterEncoding);
			writeResponseMsg(response, res);
		}
	}

	/**
	 * 
	 * 描述:发送模板信息
	 * 
	 * @param request
	 * @param response
	 * @author zs 2017年9月8日 上午10:46:01
	 */
	@RequestMapping(value = { "/sendWxTemplateMsg.do" })
	public void sendWxTemplateMsg(HttpServletRequest request,
			HttpServletResponse response) {
		// 解密key
		String keyStr = "WXTEMPLATEMESSAGE";
		String res = null;
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		String resCode = "resCode";
		String errMsg = "errMsg";
		// 默认未知错误
		resJson.put(resCode, "-99");
		resJson.put(errMsg, "未知错误");
		String requestBody = null;
		StringBuffer sb = new StringBuffer();
		List<String> readLines;
		try {
			ServletInputStream in = request.getInputStream();
			readLines = IOUtils.readLines(in);
			if (readLines != null) {
				for (String oneLine : readLines) {
					sb.append(oneLine);
				}
			}
		} catch (IOException e1) {
			logger.info("发送模板信息接口，读取异常：", e1);
		}
		requestBody = sb.toString();
		logger.info("发送模板信息接口，原始请求报文：" + requestBody);
		if (StringUtils.isNotBlank(requestBody)) {
			// 解密报文
			requestBody = AESCode.customDecypt(requestBody, keyStr);
			logger.info("发送模板信息接口，解密后请求报文：" + requestBody);
			if (StringUtils.isNotBlank(requestBody)) {
				res = WeixinUtil.sendTemplateMessage(requestBody).toString();
				logger.info("微信服务器返回原始报文：" + res);
				String uuid = UUID.randomUUID().toString().replaceAll("-", "")
						.substring(0, 16);
				// 加密微信服务器返回原始报文
				res = AESCode.customEncrypt(res, keyStr);
				logger.info("加密微信服务器返回报文：" + res);
			} else {
				res = resJson.toString();
			}
		} else {
			res = resJson.toString();
		}
		String characterEncoding = request.getCharacterEncoding();
		response.setCharacterEncoding(characterEncoding);
		writeResponseMsg(response, res);
	}
	@ResponseBody
	@RequestMapping(value="maintainOrder.do",method={RequestMethod.POST,RequestMethod.GET},produces={"text/plain;charset=utf-8"})
	public String  maintainOrder(HttpServletRequest request,@RequestBody String postData){
		long begin = System.currentTimeMillis();
		//未加密前响应json
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		resJson.put("resCode", "0");//默认失败
		//加密后响应报文
		//解密后message节点信息
		String messgaeDecrypt = null;
		PrivateKey privateKey = null;
		String md5Key = null;
		try{
			if(StringUtils.isNotBlank(postData)){
				//校验参数
				com.alibaba.fastjson.JSONObject reqJson = com.alibaba.fastjson.JSONObject.parseObject(postData);
				String requestId = reqJson.getString("requestId");
				String comCode = reqJson.getString("comCode");
				String message = reqJson.getString("message");
				String sign = reqJson.getString("sign");
				//null 转 ""
				requestId = CommonUtils.parseNull2String(requestId);
				comCode = CommonUtils.parseNull2String(comCode);
				message = CommonUtils.parseNull2String(message);
				sign = CommonUtils.parseNull2String(sign);
				
				resJson.put("responseId", requestId);
				if(StringUtils.isNotBlank(requestId)
						&&StringUtils.isNotBlank(comCode)
						&&StringUtils.isNotBlank(message)
						&&StringUtils.isNotBlank(sign)){
					//得到对应服务商的私钥
					String alias = "maintain_"+comCode;
					char[] keyPwd = "piccbj@password".toCharArray();
					try {
						privateKey = getInterfacePrivateKeyWithCache(alias , keyPwd);
					} catch (UnrecoverableKeyException | KeyStoreException
							| NoSuchAlgorithmException | CertificateException
							| IOException e) {
						logger.info("获取私钥时发生异常",e);
						throw e;
					}
					md5Key = ConfigUtil.getString("MD5KEY_"+comCode);
					if(privateKey!=null&&md5Key!=null){
						//验签
						Map<String, String> reqParams = new HashMap<String,String>();
						reqParams.put("requestId", requestId);
						reqParams.put("comCode", comCode);
						reqParams.put("message", message);
						if(CommonUtils.verifyParamsSign(md5Key , reqParams , sign)){
							//解密message
							messgaeDecrypt = CommonUtils.decryptMessageWithPrivateKey(privateKey, message);
							MaintainOrderRequest requestVo = com.alibaba.fastjson.JSONObject.parseObject(messgaeDecrypt, MaintainOrderRequest.class);
//							用户id	字符串	Y	微信openId加密后
							String userId = requestVo.getUserId();
//							卡号	字符串	Y	加密卡号
							String cardId = requestVo.getCardId();
//							卡密	字符串	Y	
							String cardPwd = requestVo.getCardPwd();
//							保养方式	字符串	Y	0：上门保养1：到店保养
							String maintainType = requestVo.getMaintainType();
//							服务车牌号	字符串	Y	
							String licenseNo = requestVo.getLicenseNo();
//							客户手机号	字符串	Y	预约手机号
							String userMobile = requestVo.getUserMobile();
//							服务日期	日期	Y	格式：yyyy-MM-dd
							String serviceDate = requestVo.getServiceDate();
//							网点名称	字符串	N	到店保养时有值
							String networkName = requestVo.getNetworkName();
//							服务地址	字符串	N	上门保养时有值
							String serviceAddress = requestVo.getServiceAddress();
//							机油型号	字符串	Y	
							String oilType = requestVo.getOilType();
//							机滤型号		Y	
							String machineType = requestVo.getMachineType();
//							增值服务		N	多值，用英文分号隔开
//							String addServices = requestVo.getAddServices();
//							第三方订单号   字符串    Y
							String thirdOrderNo = requestVo.getThirdOrderNo();
							String isBackend = requestVo.getIsBackend();
							String isRealCard = requestVo.getIsRealCard();
							//校验非空
							if(StringUtils.isNotBlank(cardId)
									&&StringUtils.isNotBlank(cardPwd)
									&&StringUtils.isNotBlank(maintainType)
									&&StringUtils.isNotBlank(licenseNo)
									&&StringUtils.isNotBlank(userMobile)
									&&StringUtils.isNotBlank(serviceDate)
									&&StringUtils.isNotBlank(oilType)
									&&StringUtils.isNotBlank(machineType)
									&&StringUtils.isNotBlank(thirdOrderNo)){
								//前端请求且为电子码券，必须存在userId
								if(!"true".equals(isBackend)&&!"true".equals(isRealCard)){
									if(StringUtils.isNotBlank(userId)){
										//解密卡号
										requestVo.setCardId(CommonUtils.decodeCardNo(cardId));
										//解密userId
										requestVo.setUserId(CommonUtils.decodeOpenId(userId));
										//上门保养，服务地址有值；到店保养，网点名称有值
										if(("0".equals(maintainType)&&StringUtils.isNotBlank(serviceAddress))||("1".equals(maintainType)&&StringUtils.isNotBlank(networkName))){
											//处理订单校验请求
											fuWuService.validateAndSaveMaintainOrder(comCode,requestVo,resJson);
										}else{
											resJson.put("resMsg", "必传参数校验失败");
										}
									}else{
										resJson.put("resMsg", "userId不能为空");
									}
								}else{//后端请求，卡号明文
									//上门保养，服务地址有值；到店保养，网点名称有值
									if(("0".equals(maintainType)&&StringUtils.isNotBlank(serviceAddress))||("1".equals(maintainType)&&StringUtils.isNotBlank(networkName))){
										//处理订单校验请求
										fuWuService.validateAndSaveMaintainOrder(comCode,requestVo,resJson);
									}else{
										resJson.put("resMsg", "必传参数校验失败");
									}
								}
							}else{
								resJson.put("resMsg", "必传参数校验失败");
							}
						}else{
							resJson.put("resMsg", "验签失败");
						}
					}else{
						resJson.put("resMsg", "无效的商户");
					}
				}else{
					resJson.put("resMsg", "参数错误");
				}
			}else{
				resJson.put("resMsg", "请求报文不能为空");
			}
		}catch(Exception e){
			resJson.put("resMsg", "处理时发生错误");
		}
		Map<String, String> resParams = new HashMap<String,String>();
		resParams.put("responseId", resJson.getString("responseId"));
		resParams.put("resCode", resJson.getString("resCode"));
		resParams.put("resMsg", resJson.getString("resMsg"));
		resParams.put("message", resJson.getString("message"));
		String resSign = CommonUtils.generateSignForParams(md5Key, resParams);
		resJson.put("sign", resSign);
		String resBody = resJson.toString();
		String encryptResBody = null ;
		try {
			encryptResBody = CommonUtils.encryptMessageWithPrivateKey(resBody, privateKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			logger.error("加密返回报文异常",e);
		}
		logger.info("保养订单接口被调用，[原始请求数据:"+postData+"],[请求数据（解密后messgae）:"+messgaeDecrypt+"],[响应数据加密前:"+resBody+"],[处理时间："+(System.currentTimeMillis()-begin)+"ms]");
		return encryptResBody;
	}
	/**
	 * 
			* 描述:修改预约保养券信息接口
			* @param request
			* @param postData
			* @return
			* @author han 2018年4月2日 下午3:23:05
	 */
	@ResponseBody
	@RequestMapping(value="updateMaintainOrder.do",method={RequestMethod.POST,RequestMethod.GET},produces={"text/plain;charset=utf-8"})
	public String  updateMaintainOrder(HttpServletRequest request,@RequestBody String postData){
		long begin = System.currentTimeMillis();
		//未加密前响应json
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		resJson.put("resCode", "0");//默认失败
		//加密后响应报文
		//解密后message节点信息
		String messgaeDecrypt = null;
		PrivateKey privateKey = null;
		String md5Key = null;
		try{
			if(StringUtils.isNotBlank(postData)){
				//校验参数
				com.alibaba.fastjson.JSONObject reqJson = com.alibaba.fastjson.JSONObject.parseObject(postData);
				String requestId = reqJson.getString("requestId");
				String comCode = reqJson.getString("comCode");
				String message = reqJson.getString("message");
				String sign = reqJson.getString("sign");
				//null 转 ""
				requestId = CommonUtils.parseNull2String(requestId);
				comCode = CommonUtils.parseNull2String(comCode);
				message = CommonUtils.parseNull2String(message);
				sign = CommonUtils.parseNull2String(sign);
				
				resJson.put("responseId", requestId);
				if(StringUtils.isNotBlank(requestId)
						&&StringUtils.isNotBlank(comCode)
						&&StringUtils.isNotBlank(message)
						&&StringUtils.isNotBlank(sign)){
					//得到对应服务商的私钥
					String alias = "maintain_"+comCode;
					char[] keyPwd = "piccbj@password".toCharArray();
					try {
						privateKey = getInterfacePrivateKeyWithCache(alias , keyPwd);
					} catch (UnrecoverableKeyException | KeyStoreException
							| NoSuchAlgorithmException | CertificateException
							| IOException e) {
						logger.info("获取私钥时发生异常",e);
						throw e;
					}
					md5Key = ConfigUtil.getString("MD5KEY_"+comCode);
					if(privateKey!=null&&md5Key!=null){
						//验签
						Map<String, String> reqParams = new HashMap<String,String>();
						reqParams.put("requestId", requestId);
						reqParams.put("comCode", comCode);
						reqParams.put("message", message);
						if(CommonUtils.verifyParamsSign(md5Key , reqParams , sign)){
							//解密message
							messgaeDecrypt = CommonUtils.decryptMessageWithPrivateKey(privateKey, message);
							MaintainOrderRequest requestVo = com.alibaba.fastjson.JSONObject.parseObject(messgaeDecrypt, MaintainOrderRequest.class);
							//我方订单号
							String orderNo = requestVo.getOrderNo();
//							保养方式	字符串	Y	0：上门保养1：到店保养
							String maintainType = requestVo.getMaintainType();
//							服务车牌号	字符串	Y	
							String licenseNo = requestVo.getLicenseNo();
//							客户手机号	字符串	Y	预约手机号
							String userMobile = requestVo.getUserMobile();
//							服务日期	日期	Y	格式：yyyy-MM-dd
							String serviceDate = requestVo.getServiceDate();
//							网点名称	字符串	N	到店保养时有值
							String networkName = requestVo.getNetworkName();
//							服务地址	字符串	N	上门保养时有值
							String serviceAddress = requestVo.getServiceAddress();
//							机油型号	字符串	Y	
							String oilType = requestVo.getOilType();
//							机滤型号		Y	
							String machineType = requestVo.getMachineType();
//							增值服务		N	多值，用英文分号隔开
							String addServices = requestVo.getAddServices();
							//校验非空
							if(StringUtils.isNotBlank(orderNo)
									&&StringUtils.isNotBlank(maintainType)
									&&StringUtils.isNotBlank(licenseNo)
									&&StringUtils.isNotBlank(userMobile)
									&&StringUtils.isNotBlank(serviceDate)
									&&StringUtils.isNotBlank(oilType)
									&&StringUtils.isNotBlank(machineType)
									){
								
									//处理订单校验请求
									fuWuService.validateAndUpdateMaintainOrder(comCode,requestVo,resJson);
								
							}else{
								resJson.put("resMsg", "必传参数校验失败");
							}
						}else{
							resJson.put("resMsg", "验签失败");
						}
					}else{
						resJson.put("resMsg", "无效的商户");
					}
				}else{
					resJson.put("resMsg", "参数错误");
				}
			}else{
				resJson.put("resMsg", "请求报文不能为空");
			}
		}catch(Exception e){
			resJson.put("resMsg", "处理时发生错误");
		}
		Map<String, String> resParams = new HashMap<String,String>();
		resParams.put("responseId", resJson.getString("responseId"));
		resParams.put("resCode", resJson.getString("resCode"));
		resParams.put("resMsg", resJson.getString("resMsg"));
		String resSign = CommonUtils.generateSignForParams(md5Key, resParams);
		resJson.put("sign", resSign);
		String resBody = resJson.toString();
		String encryptResBody = null ;
		try {
			encryptResBody = CommonUtils.encryptMessageWithPrivateKey(resBody, privateKey);
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| NoSuchPaddingException | UnsupportedEncodingException
				| IllegalBlockSizeException | BadPaddingException e) {
			logger.error("加密返回报文异常",e);
		}
		logger.info("修改预约保养券信息接口被调用，[原始请求数据:"+postData+"],[请求数据（解密后messgae）:"+messgaeDecrypt+"],[响应数据加密前:"+resBody+"],[处理时间："+(System.currentTimeMillis()-begin)+"ms]");
		return encryptResBody;
	}
	
	/**
	 * 
	 * 描述:订单取消接口
	 * @param request
	 * @param postData
	 * @return
	 * @author han 2018年1月30日 下午1:19:17
	 */
	@ResponseBody
	@RequestMapping(value="cancelMaintianOrder.do",method={RequestMethod.POST,RequestMethod.GET},produces={"application/json;charset=utf-8"})
	public String  cancelMaintianOrder(HttpServletRequest request,String requestId,String thirdOrderId,String comCode,String sign){
		long begin = System.currentTimeMillis();
		//未加密前响应json
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		resJson.put("resCode", "0");//默认失败
		PrivateKey privateKey = null;
		String md5Key = null;
		try{
			//校验参数
			resJson.put("responseId", requestId);
			if(StringUtils.isNotBlank(requestId)
					&&StringUtils.isNotBlank(thirdOrderId)
					&&StringUtils.isNotBlank(comCode)
					&&StringUtils.isNotBlank(sign)){
				//得到对应服务商的私钥
				String alias = "maintain_"+comCode;
				char[] keyPwd = "piccbj@password".toCharArray();
				try {
					privateKey = getInterfacePrivateKeyWithCache(alias , keyPwd);
				} catch (UnrecoverableKeyException | KeyStoreException
						| NoSuchAlgorithmException | CertificateException
						| IOException e) {
					logger.info("获取私钥时发生异常",e);
					throw e;
				}
				md5Key = ConfigUtil.getString("MD5KEY_"+comCode);
				if(privateKey!=null&&md5Key!=null){
					//验签
					Map<String,String> reqParams = new HashMap<String, String>();
					reqParams.put("requestId", requestId);
					reqParams.put("thirdOrderId", thirdOrderId);
					reqParams.put("comCode", comCode);
					if(CommonUtils.verifyParamsSign(md5Key, reqParams, sign)){
						//处理订单校验请求
						fuWuService.cancelMaintainOrder(comCode,thirdOrderId,resJson);
					}else{
						resJson.put("resMsg", "验签失败");
					} 
				}else{
					resJson.put("resMsg", "无效的商户");
				}
			}else{
				resJson.put("resMsg", "参数错误");
			}
		}catch(Exception e){
			resJson.put("resMsg", "处理时发生错误");
			e.printStackTrace();
		}
		Map<String,String> resParams = new HashMap<String, String>();
		resParams.put("responseId", resJson.getString("responseId"));
		resParams.put("resCode", resJson.getString("resCode"));
		resParams.put("resMsg", resJson.getString("resMsg"));
		String resSign  = CommonUtils.generateSignForParams(md5Key, resParams);
		resJson.put("sign", resSign);
		String resBody = resJson.toString();
		logger.info("保养订单取消接口被调用，[原始请求数据    响应编号:"+requestId+",服务商代码:"+comCode+",订单编号:"+thirdOrderId+",签名:"+sign+"],[响应数据:加密前"+resBody+"],[处理时间："+(System.currentTimeMillis()-begin)+"ms]");
		return resBody;
	}

	/**
	 * 
	 * 描述:电子码注册
	 * @param request
	 * @param postData
	 * @return String
	 * @author 赵硕 2018年1月30日 上午10:15:09
	 */
	@ResponseBody
	@RequestMapping(value="registCardNo.do",method={RequestMethod.POST,RequestMethod.GET},produces={"application/json;charset=utf-8"})
	public String  registCardNo(HttpServletRequest request,@RequestBody String postData){
		long begin = System.currentTimeMillis();// 开始时间
		PrivateKey privateKey = null;
		String decodeMessage = null;
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		resJson.put("resCode", "0");
		String md5Key = null;
		String requestId = null;
		try {
			// 判断报文体是否为空
			if (StringUtils.isNotBlank(postData)) {
				com.alibaba.fastjson.JSONObject reqJson = com.alibaba.fastjson.JSONObject
						.parseObject(postData);
				requestId = reqJson.getString("requestId");// 请求编号
				String comCode = reqJson.getString("comCode");// 服务商代码
				String message = reqJson.getString("message");// 信息
				String sign = reqJson.getString("sign");// 签名
				resJson.put("responseId", requestId);// 响应编号
				// 将null转换成""
				requestId = CommonUtils.parseNull2String(requestId);
				comCode = CommonUtils.parseNull2String(comCode);
				message = CommonUtils.parseNull2String(message);
				sign = CommonUtils.parseNull2String(sign);
				if (StringUtils.isNotBlank(requestId)
						&& StringUtils.isNotBlank(comCode)
						&& StringUtils.isNotBlank(message)
						&& StringUtils.isNotBlank(sign)) {
					// 得到对应服务商的私钥以及md5k
					String alias = "maintain_" + comCode;
					char[] keyPwd = "piccbj@password".toCharArray();
					try {
						privateKey = getInterfacePrivateKeyWithCache(alias,
								keyPwd);
						md5Key = ConfigUtil.getString("MD5KEY_" + comCode);
					} catch (UnrecoverableKeyException | KeyStoreException
							| NoSuchAlgorithmException | CertificateException
							| IOException e) {
						logger.info("获取私钥或md5Key时发生异常", e);
						throw e;
					}
					if (md5Key != null && privateKey != null) {
						HashMap<String, String> reqMap = new HashMap<String, String>();
						reqMap.put("requestId", requestId);
						reqMap.put("comCode", comCode);
						reqMap.put("message", message);
						// 验证签名
						if (CommonUtils.verifyParamsSign(md5Key, reqMap, sign)) {
							decodeMessage = CommonUtils
									.decryptMessageWithPrivateKey(privateKey,
											message);
							DzmRegistVo dzmRegist = com.alibaba.fastjson.JSONObject
									.parseObject(decodeMessage,
											DzmRegistVo.class);
							String userCode = comCode;// 服务商代码
							String loginPwd = dzmRegist.getLoginPwd();// 服务商登录密码
							String isRealCard = dzmRegist.getIsRealCard();// 是否为实体卡“
																		 // true”:是，否则为否；可不填默认否
							String cardType = dzmRegist.getCardType();// 卡类型
							String cardNo = dzmRegist.getCardNo();// 卡号
							String cardPwd = dzmRegist.getCardPwd();// 卡密
							String licenseNo = dzmRegist.getLicenseNo();// 车牌号
							String phoneNumber = dzmRegist.getPhoneNumber();// 手机号，指定卡类型需要自此段
							if (StringUtils.isNotBlank(loginPwd)
									&& StringUtils.isNotBlank(cardType)
									&& StringUtils.isNotBlank(cardNo)
									&& StringUtils.isNotBlank(cardPwd)
									&& StringUtils.isNotBlank(licenseNo)) {
								if ("true".equals(isRealCard)) {// 处理实体卡

								} else {
									dzmRegist.setUserCode(userCode);
									if (StringUtils.isNotBlank(phoneNumber)) {
										fuWuService.cardRegist(dzmRegist,
												resJson);
									} else if (StringUtils.isBlank(phoneNumber)
											&& "1066".equals(cardType)) {
										fuWuService.cardRegist(dzmRegist,
												resJson);
									} else {
										resJson.put("resMsg", "message节点参数错误");
									}
								}
							} else {
								resJson.put("resMsg", "message节点参数错误");
							}
						} else {
							resJson.put("resMsg", "验签失败");
						}
					} else {
						resJson.put("resMsg", "商户不存在");
					}
				} else {
					resJson.put("resMsg", "参数错误");
				}
			} else {
				resJson.put("resMsg", "接收报文为空");
			}
		} catch (Exception e) {
			resJson.put("resMsg", "处理时发生异常");
			logger.error("处理时发生异常", e);
		}
		String encryptResMessage = null;
		String resMessage = null;
		if (privateKey != null && md5Key != null) {
			String resSign = null;
			// 生成签名
			HashMap<String, String> resMapSign = new HashMap<String, String>();
			resMapSign.put("responseId", resJson.getString("responseId"));
			resMapSign.put("resCode", resJson.getString("resCode"));
			resMapSign.put("resMsg", resJson.getString("resMsg"));
			resSign = CommonUtils.generateSignForParams(md5Key,
					resMapSign);
			resJson.put("sign", resSign);
			// 对返回json报文进行私钥加密
			try {
				resMessage = resJson.toString();
				encryptResMessage = CommonUtils.encryptMessageWithPrivateKey(resMessage, privateKey);
			} catch (InvalidKeyException | NoSuchAlgorithmException
					| NoSuchPaddingException | UnsupportedEncodingException
					| IllegalBlockSizeException | BadPaddingException e) {
				logger.error("加密注册接口返回报文出错【"+requestId+"】",e);
			}
		}
		logger.info("保养订单接口被调用，[原始请求数据:" + postData + "],[请求数据（解密后messgae）:"
				+ decodeMessage + "],[响应数据:" + resMessage + "][响应加密数据:"
				+ encryptResMessage + "],[处理时间："
				+ (System.currentTimeMillis() - begin) + "ms]");
		return encryptResMessage;
	}
	@RequestMapping(value="api",produces={"text/html;charset=utf-8"},consumes={"application/json;chartset=utf-8","application/xml;chartset=utf-8"})
	public void externalAPI(HttpServletRequest request,HttpServletResponse response,@RequestHeader(value="content-type",defaultValue="application/json;chartset=utf-8") String contentType ,@RequestBody String requestBody){
		String contentTypeLower = contentType.toLowerCase();
		String requestFormat = null;
		if(contentTypeLower.contains("xml")){
			requestFormat = "xml";
		}else if(contentTypeLower.contains("json")){
			requestFormat = "json";
		}
		if(requestFormat!=null){
			if(StringUtils.isNotBlank(requestBody)){
				ReqTrans1066Entity req = new ReqTrans1066Entity();
				req.setContentType(requestFormat);
				req.setReqMsg(requestBody);
				try {
					ResTrans1066Entity resTrans1065Entity = transService.genericExecuteTrans("1066", req, ResTrans1066Entity.class);
					if(resTrans1065Entity!=null){
						ResponseHeadEntity head = resTrans1065Entity.getHead();
						ResTrans1066BodyEntity body = resTrans1065Entity.getBody();
						if("1".equals(head.getResponse_code())){
							this.writeHttpStatusAndMessage(200,body.getBasePart().getResMsg(),response);
						}else{
							this.writeHttpStatusAndMessage(500, head.getResponse_message(), response);;
						}
					}else{
						this.writeHttpStatusAndMessage(500, "内部调用错误", response);
					}
				} catch (EpiccException e) {
					this.writeHttpStatusAndMessage(500, "内部调用错误", response);
				}
			}else{
				this.writeHttpStatusAndMessage(500, "请求报文不能为空", response);
			}
		}else{
			this.writeHttpStatusAndMessage(500, "请求格式错误", response);
		}
	}
	/**
	 * 
			* 描述:
			* 	定时调用此接口进行系统运行状态检测
			* @param uuid
			* 		检查编号，便于查看日志
			* @return
			* @author 许宝众 2018年3月8日 下午2:17:31
	 */
	@RequestMapping(value="checkSysRunStatus",method={RequestMethod.POST})
	@ResponseBody
	public com.alibaba.fastjson.JSONObject checkSysRunStatus(@RequestBody String uuid){
		String openid = "oXHv4jkG56-qbM4tblkffeSI_yeY";//许宝众 openid
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();
		resJson.put("resCode", "0");//默认失败
		try{
			//测试总公司二次接口
			Map<String, String> resMap = WeixinUtil.getUserNameFormPICC(openid);
			String retcode = resMap.get("retcode");
			if(StringUtils.isNotBlank(retcode)){
				//测试落地系统接口1038，查询用户车辆信息
				try {
					giftCardService.loginGetCarInfo(openid);
					resJson.put("resCode", "1");//成功返回
				} catch (EpiccException e) {
					resJson.put("resMsg", "调用落地接口失败");
					logger.info("[检查编号："+uuid+"]-[检测系统运行状态时发生错误：]",e);
				}
			}else{
				resJson.put("resMsg", "调用总公司二次接口失败");
			}
		}catch(Exception e){
			logger.info("[检查编号："+uuid+"]-[检测系统运行状态时发生错误：]",e);
			resJson.put("resMsg", "微信端检测未知错误");
		}
		return resJson;
	}
	/**
	 * 
			* 描述:
			* 		输出HTTP状态以及内容
			* @param httpStatus
			* @param msg
			* @author 许宝众 2018年2月12日 下午1:44:08
	 */
	private void writeHttpStatusAndMessage(int httpStatus,String msg,HttpServletResponse response){
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(httpStatus);
		ServletOutputStream outputStream = null;
		try {
			outputStream = response.getOutputStream();
			IOUtils.write(msg.getBytes("UTF-8"), outputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * 
			* 描述:
			* 测试保养订单同步数据接口，返回信息
			* @param requestBody
			* @return
			* @author 许宝众 2018年2月2日 上午8:29:05
	 * @throws Exception 
	 */
	@RequestMapping(value="testMaintainOrderSyncRes")
	@ResponseBody
	public String testMaintainOrderSyncRes(@RequestBody String requestBody) throws Exception{
		//获取公钥
		PublicKey publicKey = null;
		String cerFile = "D:\\keys\\bjwxplat\\dev\\maintain_10000534_dev.cer";
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream inStream = new FileInputStream(new File(cerFile));
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream );
		publicKey = cert.getPublicKey();
		String decrypt = CommonUtils.decryptMessageWithPublicKey(publicKey, requestBody);
		com.alibaba.fastjson.JSONObject reqJson = com.alibaba.fastjson.JSONObject.parseObject(decrypt);
		String requestId = reqJson.getString("requestId");
		String thirdOrderNo = reqJson.getString("thirdOrderNo");
		String orderStatus = reqJson.getString("orderStatus");
		String order = reqJson.getString("order");
		String sign = reqJson.getString("sign");
		Map<String, String> reqParams = new HashMap<String,String>();
		reqParams.put("requestId", requestId);
		reqParams.put("thirdOrderNo", thirdOrderNo);
		reqParams.put("orderStatus", orderStatus);
		reqParams.put("order",order);
		String md5Key = "12345678910";
		//组织返回
		com.alibaba.fastjson.JSONObject resJson = new com.alibaba.fastjson.JSONObject();

		resJson.put("responseId", requestId);
		resJson.put("isTimoutOrder", "true");
		resJson.put("resCode", "0");//默认失败
		//验签
		if(CommonUtils.verifyParamsSign(md5Key , reqParams, sign)){
			resJson.put("resCode", "1");//成功返回
//			resJson.put("resMsg", "同步失败");
		}else{
			resJson.put("resMsg", "验签失败");
		}
		//生成响应签名
		Map<String, String> resParams = new HashMap<String,String>();
		resParams.put("responseId", resJson.getString("responseId"));
		resParams.put("isTimoutOrder", resJson.getString("isTimoutOrder"));
		resParams.put("resCode", resJson.getString("resCode"));
		resParams.put("resMsg", resJson.getString("resMsg"));
		resJson.put("sign", CommonUtils.generateSignForParams(md5Key, resParams ));
		String resJsonString = resJson.toJSONString();
		return CommonUtils.encryptMessageWhithPublicKey(publicKey, resJsonString);
	}
	/**
	 * 
	* 描述:
	* 通过缓存的方式得到keystore
	* @return
	* @throws KeyStoreException
	* @throws NoSuchAlgorithmException
	* @throws CertificateException
	* @throws IOException
	* @author 许宝众 2017年12月18日 上午11:02:31
	 */
	private KeyStore getInterfaceKeyStoreWithCache() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException{
		Cache cache = cacheManager.getCache("default");
		String cacheKey = "INTERFACE_KEY_STORE";
		ValueWrapper valueWrapper = cache!=null?cache.get(cacheKey):null;
		Object res = valueWrapper!=null?valueWrapper.get():null;
		if(res!=null){
			return (KeyStore) res;
		}else{
			KeyStore ks = KeyStore.getInstance("JKS");
			InputStream keyStoreIn = WxController.class.getResourceAsStream("/interface.keystore");
			ks.load(keyStoreIn , "piccbj1100".toCharArray());
			cache.put(cacheKey, ks);
			return ks;
		}
	}
	
	/**
	 * 
	* 描述:
	* 通过缓存的方式得到服务商对应的私钥
	* @param alias
	* 			key别名
	* @param keyPwd
	* 			key的password
	* @return
	* @author 许宝众 2017年12月18日 上午10:54:27
	 * @throws IOException 
	 * @throws CertificateException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyStoreException 
	 * @throws UnrecoverableKeyException 
	 */
	private PrivateKey getInterfacePrivateKeyWithCache(String alias,char[] keyPwd) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException{
		Cache cache = cacheManager.getCache("default");
		String cacheKey = "INTERFACE_PRIVATE_KEY_"+alias;
		ValueWrapper valueWrapper = cache!=null?cache.get(cacheKey):null;
		Object res = valueWrapper!=null?valueWrapper.get():null;
		if(res!=null){
			return (PrivateKey) res;
		}else{
			KeyStore keyStore = getInterfaceKeyStoreWithCache();
			PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias, keyPwd);
			cache.put(cacheKey, privateKey);
			return privateKey;
		}
	}
}
