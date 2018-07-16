package com.wap.util;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;   
import java.security.KeyFactory;   
import java.security.PrivateKey;   
import java.security.PublicKey;   
import java.security.spec.RSAPrivateKeySpec;   
import java.security.spec.RSAPublicKeySpec;   

import javax.crypto.Cipher;   

import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;   
import com.sun.org.apache.xml.internal.security.utils.Base64;   


/**   
 * @author cnchenhl   
 * Jul 8, 2011  
 */   
public class RSACode {   
	//公钥
	private static String module = "5m9m14XH3oqLJ8bNGw9e4rGpXpcktv9MSkHSVFVMjHbfv+SJ5v0ubqQxa5YjLN4vc49z7SVju8s0X4gZ6AzZTn06jzWOgyPRV54Q4I0DCYadWW4Ze3e+BOtwgVU1Og3qHKn8vygoj40J6U85Z/PTJu3hN1m75Zr195ju7g9v4Hk=";   
	private static String exponentString = "AQAB";   
	//siyao
	private static String delement = "vmaYHEbPAgOJvaEXQl+t8DQKFT1fudEysTy31LTyXjGu6XiltXXHUuZaa2IPyHgBz0Nd7znwsW/S44iql0Fen1kzKioEL3svANui63O3o5xdDeExVM6zOf1wUUh/oldovPweChyoAdMtUzgvCbJk1sYDJf++Nr0FeNW1RB1XG30=";   
	private static String encryptString = "Vx/dGjS1YWKRubsoDgiShiwLgqyNE2z/eM65U7HZx+RogwaiZimNBxjuOS6acEhKZx66cMYEAd1fc6oewbEvDIfP44GaN9dCjKE/BkkQlwEg6aTO5q+yqy+nEGe1kvLY9EyXS/Kv1LDh3e/2xAk5FNj8Zp6oU2kq4ewL8kK/ai4=";   
	private static final String RSA_PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDCgefp9136DhgcPpfqJs1u7MPmOnfe7JOjE1bhVFrcEjDy59wHHFpWKEnUd+gD+vfrVtVTJmfVV9eiA/g3TjUfG/XApTjf0PEE1h7Liu96ceg6LUIMmCGoTcaVWfv1Ty1cyTHZYk6AD7XrwWLrqlYebfKkcmtl0byU2A8JvlmONQIDAQAB";
	private static final String RSA_PRIVATE_KEY = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMKB5+n3XfoOGBw+l+omzW7sw+Y6d97sk6MTVuFUWtwSMPLn3AccWlYoSdR36AP69+tW1VMmZ9VX16ID+DdONR8b9cClON/Q8QTWHsuK73px6DotQgyYIahNxpVZ+/VPLVzJMdliToAPtevBYuuqVh5t8qRya2XRvJTYDwm+WY41AgMBAAECgYEAtT2s5sMb62P0rWTZ01JivEicZcv3ZF6XVy4vF3mtzECbIcxQpsORnZSe7v7nz8Wr+7uGvZscx5u5jvsZ8BbMwVQ9p29msqQLotC6EGnri2rGT+DJfydaOij4ftm+73/uhhgQzQiaS0gvFesnH7b1+tPKPfviMpQumdcO6FD+L6ECQQD7xAkAjTfw6rMXWPuaHMu4HJcvNs5e/n9KlPOf0nSfNO0mSq+r+c+6YHK8KrIFuRZgL5Gzk+vkXuQuw8TCsBItAkEAxcdZErNRm0ohr20FzAILLFVnV6HPwvSuGDbvfoA1C55QWApkoGVzCdP/r9wMqUfPkOH7jvAIIW99XjS0t0lZKQJAIqmfz6qhvBKkK5+9rD8wsR2GlyKFkUCax+rwC94O3miMCgyYWSYgunlS8qMyNWWBNib2eXHxNVT3N5UOfvt81QJAQWMVxMP6L/ADUP8nUno+fWrC3ssFaDwCrsjCxzzVzMCLaYAOIqVHsjEfsGF84h52tjywMWKUHZHBjfq44HvkeQJBANdhV1uWc3QnyDHmNTB/xaOqdZK54g42PMl4T9WVp4rne0TUv3w9bkQopPiRqsiZEe0SGVpCuer2HLOr7AlnC3Q=";

