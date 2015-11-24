package com.yykj.system.services;

import com.yykj.system.entity.BusiBankCompare;
import com.yykj.system.entity.Pager;

import java.util.List;

public interface IBusiBankCompareService {

	public abstract void saveBankCompareRecord(BusiBankCompare busibankcompare);

	public abstract void download();

	/**
	 * 
	 * @param cardno
	 * @param startDate
	 * @param endDate
	 * @param pageIndex
	 * @param pageSize
	 * @param isPaging 是否需要分页
	 * @return
	 */
	public abstract List<BusiBankCompare> getBankCompareByParam(String cardno,
			String startDate, String endDate, Integer pageIndex,
			Integer pageSize,boolean isPaging);

	public abstract Pager getBankComparePager(String cardno, String startDate,
			String endDate, Integer pageIndex, Integer pageSize);
}
