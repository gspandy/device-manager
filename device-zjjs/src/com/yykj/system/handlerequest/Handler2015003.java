package com.yykj.system.handlerequest;

import java.io.IOException;
import java.text.DecimalFormat;
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
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.PropertiesUtil;
import com.yykj.base.util.ReadXmlFileUtil;
import com.yykj.system.entity.hisentity.Billinfo5001;
import com.yykj.system.entity.hisentity.BilllDetail5001;
import com.yykj.system.socket.socketclient4his.HandlerHis2006;
import com.yykj.system.socket.socketclient4his.HandlerHis5001;

/**
 * 处理2015003交易 
 * 未缴费记录查询
 * @author QinShuJin
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015003 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015003.class);

	@Override
	public String handleRequest(Map<String,String> title,String deviceRequestXml) {
		String ChannelDate = title.get("ChannelDate");	//业务请求时间
		String trancode  =title.get("TranCode");		//业务代码
		String responseXml = null;
		if ("2015003".equals(trancode)) {
			try {
				//1.解析自助设务请求
				Map<String,String> map_deviceRequest =  explainDeviceRequestXml(deviceRequestXml);
				
				//2.查询未缴费记录
				Billinfo5001 billinfo5001 = HandlerHis5001.handler5001(map_deviceRequest);
				if(billinfo5001.getResultCode().equals("1")){
					responseXml = HandlerUtil.getErrorXml(trancode, billinfo5001.getErrorMsg());
					return responseXml;
				}
				//3.对返回值进行封装处理后返回给自助设备
				responseXml = createDevice2015003Response(billinfo5001,map_deviceRequest);
			} catch (Exception e) {
				responseXml= HandlerUtil.getErrorXml(trancode, e.getMessage());
				e.printStackTrace();
			}
		} else {
			responseXml = getSuccessor().handleRequest(title,deviceRequestXml);
		}
		return responseXml;
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
			throw new BusinessException("解析请求2015003请求XML异常");
		}
		return resultMap;
	}
	
	/**
	 * 将HIS 5001交易返回的XML 重新封装后回传给自助设备
	 * @param billinfo5001  未缴费记录
	 * @param map_deviceRequest	设务请求
	 * @param map_hisResponse_2006　个人信息
	 * @return
	 * @throws Exception
	 */
	public static String createDevice2015003Response(Billinfo5001 billinfo5001,Map<String,String> map_deviceRequest) throws Exception{
		String bills = "";
		List<String> receiptNos = new ArrayList<String>();	//单据号集合
		try {
			//1.从请求中获取执行科室
			String [] execDepts = map_deviceRequest.get("ExecDept").split(",");
			List<BilllDetail5001> details = billinfo5001.getBillDetail5001s(); //所有未缴费记录
			boolean bol = false;
			for (String dept : execDepts) {
				if ("所有科室".equals(dept)){
					bol = true;
					break;
				}
			}
			//2.根据执行科室筛选单据号
			if (bol) {
				for (BilllDetail5001 detail : details) {
					String receiptNo = detail.getReceiptNo();
					if (!receiptNos.contains(receiptNo)){
						receiptNos.add(receiptNo);
					}
				}

			} else {
				for (BilllDetail5001 detail : details) {
					String receiptNo = detail.getReceiptNo(); 	//单据号
					String execdept = detail.getExecDept();		//执行科室
					for (String dept : execDepts) {
						if (execdept.equals(dept) && !receiptNos.contains(receiptNo)){
							receiptNos.add(receiptNo);
						}
					}
				}
			}
			
			//3.组装备最终的xml
			for (String receiptNo : receiptNos) {
				String billXml = "<Bill>@billinfo<Items>@items</Items></Bill>";	//一个单据的结构体
				String billinfo = ""; 	//单据头信息
				String items = "";		//单据中的项目
				double shouldMoney = 0.00; 	//应收金额
				double actualMoney = 0.00; 	//实收金额
				for (BilllDetail5001 detail : details) {
					String receiptNo_ = detail.getReceiptNo(); 	//单据号
					int flag=0;
					if(receiptNo.equals(receiptNo_)){
						if(flag==0){
							billinfo="<ReceiptNo>"+receiptNo+"</ReceiptNo>"
									+"<ReceiptTime>"+detail.getReceiptTime()+"</ReceiptTime>"
									+"<BillDept>"+detail.getBillDept()+"</BillDept>"
									+"<Doctor>"+detail.getDoctor()+"</Doctor>"
									+"<ShouldMoney>@shouldMoney</ShouldMoney>"
									+"<ActualMoney>@actualMoney</ActualMoney>";
						}
						items += "<Item>"
								+"<ExecDept>"+detail.getExecDept()+"</ExecDept>"
								+"<FeesType>"+detail.getFeesType()+"</FeesType>"
								+"<FeesItem>"+detail.getFeesItem()+"</FeesItem>"
								+"<ItemID>"+detail.getItemID()+"</ItemID>"
								+"<ItemName>"+detail.getItemName()+"</ItemName>"
								+"<ItemUnit>"+detail.getItemUnit()+"</ItemUnit>"
								+"<Num>"+detail.getNum()+"</Num>"
								+"<Price>"+detail.getPrice()+"</Price>"
								+"<ShouldMoney_Item>"+detail.getShouldMoney()+"</ShouldMoney_Item>"
								+"<ActualMoney_Item>"+detail.getActualMoney()+"</ActualMoney_Item>"
							+"</Item>";
						shouldMoney += Double.valueOf(detail.getShouldMoney());	//累加应收金额
						actualMoney += Double.valueOf(detail.getActualMoney());	//累加实收金额
						flag++;
					}
				}
				
				DecimalFormat df = new DecimalFormat("######0.00"); 			//保留两位小数
				billinfo = billinfo.replace("@shouldMoney", String.valueOf(df.format(shouldMoney)));
				billinfo = billinfo.replace("@actualMoney", String.valueOf(df.format(actualMoney)));
				billXml = billXml.replace("@billinfo", billinfo);
				billXml = billXml.replace("@items", items);
				bills += billXml;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("组装未缴费明细时失败");
		}
		
		//返回的XML
		 String ghid = null;
        if("1".equals(PropertiesUtil.getInstance().get("REGISTERNO_MODE"))){
        	ghid = billinfo5001.getRegisterNo();
        }else if("2".equals(PropertiesUtil.getInstance().get("REGISTERNO_MODE"))){
        	ghid = billinfo5001.getBillDetail5001s().get(0).getRegisterNo();
        }
        StringBuilder response_builder = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
        response_builder.append("<Root>")
					        .append("<Head>")
						        .append("<TranCode>2015003</TranCode>")
						        .append("<RspCode>0</RspCode>")
						        .append("<RspMsg>查询成功</RspMsg>")
					        .append("</Head>")
					        .append("<Data>")
						        .append("<GhId>").append(ghid).append("</GhId>")
						        .append("<Bills>").append(bills).append("</Bills>")
					        .append("</Data>")
				        .append("</Root>");
		String responseXml=response_builder.toString();
		return responseXml;
	}
}
