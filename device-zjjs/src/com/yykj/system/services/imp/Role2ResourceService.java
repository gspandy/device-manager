package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.IBasUserInfoDao;
import com.yykj.system.dao.IRole2ResourceDao;
import com.yykj.system.entity.SysResource;
import com.yykj.system.services.IRole2ResourceService;

@Service("r2rServer")
public class Role2ResourceService implements IRole2ResourceService {

	@Resource(name="r2rDao")
	private IRole2ResourceDao r2rDao;
	
	@Override
	public void saveRole2Resource(Integer roleid, List ids) {
		// TODO Auto-generated method stub
		r2rDao.saveRole2Resource(roleid, ids);
	}

	@Override
	public List<SysResource> getResourceByRole(Integer roleid) {
		// TODO Auto-generated method stub
		return r2rDao.getResourceByRole(roleid);
	}

}
