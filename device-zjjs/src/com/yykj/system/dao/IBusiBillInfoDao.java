package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.BusiBillDetail;
import com.yykj.system.entity.BusiBillInfo;

public interface IBusiBillInfoDao {

	
	/**
	 * 保存未缴费记录
	 * @param busibillinfo
	 * @return
	 * @throws Exception
	 */
	public abstract int saveBillInfo(BusiBillInfo busibillinfo)throws Exception;

	
	/**
	 * 更新未缴费记录状态
	 * @param s
	 * @throws Exception
	 */
	public abstract void updateBillInfo(String receiptNo) throws Exception;

	
	/**
	 * 根据参数查询未缴费记录总条数
	 * @param type
	 * @param value
	 * @return
	 */
	public abstract int getBillInfoCount(String type, String value) throws Exception;

	
	/**
	 * 分页查询未缴费记录
	 * @param type
	 * @param value
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public abstract List<BusiBillInfo> getBillInfos(String type, String value, Integer pageIndex,
			Integer pageSize) throws Exception;
	
	
	/**
	 * 根据银行交易记录ID查询单据
	 * @param billinfoid
	 * @return
	 */
	public abstract List<BusiBillInfo> getBillInfoByBankTransId(int banktransId) throws Exception;
}
