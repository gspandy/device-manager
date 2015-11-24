package com.yykj.system.entity.requestenity;

import java.util.List;

/**
 * 
 * @author QinShuJin
 * 2015年9月21日 下午5:39:36
 */
public class RequestInfo2015004 {
	private String yhzh;	//银行账号
	private String yhmc;	//用户名称
	private String ghId;	//挂号ID
	private String idCard;	//身份证号码
	private String patientId;	//病人ID
	private String userId;	//机器码
	private String cardTypeID;	//卡类型
	private List<BillInfo2015004> billInfos; //票据信息
	
	private UnionPay2015007 unionpay2015007 ; //银联返回信息
	
	public String getYhzh() {
		return yhzh;
	}
	public void setYhzh(String yhzh) {
		this.yhzh = yhzh;
	}
	public String getYhmc() {
		return yhmc;
	}
	public void setYhmc(String yhmc) {
		this.yhmc = yhmc;
	}
	public String getGhId() {
		return ghId;
	}
	public void setGhId(String ghId) {
		this.ghId = ghId;
	}
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public List<BillInfo2015004> getBillInfos() {
		return billInfos;
	}
	public void setBillInfos(List<BillInfo2015004> billInfos) {
		this.billInfos = billInfos;
	}
	public String getCardTypeID() {
		return cardTypeID;
	}
	public void setCardTypeID(String cardTypeID) {
		this.cardTypeID = cardTypeID;
	}
	public UnionPay2015007 getUnionpay2015007() {
		return unionpay2015007;
	}
	public void setUnionpay2015007(UnionPay2015007 unionpay2015007) {
		this.unionpay2015007 = unionpay2015007;
	}
}
