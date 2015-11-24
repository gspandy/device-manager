package com.yykj.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "busi_billinfo")
public class BusiBillInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;	//主键自增
	
	@Column(name = "banktransid")
	private int      banktransid;  //Busi_banktrans _info:银行交易记录的外键
	
	@Column(name = "receiptno")
	private String   receiptno  ;  //单据号
	
	@Column(name = "receipttime")
	private Date     receipttime;  //开单时间
	
	@Column(name = "billdept")
	private String   billdept   ;  //开单科室
	
	@Column(name = "doctor")
	private String   doctor     ;  //开单医生
	
	@Column(name = "shouldmoney")
	private Double   shouldmoney;  //应收金额
	
	@Column(name = "actualmoney")
    private Double   actualmoney;  //实收金额

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBanktransid() {
		return banktransid;
	}

	public void setBanktransid(int banktransid) {
		this.banktransid = banktransid;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public Date getReceipttime() {
		return receipttime;
	}

	public void setReceipttime(Date receipttime) {
		this.receipttime = receipttime;
	}

	public String getBilldept() {
		return billdept;
	}

	public void setBilldept(String billdept) {
		this.billdept = billdept;
	}

	public String getDoctor() {
		return doctor;
	}

	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}

	public Double getShouldmoney() {
		return shouldmoney;
	}

	public void setShouldmoney(Double shouldmoney) {
		this.shouldmoney = shouldmoney;
	}

	public Double getActualmoney() {
		return actualmoney;
	}

	public void setActualmoney(Double actualmoney) {
		this.actualmoney = actualmoney;
	}
	
}
