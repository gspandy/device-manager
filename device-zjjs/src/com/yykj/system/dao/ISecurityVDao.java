package com.yykj.system.dao;

import java.util.List;

import com.yykj.base.dao.IBaseDao;
import com.yykj.system.entity.Security_V;

public interface ISecurityVDao extends IBaseDao {
	
	/**
	 * 查询用户的权限
	 * @param userid
	 * @return
	 */
	public List<Security_V> getSecurityByUserid(String userid);
	

}
