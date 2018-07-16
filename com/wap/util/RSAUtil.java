package com.wap.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

/**
 * RSA安全编码组件
 * 
 * @author 邵明达
 */
public abstract class RSAUtil {
	
	/**
	 * 缓存秘钥的容器
	 */
	private static ConcurrentHashMap<String, byte[]> 
			keys = new ConcurrentHashMap<String, byte[]>();
			
	//非对称加密密钥算法
	private static final String KEY_ALGORITHM = "RSA";
	
	//指定数字签名算法（可以换成SHA1withRSA或SHA256withRSA）
	private static final String SIGNATURE_ALGORITHM = "MD5withRSA";
	
	//指定字符集
	private static final String CHARSET = "UTF-8";

	private static final String RSAPublicKey = null;
	
	public static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCgefp9136DhgcPpfqJs1u7MPmOnfe7JOjE1bhVFrcEjDy59wHHFpWKEnUd+gD+vfrVtVTJmfVV9eiA/g3TjUfG/XApTjf0PEE1h7Liu96ceg6LUIMmCGoTcaVWfv1Ty1cyTHZYk6AD7XrwWLrqlYebfKkcmtl0byU2A8JvlmONQIDAQAB";
	public static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMKB5+n3XfoOGBw+l+omzW7sw+Y6d97sk6MTVuFUWtwSMPLn3AccWlYoSdR36AP69+tW1VMmZ9VX16ID+DdONR8b9cClON/Q8QTWHsuK73px6DotQgyYIahNxpVZ+/VPLVzJMdliToAPtevBYuuqVh5t8qRya2XRvJTYDwm+WY41AgMBAAECgYEAtT2s5sMb62P0rWTZ01JivEicZcv3ZF6XVy4vF3mtzECbIcxQpsORnZSe7v7nz8Wr+7uGvZscx5u5jvsZ8BbMwVQ9p29msqQLotC6EGnri2rGT+DJfydaOij4ftm+73/uhhgQzQiaS0gvFesnH7b1+tPKPfviMpQumdcO6FD+L6ECQQD7xAkAjTfw6rMXWPuaHMu4HJcvNs5e/n9KlPOf0nSfNO0mSq+r+c+6YHK8KrIFuRZgL5Gzk+vkXuQuw8TCsBItAkEAxcdZErNRm0ohr20FzAILLFVnV6HPwvSuGDbvfoA1C55QWApkoGVzCdP/r9wMqUfPkOH7jvAIIW99XjS0t0lZKQJAIqmfz6qhvBKkK5+9rD8wsR2GlyKFkUCax+rwC94O3miMCgyYWSYgunlS8qMyNWWBNib2eXHxNVT3N5UOfvt81QJAQWMVxMP6L/ADUP8nUno+fWrC3ssFaDwCrsjCxzzVzMCLaYAOIqVHsjEfsGF84h52tjywMWKUHZHBjfq44HvkeQJBANdhV1uWc3QnyDHmNTB/xaOqdZK54g42PMl4T9WVp4rne0TUv3w9bkQopPiRqsiZEe0SGVpCuer2HLOr7AlnC3Q=";

