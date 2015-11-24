package com.yykj.system.socket.socketclient4bank;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.SocketUtil;

/**
 * HIS通迅服务接口实现类
 * @author QinShuJin
 * 2015年10月10日 下午3:53:26
 */
public class BankSocketClient {
	private static String ENCODING = PropertiesUtil.getInstance().get("HIS_ENCODING");

	/**
	 * 连接银行Socket服务
	 * @param requestXml
	 * @return
	 * @throws Exception
	 */
	public static String ConnectBank(String requestXml) throws Exception {
		String responseXml = "";
		try {
			// 处理请求信息
			byte[] byte_gbk = requestXml.getBytes(ENCODING);
			byte[] byte_out = SocketUtil.addLenHead(byte_gbk, 8, ENCODING);

			String ip = PropertiesUtil.getInstance().get("BANK_IP");
			String port_ = PropertiesUtil.getInstance().get("BANK_PORT");
			int port = Integer.parseInt(port_.trim());
			
			Socket socket = SocketUtil.getConnect(ip, port, 10000); // 连接socket
			OutputStream socketOut = socket.getOutputStream(); 		// 发送请求
			socketOut.write(byte_out);
			InputStream is = socket.getInputStream(); 				// 获取返回值
			String bwt_len = new String(SocketUtil.readLenContent(is, 8));
			int bwbody_length = Integer.parseInt(bwt_len);
			responseXml = new String(SocketUtil.readLenContent(is,bwbody_length), ENCODING);
			
			SocketUtil.closeConnect(socket, socketOut, is);			// 关闭连接
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("连接HIS服务器异常");
		}
		System.out.println("银行响应:"+responseXml);
		return responseXml;
	}
}
