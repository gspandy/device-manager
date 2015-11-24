package com.yykj.system.dao;

import com.yykj.system.entity.BusiCxLog;

public interface IBusiCxLogDao {
	/**
	 * 保存冲销日志
	 * @param busicxlog
	 */
	public abstract void saveBusiCxLog(BusiCxLog busicxlog);
}
