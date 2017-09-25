package com.autmone.softmarket.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	/**
	 * @param Date
	 * @param formatType
	 * @return 返回格式化的日期字符串
	 */
	public static String dateToString(Date date, String formatType) {
		SimpleDateFormat formatter = new SimpleDateFormat(formatType);
		String dateStr = "";
		try {
			dateStr = formatter.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dateStr;
	}
}
