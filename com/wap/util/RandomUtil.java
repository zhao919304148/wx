package com.wap.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomUtil {
	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		return getRandomString(base, length);
	}

	public static String getRandomString(String src, int length) {
		Random random = new SecureRandom();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(src.length());
			sb.append(src.charAt(number));
		}
		return sb.toString();
	}

	public static int getRandomNumber(int num) {
		Random random = new SecureRandom();
		return random.nextInt(num);
	}
}
