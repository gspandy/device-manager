package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户、角色、资源视图
 * 用于权限控制
 * @author QinShuJin
 * 2015年8月4日 上午11:27:40
 */
@Entity
@Table(name = "user_role")
public class Security_V {
	@Id
	@Column(name = "resourceid")
	private int resouceid;
	
	@Column(name = "userid")
	private int userid;
	
	@Column(name = "roleid")
	private int roleid;
	
	@Column(name = "pid")
	private int pid;
	
	@Column(name = "resourcename")
	private String resourcename;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "icon")
	private String icon;

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getRoleid() {
		return roleid;
	}

	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}

	public int getResouceid() {
		return resouceid;
	}

	public void setResouceid(int resouceid) {
		this.resouceid = resouceid;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
}
