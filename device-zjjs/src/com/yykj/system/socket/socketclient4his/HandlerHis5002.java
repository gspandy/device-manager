package com.yykj.system.socket.socketclient4his;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import java.util.*;
import org.dom4j.*;

public class HandlerHis5002 {
	public static boolean handler5002(RequestInfo2015004 requestInfo, 
			BillInfo2015004 billInfo2015004, String channelSeq) throws Exception {
		boolean flag = true;
		try {
			//1.创建自助缴费请求
			String requestXmls_5002 = create5002RequestXml(requestInfo,billInfo2015004, channelSeq);
			
			//2.连接HIS Socket服务器
			String his5002ResponseXml = HisSocketClient.ConnHisSocket(requestXmls_5002);
			
			//3.解析请求
			Map<String,String> map_5002Response = explain5002Response(his5002ResponseXml);
			if ("1".equals(map_5002Response.get("ResultCode"))){
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	public static String create5002RequestXml(RequestInfo2015004 requestInfo,
			BillInfo2015004 billInfo2015004, String channelSeq) throws Exception {
		StringBuffer sb = new StringBuffer("");
		sb.append("<Request>");
			sb.append("<TransCode>5002</TransCode>");
			sb.append("<CardTypeID>").append(requestInfo.getCardTypeID()).append("</CardTypeID>");
			sb.append("<CardNo>").append(requestInfo.getIdCard()).append("</CardNo>");
			sb.append("<Password></Password>");
			sb.append("<PatientID>").append(requestInfo.getPatientId()).append("</PatientID>");
			sb.append("<UserId>").append(requestInfo.getUserId()).append("</UserId>");
			sb.append("<ReceiptNo>").append(billInfo2015004.getReceiptNo()).append("</ReceiptNo>");
			sb.append("<List>");
				sb.append("<Item>");
					sb.append("<PayType>3</PayType>");
					sb.append("<PayMode>").append(PropertiesUtil.getInstance().get("ZF_TYPE")).append("</PayMode>");
					sb.append("<PayAmt>").append(billInfo2015004.getActualMoney()).append("</PayAmt>");
					sb.append("<PayNo>").append(channelSeq).append("</PayNo>");
					sb.append("<PayCardNo>").append(requestInfo.getYhzh()).append("</PayCardNo>");
					sb.append("<PayNote>\u8BCA\u95F4\u7ED3\u7B97</PayNote>");
					sb.append("<ExpandList>");
						sb.append("<ItemName></ItemName>");
						sb.append("<ItemValue></ItemValue>");
					sb.append("</ExpandList>");
				sb.append("</Item>");
			sb.append("</List>");
		sb.append("</Request>");
		return sb.toString();
	}

	private static Map<String,String>  explain5002Response(String his2006ResponseXml)throws Exception {
		Map<String,String> infos = new HashMap<String,String>();
		try {
			Document docs = DocumentHelper.parseText(his2006ResponseXml);
			Element root = docs.getRootElement();
			List<Element> ele_infos = root.elements();
			for (Element element : ele_infos) {
				infos .put(element.getName(), element.getText());
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析HIS 5002应答值失败.");
		}
		return infos;
	}
}