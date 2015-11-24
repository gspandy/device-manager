package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 用户对应角色表
 * @author Rex
 */
@Entity
@Table(name = "sys_user2role")
public class User2Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "userid")
	private Integer userid;
	
	@Column(name = "roleid")
	private Integer roleid;
	
	@Column(name = "year")
	private String year;
	
	@Column(name = "admdivcode")
	private String admdivcode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAdmdivcode() {
		return admdivcode;
	}

	public void setAdmdivcode(String admdivcode) {
		this.admdivcode = admdivcode;
	}
}
