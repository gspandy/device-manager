package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.SysResource;

public interface ISysResourceDao {

	/**
	 * 查询所有资源
	 */
	public List<SysResource> getAllSysResource();
	
	/**
	 * 保存资源
	 */
	public void save(SysResource r) throws Exception ;
	
	/**
	 * 删除资源
	 */
	public void deleteSysResourceById(int id) throws Exception ;
	
	/**
	 * 修改资源
	 */
	public SysResource update(SysResource r);
	
	/**
	 * 根据id查询资源
	 */
	public SysResource getSysResourceById(int id);
	
	/**
	 * 查询用户对应资源
	 */
	public List<SysResource> getUserResource(Integer userid);
}
