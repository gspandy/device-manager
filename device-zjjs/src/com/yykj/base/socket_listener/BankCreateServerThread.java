package com.yykj.base.socket_listener;

import java.io.IOException;
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

public class BankCreateServerThread extends Thread {
	private static String ENCODING=PropertiesUtil.getInstance().get("BANK_ENCODEING");
	Socket socket = null;
	public BankCreateServerThread(Socket s) {
		this.socket = s;
	}


	public void run() {
		try {                                                                                         
			InputStream ips = socket.getInputStream();
			OutputStream ops = socket.getOutputStream();
			byte[] len = SocketUtil.readLenContent(ips, 8);
			int lenth = Integer.valueOf( new String(len));
			byte [] intData = SocketUtil.readLenContent(ips, lenth);
			String str = new String(intData,ENCODING);
			System.out.println("银行主机收到信息：" + str);
			
			Map<String,Map> map =analysisZfRquest(str);
			Map<String,String> map_title = map.get("pub");
			Map<String,String> tf_title = map.get("head");
			
			String jylx="";
			String jlx1 ="";
			if(map_title!=null){
				jylx = map_title.get("TranCode"); //交易类型
			}else if(tf_title!=null){
				jlx1 = tf_title.get("tr_code"); //交易类型
			}
			byte [] outData = createResponseXml(jylx,jlx1);
			//System.out.println("主机返回值："+new String(outData,ENCODING));
			ops.write(outData);
			ops.flush();
			ops.close();
			ips.close();
		} catch (IOException e) {
		}
	}
	
	
	/**
     * 创建返回xml
     * @param jylx
     */
    public byte [] createResponseXml(String jylx,String jlx1){
    	String response_xml="";
    	byte [] byte_out = null;
    	if("920035".equals(jylx)){ //余额查询
			response_xml = ReadXmlFileUtil.getXml("920035_response.xml","com/yykj/system/handlerequest/bankxml/");
		}else if("920001".equals(jylx)){ //支付
			response_xml = ReadXmlFileUtil.getXml("920001_response.xml","com/yykj/system/handlerequest/bankxml/");
		}else if("300001".equals(jlx1)){//退费
			response_xml = ReadXmlFileUtil.getXml("300001_response.xml","com/yykj/system/handlerequest/bankxml/");
		}else if("920065".equals(jylx)){//冲销
			response_xml = ReadXmlFileUtil.getXml("920065_response.xml","com/yykj/system/handlerequest/bankxml/");
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
     * 解析请求XML的文档头
     * 用于支付和余额查询
     * @param Xml
     */
    public  Map<String,Map>  analysisZfRquest(String requestXml){
    	
    	Map<String,Map> result_map = new HashMap<String,Map>();
    	try {
			Document docs = DocumentHelper.parseText(requestXml);
			Element root = docs.getRootElement();
			//所有二级节点
			List<Element> level2s = root.elements();
			for (Element level2 : level2s) {
				List<Element> list = level2.elements();
				Map<String,String> result_level2 = new HashMap<String,String>();
				for (Element element : list) {
					result_level2.put(element.getName(), element.getText());
				}
				result_map.put(level2.getName(), result_level2);
			}
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return result_map;
    }
}