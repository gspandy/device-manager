package com.yykj.base.netty.unionpay;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.codec.binary.Hex;

import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.SocketUtil;


/**
 * 用于连接银联Socket服务 
 * @author QinShuJin
 *
 */
public class UnionPaySocketClient {

	private static String UNIONPAY_IP = PropertiesUtil.getInstance().get("UNIONPAY_IP");
	private static int UNIONPAY_PORT = Integer.parseInt(PropertiesUtil.getInstance().get("UNIONPAY_PORT"));
	public static String requestBank(String msg) throws Exception {
		//1.连接银联Socket服务
		//Socket socket = new Socket(UNIONPAY_IP,UNIONPAY_PORT);
		Socket socket = SocketUtil.getConnect(UNIONPAY_IP,UNIONPAY_PORT,30*1000);
		//2.将报文发送到银联服务器,以16进制发送
		OutputStream socketOut = socket.getOutputStream();
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
		SocketUtil.closeConnect(socket, socketOut, is);
		String response_msg = new String(Hex.encodeHex(body)).toUpperCase();
		 
		/*byte [] body = SocketUtil.readContent(is);
		String response_msg = new String(Hex.encodeHex(body)).toUpperCase();*/
		
		//response_msg = response_msg.substring(4);
		return response_msg;
	}
}
