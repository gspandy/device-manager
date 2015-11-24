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
import com.yykj.system.socket.socketclient4his.HandlerHis4003;
import com.yykj.system.socket.socketclient4his.HandlerHis4005;

/**
 * 处理2015011交易 
 * 提交挂号
 * @author QinShuJin 
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015011 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015011.class);

	@Override
	public String handleRequest(Map title, String deviceRequestXml) {
		String responseXml = null;
		String trancode = (String) title.get("TranCode");
		try {
			if ("2015011".equals(trancode)) {
				Map<String,String> map_deviceRequest = explainDeviceRequestXml(deviceRequestXml);
				Map<String,String> response_map = HandlerHis4005.handler4005(map_deviceRequest);
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
				responseXml.append("<TranCode>2015011</TranCode>");
				responseXml.append("<RspCode>0</RspCode>");
				responseXml.append("<RspMsg>查询成功</RspMsg>");
			responseXml.append("</Head>");
			responseXml.append("<Data>");
				responseXml.append("<TranFlow>").append(response_map.get("TranFlow")).append("</TranFlow>");
				responseXml.append("<RegisterNo>").append(response_map.get("RegisterNo")).append("</RegisterNo>");
				responseXml.append("<AsRowid>").append(response_map.get("AsRowid")).append("</AsRowid>");
				responseXml.append("<JZTime>").append(response_map.get("JZTime")).append("</JZTime>");
				responseXml.append("<JZNo>").append(response_map.get("JZNo")).append("</JZNo>");
				responseXml.append("<Type>").append(response_map.get("Type")).append("</Type>");
				responseXml.append("<PatName>").append(response_map.get("PatName")).append("</PatName>");
				responseXml.append("<MZH>").append(response_map.get("MZH")).append("</MZH>");
				responseXml.append("<FeesType>").append(response_map.get("FeesType")).append("</FeesType>");
				responseXml.append("<FeesItem>").append(response_map.get("FeesItem")).append("</FeesItem>");
				responseXml.append("<DeptName>").append(response_map.get("DeptName")).append("</DeptName>");
				responseXml.append("<Loc>").append(response_map.get("Loc")).append("</Loc>");
				responseXml.append("<DoctorName>").append(response_map.get("DoctorName")).append("</DoctorName>");
				responseXml.append("<SessionType>").append(response_map.get("SessionType")).append("</SessionType>");
				responseXml.append("<RegTime>").append(response_map.get("RegTime")).append("</RegTime>");
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
