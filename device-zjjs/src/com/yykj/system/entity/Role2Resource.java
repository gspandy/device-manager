package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 角色对应资源表
 * @author Rex
 */
@Entity
@Table(name = "sys_role2resource")
public class Role2Resource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "roleid")
	private Integer roleid;
	
	@Column(name = "resourceid")
	private Integer resourceid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getRoleid() {
		return roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	public Integer getResourceid() {
		return resourceid;
	}

	public void setResourceid(Integer resourceid) {
		this.resourceid = resourceid;
	}
}
