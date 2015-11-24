package com.yykj.system.dao;

import java.util.List;

import com.yykj.system.entity.UserInfo;

public interface IBasUserInfoDao {
	
	public void save(UserInfo user);

	public void deleteUserInfoById(int userid) throws Exception ;

	public UserInfo update(UserInfo user) throws Exception ;

	public UserInfo getUser(int userid);

	public List<UserInfo> getUsers();
	
	public void updatePassword(int userid,String oldpwd, String password)throws Exception;
	
	public String password2MD5(String str);
	
	public String MD5ToPassword(String pwd);
	
	//用户登录验证
	public UserInfo validateUser(UserInfo user) throws Exception;
	
	//根据条件查询用户
	public List<UserInfo> getUsersByParam(String type,String value, Integer pageIndex, Integer pageSize);
	
	//密码重置
	public void resetPwd(List<Integer> ids);
}
