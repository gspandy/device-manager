package com.yykj.system.socket.socketclient4his;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.system.entity.hisentity.Billinfo5001;
import com.yykj.system.entity.hisentity.BilllDetail5001;

/**
 * 处理HIS5001 未缴费记录查询
 * @author QinShuJin
 * 2015年10月10日 上午10:02:11
 */
public class HandlerHis5001 {
	
	
	/**
	 * 处理HIS 5001交易
	 * @param userinfo
	 * @return
	 * @throws Exception 
	 */
	public static Billinfo5001 handler5001(Map<String,String> map_deviceRequest) throws Exception{
		//1.创建5001请求
		String requestXml_5001 = create5001RequestXml(map_deviceRequest);
		//2.连接HIS Socket服务
		String his5001ResponseXml = HisSocketClient.ConnHisSocket(requestXml_5001);
		//3.解析处理
		Billinfo5001 billinfo5001 = explain5001Response(his5001ResponseXml);
		return billinfo5001;
	}
	
	
	/**
	 * 创建HIS 5001请求
	 * @param map_hisResponse_2006
	 * @param deviceRequest_map
	 * @return
	 */
	public static String create5001RequestXml(Map<String,String> map_deviceRequest) throws Exception {
		StringBuilder RequestXml_5001 = new StringBuilder();
		RequestXml_5001.append("<Request>");
		RequestXml_5001.append("<TransCode>5001</TransCode>");
		RequestXml_5001.append("<PatientID>").append(map_deviceRequest.get("PatientId")).append("</PatientID>");
		RequestXml_5001.append("<ReceiptNo></ReceiptNo>");
		RequestXml_5001.append("<UserId>").append(map_deviceRequest.get("SerialNum")).append("</UserId>");
		RequestXml_5001.append("</Request>");
		return RequestXml_5001.toString();
	}
	
	/**
	 * 解析HIS 5001返回值
	 * @param his5001ResponseXml
	 * @return
	 * @throws Exception 
	 * @throws DocumentException
	 */
	private static Billinfo5001 explain5001Response(String his5001ResponseXml) throws Exception{
		Billinfo5001 billinfo5001 = new Billinfo5001();
		Document docs = DocumentHelper.parseText(his5001ResponseXml);
		Element root = docs.getRootElement();
		billinfo5001.setTransCode(root.elementText("TransCode"));
		billinfo5001.setResultCode(root.elementText("ResultCode"));
		billinfo5001.setErrorMsg(root.elementText("ErrorMsg"));
		if ("1".equals(PropertiesUtil.getInstance().get("REGISTERNO_MODE"))){
			billinfo5001.setRegisterNo(root.elementText("RegisterNo"));
		}
		if ("1".equals(billinfo5001.getResultCode())){
			return billinfo5001;
		}
		try {
			List<Element> Items = root.element("List").elements(); // List下所有item
			List<BilllDetail5001> billlDetail5001s = new ArrayList<BilllDetail5001>();
			for (Element item : Items) {
				BilllDetail5001 billlDetail5001 = new BilllDetail5001();
				billlDetail5001.setReceiptNo(item.elementText("ReceiptNo"));
				billlDetail5001.setReceiptTime(item.elementText("ReceiptTime"));
				billlDetail5001.setBillDept(item.elementText("BillDept"));
				billlDetail5001.setExecDept(item.elementText("ExecDept"));
				billlDetail5001.setDoctor(item.elementText("Doctor"));
				billlDetail5001.setFeesType(item.elementText("FeesType"));
				billlDetail5001.setFeesItem(item.elementText("FeesItem"));
				billlDetail5001.setGroupID(item.elementText("GroupID"));
				billlDetail5001.setGroupName(item.elementText("GroupName"));
				billlDetail5001.setItemID(item.elementText("ItemID"));
				billlDetail5001.setItemName(item.elementText("ItemName"));
				billlDetail5001.setItemUnit(item.elementText("ItemUnit"));
				billlDetail5001.setNum(item.elementText("Num"));
				billlDetail5001.setPrice(item.elementText("Price"));
				billlDetail5001.setShouldMoney(item.elementText("ShouldMoney"));
				billlDetail5001.setActualMoney(item.elementText("ActualMoney"));
				if ("2".equals(PropertiesUtil.getInstance().get("REGISTERNO_MODE"))){
					billlDetail5001.setRegisterNo(item.elementText("RegisterNo").toString());
				}
				billlDetail5001s.add(billlDetail5001);
			}
			billinfo5001.setBillDetail5001s(billlDetail5001s);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析HIS5001请求失败");
		}
		return billinfo5001;
	}
}
