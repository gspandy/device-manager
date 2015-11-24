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
import com.yykj.system.socket.socketclient4bank.HandlerBank920001;
import com.yykj.system.socket.socketclient4bank.HandlerBank920065;
import com.yykj.system.socket.socketclient4his.HandlerHis5002;

/**
 * 处理2015004交易 处方缴费业务
 * 
 * @author QinShuJin 2015年9月21日 下午3:47:48
 */
public class Handler2015004 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015004.class);
	private IBusiBanktransInfoService busiBanktransInfoService = SpringContextUtil.getBean("busiBanktransInfoService", IBusiBanktransInfoService.class);

	@Override
	public String handleRequest(Map<String, String> title,String deviceRequestXml) {
		String responseXml = null;
		String ChannelDate = title.get("ChannelDate"); // 业务请求时间
		String trancode = title.get("TranCode"); // 业务代码

		if ("2015004".equals(trancode)) {
			try {
				// 1.解析2015004支付请求
				RequestInfo2015004 requestInfo = explain2015004RequestXml(deviceRequestXml);
				
				List<BillInfo2015004> billInfos = requestInfo.getBillInfos();
				String responseMsg = "";
				boolean flag = true;
				//2.支付开始  一个单据支付一次
				for (BillInfo2015004 billInfo2015004 : billInfos) {
					// 2.1银行支付
					Map<String,Map<String,String>> map_920001Response = HandlerBank920001.handler920001(requestInfo, billInfo2015004, deviceRequestXml);
					Map<String,String> pub = map_920001Response.get("pub");
					
					//2.2 HIS自助缴费
					if (pub.get("RspCode").equals("000000")) {
						Date localTransDate = DateUtil.parse(ChannelDate);
						String localTransNum = pub.get("ChannelSeq"); //本地交易流水号
						
						UnionPay2015007 unionpay = new UnionPay2015007();
						unionpay.setTranDate(pub.get("AppDate"));	//银行交易日期
						unionpay.setTranTime(pub.get("AppTime"));	//银行交易时间
						unionpay.setRefNum(pub.get("AppSeqNo"));	//银行交易流水号
						unionpay.setTraceNum(localTransNum); 		//本地交易流水号
						unionpay.setAmount(billInfo2015004.getActualMoney()); //银行扣款金额
						
						boolean isSucess = HandlerHis5002.handler5002(requestInfo, billInfo2015004, localTransNum);
						if (isSucess) { // HIS自助缴费成功
							flag  = true;
							busiBanktransInfoService.saveBusiBanktransInfo(requestInfo, unionpay, localTransDate, "0", "1");
							responseMsg = "支付成功";
						}else { // 自助缴费失败 银行冲销
							responseMsg = "支付失败";
							Map<String,Map<String,String>> map_920065Response = HandlerBank920065.handler920065(map_920001Response,requestInfo, billInfo2015004,deviceRequestXml);
							Map<String,String> pub_map = map_920065Response.get("pub");
							if (pub_map.get("RspCode").equals("000000")) {
								busiBanktransInfoService.saveBusiBanktransInfo(requestInfo, unionpay, localTransDate, "1", "1");
							} else {
								responseMsg = "有记录冲销失败，请到财务室核对并退款.";
							}
							flag = false;

						}
					} else { // 银行交易失败
						responseXml = HandlerUtil.getErrorXml(trancode, pub.get("RspMsg").toString());
						return responseXml;
					}
				}

				String RspCode = "0";
				if (!flag) {
					RspCode = "1";
				}
				responseXml = (new StringBuilder(
						"<Root><Head><TranCode>2015004</TranCode><RspCode>"))
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
	private RequestInfo2015004 explain2015004RequestXml(String requestXML)
			throws Exception {
		RequestInfo2015004 dataInfo = new RequestInfo2015004();
		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();

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
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析2015004支付请求失败");
		}
		return dataInfo;
	}
}
