package com.wap.wx_interface.utils;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.poi.hssf.record.formula.functions.Degrees;
import org.springframework.util.Assert;

public class AESUtils {
	/**
	 * 系统内部数据转换使用的 加密
	 * 
	 * @param content
	 * @param password
	 * @return
	 */
	public static String encrypt(String content, String password) {
		try {
			SecretKeySpec key = initKeyForAES(password);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			byte[] byteContent = content.getBytes("utf-8");
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(byteContent);

			return encodeHexString(result); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 系统内部数据转换使用的 解密算法
	 * 
	 * @param contentStr
	 * @param password
	 * @return
	 */
	public static String decrypt(String contentStr, String password) {
		try {
			byte[] content = Hex.decodeHex(contentStr.toCharArray());
			SecretKeySpec key = initKeyForAES(password);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// 创建密码器
			cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
			byte[] result = cipher.doFinal(content);
			return new String(result, "UTF-8"); // 加密
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (DecoderException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static SecretKeySpec initKeyForAES(String password)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		if (null == password || password.length() == 0) {
			throw new NullPointerException("key not is null");
		}
		SecretKeySpec key = new SecretKeySpec(password.getBytes("UTF-8"), "AES");
		return key;
	}

	/**
	 * 解决apache-common-codec版本不一致，造成encodeHexString方法不存在
	 * 
	 * @return
	 */

	private static String encodeHexString(byte[] data) {
		char[] ch = Hex.encodeHex(data);
		return new String(ch);
	}

	/**
	 * 
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

	/**
	 * 根据账号得到洗车服务商对应的token
	* 描述:
	* @param account
	* @return
	* @author 许宝众 2017年7月11日 下午12:54:55
	 */
	public static String generateWashCarCodeTokeByAccount(String account) {
		return encrpyMd5AndHexString(account, 64).substring(0,16);
	}
}
