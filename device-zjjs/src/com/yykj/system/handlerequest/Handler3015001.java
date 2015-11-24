package com.yykj.system.handlerequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.netty.unionpay.UnionPaySocketClient;
import com.yykj.base.util.LogUtil;
import com.yykj.system.handlerequest.iso8583.MessagePack;
import com.yykj.system.handlerequest.iso8583.MessageParse;

/**
 * 处理3015001交易 
 * 组装签到报文
 * @author QinShuJin
 * 
 */
public class Handler3015001 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler3015001.class);

	@Override
	public String handleRequest(Map<String, String> title,String deviceRequestXml) {
		String responseXml = null;
		String ChannelDate = title.get("ChannelDate"); // 业务请求时间
		String trancode = title.get("TranCode"); // 业务代码
		if ("3015001".equals(trancode)) {
			try {
				Map<String,String> requestMap = explainDeviceRequestXml(deviceRequestXml);
				String msg = MessagePack.pack0800Info(requestMap).toUpperCase();
				log.info("[0800] 交易发送报文:"+addSpace(msg));
				String bankResponse = UnionPaySocketClient.requestBank(msg);
				log.info("[0800]交易银行响应报文:"+addSpace(bankResponse));
				String responseMsg = MessageParse.parseMsg(bankResponse);
				log.info("[0800] 交易解析报文:"+responseMsg);
				responseXml = "<?xml version=\"1.0\" encoding=\"GBK\"?><Root>"+HandlerUtil.getHandlerXml(trancode, "0", responseMsg)+"</Root>";
			} catch (Exception e) {
				e.printStackTrace();
				responseXml = HandlerUtil.getErrorXml(trancode, "签到失败");
			}
		} else {
			responseXml = this.getSuccessor().handleRequest(title,deviceRequestXml);
		}

		return responseXml;
	}
	
	
	public String addSpace (String msg) {
        String regex = "(.{2})";
        msg = msg.replaceAll (regex, "$1 ");
        return msg;
    }
	

	/**
	 * 解析自助设备请求XML
	 * @param requestXML
	 * @return
	 */
	private static Map<String,String> explainDeviceRequestXml(String requestXML) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();
		
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
}
