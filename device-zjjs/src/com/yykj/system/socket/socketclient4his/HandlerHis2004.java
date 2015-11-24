package com.yykj.system.socket.socketclient4his;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PropertiesUtil;

/**
 * 处理HIS2004 
 * 签约
 * @author QinShuJin
 * 2015年10月10日 上午10:02:11
 */
public class HandlerHis2004 {
	
	/**
	 * 处理HIS 2004 获取病人信息 
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> handler2004(Map<String,String> map_deviceRequest) throws Exception{
		//1.创建2004 请求
		String requestXml2004 = create2004Request(map_deviceRequest);
		//2.连接HIS Socket服务
		String his2004ResponseXml = HisSocketClient.ConnHisSocket(requestXml2004);
		//3.解析2004返回值
		Map<String, String> map_2004 = explain2004Response(his2004ResponseXml);
		return map_2004;
	}
	
	
	/**
	 * 创建HIS 2004 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create2004Request(Map<String,String> map_deviceRequest) throws Exception{
		StringBuilder request2004Xml = new StringBuilder();
		request2004Xml.append("<Request>");
		request2004Xml.append("<TransCode>2004</TransCode>");
		request2004Xml.append("<CardTypeID>").append(map_deviceRequest.get("CardTypeID")).append("</CardTypeID>");
		request2004Xml.append("<CardNo>").append(map_deviceRequest.get("CardNo")).append("</CardNo>");
		request2004Xml.append("<PatientName>").append(map_deviceRequest.get("PatientName")).append("</PatientName>");
		request2004Xml.append("<Sex>").append(map_deviceRequest.get("Sex")).append("</Sex>");
		request2004Xml.append("<Birthday>").append(map_deviceRequest.get("Birthday")).append("</Birthday>");
		request2004Xml.append("<Age>").append(map_deviceRequest.get("Age")).append("</Age>");
		request2004Xml.append("<IDCardNo>").append(map_deviceRequest.get("IDCardNo")).append("</IDCardNo>");
		request2004Xml.append("<Amt>").append(map_deviceRequest.get("Amt")).append("</Amt>");
		request2004Xml.append("<Tel>").append(map_deviceRequest.get("Tel")).append("</Tel>");
		request2004Xml.append("<UserId>").append(map_deviceRequest.get("UserId")).append("</UserId>");
		request2004Xml.append("<SerNo>").append(map_deviceRequest.get("SerNo")).append("</SerNo>");
		request2004Xml.append("<Password>").append(map_deviceRequest.get("Password")).append("</Password>");
		request2004Xml.append("</Request>");
		return request2004Xml.toString();
	}
	
	
	/**
	 * 解析HIS2004返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static Map<String, String> explain2004Response(String his2004ResponseXml) throws Exception{
		Map<String, String> user = new HashMap<String, String>();
		try {
			Document docs = DocumentHelper.parseText(his2004ResponseXml);
			Element root = docs.getRootElement();
			List<Element> userinfos = root.elements();
			for (Element info : userinfos) {
				user.put(info.getName(), info.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析HIS2004应答值失改");
		}
		return user;
	}
}
