package com.yykj.base.socket;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;
import com.yykj.base.util.SocketUtil;
import com.yykj.system.handlerequest.BusiHandler;


/**
 * 处理socket请求
 * @author QinShuJin
 * 2015年7月9日 上午11:55:22
 */
@SuppressWarnings("all")
public class SocketTask extends Thread {
	private static final String CHARACTER_SET="GBK"; 
	private Logger log = LogUtil.getInstance().getLogger(SocketTask.class);
	
	private Socket socket;

	public SocketTask(Socket socket) {
		this.socket = socket;
	}

	public void run() {
		InputStream ips = null;
		OutputStream ops = null;
		try {
			// 1.获取socket的输入、输出流
			ips = socket.getInputStream();
			ops = socket.getOutputStream();

			// 2.读取socket输入流中的信息
			byte[] len = SocketUtil.readLenContent(ips, 8);
			int lenth = Integer.valueOf(new String(len));
			byte[] intData = SocketUtil.readLenContent_ava(ips, lenth);
			String requestXml = new String(intData, CHARACTER_SET);
			log.info(this.getName() +Thread.currentThread().getName() + " 主机收到信息：" + requestXml);

			// 3.业务处理
			String responseXml = BusiHandler.handler(requestXml);

			// 4.输出信息
			byte[] outDate = responseXml.getBytes(CHARACTER_SET);
			byte[] byte_out = SocketUtil.addLenHead(outDate, 8, CHARACTER_SET);
			log.info(this.getName() +Thread.currentThread().getName()+"主机返回值：" + new String(byte_out, CHARACTER_SET));
			ops.write(byte_out);
		} catch (Exception e) {
			log.error("服务处理异常", e); 
		}finally{
			SocketUtil.closeResources(ops,ips);
		}
	}
}
