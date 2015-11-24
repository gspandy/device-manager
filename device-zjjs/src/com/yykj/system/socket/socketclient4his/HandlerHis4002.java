package com.yykj.system.socket.socketclient4his;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Info4002;

/**
 * 处理HIS4002 
 * 查询可挂号科室
 * @author QinShuJin
 * 
 */
public class HandlerHis4002 {
	
	/**
	 * 处理HIS 4002 查询挂号类别
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static Info4002 handler4002(Map<String,String> requestMap) throws Exception{
		//1.创建4002 请求
		String requestXml4002 = create4002Request(requestMap);
		//2.连接HIS Socket服务
		String his4002ResponseXml = HisSocketClient.ConnHisSocket(requestXml4002);
		//3.解析4002返回值
		Info4002 info = explain4002Response(his4002ResponseXml);
		return info;
	}
	
	
	/**
	 * 创建HIS 4002 请求XML
	 * @param requestXml
	 * @return
	 * @throws DocumentException 
	 */
	public static String create4002Request(Map<String,String> requestMap) throws Exception{
		StringBuilder request4002Xml = new StringBuilder();
		request4002Xml.append("<Request>");
			request4002Xml.append("<TransCode>4002</TransCode>");
			request4002Xml.append("<RegType>").append(requestMap.get("RegType")).append("</RegType>");
			request4002Xml.append("<Day>").append(requestMap.get("Day")).append("</Day>");
			request4002Xml.append("<RigsterType>").append(requestMap.get("RigsterType")).append("</RigsterType>");
			request4002Xml.append("<Start>").append(requestMap.get("Start")).append("</Start>");
			request4002Xml.append("<RequestQty>").append(requestMap.get("RequestQty")).append("</RequestQty>");
			request4002Xml.append("<UserId>").append(requestMap.get("UserId")).append("</UserId>");
		request4002Xml.append("</Request>");
		return request4002Xml.toString();
	}
	
	
	/**
	 * 解析HIS 4002返回值
	 * 
	 * @param Xml
	 * @throws Exception 
	 * @throws DocumentException 
	 */
	private static Info4002 explain4002Response(String his4002ResponseXml) throws Exception{
		Document docs = DocumentHelper.parseText(his4002ResponseXml);
		Element root = docs.getRootElement();
		
		//1.解析Response标签下的一节点
		Info4002 info = new Info4002();
		info.setTransCode(root.elementText("TransCode"));
		info.setResultCode(root.elementText("ResultCode"));
		info.setErrorMsg(root.elementText("ErrorMsg"));
		info.setCount(root.elementText("Count"));
		info.setReturnQty(root.elementText("ReturnQty"));
		
		//解析List节点下的Item节点
		List<Dept4002> depts = new ArrayList<Dept4002>();
		Element list_elem = root.element("List"); // 得到list节点
		List<Element> items = list_elem.elements();
		for (Element item : items) {
			Dept4002 dept  = new Dept4002();
			dept.setDeptId(item.elementText("DeptId"));
			dept.setDeptName(item.elementText("DeptName"));
			
			depts.add(dept);
		}
		info.setDepts(depts);
		return info;
	}
}
