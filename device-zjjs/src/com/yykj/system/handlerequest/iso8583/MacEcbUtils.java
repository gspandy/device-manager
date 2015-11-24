package com.yykj.system.handlerequest.iso8583;

import java.util.Arrays;

public class MacEcbUtils {

	public static byte[] IV = new byte[8];

	public static byte byteXOR(byte src, byte src1) {
		return (byte) ((src & 0xFF) ^ (src1 & 0xFF));
	}

	public static byte[] bytesXOR(byte[] src, byte[] src1) {
		int length = src.length;
		if (length != src1.length) {
			return null;
		}
		byte[] result = new byte[length];
		for (int i = 0; i < length; i++) {
			result[i] = byteXOR(src[i], src1[i]);
		}
		return result;
	}

	/**
	 * mac计算,数据不为8的倍数，需要补0，将数据8个字节进行异或，再将异或的结果与下一个8个字节异或，一直到最后，将异或后的数据进行DES计算
	 * 
	 * @param key
	 * @param Input
	 * @return
	 */
	public static byte[] clacMac(byte[] key, byte[] Input) {
		int length = Input.length;
		int x = length % 8;
		int addLen = 0;
		if (x != 0) {
			addLen = 8 - length % 8;
		}
		int pos = 0;
		byte[] data = new byte[length + addLen];
		System.arraycopy(Input, 0, data, 0, length);
		byte[] oper1 = new byte[8];
		System.arraycopy(data, pos, oper1, 0, 8);
		pos += 8;
		for (int i = 1; i < data.length / 8; i++) {
			byte[] oper2 = new byte[8];
			System.arraycopy(data, pos, oper2, 0, 8);
			byte[] t = bytesXOR(oper1, oper2);
			oper1 = t;
			pos += 8;
		}
		// 将异或运算后的最后8个字节（RESULT BLOCK）转换成16个HEXDECIMAL：
		byte[] resultBlock = bytesToHexString(oper1).getBytes();
		// 取前8个字节用mkey1，DES加密
		byte[] front8 = new byte[8];
		System.arraycopy(resultBlock, 0, front8, 0, 8);
		byte[] behind8 = new byte[8];
		System.arraycopy(resultBlock, 8, behind8, 0, 8);
		byte[] desfront8 = DesUtils.encrypt(front8, key);
		// 将加密后的结果与后8 个字节异或：
		byte[] resultXOR = bytesXOR(desfront8, behind8);
		// 用异或的结果TEMP BLOCK 再进行一次单倍长密钥算法运算
		byte[] buff = DesUtils.encrypt(resultXOR, key);
		// 将运算后的结果（ENC BLOCK2）转换成16 个HEXDECIMAL asc
		// 取8个长度字节
		byte[] retBuf = new byte[8];
		System.arraycopy(bytesToHexString(buff).getBytes(), 0, retBuf, 0, 8);
		return retBuf;
	}

	public static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
	 public static void main(String[] args) {  
	        byte[] buff = { 2, 1, 32, 32, 0, 0, 0, -64, -128, 25, 0, 0, 0, 0, 0, 86, 48, 49, 48, 52, 55, 51, 50, 53, 49,  
	                48, 52, 49, 49, 48, 49, 53, 52, 49, 49, 52, 54, 54, 48, 67, 78, 89, 0, 6, 48, 49, 49, 48, 48, 48, 0,  
	                68, 48, 48, 48, 48, 48, 49, 48, 56, 48, 48, 48, 49, 103, 80, 102, 53, 90, 72, 119, 67, 101, 105, 52,  
	                61, 67, 90, 83, 83, 48, 48, 49, 53, 17, 49, 51, 49, 51, 49, 51, 49, 51, 49, 51, 49 };  
	        byte [] mac = clacMac("8C126630422844CD".getBytes(), buff);  
	        
	        System.out.println(bytesToHexString(mac));
    }  
}