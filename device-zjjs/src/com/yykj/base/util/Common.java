package com.yykj.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	private final static String DEFAULT_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
	private final static String DEFAULT_PATTERN = "yyyy-MM-dd";
	private final static String TIME_PATTERN = "HH:mm:ss";
	/**
	 * 判断变量是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isEmpty(String s) {
		if (null == s || "".equals(s) || "".equals(s.trim()) || "null".equalsIgnoreCase(s)) {
			return true;
		} else {
			return false;
		}
	
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @return 1921-05-01 13:12:12
	 */
	public static String getCurrentDateTime() {
		return getCurrentDateTime(DEFAULT_TIME_PATTERN);
	}
	
	/**
	 * 格式化当前时间
	 * 
	 * @return 1921-05-01 13:12:12
	 */
	public static String getCurrentDate() {
		return getCurrentDateTime(DEFAULT_PATTERN);
	}
	
	/**
	 * 格式化当前日期
	 * 
	 * @return 1921-05-01
	 */
	public static String getCurrentTime() {
		return getCurrentDateTime(TIME_PATTERN);
	}
	/**
	 * 格式化当前时间
	 * 
	 * @return 13:12:12
	 */
	public static String getCurrentDateTime(String pattern) {
		java.text.SimpleDateFormat sd = new java.text.SimpleDateFormat(pattern);
		return sd.format(new java.util.Date());
	}
	
	public static Date getDate(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String [] strs = str.split("");
		String date_str="";
		for(int i=0;i<strs.length;i++){
			if(i==4){
				date_str+=strs[i];
				date_str += "-";
			}else if(i==6){
				date_str+=strs[i];
				date_str += "-";
			}else if(i==8){
				date_str+=strs[i];
				date_str += " ";
			}else if(i==10){
				date_str+=strs[i];
				date_str += ":";
			}else if(i==12){
				date_str+=strs[i];
				date_str += ":";
			}else{
				date_str+=strs[i];
			}
		}
		return sdf.parse(date_str);
	}
}
