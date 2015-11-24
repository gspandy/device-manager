package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.ISysResourceDao;
import com.yykj.system.entity.SysResource;
import com.yykj.system.services.ISysResourceService;

@Service("resServer")
public class SysResourceService implements ISysResourceService {

	@Resource(name="resDao")
	private ISysResourceDao resdao;
	
	@Override
	public List<SysResource> getAllSysResource() {
		// TODO Auto-generated method stub
		return resdao.getAllSysResource();
	}

	@Override
	public void save(SysResource r) throws Exception {
		// TODO Auto-generated method stub
		resdao.save(r);
	}

	@Override
	public void deleteSysResourceById(int id) throws Exception {
		// TODO Auto-generated method stub
		resdao.deleteSysResourceById(id);
	}

	@Override
	public SysResource update(SysResource r) {
		// TODO Auto-generated method stub
		return resdao.update(r);
	}

	@Override
	public SysResource getSysResourceById(int id) {
		// TODO Auto-generated method stub
		return resdao.getSysResourceById(id);
	}

	@Override
	public List<SysResource> getUserResource(Integer userid) {
		// TODO Auto-generated method stub
		return resdao.getUserResource(userid);
	}
}
