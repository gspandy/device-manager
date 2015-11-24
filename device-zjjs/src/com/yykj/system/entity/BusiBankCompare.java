package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "busi_bankcompore")
public class BusiBankCompare {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(name = "hspcode")
	private String hspcode;
	
	@Column(name = "bankcard")
	private String bankcard;
	
	@Column(name = "bankdealdate")
	private String bankdealdate;
	
	@Column(name = "bankdealnum")
	private String bankdealnum;
	
	@Column(name = "dealtime")
	private String dealtime;
	
	@Column(name = "dealamount")
	private String dealamount;
	
	@Column(name = "hspdealdate")
	private String hspdealdate;
	
	@Column(name = "hspdealnum")
	private String hspdealnum;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getHspcode() {
		return hspcode;
	}

	public void setHspcode(String hspcode) {
		this.hspcode = hspcode;
	}

	public String getBankcard() {
		return bankcard;
	}

	public void setBankcard(String bankcard) {
		this.bankcard = bankcard;
	}

	public String getBankdealdate() {
		return bankdealdate;
	}

	public void setBankdealdate(String bankdealdate) {
		this.bankdealdate = bankdealdate;
	}

	public String getBankdealnum() {
		return bankdealnum;
	}

	public void setBankdealnum(String bankdealnum) {
		this.bankdealnum = bankdealnum;
	}

	public String getDealtime() {
		return dealtime;
	}

	public void setDealtime(String dealtime) {
		this.dealtime = dealtime;
	}

	public String getDealamount() {
		return dealamount;
	}

	public void setDealamount(String dealamount) {
		this.dealamount = dealamount;
	}

	public String getHspdealdate() {
		return hspdealdate;
	}

	public void setHspdealdate(String hspdealdate) {
		this.hspdealdate = hspdealdate;
	}

	public String getHspdealnum() {
		return hspdealnum;
	}

	public void setHspdealnum(String hspdealnum) {
		this.hspdealnum = hspdealnum;
	}
}
