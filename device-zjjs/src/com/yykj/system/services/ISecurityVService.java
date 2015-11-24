package com.yykj.system.services;

import java.util.List;

import com.yykj.base.services.IBaseService;
import com.yykj.system.entity.Security_V;

public interface ISecurityVService extends  IBaseService {
	
	/**
	 * 查询用户的权限
	 * @param userid
	 * @return
	 */
	public List<Security_V> getSecurityByUserid(String userid);
	

}
