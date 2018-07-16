package com.wap.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

public class SignUtil {
	
	/**
	 * 描述:
	 * 根据openId与当前时间生成一个签名
	 * @param sessionId
	 * @return
	 * @author 2017年7月21日 下午1:38:02
	 */
	public static String generateSign(String key){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String now = sdf.format(new Date());
		String src=String.format("sessionid=%s&timestamp=%s", key,now);
		int random = 5+RandomUtil.getRandomNumber(64);
		return encrpyMd5AndHexString(src, random);
	}
	
	/**
	 * 描述:根据UUID值加时间戳在进行N次加密生成签名
	 * @return
	 * @author 吕亮 2018年4月25日 下午7:07:04
	 */
	public static String generateSignByUUID(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String now = sdf.format(new Date());
		String key = UUID.randomUUID().toString().toUpperCase();
		String src=String.format("sessionid=%s&timestamp=%s", key,now);
		int random = 5+RandomUtil.getRandomNumber(64);
		return encrpyMd5AndHexString(src, random);
	}
	
	/**
	 * 描述: 将字符串进行N次加密
	 * 
	 * @param src
	 *            源字符串
	 * @param cycleIndex
	 *            加密次数
	 * @return
	 * @author 许宝众2017年7月11日 上午10:39:28
	 */
	public static String encrpyMd5AndHexString(String src, int cycleIndex) {
		String res = src;
		for (int i = 0; i < cycleIndex; i++) {
			try {
				res = Hex.encodeHexString(DigestUtils.md5(res));
				res = res.substring(1) + res.substring(0, 1);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		return res;
	}

}
