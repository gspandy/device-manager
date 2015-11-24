package com.yykj.system.entity.requestenity;

public class UnionPay2015007 {
	private String tranType     ;//XF：消费 CZ：冲正
	private String bankFlag     ;// ‘3’：银联
	private String amount       ;//产生的交易金额 比如：4.50以分为单位
	private String authNum      ;//预授权号 不存在直接 
	private String batchNum     ;//批次号 不存在直接 
	private String cardName     ;//发卡行名称 不存在直接 
	private String expDate      ;//卡有效期 不存在直接 
	private String forUpdate    ;//保留信息 不存在直接 
	private String merchantName ;//商户名称 不存在直接 
	private String merchantNum  ;//商户号
	private String refNum       ;//检索参考号(银行交易流水号)
	private String retMsg       ;//交易结果返回描述  “00”：交易成功（详见终端常见交易返回码）
	private String termId       ;//终端号
	private String traceNum     ;//交易流水号
	private String track2       ;//二磁道数据
	private String track3       ;//三磁道数据
	private String tranCardNum  ;//交易卡号
	private String tranDate     ;//交易日期 YYYYMMDD
	private String tranTime     ;//交易时间 HHMMSS
	private String version      ;//版本号
	private String balanceAmount;//余额 查询用 分为单位  第一位：正负 位，表示金额的正负值
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	public String getBankFlag() {
		return bankFlag;
	}
	public void setBankFlag(String bankFlag) {
		this.bankFlag = bankFlag;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getAuthNum() {
		return authNum;
	}
	public void setAuthNum(String authNum) {
		this.authNum = authNum;
	}
	public String getBatchNum() {
		return batchNum;
	}
	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getForUpdate() {
		return forUpdate;
	}
	public void setForUpdate(String forUpdate) {
		this.forUpdate = forUpdate;
	}
	public String getMerchantName() {
		return merchantName;
	}
	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}
	public String getMerchantNum() {
		return merchantNum;
	}
	public void setMerchantNum(String merchantNum) {
		this.merchantNum = merchantNum;
	}
	public String getRefNum() {
		return refNum;
	}
	public void setRefNum(String refNum) {
		this.refNum = refNum;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getTermId() {
		return termId;
	}
	public void setTermId(String termId) {
		this.termId = termId;
	}
	public String getTraceNum() {
		return traceNum;
	}
	public void setTraceNum(String traceNum) {
		this.traceNum = traceNum;
	}
	public String getTrack2() {
		return track2;
	}
	public void setTrack2(String track2) {
		this.track2 = track2;
	}
	public String getTrack3() {
		return track3;
	}
	public void setTrack3(String track3) {
		this.track3 = track3;
	}
	public String getTranCardNum() {
		return tranCardNum;
	}
	public void setTranCardNum(String tranCardNum) {
		this.tranCardNum = tranCardNum;
	}
	public String getTranDate() {
		return tranDate;
	}
	public void setTranDate(String tranDate) {
		this.tranDate = tranDate;
	}
	public String getTranTime() {
		return tranTime;
	}
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getBalanceAmount() {
		return balanceAmount;
	}
	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}
}
