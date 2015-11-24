package com.yykj.system.handlerequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
//import sun.util.logging.resources.logging;
import com.yykj.base.util.LogUtil;

/**
 * 业务处理入口
 * 
 * @author QinShuJin 
 * 2015年7月10日 下午2:32:07
 */
@SuppressWarnings("all")
public class BusiHandler {
	private static Logger log = LogUtil.getInstance().getLogger(BusiHandler.class);

	public static String handler(String requestXml){
    	String responseXml = null;
		//1.解析请求XML
		Map<String, String> title = null;
		try {
			title = explainXml(requestXml);
		} catch (Exception e) {
			e.printStackTrace();
			responseXml = HandlerUtil.getErrorXml("000000", e.getMessage());
			return responseXml;
		}
		
		//2.处理请求
		Handler handler1 = new Handler2015001();
		Handler handler2 = new Handler2015002();
		Handler handler3 = new Handler2015003();
		Handler handler4 = new Handler2015004();
		Handler handler5 = new Handler2015005();
		Handler handler6 = new Handler2015006();
		Handler handler7 = new Handler2015007();
		Handler handler8 = new Handler2015008();
		Handler handler9 = new Handler2015009();
		Handler handler10 = new Handler2015010();
		Handler handler11 = new Handler2015011();
		Handler handler12 = new Handler2015012();
		
		Handler handler31 = new Handler3015001();
		Handler handler32 = new Handler3015002();
		Handler handler33 = new Handler3015003();
		Handler handler34 = new Handler3015004();
		
		Handler handlerend = new HandlerEnd();
		
		handler1.setSuccessor(handler2);
		handler2.setSuccessor(handler3);
		handler3.setSuccessor(handler4);
		handler4.setSuccessor(handler5);
		handler5.setSuccessor(handler6);
		handler6.setSuccessor(handler7);
		handler7.setSuccessor(handler8);
		handler8.setSuccessor(handler9);
		handler9.setSuccessor(handler10);
		handler10.setSuccessor(handler11);
		handler11.setSuccessor(handler12);
		
		
		handler12.setSuccessor(handler31);
		handler31.setSuccessor(handler32);
		handler32.setSuccessor(handler33);
		handler33.setSuccessor(handler34);
		
		handler34.setSuccessor(handlerend);
		responseXml = handler1.handleRequest(title,requestXml);
        return responseXml;
    }

	/**
	 * 解析请求XML的文档头
	 * 
	 * @param requestXml
	 * @throws DocumentException
	 */
	public static Map<String, String> explainXml(String requestXml)throws Exception{
		Map<String, String> title_map = new HashMap<String, String>();
		try {
			Document docs = DocumentHelper.parseText(requestXml);
			Element root = docs.getRootElement();
			// 1.解析头
			List<Element> head_eles = root.selectNodes("/Root/Head");
			for (Element head : head_eles) {
				List<Element> headInfos = head.elements();
				for (Element element : headInfos) {
					title_map.put(element.getName(), element.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("请求文档不合规");
		}
		return title_map;
	}
}
