package com.yykj.system.handlerequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.sun.accessibility.internal.resources.accessibility;
import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.ReadXmlFileUtil;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.BilllDetail2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.socket.socketclient4his.HandlerHis2006;

/**
 * 处理2015006交易 
 * 获取病人基本信息
 * @author QinShuJin
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015006 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015006.class);
	@Override
	public String handleRequest(Map title, String deviceRequestXml){
        String responseXml = null;
        String trancode = (String)title.get("TranCode");
        try {
			if("2015006".equals(trancode)){
			    Map map_deviceRequest = explainDeviceRequestXml(deviceRequestXml);
			    Map map_hisResponse_2006 = HandlerHis2006.handler2006(map_deviceRequest);
			    StringBuilder response_builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
			    response_builder.append("<Root>")
						        	.append("<Head>")
							        	.append("<TranCode>2015006</TranCode>")
							        	.append("<RspCode>0</RspCode>")
							        	.append("<RspMsg>").append((String)map_hisResponse_2006.get("ErrorMsg"))
							        	.append("</RspMsg>")
						        	.append("</Head>")
						        	.append("<Data>")
							        	.append("<PatientId>").append((String)map_hisResponse_2006.get("PatientID")).append("</PatientId>")
							        	.append("<PatName>").append((String)map_hisResponse_2006.get("PatName")).append("</PatName>")
							        	.append("<PatSex>").append((String)map_hisResponse_2006.get("PatSex")).append("</PatSex>")
							        	.append("<IDCard>").append((String)map_hisResponse_2006.get("IDCard"))
							        	.append("</IDCard>")
							        	.append("<AccBalance>").append((String)map_hisResponse_2006.get("AccBalance")).append("</AccBalance>")
							        	.append("<Tel>").append((String)map_hisResponse_2006.get("Tel")).append("</Tel>")
						        	.append("</Data>")
						    	.append("</Root>");
			    responseXml = response_builder.toString();
			}else {
				responseXml = getSuccessor().handleRequest(title,deviceRequestXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseXml = HandlerUtil.getErrorXml(trancode, e.getMessage());
		}
        return responseXml.toString();
    }

	/**
	 * 解析自助设备请求XML
	 * @param requestXML
	 * @return
	 */
	private static Map<String,String> explainDeviceRequestXml(String requestXML) throws Exception{
		Map<String,String> resultMap = new HashMap<String,String>();
		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();
			List<Element> data_eles = root.selectNodes("/Root/Data");
			for (Element head : data_eles) {
				List<Element> headInfos = head.elements();
				for (Element element : headInfos) {
					resultMap.put(element.getName(), element.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析请求2015006请求XML异常");
		}
		return resultMap;
	}
}
