package com.yykj.system.socket.socketclient4his;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.SocketUtil;

/**
 * 中联HIS通迅服务接口实现类
 * @author QinShuJin
 * 2015年10月10日 下午3:53:34
 */
public class HisSocketClient{
	private static String ENCODING=PropertiesUtil.getInstance().get("HIS_ENCODING");
	
	/**
	 * 连接HIS socket服务
	 * @param requestXml
	 * @return
	 * @throws Exception
	 */
	public static String ConnHisSocket(String requestXml) throws Exception {
		String responseXml = "";
		try {
			String ip = PropertiesUtil.getInstance().get("HIS_IP");
			String port_ = PropertiesUtil.getInstance().get("HIS_PORT");
			int port = Integer.parseInt(port_.trim());
			Socket socket = SocketUtil.getConnect(ip,port,10000);	//连接socket 
			OutputStream os = socket.getOutputStream();				//发送请求
			InputStream is = socket.getInputStream();				//获取返回值
			os.write(requestXml.getBytes());
			String bwt_len = new String(SocketUtil.readLenContent(is,8));
			int bwbody_length = Integer.parseInt(bwt_len);
			responseXml = new String(SocketUtil.readLenContent(is,bwbody_length),ENCODING);
			SocketUtil.closeConnect(socket, os, is);				//关闭连接
		}catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("连接HIS服务器异常");
		}
		System.out.println("HIS响应:"+responseXml);
		return responseXml; 
	}
}
