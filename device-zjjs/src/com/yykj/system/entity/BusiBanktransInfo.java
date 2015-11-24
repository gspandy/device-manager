package com.yykj.system.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 银行交易信息
 * @author QinShuJin
 *
 */

@Entity
@Table(name = "busi_banktrans_info")
public class BusiBanktransInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id; 				// 主键自增
	
	@Column(name = "yhmc")
	private String yhmc; 			// 用户姓名
	
	@Column(name = "idcard")
	private String idcard;	 		// 身份证号
	
	@Column(name = "yhzh")
	private String yhzh; 			// 银行账号
	
	@Column(name = "patientid")
	private String patientid; 		// 病人ID
	
	@Column(name = "ghid")
	private String ghid; 			// 挂号ID
	
	@Column(name = "userid")
	private String userid; 			// 机器码
	
	@Column(name = "cardtypeid")
	private String cardtypeid; 		// 卡类别
	
	@Column(name = "trantype")
	private String trantype; 		// 交易类别:0:消费1：冲正 2：异常
	
	@Column(name = "bankflag")
	private String bankflag; 		// 1:汉口银行 3:银联
	
	@Column(name = "shouldmoney")
	private Double shouldmoney; 	// 应收金额
	
	@Column(name = "actualmoney")
	private Double actualmoney; 	// 实收金额
	
	@Column(name = "bankmoney")
	private Double bankmoney; 		// 银扣款金额
	
	@Column(name = "local_trans_date")
	private Date localTransDate; 	// 发起交易时间：YYYY-MM-DD HH:MM:SS
	
	@Column(name = "local_trans_num")
	private String localTransNum; 	// 本地交易流水号
	
	@Column(name = "bank_trans_date")
	private Date bankTransDate; 	// 银行交易时间：YYYY-MM-DD HH:MM:SS
	
	@Column(name = "bank_trans_num")
	private String bankTransNum; 	// 银行交易流水号

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getYhmc() {
		return yhmc;
	}

	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getYhzh() {
		return yhzh;
	}

	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}

	public String getPatientid() {
		return patientid;
	}

	public void setPatientid(String patientid) {
		this.patientid = patientid;
	}

	public String getGhid() {
		return ghid;
	}

	public void setGhid(String ghid) {
		this.ghid = ghid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getCardtypeid() {
		return cardtypeid;
	}

	public void setCardtypeid(String cardtypeid) {
		this.cardtypeid = cardtypeid;
	}

	public String getTrantype() {
		return trantype;
	}

	public void setTrantype(String trantype) {
		this.trantype = trantype;
	}

	public String getBankflag() {
		return bankflag;
	}

	public void setBankflag(String bankflag) {
		this.bankflag = bankflag;
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

	public Double getBankmoney() {
		return bankmoney;
	}

	public void setBankmoney(Double bankmoney) {
		this.bankmoney = bankmoney;
	}

	public Date getLocalTransDate() {
		return localTransDate;
	}

	public void setLocalTransDate(Date localTransDate) {
		this.localTransDate = localTransDate;
	}

	public String getLocalTransNum() {
		return localTransNum;
	}

	public void setLocalTransNum(String localTransNum) {
		this.localTransNum = localTransNum;
	}

	public Date getBankTransDate() {
		return bankTransDate;
	}

	public void setBankTransDate(Date bankTransDate) {
		this.bankTransDate = bankTransDate;
	}

	public String getBankTransNum() {
		return bankTransNum;
	}

	public void setBankTransNum(String bankTransNum) {
		this.bankTransNum = bankTransNum;
	}
	
	

}
