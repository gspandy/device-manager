package com.yykj.system.handlerequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.LogUtil;
import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Doctor4003;
import com.yykj.system.entity.hisentity.Info4002;
import com.yykj.system.entity.hisentity.Info4003;
import com.yykj.system.socket.socketclient4his.HandlerHis2004;
import com.yykj.system.socket.socketclient4his.HandlerHis4003;
import com.yykj.system.socket.socketclient4his.HandlerHis4005;

/**
 * 处理2015012交易 
 * 提交挂号
 * @author QinShuJin 
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015012 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015012.class);

	@Override
	public String handleRequest(Map title, String deviceRequestXml) {
		String responseXml = null;
		String trancode = (String) title.get("TranCode");
		try {
			if ("2015012".equals(trancode)) {
				Map<String,String> map_deviceRequest = explainDeviceRequestXml(deviceRequestXml);
				Map<String,String> response_map = HandlerHis2004.handler2004(map_deviceRequest);
				if ("1".equals(response_map.get("ResultCode"))) {
					responseXml = HandlerUtil.getErrorXml(trancode,response_map.get("ErrorMsg"));
				} else {
					responseXml = createResponseXml(response_map);
				}
			} else {
				responseXml = getSuccessor().handleRequest(title,deviceRequestXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseXml = HandlerUtil.getErrorXml(trancode, e.getMessage());
		}
		return responseXml.toString();
	}

	public String createResponseXml(Map<String,String> response_map) {
		StringBuilder responseXml = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		responseXml.append("<Root>");
			responseXml.append("<Head>");
				responseXml.append("<TranCode>2015012</TranCode>");
				responseXml.append("<RspCode>0</RspCode>");
				responseXml.append("<RspMsg>查询成功</RspMsg>");
			responseXml.append("</Head>");
			responseXml.append("<Data>");
				responseXml.append("<PatientID>").append(response_map.get("PatientID")).append("</PatientID>");
				responseXml.append("<CardTranFlow>").append(response_map.get("CardTranFlow")).append("</CardTranFlow>");
				responseXml.append("<PayTranFlow>").append(response_map.get("PayTranFlow")).append("</PayTranFlow>");
				responseXml.append("<TranTime>").append(response_map.get("TranTime")).append("</TranTime>");
				responseXml.append("<Amt>").append(response_map.get("Amt")).append("</Amt>");
			responseXml.append("</Data>");
		responseXml.append("</Root>");
		return responseXml.toString();
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
			throw new BusinessException("解析请求2015009请求XML异常");
		}
		return resultMap;
	}
}
