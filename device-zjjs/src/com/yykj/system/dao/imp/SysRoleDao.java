package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.system.dao.ISysRoleDao;
import com.yykj.system.entity.SysRole;

@Repository("roleDao")
public class SysRoleDao extends BaseDao implements ISysRoleDao {

	@Override
	public List<SysRole> getAllSysRole() {
		return this.getSession().createQuery("from SysRole t order by t.id asc").list();
	}

	@Override
	public void save(SysRole role) throws Exception {
		String hql = "from SysRole where rolecode='"+role.getRolecode()+"'";
		List list = this.getSession().createQuery(hql).list();
		if(list.size()>0){
			throw new BusinessException("existed");
		}else{
			this.getSession().save(role);
		}
	}

	@Override
	public void deleteSysRoleById(int id) throws Exception {
		String hql="delete SysRole where id=:id";
		Query query=this.getSession().createQuery(hql);
		query.setInteger("id", id);
		query.executeUpdate();
	}

	@Override
	public SysRole update(SysRole role) {
		this.getSession().update(role);		
		return this.getSysRoleById(role.getId());
	}

	@Override
	public SysRole getSysRoleById(int id) {
		return (SysRole) this.getSession().get(SysRole.class,id);
	}

}
