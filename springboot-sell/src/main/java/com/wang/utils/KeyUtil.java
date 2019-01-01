package com.wang.utils;

import java.util.Random;

/**
 * Create by wangjf
 * Date 2018/12/30 18:48
 */
public class KeyUtil {

	/**
	 * 生成唯一的主键
	 * 格式：时间+随机数
	 *
	 * @return
	 */
	public static synchronized String genUniqueKey() {
		Random random = new Random();
		Integer num = random.nextInt(900000) + 100000;
		return System.currentTimeMillis() + String.valueOf(num);
	}

}
