package com.yykj.system.entity.hisentity;
/**
 * HIS 5001 单据详情
 * @author QinShuJin
 * 2015年9月21日 下午5:11:44
 */
public class BilllDetail5001 {
	private String registerNo;	//挂号ID
	private String receiptNo;	//单据号
	private String receiptTime;	//开单时间
	private String billDept;	//开单部门
	private String execDept;	//执行部门
	private String doctor;		//医生姓名
	private String feesType;	//费别
	private String feesItem;	//收据费目
	private String groupID;		//诊疗项目ID
	private String groupName;	//诊疗项目名称
	private String itemID;		//收费ID
	private String itemName;	//收费名称
	private String itemUnit;	//计量单位
	private String num;			//数量
	private String price;		//单价
	private String shouldMoney;	//应收金额
	private String actualMoney;	//实收金额
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
	public String getExecDept() {
		return execDept;
	}
	public void setExecDept(String execDept) {
		this.execDept = execDept;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getFeesType() {
		return feesType;
	}
	public void setFeesType(String feesType) {
		this.feesType = feesType;
	}
	public String getFeesItem() {
		return feesItem;
	}
	public void setFeesItem(String feesItem) {
		this.feesItem = feesItem;
	}
	public String getGroupID() {
		return groupID;
	}
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getItemID() {
		return itemID;
	}
	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
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
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	
	
}
