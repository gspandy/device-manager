package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.system.dao.ISysResourceDao;
import com.yykj.system.entity.SysResource;

@Repository("resDao")
public class SysResourceDao extends BaseDao implements ISysResourceDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<SysResource> getAllSysResource() {
		return this.getSession().createQuery("from SysResource t order by t.sort asc").list();
	}

	@Override
	public void save(SysResource r) throws Exception {
		String hql = "from SysResource where resourcecode='"+r.getResourcecode()+"'";
		List list = this.getSession().createQuery(hql).list();
		if(list.size()>0){
			throw new BusinessException("existed");
		}else{
			this.getSession().save(r);
		}
	}

	@Override
	public void deleteSysResourceById(int id) throws Exception {
		List list = this.getChildrenResource(id);
		if(list.size()>0){
			throw new BusinessException("existed");
		}else{
			String hql="delete SysResource where id=:id";
			Query query=this.getSession().createQuery(hql);
			query.setInteger("id", id);
			query.executeUpdate();
		}		
	}

	@Override
	public SysResource update(SysResource r) {
		this.getSession().update(r);		
		return this.getSysResourceById(r.getId());
	}

	@Override
	public SysResource getSysResourceById(int id) {
		return (SysResource) this.getSession().get(SysResource.class,id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<SysResource> getUserResource(Integer userid) {
		String hql = "from SysResource t where t.id in("
				+ "select resourceid from Role2Resource r where roleid=("
				+ "select roleid from User2Role u where u.userid='"+userid+"'))";
		return this.getSession().createQuery(hql).list();
	}
	
	//查询子节点
	private List<SysResource> getChildrenResource(Integer id){
		String hql = "from SysResource o where o.menutype="+id+"";
		return  this.getSession().createQuery(hql).list();
	}
}
