package com.yykj.system.services;

import java.util.List;

import com.yykj.system.entity.SysResource;

public interface IRole2ResourceService {

	//保存
	public void saveRole2Resource(Integer roleid,List ids);
	
	//根据角色查询对应的功能
	public List<SysResource> getResourceByRole(Integer roleid);
	
}
