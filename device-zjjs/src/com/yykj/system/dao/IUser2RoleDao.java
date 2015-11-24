package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.UserInfo;

public interface IUser2RoleDao {

	//保存、修改、取消
	public void saveUser2Role(Integer userid,Integer roleid);
	
	//查询用户对应的角色
	public SysRole getRoleByUserId(Integer userid);
	
	//多用户对应角色
	public void saveUsersRole(List<Integer> ids,Integer roleid);
	
	//查询角色下所有用户
	public List<UserInfo> getUsersByRole(Integer roleid);
	
	//查询组织机构下的用户
	public List<UserInfo> getUsersByOrg(String orgid);
}
