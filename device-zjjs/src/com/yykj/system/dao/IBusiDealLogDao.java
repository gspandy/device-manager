package com.yykj.system.dao;

import com.yykj.system.entity.BusiDealLog;

public interface IBusiDealLogDao {

	
	/**
	 * 保存银行交易日志
	 * @param busideallog
	 */
	public abstract void saveBusiDealLog(BusiDealLog busideallog);
}
