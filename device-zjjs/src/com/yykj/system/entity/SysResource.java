package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统资源表
 * @author Rex
 */
@Entity
@Table(name = "sys_resource")
public class SysResource {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "resourcecode")
	private String resourcecode;		//资源编码
	
	@Column(name = "resourcename")
	private String resourcename;		//资源名称
	
	@Column(name = "resourcetype")
	private String resourcetype;		//资源类型
	
	@Column(name = "resourceurl")
	private String resourceurl;			//资源链接
	
	@Column(name = "stateflag")
	private String stateflag;			//状态
	
	@Column(name = "description")
	private String description;			//描述
	
	@Column(name = "menutype")
	private Integer menutype;			//菜单类别
	
	@Column(name = "sort")		
	private int sort;//排序号
	
	@Column(name = "i_con")
	private String i_con; //资源图标
	
	@Column(name = "isenable")
	private String isenable; //是否启用
	
	public Integer getMenutype() {
		return menutype;
	}

	public void setMenutype(Integer menutype) {
		this.menutype = menutype;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResourcecode() {
		return resourcecode;
	}

	public void setResourcecode(String resourcecode) {
		this.resourcecode = resourcecode;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	public String getResourcetype() {
		return resourcetype;
	}

	public void setResourcetype(String resourcetype) {
		this.resourcetype = resourcetype;
	}

	public String getResourceurl() {
		return resourceurl;
	}

	public void setResourceurl(String resourceurl) {
		this.resourceurl = resourceurl;
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

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

	public String getI_con() {
		return i_con;
	}

	public void setI_con(String i_con) {
		this.i_con = i_con;
	}

	public String getIsenable() {
		return isenable;
	}

	public void setIsenable(String isenable) {
		this.isenable = isenable;
	}
	
	
}
