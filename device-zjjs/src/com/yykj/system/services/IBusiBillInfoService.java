package com.yykj.system.services;

import java.util.Date;
import java.util.List;

import com.yykj.system.entity.BusiBillInfo;
import com.yykj.system.entity.Pager;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;

public interface IBusiBillInfoService {

	/*public abstract void saveBillInfo(RequestInfo2015004 requestinfo2015004,
			BillInfo2015004 billinfo2015004, String s, Date date, Date date1,String bankMoney)
			throws Exception;
	
	public abstract void saveBillInfo_cx(RequestInfo2015004 requestinfo2015004,
			BillInfo2015004 billinfo2015004, String s, Date date, Date date1,String state,String bamkMoney)
			throws Exception;*/

	public abstract void updateBillInfo(String receiptNo) throws Exception;

	public abstract Pager getBillInfoPager(String type, String value, Integer pageIndex,
			Integer pageSize);

	public abstract List<BusiBillInfo> getBillInfos(String type, String value, Integer pageIndex,
			Integer pageSize);
	
	/**
	 * 根据银行交易记录ID查询单据
	 * @param billinfoid
	 * @return
	 */
	public abstract List<BusiBillInfo> getBillInfoByBankTransId(int banktransId) ;
}
