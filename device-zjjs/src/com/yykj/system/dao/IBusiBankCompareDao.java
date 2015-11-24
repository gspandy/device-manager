package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.BusiBankCompare;

public interface IBusiBankCompareDao {

	
	/**
	 * 保存银行对账记录
	 * @param busibankcompare
	 */
	public  void saveBankCompareRecord(BusiBankCompare busibankcompare) throws Exception;

	/**
	 * 根据条件  查询对账记录条数
	 * @param cardno
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public  int getBillInfoCount(String cardno, String startDate, String endDate) throws Exception;

	
	/**
	 * 根据条件  查询对账记录
	 * @param cardno
	 * @param startDate
	 * @param endDate
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging 是否分页
	 * @return
	 */
	public  List<BusiBankCompare> getBankCompareByParam(String cardno, String startDate,
			String endDate, Integer pageIndex, Integer pageSize,boolean isPaging)throws Exception;
}
