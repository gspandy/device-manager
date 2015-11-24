package com.yykj.system.entity.requestenity;

import java.util.List;

/**未缴费单据
 * @author QinShuJin
 * 2015年9月21日 下午5:07:12
 */
public class BillInfo2015004 {
	private String receiptNo;	//单据号
	private String receiptTime;	//开单时间
	private String billDept;	//开单科室
	private String doctor;		//开单医生
	private String shouldMoney;	//应收总金额
	private String actualMoney; //实收总金额
	private List<BilllDetail2015004> datails; //单据下所药品和项目
	
	public String getReceiptNo() {
		return receiptNo;
	}
	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	public String getReceiptTime() {
		return receiptTime;
	}
	public void setReceiptTime(String receiptTime) {
		this.receiptTime = receiptTime;
	}
	public String getBillDept() {
		return billDept;
	}
	public void setBillDept(String billDept) {
		this.billDept = billDept;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getShouldMoney() {
		return shouldMoney;
	}
	public void setShouldMoney(String shouldMoney) {
		this.shouldMoney = shouldMoney;
	}
	public String getActualMoney() {
		return actualMoney;
	}
	public void setActualMoney(String actualMoney) {
		this.actualMoney = actualMoney;
	}
	public List<BilllDetail2015004> getDatails() {
		return datails;
	}
	public void setDatails(List<BilllDetail2015004> datails) {
		this.datails = datails;
	}
	

}
