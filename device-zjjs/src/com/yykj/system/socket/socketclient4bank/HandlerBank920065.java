package com.yykj.system.socket.socketclient4bank;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.*;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.services.IBusiLogServie;

import java.util.*;

import org.dom4j.*;


/**
 * 处理银行920065交易
 * 银行冲销
 * @author QinShuJin
 * 2015年10月30日 下午3:24:15
 */
public class HandlerBank920065{
    public static Map<String,Map<String,String>> handler920065(Map<String,Map<String,String>> map_920001Response, 
    		RequestInfo2015004 requestInfo, BillInfo2015004 billInfo2015004, String deviceRequestXml)throws Exception{
    	//1.创建920065冲销请求
        String requestXml_920065 = create920065RequestXml(map_920001Response, requestInfo);
        
        //2.连接银行Socket服务器
        String bank920065ResponseXml = BankSocketClient.ConnectBank(requestXml_920065);
        
        //3.写冲销日志
        IBusiLogServie busiLogServie = (IBusiLogServie)SpringContextUtil.getBean("busiLogServie", IBusiLogServie.class);
        busiLogServie.saveBusiCxLog(requestInfo, billInfo2015004, requestXml_920065, bank920065ResponseXml, deviceRequestXml);
        
        //4.解析银行返回值
        Map<String,Map<String,String>> map_920065Response = explain920065Response(bank920065ResponseXml);
        return map_920065Response;
    }

    /**
     * 创建920065冲销请求
     * @param map_920001Response
     * @param requestInfo
     * @return
     * @throws Exception
     */
    public static String create920065RequestXml(Map<String,Map<String,String>> map_920001Response, 
    		RequestInfo2015004 requestInfo) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("<msg>");
	        sb.append("<pub>");
		        sb.append("<TranCode>920065</TranCode>");
		        sb.append("<ChannelSeq>").append(SerialNumberUtil.getInstance().generaterNextNumber(null)).append("</ChannelSeq>");
		        sb.append("<ChannelDate>").append(Common.getCurrentDate().replace("-", "")).append("</ChannelDate>");
		        sb.append("<ChannelTime>").append(Common.getCurrentTime().replace(":", "")).append("</ChannelTime>");
	        sb.append("</pub>");
	        sb.append("<data>");
		        sb.append("<yhzh>").append(requestInfo.getYhzh()).append("</yhzh>");
		        sb.append("<yhmc>").append(requestInfo.getYhmc()).append("</yhmc>");
		        sb.append("<ywbh>").append(PropertiesUtil.getInstance().get("HSP_CODE")).append("</ywbh>");
		        sb.append("<yhh1>").append(requestInfo.getGhId()).append("</yhh1>");
		        sb.append("<jyrq>").append(map_920001Response.get("data").get("jyrq")).append("</jyrq>");
		        sb.append("<jylsh>").append(map_920001Response.get("data").get("jylsh")).append("</jylsh>");
	        sb.append("</data>");
        sb.append("</msg>");
        return sb.toString();
    }

   /**
    * 解析920065返回值
    * @param bank920001ResponseXml
    * @return
    * @throws Exception
    */
    @SuppressWarnings("all")
	public static Map<String, Map<String, String>> explain920065Response(String bank920001ResponseXml) throws Exception {
		Map<String, Map<String, String>> result_map = new HashMap<String, Map<String, String>>();
		try {
			Document docs = DocumentHelper.parseText(bank920001ResponseXml);
			Element root = docs.getRootElement();
			List<Element> level2s = root.elements();
			for (Element level2 : level2s) {
				Map<String, String> result_level2 = new HashMap<String, String>();
				List<Element> ldevel2_els = level2.elements();
				for (Element element : ldevel2_els) {
					result_level2.put(element.getName(), element.getText());
				}
				result_map.put(level2.getName(), result_level2);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析920001银行返回值时失败");
		}
		return result_map;
	}
}