	/**   
	 * @param args   
	 * 542d55dfe7b519a1ebff69de66f266101b8b9b8a0cc1db70562b3838850461732e5ae91e903dfacdcc3a8a766fd1910e8e77379d8d792bf753d072c116fe41df2d132552f1759ff9f1bab660a3324716dc1856b3db881489cda8efe82dab36de88a1c871c6b122998553366eae898761a6456966f4c3322abf61ac2d1ab14146
	 * @throws Base64DecodingException 
	 */   
	public static void main(String[] args) throws Base64DecodingException {   
		//    	String base="542d55dfe7b519a1ebff69de66f266101b8b9b8a0cc1db70562b3838850461732e5ae91e903dfacdcc3a8a766fd1910e8e77379d8d792bf753d072c116fe41df2d132552f1759ff9f1bab660a3324716dc1856b3db881489cda8efe82dab36de88a1c871c6b122998553366eae898761a6456966f4c3322abf61ac2d1ab14146";
		//    	byte[] hexStringToBytes = hexStringToBytes(base);
		//		byte[] dencrypt = Dencrypt(hexStringToBytes);
		//		String string = new String(dencrypt);
		//		System.out.println(string);

		/*byte[] en = encrypt("123456");   
		System.out.println("123456="+Base64.encode(en));   
		byte[] enTest = null;   
		String ss="OzYOox4qE59e5y+jA3p4O0BrmX+yMQ5k4wOjifJ5ohHSCDJuasqlUsksMQElxapJhoFFARgZXB36FFqQzClfAXigV1u9ADaMJPz3QGI4y5/+hsolwfxjx7tzrKlYm9bksMNPY4uo9hbPQABy3YAe1P6eIwV03d01ThaxHeyBpb0=";
		try {   
			enTest = Base64.decode(ss);   
		} catch (Base64DecodingException e) {   
			e.printStackTrace();   
		}   
		System.out.println(enTest.length);   
		System.out.println(en.length);   
		System.out.println(new String(Dencrypt(en)));   
		System.out.println(new String(Dencrypt(enTest)));*/  
		String aesKey="glor";
		//Ze9Qgob5dIpusnquCfCsXE+PjHfarR2Lc8EfPt03MVTAz+Y9sg1NzQ8Hq2gJw68P3NewX1jMM0YMBiji0MHOn7cg3jKyBwtkR8iO4TF2fZgYIRimZpk6MRatcKZ3jFafKlIpRJMlPeVKwDtfPzdwqG9W+m5AoshwCSPNRfQf864=
		String str = "K8MG7R+YI+D+8/tZeW+LYU3IWvga+2m6FDyKuB8+OQ4RF50ilAtiNSJ7V1D4+/2G0312ZiU0BVcY+MZ4/ClUDU+7V3qwJOZCtHTg0yTDA20qIaqc9pN/g+X4lXJcj1k7WjdCi6ZYFc8hSmKJ5KayNYK7qfCCeCHlXgnFz61IbWw=";
		byte[] encrypt = encrypt(aesKey);
		String enTest = Base64.encode(encrypt);
		System.out.println("rsa密文"+enTest);
		byte[] dencrypt = Dencrypt(Base64.decode(str));
		System.out.println(new String(dencrypt));

	}   

	public static byte[] encrypt(String str) {     
		try {   
			byte[] modulusBytes = Base64.decode(module);   
			byte[] exponentBytes = Base64.decode(exponentString);
			//系数；模数
			BigInteger modulus = new BigInteger(1, modulusBytes); 
			//			BigInteger modulus = new BigInteger("22492476035981112498259632490702542716642494198681203654742017353458405506863763961469499209654439502321419941202988321537856780709724326611201163755429855523082695160571410722271276510429791724273899569790863371686758462154978177473419362600994420189500304065969502469923448274114120667690245570666892348013150072765763098241845967660514873339845940847693083241814588965490317187199718803413735149322332593765735844766642341604026905816553394612938322191165612899466306829062893756757791936427434914482024922992195474262895106222669695736298615223434094496242606294157019881116552308443795718063100663135051775467511"); 
			//指数
			BigInteger exponent = new BigInteger(1, exponentBytes);   
			//			BigInteger exponent = new BigInteger("65537");   

			RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(modulus, exponent);   
			KeyFactory fact = KeyFactory.getInstance("RSA");   
			PublicKey pubKey = fact.generatePublic(rsaPubKey);   

			Cipher cipher = Cipher.getInstance("RSA");   
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);   

			byte[] cipherData = cipher.doFinal(new String(str).getBytes());   
			return cipherData;   
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
		return null;   

	}   

