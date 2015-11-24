package com.yykj.system.services.imp;



import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.IBusiBillDetailDao;
import com.yykj.system.dao.IBusiBillInfoDao;
import com.yykj.system.entity.BusiBillDetail;
import com.yykj.system.entity.BusiBillInfo;
import com.yykj.system.entity.Pager;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.BilllDetail2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.services.IBusiBillInfoService;

@Service("busiBillInfoService")
public class BusiBillInfoService implements IBusiBillInfoService {

	@Resource(name="busiBillInfoDao")
	private IBusiBillInfoDao busiBillInfoDao;
	
	@Resource(name="busiBillDetailDao")
	private IBusiBillDetailDao busiBillDetailDao;

	/*@Override
	public void saveBillInfo(RequestInfo2015004 requestInfo, BillInfo2015004 billInfo2015004, 
			String bankJylsh, Date bankJyrq,Date channelDate,String bankMoney) throws Exception {
		BusiBillInfo bill = new BusiBillInfo();
		bill.setYhmc(requestInfo.getYhmc());
		bill.setYhzh(requestInfo.getYhzh());
		bill.setGhid(requestInfo.getGhId());
		bill.setIdcard(requestInfo.getIdCard());
		bill.setPatientid(requestInfo.getPatientId());
		bill.setUserid(requestInfo.getUserId());
		bill.setReceiptno(billInfo2015004.getReceiptNo());
		bill.setBilldept(billInfo2015004.getBillDept());
		bill.setDoctor(billInfo2015004.getDoctor());
		bill.setShouldmoney(Double.valueOf(billInfo2015004.getShouldMoney()));
		bill.setActualmoney(Double.valueOf(billInfo2015004.getActualMoney()));
		bill.setBankmoney(Double.valueOf(bankMoney));
		bill.setBankjylsh(bankJylsh);
		bill.setBankjyrq(bankJyrq);
		bill.setReceipttime(channelDate);
		bill.setState("0");
		int billinfoid = busiBillInfoDao.saveBillInfo(bill);
		List<BilllDetail2015004> datails = billInfo2015004.getDatails();
		for (BilllDetail2015004 billlDetail : datails) {
			BusiBillDetail detail = new BusiBillDetail();
			detail.setBillinfoid(billinfoid);
			detail.setReceiptno(billInfo2015004.getReceiptNo());
			detail.setExecdept(billlDetail.getExecDept());
			detail.setFeestype(billlDetail.getFeesType());
			detail.setFeesitem(billlDetail.getFeesItem());
			detail.setItemid(billlDetail.getItemID());
			detail.setItemname(billlDetail.getItemName());
			detail.setItemunit(billlDetail.getItemUnit());
			detail.setNum(Double.parseDouble(billlDetail.getNum()));
			detail.setPrice(Double.parseDouble(billlDetail.getPrice()));
			detail.setShouldmoney_item(Double.parseDouble(billlDetail.getShouldMoney()));
			detail.setActualmoney_item(Double.parseDouble(billlDetail.getActualMoney()));
			busiBillDetailDao.saveBillDetail(detail);
		}
	}
	
	
	@Override
	public void saveBillInfo_cx(RequestInfo2015004 requestInfo, BillInfo2015004 billInfo2015004, 
			String bankJylsh, Date bankJyrq,Date channelDate,String state,String bamkMoney) throws Exception {
		BusiBillInfo bill = new BusiBillInfo();
		bill.setYhmc(requestInfo.getYhmc());
		bill.setYhzh(requestInfo.getYhzh());
		bill.setGhid(requestInfo.getGhId());
		bill.setIdcard(requestInfo.getIdCard());
		bill.setPatientid(requestInfo.getPatientId());
		bill.setUserid(requestInfo.getUserId());
		bill.setReceiptno(billInfo2015004.getReceiptNo());
		bill.setBilldept(billInfo2015004.getBillDept());
		bill.setDoctor(billInfo2015004.getDoctor());
		bill.setShouldmoney(Double.valueOf("-"+billInfo2015004.getShouldMoney()));
		bill.setActualmoney(Double.valueOf("-"+billInfo2015004.getActualMoney()));
		bill.setBankmoney(Double.valueOf("-"+bamkMoney));
		bill.setBankjylsh(bankJylsh);
		bill.setBankjyrq(bankJyrq);
		bill.setReceipttime(channelDate);
		bill.setState("1");
		int billinfoid = busiBillInfoDao.saveBillInfo(bill);
		List<BilllDetail2015004> datails = billInfo2015004.getDatails();
		for (BilllDetail2015004 billlDetail : datails) {
			BusiBillDetail detail = new BusiBillDetail();
			detail.setBillinfoid(billinfoid);
			detail.setReceiptno(billInfo2015004.getReceiptNo());
			detail.setExecdept(billlDetail.getExecDept());
			detail.setFeestype(billlDetail.getFeesType());
			detail.setFeesitem(billlDetail.getFeesItem());
			detail.setItemid(billlDetail.getItemID());
			detail.setItemname(billlDetail.getItemName());
			detail.setItemunit(billlDetail.getItemUnit());
			detail.setNum(Double.parseDouble(billlDetail.getNum()));
			detail.setPrice(Double.parseDouble(billlDetail.getPrice()));
			detail.setShouldmoney_item(Double.parseDouble(billlDetail.getShouldMoney()));
			detail.setActualmoney_item(Double.parseDouble(billlDetail.getActualMoney()));
			busiBillDetailDao.saveBillDetail(detail);
		}
	}
*/
	public void updateBillInfo(String receiptNo) throws Exception {
		busiBillInfoDao.updateBillInfo(receiptNo);
	}

	public Pager getBillInfoPager(String type, String value, Integer pageIndex,
			Integer pageSize) {
		Pager page = new Pager();
		try {
			int totalCount = busiBillInfoDao.getBillInfoCount(type, value);
			int totalPages = totalCount / pageSize.intValue();
			if (totalPages % pageSize.intValue() != 0)
				totalPages++;
			else if (totalCount < pageSize.intValue())
				totalPages++;
			
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setTotalCount(Integer.valueOf(totalCount));
			page.setTotalPages(Integer.valueOf(totalPages));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}

	public List<BusiBillInfo> getBillInfos(String type, String value, Integer pageIndex,
			Integer pageSize) {
		List<BusiBillInfo> list = null;
		try {
			list = busiBillInfoDao.getBillInfos(type, value, pageIndex, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list==null?new ArrayList<BusiBillInfo>():list;
	}

	@Override
	public List<BusiBillInfo> getBillInfoByBankTransId(int banktransId) {
		try {
			return busiBillInfoDao.getBillInfoByBankTransId(banktransId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
