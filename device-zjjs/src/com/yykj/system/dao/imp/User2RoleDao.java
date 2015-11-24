package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.system.dao.IUser2RoleDao;
import com.yykj.system.entity.Role2Resource;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.User2Role;
import com.yykj.system.entity.UserInfo;

@Repository("u2rDao")
public class User2RoleDao extends BaseDao implements IUser2RoleDao {

	@Override
	public void saveUser2Role(Integer userid, Integer roleid) {
		List list = this.getSession().createQuery("from User2Role b where b.userid='"+userid+"'").list();		
		if(list.size()==0){//新增
			if(roleid!=null){
				User2Role ur = new User2Role();
				ur.setUserid(userid);
				ur.setRoleid(roleid);
				this.getSession().save(ur);
			}			
		}else if(list.size()==1){
			User2Role u = (User2Role)list.get(0);
			if(roleid!=null){//修改
				u.setRoleid(roleid);
				this.getSession().update(u);
			}else{//取消
				String hql="delete User2Role where id=:id";
				Query query=this.getSession().createQuery(hql);
				query.setInteger("id", u.getId());
				query.executeUpdate();
			}
		}else{}				
	}

	@Override
	public SysRole getRoleByUserId(Integer userid) {
		String hql = "from SysRole b where b.id=(select u.roleid from User2Role u where u.userid='"+userid+"')";
		List list = this.getSession().createQuery(hql).list();
		if(list.size()==1){
			return (SysRole)list.get(0);
		}else{
			return null;
		}
	}

	@Override
	public void saveUsersRole(List<Integer> ids, Integer roleid){
		//删除所有角色对应的用户
		String hql="delete User2Role where roleid =:id";
		Query query=this.getSession().createQuery(hql);
		query.setInteger("id", roleid);
		query.executeUpdate();
		//保存
		for(int i=0;i<ids.size();i++){
			User2Role u2r = new User2Role();
			u2r.setRoleid(roleid);
			u2r.setUserid(Integer.parseInt(ids.get(i).toString()));
			this.getSession().save(u2r);
		}
	}

	
	@Override
	public List<UserInfo> getUsersByRole(Integer roleid) {
		String hql = "from UserInfo where id in(select userid from User2Role where roleid='"+roleid+"')";
		List list = this.getSession().createQuery(hql).list();
		return list;
	}
	
	@Override
	public List<UserInfo> getUsersByOrg(String orgid) {
		String hql = "from UserInfo where dept = '"+orgid+"'";
		List list = this.getSession().createQuery(hql).list();
		return list;
	}
}
