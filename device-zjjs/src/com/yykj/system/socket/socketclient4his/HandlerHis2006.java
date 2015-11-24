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
 * 处理HIS2006 获取病人信息 
 * @author QinShuJin
 * 2015年10月10日 上午10:02:11
 */
public class HandlerHis2006 {
	
	/**
	 * 处理HIS 2006 获取病人信息 
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Map<String, String> handler2006(Map<String,String> map_deviceRequest) throws Exception{
		//1.创建2006 请求
		String requestXml2006 = create2006Request(map_deviceRequest);
		//2.连接HIS Socket服务
		String his2006ResponseXml = HisSocketClient.ConnHisSocket(requestXml2006);
		//3.解析2006返回值
		Map<String, String> map_2006 = explain2006Response(his2006ResponseXml);
		return map_2006;
	}
	
	
	/**
	 * 创建HIS 2006 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create2006Request(Map<String,String> map_deviceRequest) throws Exception{
		StringBuilder request2006Xml = new StringBuilder();
		request2006Xml.append("<Request>");
		request2006Xml.append("<TransCode>2006</TransCode>");
		request2006Xml.append("<CardTypeID>"+PropertiesUtil.getInstance().get("CARD_TYPE")+"</CardTypeID>");
		request2006Xml.append("<CardNo>").append(map_deviceRequest.get("IdCard")).append("</CardNo>");
		request2006Xml.append("<UserId>").append(map_deviceRequest.get("SerialNum")).append("</UserId>");
		request2006Xml.append("</Request>");
		return request2006Xml.toString();
	}
	
	
	/**
	 * 解析HIS2006返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static Map<String, String> explain2006Response(String his2006ResponseXml) throws Exception{
		Map<String, String> user = new HashMap<String, String>();
		try {
			Document docs = DocumentHelper.parseText(his2006ResponseXml);
			Element root = docs.getRootElement();
			List<Element> userinfos = root.elements();
			for (Element info : userinfos) {
				user.put(info.getName(), info.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析HIS2006应答值失改");
		}
		return user;
	}
}
