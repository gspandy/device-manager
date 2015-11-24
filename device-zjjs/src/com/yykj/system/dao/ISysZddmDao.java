package com.yykj.system.dao;

import java.util.List;

import com.yykj.base.dao.IBaseDao;
import com.yykj.system.entity.SysZddm;

public interface ISysZddmDao extends IBaseDao {
	
	public List<SysZddm> getSysZddmByClassType(String ClassType);
	
	
	/**
	 * 更新交易流水号
	 * @param lsh
	 */
	public boolean updateJylsh(String lsh);
	
	
	/**
	 * 获取交易流水号
	 * @return
	 */
	public String getJylsh();
}
