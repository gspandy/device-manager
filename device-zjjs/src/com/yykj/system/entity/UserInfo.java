package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "sys_userinfo")
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name="usercode")
	private String usercode;			//用户编码
	
	@Column(name="password")
	private String password;			//用户密码
	
	@Column(name="username")
	private String username;			//用户姓名
	
	@Column(name="sex")
	private String sex;					//用户性别
	
	@Column(name="tel")
	private String tel;					//联系电话
	
	@Column(name="officetel")			//工作电话
	private String officetel;
	
	@Column(name="email")				//电子邮件地址
	private String email;
	
	@Column(name="isenabled")
	private String isenabled;			//是否启用
	
	@Transient
	private Pager pager;
	
	public String getOfficetel() {
		return officetel;
	}
	public void setOfficetel(String officetel) {
		this.officetel = officetel;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Pager getPager() {
		return pager;
	}
	public void setPager(Pager pager) {
		this.pager = pager;
	}
	public String getUsercode() {
		return usercode;
	}
	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIsenabled() {
		return isenabled;
	}
	public void setIsenabled(String isenabled) {
		this.isenabled = isenabled;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
}
