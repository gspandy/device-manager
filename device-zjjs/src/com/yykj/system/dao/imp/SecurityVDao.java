package com.yykj.system.dao.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.ISecurityVDao;
import com.yykj.system.entity.Security_V;

@Repository("securityVDao")
public class SecurityVDao extends BaseDao implements ISecurityVDao {
	@Override
	public List<Security_V> getSecurityByUserid(String userid) {
		String hql = "from Security_V where userid="+userid +" and resouceid is not null";
		List<Security_V> list  = this.getEntities(hql);
		return list;
	}
}
