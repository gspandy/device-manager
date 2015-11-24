package com.yykj.system.socket.socketclient4his;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;

/**
 * 处理HIS4001 查询挂号类别
 * @author QinShuJin
 * 
 */
public class HandlerHis4001 {
	
	/**
	 * 处理HIS 4001 查询挂号类别
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static List<String> handler4001() throws Exception{
		//1.创建4001 请求
		String requestXml4001 = create4001Request();
		//2.连接HIS Socket服务
		String his4001ResponseXml = HisSocketClient.ConnHisSocket(requestXml4001);
		//3.解析4001返回值
		List<String> types = explain4001Response(his4001ResponseXml);
		return types;
	}
	
	
	/**
	 * 创建HIS 4001 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create4001Request() throws Exception{
		StringBuilder request4001Xml = new StringBuilder();
		request4001Xml.append("<Request>");
		request4001Xml.append("<TransCode>4001</TransCode>");
		request4001Xml.append("</Request>");
		return request4001Xml.toString();
	}
	
	
	/**
	 * 解析HIS 4001返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static List<String> explain4001Response(String his4001ResponseXml) throws Exception{
		List<String> types = new ArrayList<String>();
		
		//解析List节点下的Item节点
		try {
			Document docs = DocumentHelper.parseText(his4001ResponseXml);
			Element root = docs.getRootElement();
			Element list_elem = root.element("List"); // 得到List节点
			List<Element> items = list_elem.elements();
			
			for (Element item : items) {
				String type = item.elementText("CardTypeName");
				types.add(type);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析HIS 4001应答值失改");
		}
		return types;
	}
}
