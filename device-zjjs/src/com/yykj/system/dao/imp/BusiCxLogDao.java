package com.yykj.system.dao.imp;


import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.IBusiCxLogDao;
import com.yykj.system.entity.BusiCxLog;

@Repository("busiCxLogDao")
public class BusiCxLogDao extends BaseDao implements IBusiCxLogDao {

	public void saveBusiCxLog(BusiCxLog log) {
		save(log);
	}
}
