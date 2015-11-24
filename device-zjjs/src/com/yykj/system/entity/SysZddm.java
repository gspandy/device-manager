package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 字典代码
 * @author QinShuJin
 * 2015年7月13日 上午10:22:41
 */
@Entity
@Table(name = "sys_zddm")
public class SysZddm {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name="classtype")
	private String classtype;
	
	@Column(name="classname")
	private String classname;
	
	@Column(name="entrycode")
	private String entrycode;
	
	@Column(name="entryvalue")
	private String entryvalue;
	
	@Column(name="state")
	private String state;
	
	@Column(name="pid")
	private String pid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClasstype() {
		return classtype;
	}

	public void setClasstype(String classtype) {
		this.classtype = classtype;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getEntrycode() {
		return entrycode;
	}

	public void setEntrycode(String entrycode) {
		this.entrycode = entrycode;
	}

	public String getEntryvalue() {
		return entryvalue;
	}

	public void setEntryvalue(String entryvalue) {
		this.entryvalue = entryvalue;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	
}
