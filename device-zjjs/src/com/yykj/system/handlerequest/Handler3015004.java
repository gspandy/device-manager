package com.yykj.system.handlerequest;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.netty.unionpay.UnionPaySocketClient;
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.StringUtil;
import com.yykj.system.handlerequest.iso8583.MessageParse;

/**
 * 处理3015001交易 组装签到报文
 * 
 * @author QinShuJin
 * 
 */
public class Handler3015004 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler3015004.class);

	@Override
	public String handleRequest(Map<String, String> title,
			String deviceRequestXml) {
		String responseXml = null;
		String ChannelDate = title.get("ChannelDate"); // 业务请求时间
		String trancode = title.get("TranCode"); // 业务代码
		if ("3015004".equals(trancode)) {
			try {
				Map<String, String> result_map = explainDeviceRequestXml(deviceRequestXml);
				String msg_type = result_map.get("MsgType"); // 交易类型
				String msg = result_map.get("Msg"); // 报文
				// 计算长度
				int length = msg.length() / 2;
				String hex_length = Integer.toHexString(length);
				int c_length = 4 - hex_length.length();
				for (int i = 0; i < c_length; i++) {
					hex_length = "0" + hex_length;
				}
				msg = hex_length + msg.trim();
				log.info("[" + msg_type + "] 交易发送报文:" + StringUtil.addSpace(msg));

				String bankResponse = UnionPaySocketClient.requestBank(msg);
				//String bankResponse = unionPaySocketClient(msg).substring(4);
				;
				log.info("[" + msg_type + "] 交易银行响应报文:" + StringUtil.addSpace(bankResponse));

				String responseMsg = MessageParse.parseMsg(bankResponse).trim();
				log.info("[" + msg_type + "] 交易解析报文:" + responseMsg);
				responseXml = "<?xml version=\"1.0\" encoding=\"GBK\"?><Root>" + HandlerUtil.getHandlerXml(trancode, "0", responseMsg) + "</Root>";
			} catch (Exception e) {
				e.printStackTrace();
				responseXml = "<?xml version=\"1.0\" encoding=\"GBK\"?>"+HandlerUtil.getErrorXml(trancode, "银行交易失败");
			}
		} else {
			responseXml = this.getSuccessor().handleRequest(title,
					deviceRequestXml);
		}

		return responseXml;
	}

	/**
	 * 解析自助设备请求XML
	 * 
	 * @param requestXML
	 * @return
	 */
	private static Map<String, String> explainDeviceRequestXml(String requestXML)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();
			List<Element> data_eles = root.selectNodes("/Root/Data");
			for (Element head : data_eles) {
				List<Element> headInfos = head.elements();
				for (Element element : headInfos) {
					resultMap.put(element.getName(), element.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析请求3015001请求XML异常");
		}
		return resultMap;
	}

	public String unionPaySocketClient(String msg) throws IOException,
			InterruptedException, DecoderException {
		Socket socket = new Socket();
		String response_msg = "";
		try {
			
			//关闭socket时，底层socket不会直接关闭，会延迟一会，直到发送完所有数据  
            //等待10秒再关闭底层socket连接，0为立即关闭底层socket连接 
			socket.setSoLinger(true, 60);
			
			socket.setTrafficClass(0x04 | 0x10);
			
			//设置性能参数，可设置任意整数，数值越大，相应的参数重要性越高（连接时间，延迟，带宽）
			socket.setPerformancePreferences(2, 1, 3);
			
			//关闭传输缓存，默认为false  
			socket.setTcpNoDelay(true);
			
			socket.setReceiveBufferSize(512);
			//如果输入流等待1000毫秒还未获得服务端发送数据，则提示超时，0为永不超时  
			socket.setSoTimeout(30 * 1000);
			socket.connect(new InetSocketAddress("59.175.205.114", 58008),40 * 1000);
			socket.getOutputStream().write(Hex.decodeHex(msg.toCharArray()));

			System.out.println("is connect:" + socket.isConnected());
			System.out.println("is close:" + socket.isClosed());

			/*int flag = 0;
			Thread.sleep(2*1000);
			InputStream is = socket.getInputStream();
			int count = is.available();
			byte[] tt = new byte[count];
			int z;
			StringBuilder bul = new StringBuilder();
			while ((z = is.read(tt, 0, tt.length)) != -1) {
				String code = new String(Hex.encodeHex(tt)).toUpperCase();
				bul.append(code);
				System.out.println(new String(Hex.encodeHex(tt)).toUpperCase());
				if(z==count){
					break;
				}
			}
			response_msg = bul.toString();
			System.out.println(flag);*/
			byte[] btyes = new byte[1024];
			DataInputStream dis = new DataInputStream(socket.getInputStream());
	        dis.read(btyes);
	        response_msg = new String(Hex.encodeHex(btyes)).toUpperCase();
			System.out.println("----->"+response_msg);

			socket.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response_msg;
	}
}
