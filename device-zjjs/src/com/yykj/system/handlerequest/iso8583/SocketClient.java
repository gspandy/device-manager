package com.yykj.system.handlerequest.iso8583;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.Hex;

import com.yykj.base.util.SocketUtil;

public class SocketClient {
	private static final String CHARACTER_SET = "GBK";

	public static void main(String[] args) throws Exception {
		test();
		
//		String msg="60 05 85 00 00 60 22 10 00 00 00 08 00 00 20 00 00 00 C0 00 00 00 03 06 30 30 30 32 30 30 30 31 33 30 32 34 32 30 31 35 39 39 38 30 30 30 32";
//		MessageParse.parseMsg(msg.replace(" ",""));
	}
	public static void test() throws Exception {
		try {
			//String msg = MessagePack.pack0800Info();
			//String msg = MessagePack.pack0200Info();
			//String msg = MessagePack.pack0400Info();
			
			
			String msg="007460058500006021000000000200702004C020C0980117622396888072531200190000000000010000098770021082063562239688807253120D491252010000822000303030323030303133303234323031353939383030303231353679348228DC95907A26000000000000004B993C1B7838920E";
			System.out.println("request:"+msg);
			
			//1.连接socket
			String ip = "59.175.205.114";
			int bank_port = 58008;
			Socket socket = new Socket(ip, bank_port);
			OutputStream socketOut = socket.getOutputStream();
			
			//2.发送16进制报文
	        socketOut.write(Hex.decodeHex(msg.toCharArray()));

	        //3.获取返回值
			InputStream is = socket.getInputStream();
			//读取2节字的长度
			byte [] buflen = new byte[2];
			buflen = SocketUtil.readLenContent(is,2);
			String length =  new String(Hex.encodeHex(buflen)).toUpperCase();
			int size = Integer.parseInt(length,16);
			//读取报文值
			byte [] body = SocketUtil.readLenContent(is,size);
			String response_msg = new String(Hex.encodeHex(body)).toUpperCase();
			System.out.println("response:"+length+":"+response_msg);
			
			//解析报文
			MessageParse.parseMsg(response_msg);
			
			
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
