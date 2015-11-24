package com.yykj.system.socket.socketclient4bank;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.Common;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.SerialNumberUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.services.IBusiLogServie;

/**
 * 处理银行92001交易
 * 银行支付交易
 * @author QinShuJin
 * 2015年10月30日 下午2:59:51
 */

@SuppressWarnings("all")
public class HandlerBank920001 {
	
	/**
	 * 处理92001交易
	 * @param requestInfo
	 * @param billInfo2015004
	 * @param deviceRequestXml
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Map<String,String>> handler920001(RequestInfo2015004 requestInfo,BillInfo2015004 billInfo2015004, String deviceRequestXml)throws Exception {
		//1.创建92001 交易请求
		String requestXml_920001 = create920001RequestXml(requestInfo,billInfo2015004);
		
		//2.连接银行Socket服务
		String bank920001ResponseXml = BankSocketClient.ConnectBank(requestXml_920001);
		
		//3.写银行交易日志
		IBusiLogServie busiLogServie = (IBusiLogServie) SpringContextUtil.getBean("busiLogServie", IBusiLogServie.class);
		busiLogServie.saveBusiDealLog(requestInfo, billInfo2015004,requestXml_920001, bank920001ResponseXml, deviceRequestXml);
		
		//4.解析银行交易应答
		Map<String,Map<String,String>> map_920001Response = explain920001Response(bank920001ResponseXml);
		return map_920001Response;
	}

	
	/**
	 * 创建银行交易请求
	 * @param requestInfo
	 * @param billInfo2015004
	 * @return
	 * @throws Exception
	 */
	public static String create920001RequestXml(RequestInfo2015004 requestInfo,BillInfo2015004 billInfo2015004) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			DecimalFormat df = new DecimalFormat("######0.00");
			sb.append("<msg>");
			sb.append("<pub>");
			sb.append("<TranCode>920001</TranCode>");
			sb.append("<ChannelSeq>").append(SerialNumberUtil.getInstance().generaterNextNumber(null)).append("</ChannelSeq>");
			sb.append("<ChannelDate>").append(Common.getCurrentDate().replace("-", "")).append("</ChannelDate>");
			sb.append("<ChannelTime>").append(Common.getCurrentTime().replace(":", "")).append("</ChannelTime>");
			sb.append("</pub>");
			sb.append("<data>");
			sb.append("<yhzh>").append(requestInfo.getYhzh()).append("</yhzh>");
			sb.append("<yhmc>").append(requestInfo.getYhmc()).append("</yhmc>");
			if (PropertiesUtil.getInstance().get("IS_TEST").equals("Y")){ //测试环境
				sb.append("<jyje>0.01</jyje>");
			}else{
				sb.append("<jyje>").append(df.format(billInfo2015004.getActualMoney())).append("</jyje>");
			}
			sb.append("<ywbh>").append(PropertiesUtil.getInstance().get("HSP_CODE")).append("</ywbh>");
			String RegisterNo = requestInfo.getGhId();
			if ("".equals(RegisterNo) || RegisterNo == null || "" == RegisterNo){
				sb.append("<yhh1>").append(PropertiesUtil.getInstance().get("REGISTERNO")).append("</yhh1>");
			}else{
				sb.append("<yhh1>").append(RegisterNo).append("</yhh1>");
			}
			sb.append("</data>");
			sb.append("</msg>");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			throw new BusinessException("创建银行920001支付请求时失败.");
		}
		return sb.toString();
	}

	
	/**
	 * 解析92001返回值
	 * @param bank920001ResponseXml
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Map<String, String>> explain920001Response(String bank920001ResponseXml) throws Exception {
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
