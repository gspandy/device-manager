package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "busi_deal_log")
public class BusiDealLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "receiptno")
	private String receiptno;
	
	@Column(name = "yhmc")
	private String yhmc;
	
	@Column(name = "yhzh")
	private String yhzh;
	
	@Column(name = "ghid")
	private String ghid;
	
	@Column(name = "idcard")
	private String idcard;
	
	@Column(name = "patientid")
	private String patientid;
	
	@Column(name = "userid")
	private String userid;
	
	@Column(name = "requestxml")
	private String requestxml;
	
	@Column(name = "responsexml")
	private String responsexml;
	
	@Column(name = "devicerequestxml")
	private String devicerequestxml;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public String getYhmc() {
		return yhmc;
	}

	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}

	public String getYhzh() {
		return yhzh;
	}

	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

	public String getGhid() {
		return ghid;
	}

	public void setGhid(String ghid) {
		this.ghid = ghid;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getRequestxml() {
		return requestxml;
	}

	public void setRequestxml(String requestxml) {
		this.requestxml = requestxml;
	}

	public String getResponsexml() {
		return responsexml;
	}

	public void setResponsexml(String responsexml) {
		this.responsexml = responsexml;
	}

	public String getDevicerequestxml() {
		return devicerequestxml;
	}

	public void setDevicerequestxml(String devicerequestxml) {
		this.devicerequestxml = devicerequestxml;
	}
}
