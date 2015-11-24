package com.yykj.system.services;

import java.util.List;

import com.yykj.system.entity.SysRole;

public interface ISysRoleService {
	
	/**
	 * 查询所有角色
	 */
	public List<SysRole> getAllSysRole();
	
	/**
	 * 保存角色
	 */
	public void save(SysRole role) throws Exception ;
	
	/**
	 * 删除角色
	 */
	public void deleteSysRoleById(int id) throws Exception ;
	
	/**
	 * 修改角色
	 */
	public SysRole update(SysRole role);
	
	/**
	 * 根据id查询角色
	 */
	public SysRole getSysRoleById(int id);
	
}
