package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色表
 * @author Rex
 */
@Entity
@Table(name = "sys_role")
public class SysRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "rolecode")
	private String rolecode;
	
	@Column(name = "rolename")
	private String rolename;
	
	@Column(name = "stateflag")
	private String stateflag;
	
	@Column(name = "description")
	private String description;

	@Column(name = "year")
	private String year;
	
	@Column(name = "admdivcode")
	private String admdivcode;
	
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRolecode() {
		return rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getStateflag() {
		return stateflag;
	}

	public void setStateflag(String stateflag) {
		this.stateflag = stateflag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
