package com.yykj.system.handlerequest.iso8583;

import java.io.ByteArrayOutputStream;

import com.yykj.base.util.StringUtil;

public class Util {

	/**
	 * 二进制字符串转16进制字符串
	 * 
	 * @param bString
	 * @return
	 */
	public static String binaryString2hexString(String bString) {
		if (bString == null || bString.equals("") || bString.length() % 8 != 0) {
			return null;
		}
		StringBuffer tmp = new StringBuffer();
		int iTmp = 0;
		for (int i = 0; i < bString.length(); i += 4) {
			iTmp = 0;
			for (int j = 0; j < 4; j++) {
				iTmp += Integer.parseInt(bString.substring(i + j, i + j + 1)) << (4 - j - 1);
			}
			tmp.append(Integer.toHexString(iTmp));
		}
		return tmp.toString();
	}

	/**
	 * 16进制字符串转二进制字符串
	 * 
	 * @param bString
	 * @return
	 */
	public static String hexString2binaryString(String hexString) {
		if (hexString == null || hexString.length() % 2 != 0)
			return null;
		String bString = "", tmp;
		for (int i = 0; i < hexString.length(); i++) {
			tmp = "0000"+ Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
			bString += tmp.substring(tmp.length() - 4);
		}
		return bString;
	}

	/**
	 * 右靠左补零
	 * 
	 * @return
	 */
	public static String leftFillZero(BitMap bitmap) {
		String value = bitmap.getValue();
		if(!StringUtil.isNotNullOrEmpty(value)){
			return "";
		}
		value = value.replace("=", "D");
		int str_length = value.length();
		int c_lenght = bitmap.getLength() - str_length;
		for (int i = 0; i < c_lenght; i++) {
			value = "0" + value;
		}
		if ("BCD".equals(bitmap.getDatatype())) {
			value = value;
		} else if ("ASC".equals(bitmap.getDatatype())) {
			value = encodeASCII(value);
		}

		if ("LLVAR".equals(bitmap.getFormat())) {
			String value_length = value.length() + "";
			int c_length = 2 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			if (value.length() % 2 != 0) {
				value = value + "0";
			}
		} else if ("LLLVAR".equals(bitmap.getFormat())) {
			String value_length = value.length() + "";
			int c_length = 4 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			if (value.length() % 2 != 0) {
				value = value + "0";
			}
		}

		// 此代码待确定?
		if (value.length() % 2 != 0) {
			value = value + "0";
		}
		return value;
	}

	/**
	 * 右靠左补零
	 * 
	 * @return
	 */
	public static String leftFillZero_N(BitMap bitmap) {
		String value = bitmap.getValue();
		if(!StringUtil.isNotNullOrEmpty(value)){
			return "";
		}
		value = value.replace("=", "D");
		/*
		 * int str_length = bitmap.getValue().length(); int c_lenght =
		 * bitmap.getLength()-str_length; for (int i = 0; i < c_lenght; i++) {
		 * value="0"+value; }
		 */
		if ("BCD".equals(bitmap.getDatatype())) {
			value = value;
		} else if ("ASC".equals(bitmap.getDatatype())) {
			value = encodeASCII(value);
		}

		if ("LLVAR".equals(bitmap.getFormat())) {
			String value_length = value.length() + "";
			int c_length = 2 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			if (value.length() % 2 != 0) {
				value = value + "0";
			}
		} else if ("LLLVAR".equals(bitmap.getFormat())) {
			int lenght = value.length();
			if(bitmap.getBit()==61){
				lenght = lenght/2;
			}
			String value_length = lenght + "";
			int c_length = 4 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			if (value.length() % 2 != 0) {
				value = value + "0";
			}
		}

		// 此代码待确定?
		if (value.length() % 2 != 0) {
			value = value + "0";
		}
		return value;
	}

	/**
	 * 左靠右补空格
	 * 
	 * @return
	 */
	public static String rightFillSpace(BitMap bitmap) {
		String value = bitmap.getValue();
		if(!StringUtil.isNotNullOrEmpty(value)){
			return "";
		}
		value = value.replace("=", "D");
		if ("BCD".equals(bitmap.getDatatype())) {
			value = value;
		} else if ("ASC".equals(bitmap.getDatatype())) {
			value = encodeASCII(value);
		}
		if ("LLVAR".equals(bitmap.getFormat())) {
			String value_length = value.length() + "";
			int c_length = 2 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			
		} else if ("LLLVAR".equals(bitmap.getFormat())) {
			String value_length = value.length() + "";
			int c_length = 4 - value_length.length();
			for (int i = 0; i < c_length; i++) {
				value_length = "0" + value_length;
			}
			value = value_length + value;
			if (value.length() % 2 != 0) {
				value = value + " ";
			}
		}
		
		return value;
	}

	private static String hexString = "0123456789ABCDEF";

	/**
	 * 将字符编码为ASCII
	 * 
	 * @param str
	 * @return
	 */
	public static String encodeASCII(String str) {
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

	/**
	 * 将ＡＳＳＩＩ码解析成字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String decodeASSII(String bytes) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream(
				bytes.length() / 2);
		// 将每2位16进制整数组装成一个字节
		for (int i = 0; i < bytes.length(); i += 2)
			baos.write((hexString.indexOf(bytes.charAt(i)) << 4 | hexString
					.indexOf(bytes.charAt(i + 1))));
		return new String(baos.toByteArray());
	}
}
