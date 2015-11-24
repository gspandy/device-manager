package com.yykj.system.services;

import java.util.Date;
import java.util.List;

import com.yykj.system.entity.BusiBanktransInfo;
import com.yykj.system.entity.Pager;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.entity.requestenity.UnionPay2015007;

public interface IBusiBanktransInfoService {

	/**
	 * 
	 * @param requestInfo
	 * @param unionpay
	 * @param localTransDate
	 * @param tranType 交易类型   0：付费交易  1：冲正交易 2：异常交易
	 * @param bankfalg 付款类型  1:汉口银行  3:银联
	 * @throws Exception
	 */
	public abstract void saveBusiBanktransInfo(RequestInfo2015004 requestInfo,
			UnionPay2015007 unionpay, Date localTransDate, String tranType,
			String bankfalg) throws Exception;

	/**
	 * 根据参数查询保存银行交易记录总条数
	 * 
	 * @param type
	 * @param value
	 * @return
	 */
	public abstract int getBanktransInfoCount(String type, String value)
			throws Exception;

	/**
	 * 分页查询未缴费记录
	 * @param type
	 * @param value
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging 是否需要分页
	 * @return
	 * @throws Exception
	 */
	public abstract List<BusiBanktransInfo> getBanktransInfos(String type,
			String value, Integer pageIndex, Integer pageSize,boolean isPaging) throws Exception;
	
	
	public abstract Pager getBillInfoPager(String type, String value, Integer pageIndex,
			Integer pageSize);
}
