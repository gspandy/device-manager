package com.yykj.base.netty.unionpay;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.codec.binary.Hex;

import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.SocketUtil;


/**
 * 用于连接银联Socket服务 
 * 用于第一种方式
 * @author QinShuJin
 *
 */
public class UnionPaySocketClient_Mu {

	private static String UNIONPAY_IP = PropertiesUtil.getInstance().get("UNIONPAY_IP");
	private static int UNIONPAY_PORT = Integer.parseInt(PropertiesUtil.getInstance().get("UNIONPAY_PORT"));
	public static String requestBank(String msg) throws Exception {
		//1.去掉消息附加信息
		msg = msg.replace("323232323232323232323232313131313131313131313131", "").trim();
		
		//2.计算消息长度
		int msg_length = msg.length()/2;
		String hex_length = Integer.toHexString(msg_length);
		int c_length = 4-hex_length.length();
		for (int i = 0; i < c_length; i++) {
			hex_length = "0"+hex_length;
		}
		msg = hex_length+msg;
		
		System.out.println("发送给银行的："+msg);
		
		//3.连接银联Socket服务
		Socket socket = new Socket(UNIONPAY_IP,UNIONPAY_PORT);
		
		//4.将报文发送到银联服务器,以16进制发送
		OutputStream socketOut = socket.getOutputStream();
        socketOut.write(Hex.decodeHex(msg.toCharArray()));

        //5.获取返回值
		InputStream is = socket.getInputStream();
		//读取2节字的长度
		byte [] buflen = new byte[2];
		buflen = SocketUtil.readLenContent(is,2);
		String length =  new String(Hex.encodeHex(buflen)).toUpperCase();
		int size = Integer.parseInt(length,16);
		//读取报文值
		byte [] body = SocketUtil.readLenContent(is,size);
		String response_msg = new String(Hex.encodeHex(body)).toUpperCase();
		
		System.out.println("银行返回的："+response_msg);
		
		//长度要做转换
		String le_ = length.replaceFirst("^0+", "");
		int lee = 4-le_.length();
		for (int i = 0; i < lee; i++) {
			le_ = le_+"0";
		}
		
		response_msg = le_+response_msg;
		
		System.out.println("长度转换后："+response_msg);
		return response_msg;
	}
}
