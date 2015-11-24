package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.base.services.imp.BaseService;
import com.yykj.system.dao.IBasUserInfoDao;
import com.yykj.system.entity.UserInfo;
import com.yykj.system.services.IBasUserInfoService;

//��ע�ⷽʽ��������
@Service("userServer")
public class BasUserInfoService extends BaseService implements IBasUserInfoService {

	@Resource(name="userDao")
	private IBasUserInfoDao userdao;
	
	@Override
	public void save(UserInfo user) {
		 userdao.save(user);
	}

	@Override
	public void deleteUserInfoById(int userid) throws Exception {
		userdao.deleteUserInfoById(userid);
	}

	@Override
	public UserInfo update(UserInfo user) throws Exception {
		return userdao.update(user);
	}

	@Override
	public UserInfo getUser(int userid) {
		return userdao.getUser(userid);
	}

	@Override
	public List<UserInfo> getUsers() {
		return userdao.getUsers();
	}

	@Override
	public void updatePassword(int userid,String oldpwd, String password)throws Exception {
		userdao.updatePassword(userid,oldpwd,password);
	}
	
	@Override
	public UserInfo validateUser(UserInfo user) throws Exception {
		return userdao.validateUser(user);
	}
	
	@Override
	public List<UserInfo> getUsersByParam(String type, String value, Integer pageIndex, Integer pageSize) {
		return userdao.getUsersByParam(type, value, pageIndex, pageSize);
	}
	
	@Override
	public void resetPwd(List<Integer> ids) throws Exception {
		// TODO Auto-generated method stub
		userdao.resetPwd(ids);
	}
}