	public static byte[] Dencrypt(byte[] encrypted) {   
		try {   
			byte[] expBytes = Base64.decode(delement);   
			byte[] modBytes = Base64.decode(module);   

			BigInteger modules = new BigInteger(1, modBytes);   
			//			BigInteger modules = new BigInteger("22492476035981112498259632490702542716642494198681203654742017353458405506863763961469499209654439502321419941202988321537856780709724326611201163755429855523082695160571410722271276510429791724273899569790863371686758462154978177473419362600994420189500304065969502469923448274114120667690245570666892348013150072765763098241845967660514873339845940847693083241814588965490317187199718803413735149322332593765735844766642341604026905816553394612938322191165612899466306829062893756757791936427434914482024922992195474262895106222669695736298615223434094496242606294157019881116552308443795718063100663135051775467511"); 

			BigInteger exponent = new BigInteger(1, expBytes);   
			//			BigInteger exponent = new BigInteger("20096921116910233983255097117018917464309238030306166629701211684490651864830880667283663658997741946342301104674052628962147786868781411925661939759627478368938971581207258459557793896901556586329962267242070684588270980795719320968476091609723213803447965350732809804100086372389651096906140193807180908839103839613902410152131296403144146925086668816931333253428221445958932872852736140880902488798295673237205938506715407570292187255522184342357405017587288032596408686375643150302649425414471354578890242883621274459990913892329331044414174950290954663494908640316426747141072457855158080275748955568305266224065");   

			KeyFactory factory = KeyFactory.getInstance("RSA");   
			Cipher cipher = Cipher.getInstance("RSA");   

			RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modules, exponent);   
			PrivateKey privKey = factory.generatePrivate(privSpec);   
			cipher.init(Cipher.DECRYPT_MODE, privKey);   
			byte[] decrypted = cipher.doFinal(encrypted);   
			return decrypted;   
		} catch (Exception e) {   
			e.printStackTrace();   
		}   
		return null;   
	}   

	/**  
	 * Convert hex string to byte[]  
	 * @param hexString the hex string  
	 * @return byte[]  
	 */  
	public static byte[] hexStringToBytes(String hexString) {   
		if (hexString == null || hexString.equals("")) {   
			return null;   
		}   
		hexString = hexString.toUpperCase();   
		int length = hexString.length() / 2;   
		char[] hexChars = hexString.toCharArray();   
		byte[] d = new byte[length];   
		for (int i = 0; i < length; i++) {   
			int pos = i * 2;   
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));   
		}   
		return d;   
	} 

	/**

	 * Convert byte[] to hex string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。   
	 * @param src byte[] data   
	 * @return hex string   
	 */      
	public static String bytesToHexString(byte[] src){   
		StringBuilder stringBuilder = new StringBuilder("");   
		if (src == null || src.length <= 0) {   
			return null;   
		}   
		for (int i = 0; i < src.length; i++) {   
			int v = src[i] & 0xFF;   
			String hv = Integer.toHexString(v);   
			if (hv.length() < 2) {   
				stringBuilder.append(0);   
			}   
			stringBuilder.append(hv);   
		}   
		return stringBuilder.toString();   
	}  
	/**  
	 * Convert char to byte  
	 * @param c char  
	 * @return byte  
	 */  
	public static byte charToByte(char c) {   
		return (byte) "0123456789ABCDEF".indexOf(c);   
	}  

	/**
	 * 
	 * @Title: getKey 
	 * @Description: TODO(给定秘钥路径，读取秘钥返回秘钥的字节数组) 
	 * @author: shaomd 
	 */
	private static byte[] getKey(String keyPath) throws IOException{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(keyPath);
		System.out.println(is);
		byte[] key = readFile(is);
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
	 * 下面是Key的互通代码
	 */
	private byte[] removeMSZero(byte[] data) {   
		byte[] data1;   
		int len = data.length;   
		if (data[0] == 0) {   
			data1 = new byte[data.length - 1];   
			System.arraycopy(data, 1, data1, 0, len - 1);   
		} else  
			data1 = data;   

		return data1;   
	}  
}  
