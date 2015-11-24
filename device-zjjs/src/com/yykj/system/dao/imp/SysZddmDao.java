package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.ISysZddmDao;
import com.yykj.system.entity.SysZddm;

@Repository("sysZddmDao")
public class SysZddmDao extends BaseDao implements ISysZddmDao {

	@Override
	public List<SysZddm> getSysZddmByClassType(String ClassType) {
		String hql = "from SysZddm where classtype=:classtype and pid <> -1";
		Query query = this.getSession().createQuery(hql);
		query.setString("classtype", ClassType);
		return query.list();
	}

	@Override
	public boolean updateJylsh(String lsh) {
		String hql = "update SysZddm set entryvalue='" + lsh+ "' where entrycode='JYLSH'";
		Query query = this.getSession().createQuery(hql);
		int i = query.executeUpdate();
		return i > 0 ? true : false;
	}

	@Override
	public String getJylsh() {
		String hql = "from SysZddm where entrycode='JYLSH'";
		Query query = this.getSession().createQuery(hql);
		List<SysZddm> list = query.list();
		return list.size() > 0 ? list.get(0).getEntryvalue() : null;
	}

}
