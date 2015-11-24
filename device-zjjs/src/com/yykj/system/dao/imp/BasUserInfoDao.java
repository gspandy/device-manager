package com.yykj.system.dao.imp;

import java.security.MessageDigest;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.PublicUtil;
import com.yykj.system.dao.IBasUserInfoDao;
import com.yykj.system.entity.UserInfo;

@Repository("userDao")
public class BasUserInfoDao extends BaseDao implements IBasUserInfoDao {

	/****
	 *   ����md5����
	 *   @author Rex
	 */
	 public final static String[] hexDigits = {"0", "1", "2", "3", "4",      
         "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"}; 
	
	//����û����������õ�md5ֵ
	 public  String password2MD5(String password){
		if (password != null){      
           try{      
               MessageDigest md = MessageDigest.getInstance("MD5");      
               //ʹ��ָ�����ֽ������ժҪ���������£�Ȼ�����ժҪ����      
               byte[] results = md.digest(password.getBytes());      
               //���õ����ֽ��������ַ���      
               String resultString = byteArrayToHexString(results);      
               String pass =  resultString.toUpperCase();      
               return pass;    
           } catch(Exception ex){      
               ex.printStackTrace();      
           }      
       }      
       return null;  
	}

	public String MD5ToPassword(String pwd32) {   
		  char[] a = pwd32.toCharArray();   
		  for (int i = 0; i < a.length; i++) {   
		   a[i] = (char) (a[i] ^ 't');   
		  }   
		  String k = new String(a);   
		  return k;   
	} 
	 
	//���õ����ֽ��������ַ��� 
	private String byteArrayToHexString(byte[] b){      
	     StringBuffer resultSb = new StringBuffer();      
	     for (int i = 0; i < b.length; i++){      
	         resultSb.append(byteToHexString(b[i]));      
	     }      
	         return resultSb.toString();      
	     }    
		 
	private String byteToHexString(byte b){      
	     int n = b;      
	     if (n < 0)      
	     n = 256 + n;      
	     int d1 = n / 16;      
	     int d2 = n % 16;      
	     return hexDigits[d1] + hexDigits[d2];      
	}
	
	@Override
	public void save(UserInfo user) {
		String hql = "from UserInfo where usercode='"+user.getUsercode()+"'";
		List list = this.getSession().createQuery(hql).list();
		if(list.size()>0){
			throw new BusinessException("existed");
		}else{
			String pwd = user.getPassword();
			user.setPassword(this.password2MD5(pwd));
			this.getSession().save(user);
		}
		
	}

	@Override
	public void deleteUserInfoById(int userid) throws Exception {
		String hql="delete UserInfo where id=:id";
		Query query=this.getSession().createQuery(hql);
		query.setInteger("id", userid);
		query.executeUpdate();
	}

	@Override
	public UserInfo update(UserInfo user) {
		String hql1 = "from UserInfo where usercode='"+user.getUsercode()+"' and id<>"+user.getId()+"";
		List list = this.getSession().createQuery(hql1).list();
		if(list.size()>0){
			throw new BusinessException("existed");
		}else{
			String hql="update UserInfo set "
					+ "usercode=:usercode,"
					+ "username=:username,"
					+ "sex=:sex,"
					+ "tel=:tel,"
					+ "officetel=:officetel,"
					+ "email=:email,"
					+ "isenabled=:isenabled"
					+ " where id=:id";
			Query query=this.getSession().createQuery(hql);
			query.setInteger("id", user.getId());
			query.setString("usercode", user.getUsercode());
			query.setString("username", user.getUsername());
			query.setString("sex", user.getSex());
			query.setString("tel", user.getTel());
			query.setString("isenabled", user.getIsenabled());
			query.setString("officetel", user.getOfficetel());
			query.setString("email", user.getEmail());
			query.executeUpdate();
		}		
		return this.getUser(user.getId());
	}

	@Override
	public UserInfo getUser(int userid) {
		return (UserInfo) this.getSession().get(UserInfo.class,userid);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserInfo> getUsers() {
		return this.getSession().createQuery("from UserInfo order by id desc").list();
	}
	
	@Override
	public void updatePassword(int userid,String oldpwd,String password) {
		UserInfo user = this.getUser(userid);
		if(user==null){
			throw new BusinessException("�û������ڣ�");
		}else{
			if(!user.getPassword().equals(this.password2MD5(oldpwd))){
				throw new BusinessException("uncorrectpwd");
			}else{
				user.setPassword(this.password2MD5(password));
				this.update(user);
			}
			
		}
	}
	
	@Override
	public UserInfo validateUser(UserInfo user) throws Exception {
		UserInfo u = null;
		String usercode = user.getUsercode();
		String pwd = this.password2MD5(user.getPassword());//把用户输入的密码转换成md5值
		String sql = "from UserInfo u where u.usercode='"+usercode+"'"
				+ " and u.password='"+pwd+"'";
		List<UserInfo> list = this.getSession().createQuery(sql).list();
		if(list.size()==1){
			u = list.get(0);
			if("0".equals(u.getIsenabled())){
				throw new BusinessException("unabled");
			}
		}else if(list.size()==0){
			throw new BusinessException("error");
		}
		return u;
	}
	
	@Override
	public List<UserInfo> getUsersByParam(String type, String value, Integer pageIndex, Integer pageSize) {
		int firstResult = (pageIndex-1) * pageSize;
		int maxResult = pageSize;
		String str = "";
		String sql1 = "select count(*) from UserInfo u where 1=1";
		
		if("1".equals(type)){
			str += " and usercode like '%"+value+"%' order by id asc";			
		}else if("2".equals(type)){
			str += " and username like '%"+value+"%' order by id asc";
		}else if("0".equals(type)){
			str += "";
		}	
		Query query = this.getSession().createQuery(sql1+str);	
		Integer totalCount = Integer.parseInt(query.uniqueResult().toString());
		String sql2 = "from UserInfo u where 1=1 ";
		query = this.getSession().createQuery(sql2+str);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);	
		List<UserInfo> list = query.list();		
		if(list.size()>0){
			for (UserInfo userInfo : list) {
				userInfo.setPager(PublicUtil.getPager(totalCount, pageSize, pageIndex));
			}
		}		
		return list;
	}
	
	@Override
	public void resetPwd(List<Integer> ids) {
		if(ids.size()>0){
			for(int i=0;i<ids.size();i++){
				UserInfo user = (UserInfo) this.getSession().get(UserInfo.class,ids.get(i));
				if(user==null){
					throw new BusinessException("illegal");
				}else{
					user.setPassword(this.password2MD5("111111"));//重置默认111111
					this.update(user);
				}				
			}			
		}else{
			throw new BusinessException("none");
		}
	}
}
