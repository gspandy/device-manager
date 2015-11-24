package com.yykj.system.services.imp;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.base.services.imp.BaseService;
import com.yykj.system.dao.ISecurityVDao;
import com.yykj.system.entity.Security_V;
import com.yykj.system.services.ISecurityVService;

@Service("securityVService")
public class SecurityVService extends BaseService implements ISecurityVService {
	@Resource(name="securityVDao")
	private ISecurityVDao securityVDao;
	
	@Override
	public List<Security_V> getSecurityByUserid(String userid) {
		return securityVDao.getSecurityByUserid(userid);
	}
}
