package com.yykj.system.socket.socketclient4his;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.system.entity.hisentity.Doctor4003;
import com.yykj.system.entity.hisentity.Info4003;

/**
 * 处理HIS4003 
 * 查询可挂号科室
 * @author QinShuJin
 * 
 */
public class HandlerHis4003 {
	
	/**
	 * 处理HIS 4003 查询挂号类别
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Info4003 handler4003(Map<String,String> requestMap) throws Exception{
		//1.创建4003 请求
		String requestXml4003 = create4003Request(requestMap);
		//2.连接HIS Socket服务
		String his4003ResponseXml = HisSocketClient.ConnHisSocket(requestXml4003);
		//3.解析4003返回值
		Info4003 info = explain4003Response(his4003ResponseXml);
		return info;
	}
	
	
	/**
	 * 创建HIS 4003 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create4003Request(Map<String,String> requestMap) throws Exception{
		StringBuilder request4003Xml = new StringBuilder();
		request4003Xml.append("<Request>");
			request4003Xml.append("<TransCode>4003</TransCode>");
			request4003Xml.append("<RegType>").append(requestMap.get("RegType")).append("</RegType>");
			request4003Xml.append("<Day>").append(requestMap.get("Day")).append("</Day>");
			request4003Xml.append("<RigsterType>").append(requestMap.get("RigsterType")).append("</RigsterType>");
			request4003Xml.append("<DeptID>").append(requestMap.get("DeptID")).append("</DeptID>");
			request4003Xml.append("<Start>").append(requestMap.get("Start")).append("</Start>");
			request4003Xml.append("<RequestQty>").append(requestMap.get("RequestQty")).append("</RequestQty>");
			request4003Xml.append("<UserId>").append(requestMap.get("UserId")).append("</UserId>");
		request4003Xml.append("</Request>");
		return request4003Xml.toString();
	}
	
	
	/**
	 * 解析HIS 4003返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static Info4003 explain4003Response(String his4003ResponseXml) throws Exception{
		Document docs = DocumentHelper.parseText(his4003ResponseXml);
		Element root = docs.getRootElement();
		
		//1.解析Response标签下的一节点
		Info4003 info = new Info4003();
		info.setTransCode(root.elementText("TransCode"));
		info.setResultCode(root.elementText("ResultCode"));
		info.setErrorMsg(root.elementText("ErrorMsg"));
		info.setCount(root.elementText("Count"));
		info.setReturnQty(root.elementText("ReturnQty"));
		
		//解析List节点下的Item节点
		List<Doctor4003> doctors = new ArrayList<Doctor4003>();
		Element list_elem = root.element("List"); // 得到list节点
		List<Element> items = list_elem.elements();
		for (Element item : items) {
			Doctor4003 doctor  = new Doctor4003();
			doctor.setAsRowid(item.elementText("AsRowid"));
			doctor.setMarkId(item.elementText("MarkId"));
			doctor.setMarkDesc(item.elementText("MarkDesc"));
			doctor.setSessionType(item.elementText("SessionType"));
			doctor.sethBTime(item.elementText("HBTime"));
			doctor.setRegCount(item.elementText("RegCount"));
			doctor.setPrice(item.elementText("Price"));
			doctor.setIsTime(item.elementText("IsTime"));
			doctors.add(doctor);
		}
		info.setDoctors(doctors);
		return info;
	}
}
