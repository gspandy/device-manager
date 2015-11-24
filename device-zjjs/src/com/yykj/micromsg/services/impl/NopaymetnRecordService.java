package com.yykj.micromsg.services.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.springframework.stereotype.Service;

import com.yykj.micromsg.services.INopaymetnRecordService;
import com.yykj.system.entity.hisentity.Billinfo5001;
import com.yykj.system.entity.hisentity.BilllDetail5001;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.BilllDetail2015004;
import com.yykj.system.socket.socketclient4his.HandlerHis2006;
import com.yykj.system.socket.socketclient4his.HandlerHis5001;


@Service("nopaymetnRecordService")
public class NopaymetnRecordService implements INopaymetnRecordService {

	public String getNopayMentRecord(String idcardNum, String wxappid) {
		String returnval = null;
		try {
			Map<String, String> map_param = new HashMap<String, String>();
			map_param.put("UserId", wxappid);
			map_param.put("CardTypeID", "1");
			Map<String, String> map_hisResponse_2006 = HandlerHis2006.handler2006(map_param);
			if ((map_hisResponse_2006.get("ResultCode")).equals("1")){
				return null;
			}
			String PatientID = (String) map_hisResponse_2006.get("PatientID");
			map_param.put("PatientID", PatientID);
			Billinfo5001 billinfo5001 = HandlerHis5001.handler5001(map_param);
			if (billinfo5001.getResultCode().equals("1")) {
				return null;
			}
			List<BilllDetail5001> billDetails = billinfo5001.getBillDetail5001s();
			List<String> receiptNos = new ArrayList<String>();
			for (BilllDetail5001 billlDetail5001 : billDetails) {
				String receiptNo = billlDetail5001.getReceiptNo();
				if (!receiptNos.contains(receiptNo)){
					receiptNos.add(receiptNo);
				}
			}

			List<BillInfo2015004> billInfo2015004s = new ArrayList<BillInfo2015004>();
			
			for (String receiptNo : receiptNos) {
				int count = 0;
				double shouldMoney = 0.0D;
				double actualMoney = 0.0D;
				BillInfo2015004 billInfo2015004 = new BillInfo2015004();
				List<BilllDetail2015004> billDetails2015004s = new ArrayList<BilllDetail2015004>();
				for (BilllDetail5001 billlDetail5001 : billDetails) {
					if (receiptNo.equals(billlDetail5001.getReceiptNo())) {
						if (count == 0) {
							billInfo2015004.setReceiptNo(receiptNo);
							billInfo2015004.setReceiptTime(billlDetail5001.getReceiptTime());
							billInfo2015004.setBillDept(billlDetail5001.getBillDept());
							billInfo2015004.setDoctor(billlDetail5001.getDoctor());
						}
						shouldMoney += Double.valueOf(billlDetail5001.getShouldMoney()).doubleValue();
						actualMoney += Double.valueOf(billlDetail5001.getActualMoney()).doubleValue();
						BilllDetail2015004 billlDetail2015004 = new BilllDetail2015004();
						billlDetail2015004.setExecDept(billlDetail5001.getExecDept());
						billlDetail2015004.setFeesType(billlDetail5001.getFeesType());
						billlDetail2015004.setFeesItem(billlDetail5001.getFeesItem());
						billlDetail2015004.setItemID(billlDetail5001.getItemID());
						billlDetail2015004.setItemName(billlDetail5001.getItemName());
						billlDetail2015004.setItemUnit(billlDetail5001.getItemUnit());
						billlDetail2015004.setNum(billlDetail5001.getNum());
						billlDetail2015004.setPrice(billlDetail5001.getPrice());
						billlDetail2015004.setShouldMoney(billlDetail5001.getShouldMoney());
						billlDetail2015004.setActualMoney(billlDetail5001.getActualMoney());
						billDetails2015004s.add(billlDetail2015004);
					}
				}

				DecimalFormat df = new DecimalFormat("######0.00");
				billInfo2015004.setShouldMoney(String.valueOf(df.format(shouldMoney)));
				billInfo2015004.setActualMoney(String.valueOf(df.format(actualMoney)));
				billInfo2015004.setDatails(billDetails2015004s);
				billInfo2015004s.add(billInfo2015004);
			}

			JSONArray dataJson = JSONArray.fromObject(billInfo2015004s);
			returnval = dataJson.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnval;
	}
}