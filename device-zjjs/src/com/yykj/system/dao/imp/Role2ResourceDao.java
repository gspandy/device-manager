package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.IRole2ResourceDao;
import com.yykj.system.entity.Role2Resource;
import com.yykj.system.entity.SysResource;
import com.yykj.system.entity.SysRole;

@Repository("r2rDao")
public class Role2ResourceDao extends BaseDao implements IRole2ResourceDao {

	@Override
	public void saveRole2Resource(Integer roleid, List ids) {
		//删除所有角色对应的资源
		String hql="delete Role2Resource where roleid =:id";
		Query query=this.getSession().createQuery(hql);
		query.setInteger("id", roleid);
		query.executeUpdate();
		//保存
		for(int i=0;i<ids.size();i++){
			Role2Resource r2r = new Role2Resource();
			r2r.setRoleid(roleid);
			r2r.setResourceid(Integer.parseInt(ids.get(i).toString()));
			this.getSession().save(r2r);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<SysResource> getResourceByRole(Integer roleid) {
		String hql = "from SysResource b where b.id in (select r.resourceid from Role2Resource r where r.roleid='"+roleid+"')";
		List list = this.getSession().createQuery(hql).list();
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}

}
