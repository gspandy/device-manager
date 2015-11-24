package com.yykj.base.socket;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;
import com.yykj.base.util.PropertiesUtil;

/**
 * 线程类
 * @author QinShuJin
 * 2015年7月9日 上午11:42:43
 */
public class SocketThread extends Thread {
	private ServerSocket serverSocket = null;
	private Logger log = LogUtil.getInstance().getLogger(SocketThread.class);
	private int port = Integer.parseInt(PropertiesUtil.getInstance().get("SOCKET_PORT","8991").toString());

	public SocketThread(ServerSocket serverScoket) {
		try {
			if (null == serverSocket) {
				this.serverSocket = new ServerSocket(port);
				log.info("socket service is start ....................");
			}
		} catch (Exception e) {
			log.error("SocketThread创建socket服务出错:"+e.getMessage());
		}
	}

	public void run() {
		while (!this.isInterrupted()) {
			try {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(5*1000);
				if (null != socket && !socket.isClosed()) {
					SocketTask task = new SocketTask(socket);
					ThreadPool.getInstance().getPool().execute(task);
				}
			} catch (Exception e) {
				log.error("创建Socket处理程序异常："+ e.getMessage(),e);
			}
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
