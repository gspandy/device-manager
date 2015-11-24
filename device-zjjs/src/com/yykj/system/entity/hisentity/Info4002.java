package com.yykj.system.entity.hisentity;

import java.util.List;

/**
 * 
 * @author QinShuJin
 *封装HIS4002交易返回值
 */
public class Info4002 {
	private String transCode;
	private String resultCode;
	private String errorMsg;
	private String count;
	private String returnQty;
	private List<Dept4002> depts;
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
	public List<Dept4002> getDepts() {
		return depts;
	}
	public void setDepts(List<Dept4002> depts) {
		this.depts = depts;
	}
	
}
