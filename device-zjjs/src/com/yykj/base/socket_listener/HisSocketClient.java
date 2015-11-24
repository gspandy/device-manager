package com.yykj.base.socket_listener;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
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

@SuppressWarnings("all")
public class HisSocketClient {
    public static void main(String[] args) {
		try {
			
			//String requestXml = ReadXmlFileUtil.getXml("zl_wjf_request.xml");
			String requestXml = ReadXmlFileUtil.getXml("zl_zzjf_request.xml","com/yykj/system/handlerequest/hisxml/");
			String ip = PropertiesUtil.getInstance().get("HIS_IP");
			String port_ = PropertiesUtil.getInstance().get("HIS_PORT");
			int port = Integer.parseInt(port_.trim());
			//连接socket
			Socket socket = SocketUtil.getConnect(ip, port,10000);
			//发送请求
			String head = addHead(8,requestXml.getBytes().length);
			OutputStream os = socket.getOutputStream();
			byte [] out_byte= requestXml.getBytes();
			byte [] out_byte_1 = SocketUtil.addLenHead(out_byte, 0, "UTF-8");
			System.out.println("请求值XML："+new String(out_byte_1,"UTF-8"));
			os.write(out_byte_1);
			//获取返回值
			InputStream is = socket.getInputStream();
	        String bwt_len = new String(SocketUtil.readLenContent(is,8));
	        int bwbody_length = Integer.parseInt(bwt_len);
	        //String responseXml = new String(SocketUtil.readContent(is),"UTF-8");
	        String responseXml = new String(SocketUtil.readLenContent(is, bwbody_length),"UTF-8");
	        //关闭连接
	        SocketUtil.closeConnect(socket, os, is);
	        
	        System.out.println("返回值XML："+responseXml);
			
			
			
			//银行返回值
			//System.out.println("Server say : " + result);
			//解析返回值
            //Map<String,Map> response_map = new ZlHisSocketClient().analysisZfResponse(bw_body);
			
			//中联卡类型查询
			//List<Map<String,String>> list = new ZlHisSocketClient().analysisZlCradTypeResponse(responseXml);
			//System.out.println(list.get(2));
			//中联用户信息查询
			//Map<String,String> user = new ZlHisSocketClient().analysisZlUserifnoResponse(responseXml);
			//System.out.println(user);
			
			
			//中联未缴费记录查询
            //List<Map<String,String>> list = new ZlHisSocketClient().analysisZlWjfResponse(bw_body);
			//System.out.println(list.get(1));
			
            //中联自助缴费
			/*Map<String,String> map = new ZlHisSocketClient().analysisZlZzJfResponse(bw_body);
			System.out.println(map);*/
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
	}
    
    
    public static String addHead(int lenght,int count){
    	String str_count = String.valueOf(count);
    	int i = lenght-str_count.length();
    	String head="";
    	for (int j = 0; j < i; j++) {
    		head+="0"+str_count;
		}
    	return head;
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
    
    /**
     * 解析应答XML的文档头
     * 用于支付和余额查询
     * @param Xml
     */
    public  Map<String,Map> analysisZfResponse(String responseXml){
    	Map<String,Map> result_map = new HashMap<String,Map>();
    	try {
			Document docs = DocumentHelper.parseText(responseXml);
			Element root = docs.getRootElement();
			List<Element> level2s = root.elements();//所有二级节点
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
    
    
    /**
     * 解析中联HIS卡类型查询的文档
     * 用于中联HIS支付
     * @param Xml
     */
    public  List<Map<String,String>> analysisZlCradTypeResponse(String responseXml){
    	List<Map<String,String>> cards = new ArrayList<Map<String,String>>();
    	try {
			Document docs = DocumentHelper.parseText(responseXml);
			Element root = docs.getRootElement();
			String sucessOrErro = root.elementText("ResultCode"); //成功或失身败标志
			if("1".equals(sucessOrErro)){ //失败
				return null;
			}
			List<Element> cardTypes = root.element("List").elements();  //items
			for (Element cardtype : cardTypes) {
				List<Element> cardInfos = cardtype.elements();			//item下面所有元素
				Map<String,String> card = new HashMap<String,String>();
				for (Element cardinfo : cardInfos) {
					card.put(cardinfo.getName(), cardinfo.getText());
				}
				cards.add(card);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return cards;
    }
    
    
    /**
     * 解析中联HIS用户信息查询的应答文档
     * 用于中联HIS支付
     * @param Xml
     */
    public  Map<String,String> analysisZlUserifnoResponse(String responseXml){
    	Map<String,String> user = new HashMap<String,String>();
    	try {
			Document docs = DocumentHelper.parseText(responseXml);
			Element root = docs.getRootElement();
			String sucessOrErro = root.elementText("ResultCode"); //成功或失身败标志
			if("1".equals(sucessOrErro)){ //失败
				return null;
			}
			List<Element> userinfos = root.elements();  
			for (Element info : userinfos) {
				user.put(info.getName(), info.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return user;
    }
    
    
    /**
     * 解析中联HIS未缴费查询的应答文档
     * 用于中联HIS支付
     * @param Xml
     */
    public  List<Map<String,String>> analysisZlWjfResponse(String responseXml){
    	List<Map<String,String>> jls = new ArrayList<Map<String,String>>();
    	try {
			Document docs = DocumentHelper.parseText(responseXml);
			Element root = docs.getRootElement();
			String sucessOrErro = root.elementText("ResultCode"); //成功或失身败标志
			if("1".equals(sucessOrErro)){ //失败
				return null;
			}
			List<Element> wjfjls = root.element("List").elements();  //items
			for (Element wjfjl : wjfjls) {
				List<Element> wjfjlinfos = wjfjl.elements();			//item下面所有元素
				Map<String,String> card = new HashMap<String,String>();
				for (Element wjfjlinfo : wjfjlinfos) {
					card.put(wjfjlinfo.getName(), wjfjlinfo.getText());
				}
				jls.add(card);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return jls;
    }
    
    
    
    /**
     * 解析中联所有请求文档
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
    
    
    /**
     * 解析中联HIS用户信息查询的应答文档
     * 用于中联HIS支付
     * @param Xml
     */
    public  Map<String,String> analysisZlZzJfResponse(String responseXml){
    	Map<String,String> user = new HashMap<String,String>();
    	try {
			Document docs = DocumentHelper.parseText(responseXml);
			Element root = docs.getRootElement();
			String sucessOrErro = root.elementText("ResultCode"); //成功或失身败标志
			if("1".equals(sucessOrErro)){ //失败
				return null;
			}
			List<Element> userinfos = root.elements();  
			for (Element info : userinfos) {
				user.put(info.getName(), info.getText());
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
    	return user;
    }
}



