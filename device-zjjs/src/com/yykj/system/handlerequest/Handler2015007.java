package com.yykj.system.handlerequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.DateUtil;
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.BilllDetail2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.entity.requestenity.UnionPay2015007;
import com.yykj.system.services.IBusiBanktransInfoService;
import com.yykj.system.socket.socketclient4his.HandlerHis5002;

/**
 * 处理2015007交易 
 * 处方支付、冲正
 * @author QinShuJin 2015年9月21日 下午3:47:48
 */
public class Handler2015007 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015007.class);
	private IBusiBanktransInfoService busiBanktransInfoService = SpringContextUtil.getBean("busiBanktransInfoService", IBusiBanktransInfoService.class);

	@Override
	public String handleRequest(Map<String, String> title,String deviceRequestXml) {
		String responseXml = null;
		String ChannelDate = title.get("ChannelDate"); // 业务请求时间
		String trancode = title.get("TranCode"); // 业务代码

		if ("2015007".equals(trancode)) {
			try {
				// 1.解析2015007支付请求
				RequestInfo2015004 requestInfo = explain2015004RequestXml(deviceRequestXml);
				
				List<BillInfo2015004> billInfos = requestInfo.getBillInfos();
				
				UnionPay2015007 unionpay = requestInfo.getUnionpay2015007();
				String tranType = unionpay.getTranType(); 	//交易类型
				Date localTransDate = DateUtil.parse(ChannelDate); //本地交易日期
				String responseMsg = "";
				String RspCode = "0";
				if("XF".equals(tranType)){//消费
					try {
						//HIS自助缴费
						for (BillInfo2015004 billInfo2015004 : billInfos) {
							HandlerHis5002.handler5002(requestInfo, billInfo2015004, ChannelDate);
						}
						//保存交费记录
						busiBanktransInfoService.saveBusiBanktransInfo(requestInfo, unionpay, localTransDate, "0", "3");
						responseMsg="自助缴费成功.";
					} catch (Exception e) {
						RspCode = "1";
						responseMsg="自助缴费失败.";
					}
					
					
				}else if("CZ".equals(tranType)){
					try {
						//冲正 保存冲正记录
						busiBanktransInfoService.saveBusiBanktransInfo(requestInfo, unionpay, localTransDate, "1", "3");
						responseMsg="保存冲正记录成功.";
					} catch (Exception e) {
						RspCode = "1";
						responseMsg="保存冲正失败.";
					}
				
				}else if("YC".equals(tranType)){
					try {
						//银联交易失败 保存失败记录
						busiBanktransInfoService.saveBusiBanktransInfo(requestInfo, unionpay, localTransDate, "2", "3");
						responseMsg="保存失败交易记录成功";
					} catch (Exception e) {
						RspCode = "1";
						responseMsg="保存失败交易记录失败.";
					}
				}
				responseXml = (new StringBuilder(
						"<Root><Head><TranCode>2015007</TranCode><RspCode>"))
						.append(RspCode).append("</RspCode>")
						.append("<RspMsg>").append(responseMsg)
						.append("</RspMsg>").append("</Head>")
						.append("</Root>").toString();
				
			} catch (Exception e) {
				e.printStackTrace();
				responseXml = HandlerUtil.getErrorXml(trancode, "交易失败.");
			}
		} else {
			responseXml = this.getSuccessor().handleRequest(title,deviceRequestXml);
		}

		return responseXml;
	}

	/**
	 * 解析请求XML下的Data节点
	 * 
	 * @param requestXML
	 * @return
	 * @throws DocumentException
	 */
	private RequestInfo2015004 explain2015004RequestXml(String requestXML)throws Exception {
		RequestInfo2015004 dataInfo = new RequestInfo2015004();
		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();
			Map<String, String> resultMap = new HashMap<String, String>();

			// 1.解析Data节点下的节点
			Element data = root.element("Data"); // 得到Data节点
			dataInfo.setGhId(data.elementText("GhId"));
			dataInfo.setYhzh(data.elementText("yhzh"));
			dataInfo.setYhmc(data.elementText("yhmc"));
			dataInfo.setIdCard(data.elementText("IdCard"));
			dataInfo.setPatientId(data.elementText("PatientId"));
			dataInfo.setUserId(data.elementText("UserId"));
			dataInfo.setCardTypeID(data.elementText("CardTypeID"));

			// 2.解析Data下的Bills节点
			List<Element> bill_eles = data.element("Bills").elements("Bill"); // 得到多外Bill节点
			List<BillInfo2015004> billInfos = new ArrayList<BillInfo2015004>();
			for (Element bill_e : bill_eles) {
				BillInfo2015004 bill = new BillInfo2015004();
				bill.setReceiptNo(bill_e.elementText("ReceiptNo"));
				bill.setReceiptTime(bill_e.elementText("ReceiptTime"));
				bill.setBillDept(bill_e.elementText("BillDept"));
				bill.setDoctor(bill_e.elementText("Doctor"));
				bill.setShouldMoney(bill_e.elementText("ShouldMoney"));
				bill.setActualMoney(bill_e.elementText("ActualMoney"));

				// 3.解析bill下的items中的内容
				List<Element> item_eles = bill_e.element("Items").elements("Item"); // 得到多个Item节点
				List<BilllDetail2015004> details = new ArrayList<BilllDetail2015004>();
				for (Element item : item_eles) {
					BilllDetail2015004 datail = new BilllDetail2015004();
					datail.setExecDept(item.elementText("ExecDept"));
					datail.setFeesType(item.elementText("FeesType"));
					datail.setFeesItem(item.elementText("FeesItem"));
					datail.setItemID(item.elementText("ItemID"));
					datail.setItemName(item.elementText("ItemName"));
					datail.setItemUnit(item.elementText("ItemUnit"));
					datail.setNum(item.elementText("Num"));
					datail.setPrice(item.elementText("Price"));
					datail.setShouldMoney(item.elementText("ShouldMoney_Item"));
					datail.setActualMoney(item.elementText("ActualMoney_Item"));
					details.add(datail);
				}
				bill.setDatails(details);
				billInfos.add(bill);
			}
			dataInfo.setBillInfos(billInfos);
			
			//4.解析银联返回值
			Element unionpy_ele = root.element("UnionPay"); // 得到UnionPay节点
			UnionPay2015007 unionpay = new UnionPay2015007();
			unionpay.setTranType(unionpy_ele.elementText("tranType"));
			unionpay.setBankFlag(unionpy_ele.elementText("bankFlag"));
			unionpay.setAmount(unionpy_ele.elementText("amount"));
			unionpay.setAuthNum(unionpy_ele.elementText("authNum"));
			unionpay.setBatchNum(unionpy_ele.elementText("batchNum"));
			unionpay.setCardName(unionpy_ele.elementText("cardName"));
			unionpay.setExpDate(unionpy_ele.elementText("expDate"));
			unionpay.setForUpdate(unionpy_ele.elementText("forUpdate"));
			unionpay.setMerchantName(unionpy_ele.elementText("merchantName"));
			unionpay.setMerchantNum(unionpy_ele.elementText("merchantNum"));
			unionpay.setRefNum(unionpy_ele.elementText("refNum"));
			unionpay.setRetMsg(unionpy_ele.elementText("retMsg"));
			unionpay.setTermId(unionpy_ele.elementText("termId"));
			unionpay.setTraceNum(unionpy_ele.elementText("traceNum"));
			unionpay.setTrack2(unionpy_ele.elementText("track2"));
			unionpay.setTrack3(unionpy_ele.elementText("track3"));
			unionpay.setTranCardNum(unionpy_ele.elementText("tranCardNum"));
			unionpay.setTranDate(unionpy_ele.elementText("tranDate"));
			unionpay.setTranTime(unionpy_ele.elementText("tranTime"));
			unionpay.setVersion(unionpy_ele.elementText("version"));
			unionpay.setBalanceAmount(unionpy_ele.elementText("balanceAmount"));
			dataInfo.setUnionpay2015007(unionpay);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析2015007支付请求失败");
		}
		return dataInfo;
	}
}
