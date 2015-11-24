package com.yykj.base.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DateUtil {

	public DateUtil() {
	}

	public static Date parse(String str) throws Exception {
		Date date = null;
		Pattern p1 = Pattern
				.compile("^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$");
		Matcher m1 = p1.matcher(str);
		Pattern p2 = Pattern.compile("^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$");
		Matcher m2 = p2.matcher(str);
		Pattern p3 = Pattern
				.compile("^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$");
		Matcher m3 = p3.matcher(str);
		Pattern p4 = Pattern.compile("^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$");
		Matcher m4 = p4.matcher(str);
		try {
			if (m1.matches())
				date = sdf1.parse(str);
			else if (m2.matches())
				date = sdf2.parse(str);
			else if (m3.matches())
				date = sdf3.parse(str);
			else if (m4.matches())
				date = sdf4.parse(str);
		} catch (ParseException e) {
			throw new Exception(
					(new StringBuilder(
							"非法日期字符串，解析失败!"))
							.append(str).toString());
		}
		return date;
	}

	public static String format(Date date) {
		if (date != null)
			return sdf2.format(date);
		else
			return "";
	}

	public static String formatDate(Date date, String pattern) {
		if (date == null || pattern == null) {
			return "";
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}

	public static String format(Object value, String pattern) {
		if (value == null)
			return "";
		if (PublicUtil.isNull(pattern))
			return value.toString();
		else
			return (new DecimalFormat(pattern)).format(value);
	}
	
	 public static String formatStr2DateStr(String strDate)
	    {
	        String strDates[] = strDate.split("");
	        String date_str = "";
	        for(int i = 0; i < strDates.length; i++)
	            if(i == 4)
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	                date_str = (new StringBuilder(String.valueOf(date_str))).append("-").toString();
	            } else
	            if(i == 6)
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	                date_str = (new StringBuilder(String.valueOf(date_str))).append("-").toString();
	            } else
	            if(i == 8)
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(" ").toString();
	            } else
	            if(i == 10)
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(":").toString();
	            } else
	            if(i == 12)
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(":").toString();
	            } else
	            {
	                date_str = (new StringBuilder(String.valueOf(date_str))).append(strDates[i]).toString();
	            }

	        return date_str;
	    }

	private static final String sdf1reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
	private static final SimpleDateFormat sdf1 = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final String sdf2reg = "^\\d{2,4}\\-\\d{1,2}\\-\\d{1,2}$";
	private static final SimpleDateFormat sdf2 = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final String sdf3reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$";
	private static final SimpleDateFormat sdf3 = new SimpleDateFormat(
			"yyyy/MM/dd HH:mm:ss");
	private static final String sdf4reg = "^\\d{2,4}\\/\\d{1,2}\\/\\d{1,2}$";
	private static final SimpleDateFormat sdf4 = new SimpleDateFormat(
			"yyyy/MM/dd");
	public static final String pattern1 = "yyyy-MM-dd";
	public static final String pattern2 = "yyyy-MM-dd HH:mm:ss";
	public static final String pattern3 = "yyyy/MM/dd";
	public static final String pattern4 = "yyyy/MM/dd HH:mm:ss";

}
