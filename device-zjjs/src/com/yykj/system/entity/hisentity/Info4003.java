package com.yykj.system.entity.hisentity;

import java.util.List;

/**
 * HIS 4003 返回信息
 * 
 * @author QinShuJin
 * 
 */
public class Info4003 {
	private String transCode;
	private String resultCode;
	private String errorMsg;
	private String count;
	private String returnQty;
	private List<Doctor4003> doctors;
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
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getReturnQty() {
		return returnQty;
	}
	public void setReturnQty(String returnQty) {
		this.returnQty = returnQty;
	}
	public List<Doctor4003> getDoctors() {
		return doctors;
	}
	public void setDoctors(List<Doctor4003> doctors) {
		this.doctors = doctors;
	}
}
