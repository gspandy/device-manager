package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.BusiBillDetail;

public interface IBusiBillDetailDao {

	/**
	 * 保存未缴费记录明细
	 * @param busibilldetail
	 */
	public abstract void saveBillDetail(BusiBillDetail busibilldetail) throws Exception;

	
	/**
	 * 根据未缴费记录id 查询未缴费记录明细
	 * @param billinfoid
	 * @return
	 */
	public abstract List<BusiBillDetail> getBillDetailByBillId(int billinfoid) throws Exception;
}
