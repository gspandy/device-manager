package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.ISysRoleDao;
import com.yykj.system.entity.SysRole;
import com.yykj.system.services.ISysRoleService;

@Service("roleServer")
public class SysRoleService implements ISysRoleService {

	@Resource(name="roleDao")
	private ISysRoleDao roleDao;
	
	@Override
	public List<SysRole> getAllSysRole() {
		// TODO Auto-generated method stub
		return roleDao.getAllSysRole();
	}

	@Override
	public void save(SysRole role) throws Exception {
		// TODO Auto-generated method stub
		roleDao.save(role);
	}

	@Override
	public void deleteSysRoleById(int id) throws Exception {
		// TODO Auto-generated method stub
		roleDao.deleteSysRoleById(id);
	}

	@Override
	public SysRole update(SysRole role) {
		// TODO Auto-generated method stub
		return roleDao.update(role);
	}

	@Override
	public SysRole getSysRoleById(int id) {
		// TODO Auto-generated method stub
		return roleDao.getSysRoleById(id);
	}

}
