package com.yykj.system.socket.socketserv4local;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.ReadXmlFileUtil;
import com.yykj.base.util.SocketUtil;

public class ClientThread extends Thread{
	private String filename = "";
	private static final String CHARACTER_SET="GBK"; 

	public ClientThread(String filenm){
		this.filename=filenm;
	}
	@Override
	public void run() {
		try {
			String requestXml = ReadXmlFileUtil.getXml(filename,"com/yykj/system/handlerequest/xml/");
			byte [] byte_gbk = requestXml.getBytes(CHARACTER_SET);
			byte [] byte_out = SocketUtil.addLenHead(byte_gbk, 8,CHARACTER_SET);
			String ip = "127.0.0.1";
			int port =Integer.valueOf(PropertiesUtil.getInstance().get("SOCKET_PORT").toString()) ;
			int bank_port = Integer.valueOf(port); 
			//int bank_port = 8888;
			Socket socket = new Socket(ip, bank_port);
			OutputStream socketOut = socket.getOutputStream();
			socketOut.write(byte_out);
			
			//获取返回值
			InputStream is = socket.getInputStream();
	        //String bwt_len = new String(SocketUtil.readLenContent(is,8));
	        //int bwbody_length = Integer.parseInt(bwt_len);
	        String responseXml = new String(SocketUtil.readContent(is),CHARACTER_SET);
	        //关闭连接
	        SocketUtil.closeConnect(socket, socketOut, is);
			
			//平台返回值
			System.out.println("Server say : " + responseXml);
			
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
    }
}