	/**
	 * 
	 * @Title: getKey 
	 * @Description: TODO(给定秘钥路径，读取秘钥返回秘钥的字节数组) 
	 * @author: shaomd 
	 */
	private static byte[] getKey(String keyPath) throws IOException{
		byte[] key = keys.get(keyPath);
		if(key == null){
			synchronized(keys){
				if((key = keys.get(keyPath)) == null){
					InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyPath);
					System.out.println(is);
					key = readFile(is);
					keys.put(keyPath, key);
				}
			}
		}
		return key;
	}
	
	/**
	 * 
	 * @Title: readFile 
	 * @Description: TODO(给定文件流，读取文件，将文件的字节数组返回) 
	 * @author: shaomd 
	 */
	private static byte[] readFile(InputStream is) throws IOException {
		int len = -1;
		int offset = 0;
		byte[] results = new byte[is.available()];
		byte[] buffer = new byte[1024];
		while ((len = is.read(buffer)) != -1) {
			System.arraycopy(buffer, 0, results, offset, len);
			offset += len;
		}
		return results;
	}

	/**
	 * 用RSA私钥对参数生成签名
	 * @param data 加密数据
	 * @param privateKey 私钥
	 * @throws Exception
	 */
	public static String sign(String data,String pvkPath) throws Exception {
		return sign(data, getKey(pvkPath));
	}
	
	
	public static String sign(String data,byte[] pvkBytes) throws Exception {
		// 构造PKCS8EncodedKeySpec对象
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pvkBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取私钥匙对象
		PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 用私钥对信息生成数字签名
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initSign(priKey);
		signature.update(data.getBytes(CHARSET));
		return Base64.encodeBase64String(signature.sign());
	}
	

	/**
	 * 校验数字签名
	 * 
	 * @param data 加密数据
	 * @param publicKey 公钥
	 * @param sign 数字签名
	 * @return 校验成功返回true 失败返回false
	 * @throws Exception
	 */
	public static boolean verify(String data, String sign,String pukPath) throws Exception {
		return verify(data, sign, getKey(pukPath));
	}
	
	
	public static boolean verify(String data, String sign,byte[] pukBytes) throws Exception {
		// 构造X509EncodedKeySpec对象
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pukBytes);
		// KEY_ALGORITHM 指定的加密算法
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		// 取公钥匙对象
		PublicKey pubKey = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
		signature.initVerify(pubKey);
		signature.update(data.getBytes(CHARSET));
		// 验证签名是否正常
		return signature.verify(Base64.decodeBase64(sign));
	}

	
	/**
	 * @Title: decryptByPrivateKey 
	 * @Description: TODO(用私钥对密文进行解密) 
	 * @param @param data	密文
	 * @param @param pvkPath	私钥路径
	 * @author: shaomd 
	 */
	public static String decryptByPrivateKey(String data,String pvkPath) throws Exception {
		return decryptByPrivateKey(data, getKey(pvkPath));
	}
	
	public static String decryptByPrivateKey(String data,byte[] pvkBytes) throws Exception {
		byte[] dataBytes = Base64.decodeBase64(data);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pvkBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		RSAPrivateKey privateKey2 = (RSAPrivateKey)keyFactory.generatePrivate(pkcs8KeySpec);
		BigInteger modulus = privateKey2.getModulus();
		System.out.println("privateKey2="+privateKey2);      
		System.out.println("privateKey2.getPrivateExponent="+privateKey2.getPrivateExponent());
		// 对数据解密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		return new String(cipher.doFinal(dataBytes), CHARSET);
	}

	/**
	 * 用公钥解密
	 */
	/*public static String decryptByPublicKey(String data,String pukPath) throws Exception {
		byte[] dataBytes = Base64.decodeBase64(data);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(getKey(pukPath));
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);
		return new String(cipher.doFinal(dataBytes), CHARSET);
	}*/

	/**
	 * 
	 * @Title: encryptByPublicKey 
	 * @Description: TODO(用公钥对参数明文进行加密) 
	 * @param @param data	明文
	 * @param @param pukPath	公钥路径
	 * @author: shaomd 
	 */
	public static String encryptByPublicKey(String data,String pukPath) throws Exception {
		return encryptByPublicKey(data, getKey(pukPath));
	}
	
	public static String encryptByPublicKey(String data,byte[] pukBytes) throws Exception {
		byte[] dataBytes = data.getBytes(CHARSET);
		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pukBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);
		
		RSAPublicKey publicKey2 =(RSAPublicKey) keyFactory.generatePublic(x509KeySpec);
		System.out.println("publicKey2,getModulus="+publicKey2.getModulus());
		System.out.println("publicKey2,getPublicExponent="+publicKey2.getPublicExponent());
		// 对数据加密
