package com.yykj.base.socket_listener;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.ReadXmlFileUtil;
import com.yykj.base.util.SocketUtil;

public class HisCreateServerThread implements Runnable  {
	Socket socket = null;
	private static String ENCODING=PropertiesUtil.getInstance().get("HIS_ENCODING");
	public HisCreateServerThread(Socket s) {
		this.socket = s;
	}

	
	public void run() {
		try {
			InputStream ips = socket.getInputStream();
			OutputStream ops = socket.getOutputStream();
			byte[] bt = SocketUtil.readContent(ips);
			String str = new String(bt);
			System.out.println("HIS主机收到信息：" + str);
			
			//解析消息
			Map<String,String> map =analysisZlRequest(str);
			String jylx = map.get("TransCode"); //交易类型
			
			byte [] outData = createResponseXml(jylx);
			
			//String restr = "你好，主机已经收到信息！";
			ops.write(outData);
			ops.flush();
			ops.close();
			ips.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
     * 创建返回xml
     * @param jylx
     */
    public byte [] createResponseXml(String jylx){
    	String response_xml="";
    	byte [] byte_out = null;
    	if ("2002".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("2002_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("2004".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("2004_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("2006".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("2006_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("5001".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("5001_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("5002".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("5002_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("5003".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("5003_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("4001".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("4001_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("4002".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("4002_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("4003".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("4003_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("4005".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("4005_response.xml","com/yykj/system/handlerequest/hisxml/");
		}else if ("4009".equals(jylx)){
			response_xml = ReadXmlFileUtil.getXml("4009_response.xml","com/yykj/system/handlerequest/hisxml/");
		}
    	try {
			byte [] outDate = response_xml.getBytes(ENCODING);
			byte_out=SocketUtil.addLenHead(outDate, 8,ENCODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return byte_out;
    }

	
	
	/**
     * 解析HIS所有请求文档
     * 用于中联HIS支付
     * @param Xml
     */
    public  Map<String,String> analysisZlRequest(String requestXml){
    	Map<String,String> requestInfo = new HashMap<String,String>();
    	try {
			Document docs = DocumentHelper.parseText(requestXml);
			Element root = docs.getRootElement();
			List<Element> infos = root.elements();  
			for (Element info : infos) {
				requestInfo.put(info.getName(), info.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return requestInfo;
    }
}