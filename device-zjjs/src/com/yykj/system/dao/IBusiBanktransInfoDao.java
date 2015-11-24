package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.BusiBanktransInfo;

public interface IBusiBanktransInfoDao {

	
	/**
	 * 保存银行交易记录
	 * @param busibillinfo
	 * @return
	 * @throws Exception
	 */
	public abstract int saveBusiBanktransInfo(BusiBanktransInfo banktransInfo)throws Exception;

	
	
	/**
	 * 根据参数查询保存银行交易记录总条数
	 * @param type
	 * @param value
	 * @return
	 */
	public abstract int getBanktransInfoCount(String type, String value) throws Exception;

	
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
	public abstract List<BusiBanktransInfo> getBanktransInfos(String type, String value, Integer pageIndex,
			Integer pageSize,boolean isPaging) throws Exception;
}
