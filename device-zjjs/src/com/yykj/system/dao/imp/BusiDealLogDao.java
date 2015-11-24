package com.yykj.system.dao.imp;

import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.IBusiDealLogDao;
import com.yykj.system.entity.BusiDealLog;


@Repository("busiDealLogDao")
public class BusiDealLogDao extends BaseDao implements IBusiDealLogDao {

	public void saveBusiDealLog(BusiDealLog log) {
		save(log);
	}
}
