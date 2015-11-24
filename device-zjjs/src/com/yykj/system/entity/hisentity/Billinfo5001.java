package com.yykj.system.entity.hisentity;

import java.util.List;

/**
 * HIS 5001 返回值
 * @author QinShuJin
 * 2015年10月10日 上午11:56:27
 */
public class Billinfo5001 {
	private String transCode;
	private String resultCode;
	private String errorMsg;
	private String registerNo;
	List<BilllDetail5001> BillDetail5001s;
	public String getTransCode() {
		return transCode;
	}
	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getRegisterNo() {
		return registerNo;
	}
	public void setRegisterNo(String registerNo) {
		this.registerNo = registerNo;
	}
	public List<BilllDetail5001> getBillDetail5001s() {
		return BillDetail5001s;
	}
	public void setBillDetail5001s(List<BilllDetail5001> billDetail5001s) {
		BillDetail5001s = billDetail5001s;
	}
}
