package com.yykj.system.services;

import java.util.List;

import com.yykj.system.entity.UserInfo;

public interface IBasUserInfoService {
	
	//�����û�
	public void save(UserInfo user) throws Exception;

	//ɾ���û�
	public void deleteUserInfoById(int userid) throws Exception;

	//�޸��û�
	public UserInfo update(UserInfo user) throws Exception;

	//���id�õ��û�
	public UserInfo getUser(int userid);

	//�õ������û�
	public List<UserInfo> getUsers();
	
	//�޸�����
	public void updatePassword(int userid,String oldpwd,String password)throws Exception;
	
	//用户登录验证
	public UserInfo validateUser(UserInfo user) throws Exception;
		
	//根据条件查询用户
	public List<UserInfo> getUsersByParam(String type,String value, Integer pageIndex, Integer pageSize);
	
	//密码重置
	public void resetPwd(List<Integer> ids) throws Exception;
}
