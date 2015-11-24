package com.yykj.system.socket.socketclient4his;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * 处理HIS40055
 * 提交挂号
 * @author QinShuJin
 * 
 */
public class HandlerHis4005 {
	
	/**
	 * 处理HIS 4005 查询挂号类别
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String,String> handler4005(Map<String,String> requestMap) throws Exception{
		//1.创建4005 请求
		String requestXml4005 = create4005Request(requestMap);
		//2.连接HIS Socket服务
		String his4005ResponseXml = HisSocketClient.ConnHisSocket(requestXml4005);
		//3.解析4005返回值
		Map<String,String> result_map = explain4005Response(his4005ResponseXml);
		return result_map;
	}
	
	
	/**
	 * 创建HIS 4005 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create4005Request(Map<String,String> requestMap) throws Exception{
		StringBuilder request4005Xml = new StringBuilder();
		request4005Xml.append("<Request>");
			request4005Xml.append("<TransCode>4005</TransCode>");
			request4005Xml.append("<RegType>").append(requestMap.get("RegType")).append("</RegType>");
			request4005Xml.append("<Day>").append(requestMap.get("Day")).append("</Day>");
			request4005Xml.append("<PatientID>").append(requestMap.get("PatientID")).append("</PatientID>");
			request4005Xml.append("<CardTypeID>").append(requestMap.get("CardTypeID")).append("</CardTypeID>");
			request4005Xml.append("<CardNo>").append(requestMap.get("CardNo")).append("</CardNo>");
			request4005Xml.append("<Password>").append(requestMap.get("Password")).append("</Password>");
			request4005Xml.append("<AsRowid>").append(requestMap.get("AsRowid")).append("</AsRowid>");
			request4005Xml.append("<TimeValue>").append(requestMap.get("TimeValue")).append("</TimeValue>");
			request4005Xml.append("<List>");
				request4005Xml.append("<Item>");
					request4005Xml.append("<PayType>").append(requestMap.get("PayType")).append("</PayType>");
					request4005Xml.append("<PayMode>").append(requestMap.get("PayMode")).append("</PayMode>");
					request4005Xml.append("<PayAmt>").append(requestMap.get("PayAmt")).append("</PayAmt>");
					request4005Xml.append("<PayNo>").append(requestMap.get("PayNo")).append("</PayNo>");
					request4005Xml.append("<PayCardNo>").append(requestMap.get("PayCardNo")).append("</PayCardNo>");
					request4005Xml.append("<PayNote>").append(requestMap.get("PayNote")).append("</PayNote>");
					request4005Xml.append("<ExpandList>");
						request4005Xml.append("<ItemName></ItemName>");
						request4005Xml.append("<ItemValue></ItemValue>");
					request4005Xml.append("</ExpandList>");
				request4005Xml.append("</Item>");
			request4005Xml.append("</List>");
		request4005Xml.append("</Request>");
		return request4005Xml.toString();
	}
	
	
	/**
	 * 解析HIS 4005返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static Map<String,String> explain4005Response(String his4005ResponseXml) throws Exception{
		Map<String,String> result_map = new HashMap<String,String>();
		Document docs = DocumentHelper.parseText(his4005ResponseXml);
		Element root = docs.getRootElement();
		List<Element> eles=  root.elements();
		for (Element ele : eles) {
			result_map.put(ele.getName(), ele.getText());
		}
		return result_map;
	}
}
