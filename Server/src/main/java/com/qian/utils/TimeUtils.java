package com.qian.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

	/*
	 * 生成当前系统时间
	 * 格式yyyy-mm-dd hh:mm:ss
	 */
	public static String getCurrentTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(new Date());
	}
}