//		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		return Base64.encodeBase64String(cipher.doFinal(dataBytes));
	}

	/**
	 * 用私钥加密
	 * @throws Exception 
	 */
	/*public static String encryptByPrivateKey(String data,String pvkPath) throws Exception {
		byte[] dataBytes = data.getBytes(CHARSET);
		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(getKey(pvkPath));
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		return Base64.encodeBase64String(cipher.doFinal(dataBytes));
	}*/

	/*public static void main(String[] args) throws Exception {
		//RSAUtil.init("196000101000082.pvk", "196000101000082.puk");
		String pvkPath = "196000101000082.pvk";
		String pukPath = "196000101000082.puk";
		String str = "我是要加密的字符串！";
		String sign = RSAUtil.sign(str,pvkPath);
		str += "&signvalue=" + sign;
		System.out.println(str);
		String initKey = AESUtil.initAESKey();
		String aesEncryptToBytes = AESUtil.aesEncryptToBytes(str, initKey);
		System.out.println("AES密文：" + aesEncryptToBytes);
		String encryptByPrivateKey = RSAUtil.encryptByPrivateKey(initKey,pvkPath);
		System.out.println("RSA加密AES私钥密文：" + encryptByPrivateKey);
		String decryptByPublicKey = RSAUtil.decryptByPublicKey(encryptByPrivateKey,pukPath);
		System.out.println("RSA解密AES私钥明文：" + decryptByPublicKey);
		String aesDecryptByBytes = AESUtil.aesDecryptByBytes(aesEncryptToBytes, decryptByPublicKey);
		System.out.println("AES明文：" + aesDecryptByBytes);
		int indexOf = aesDecryptByBytes.indexOf("&signvalue=");
		String data = aesDecryptByBytes.substring(0,indexOf);
		System.out.println("签名字符串：" + data);
		String signDec = aesDecryptByBytes.substring(indexOf + "&signvalue=".length(),aesDecryptByBytes.length());
		System.out.println("签名：" + signDec);
		boolean verify = RSAUtil.verify(data, signDec,pukPath);
		System.out.println(verify);
		
		
		System.out.println("------------------");
		String key = initKey;
		System.out.println(key);
		String encryptByPrivateKey2 = RSAUtil.encryptByPrivateKey(key,pvkPath);
		System.out.println(encryptByPrivateKey2);
		String decryptByPublicKey2 = RSAUtil.decryptByPublicKey(encryptByPrivateKey2,pukPath);
		System.out.println(decryptByPublicKey2);
	}*/
	
	public static void main(String[] args) throws Exception {
		//private:20096921116910233983255097117018917464309238030306166629701211684490651864830880667283663658997741946342301104674052628962147786868781411925661939759627478368938971581207258459557793896901556586329962267242070684588270980795719320968476091609723213803447965350732809804100086372389651096906140193807180908839103839613902410152131296403144146925086668816931333253428221445958932872852736140880902488798295673237205938506715407570292187255522184342357405017587288032596408686375643150302649425414471354578890242883621274459990913892329331044414174950290954663494908640316426747141072457855158080275748955568305266224065
		//public:22492476035981112498259632490702542716642494198681203654742017353458405506863763961469499209654439502321419941202988321537856780709724326611201163755429855523082695160571410722271276510429791724273899569790863371686758462154978177473419362600994420189500304065969502469923448274114120667690245570666892348013150072765763098241845967660514873339845940847693083241814588965490317187199718803413735149322332593765735844766642341604026905816553394612938322191165612899466306829062893756757791936427434914482024922992195474262895106222669695736298615223434094496242606294157019881116552308443795718063100663135051775467511
		/*String publicKey = "22492476035981112498259632490702542716642494198681203654742017353458405506863763961469499209654439502321419941202988321537856780709724326611201163755429855523082695160571410722271276510429791724273899569790863371686758462154978177473419362600994420189500304065969502469923448274114120667690245570666892348013150072765763098241845967660514873339845940847693083241814588965490317187199718803413735149322332593765735844766642341604026905816553394612938322191165612899466306829062893756757791936427434914482024922992195474262895106222669695736298615223434094496242606294157019881116552308443795718063100663135051775467511";
		String privateKey ="20096921116910233983255097117018917464309238030306166629701211684490651864830880667283663658997741946342301104674052628962147786868781411925661939759627478368938971581207258459557793896901556586329962267242070684588270980795719320968476091609723213803447965350732809804100086372389651096906140193807180908839103839613902410152131296403144146925086668816931333253428221445958932872852736140880902488798295673237205938506715407570292187255522184342357405017587288032596408686375643150302649425414471354578890242883621274459990913892329331044414174950290954663494908640316426747141072457855158080275748955568305266224065"; 
		System.out.println("=="+Base64.encodeBase64String(publicKey.getBytes()));
		String pvkPath = "123456789.pvk";
		String pukPath = "123456789.puk";
		String str = "我是要加密的字符串！";
		System.out.println("原字符串为：" + str);
		String sign = RSAUtil.sign(str, pvkPath); 
		System.out.println("生成签名：" + sign); 
		str += "&signvalue="+sign;
		String key = AESUtil.initAESKey();
		System.out.println("生成AES KEY：" + key);
		String aesEncrypt = AESUtil.aesEncryptToBytes(str, key);
		System.out.println("AES密文：" + aesEncrypt);
		String keyEncrypt = RSAUtil.encryptByPublicKey(key, pukPath);
		System.out.println("RSA加密AES秘钥key密文：" + keyEncrypt);
		String keyDecrypt = RSAUtil.decryptByPrivateKey(keyEncrypt, pvkPath);
		System.out.println("RSA解密AES秘钥key明文：" + keyDecrypt);
		String aesDecrypt = AESUtil.aesDecryptByBytes(aesEncrypt, keyDecrypt);
		int indexOf = aesDecrypt.indexOf("&");
		System.out.println("AES明文：" + aesDecrypt);
		boolean verify = RSAUtil.verify(aesDecrypt.substring(0,indexOf), aesDecrypt.substring(indexOf + "&signvalue=".length()), pukPath);
		System.out.println("验签结果：" + verify);*/
//		String miwen = encryptByPublicKey("123", Base64.decodeBase64(RSA_PUBLIC_KEY));
//		System.out.println("加密后："+miwen);
		String miwen = "OlI/nGaICWH2uv+1kALRffdihoDqpdjVbpVrOUo9okTbWy/Sng2ZEZsrKOH1xEEuYk0Cp1mm8q45jC7fQ6Qgo9hcKLtDMZKeGrpVlickTihJ6H2h+JyeZTBYXlMvX4mK7oF9PWoEvXYxsFZhXy0z/34Fb3M1NVVzCP+58908Cmo=";
		String decryptByPrivateKey = decryptByPrivateKey(miwen,  Base64.decodeBase64(RSA_PRIVATE_KEY));
		System.out.println("==解密=="+decryptByPrivateKey);
	}
}