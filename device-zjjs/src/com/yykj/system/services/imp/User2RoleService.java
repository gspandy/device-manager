package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.IUser2RoleDao;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.UserInfo;
import com.yykj.system.services.IUser2RoleService;

@Service("u2rServer")
public class User2RoleService implements IUser2RoleService {

	@Resource(name="u2rDao")
	private IUser2RoleDao u2rDao;
	
	@Override
	public void saveUser2Role(Integer userid, Integer roleid) {
		// TODO Auto-generated method stub
		u2rDao.saveUser2Role(userid, roleid);
	}

	@Override
	public SysRole getRoleByUserId(Integer userid) {
		// TODO Auto-generated method stub
		return u2rDao.getRoleByUserId(userid);
	}

	@Override
	public void saveUsersRole(List<Integer> ids, Integer roleid) {
		// TODO Auto-generated method stub
		u2rDao.saveUsersRole(ids, roleid);
	}

	@Override
	public List<UserInfo> getUsersByRole(Integer roleid) {
		// TODO Auto-generated method stub
		return u2rDao.getUsersByRole(roleid);
	}

	@Override
	public List<UserInfo> getUsersByOrg(String orgid) {
		// TODO Auto-generated method stub
		return u2rDao.getUsersByOrg(orgid);
	}
}
