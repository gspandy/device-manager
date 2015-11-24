package com.yykj.base.socket_listener;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;
import com.yykj.base.util.PropertiesUtil;

public class HisServer extends Thread {
	private static Logger log = LogUtil.getInstance().getLogger(HisServer.class);
	private ServerSocket serverSocket = null;
	String port_ = PropertiesUtil.getInstance().get("HIS_PORT");
	int port = Integer.parseInt(port_.trim());
	
	public HisServer(ServerSocket serverScoket) {
		try {
			if (null == serverSocket) {
				this.serverSocket = new ServerSocket(port);
				log.info("his socket service is start port:"+port+"....................");
			}
		} catch (Exception e) {
			log.error("创建HIS_Socket服务出错:"+e.getMessage());
		}
	}

	@Override
	public void run() {
		try {
			boolean bRunning = true;
			while (bRunning) {
				// 接收请求
				Socket s = serverSocket.accept();
				// 将请求指定一个线程去执行
				new Thread(new HisCreateServerThread(s)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void closeSocketServer() {
		try {
			if (null != serverSocket && !serverSocket.isClosed()) {
				serverSocket.close();
			}
		} catch (IOException e) {
			log.error("关闭Socket处连接异常："+ e.getMessage());
			e.printStackTrace();
		}
	}
}