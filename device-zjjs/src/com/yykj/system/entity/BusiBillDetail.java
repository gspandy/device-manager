package com.yykj.system.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "busi_billdetail")
public class BusiBillDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(name = "billinfoid")
	private int billinfoid;
	
	@Column(name = "receiptno")
	private String receiptno;
	
	@Column(name = "execdept")
	private String execdept;
	
	@Column(name = "feestype")
	private String feestype;
	
	@Column(name = "feesitem")
	private String feesitem;
	
	@Column(name = "itemid")
	private String itemid;
	
	@Column(name = "itemname")
	private String itemname;
	
	@Column(name = "itemunit")
	private String itemunit;
	
	@Column(name = "num")
	private double num;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "shouldmoney_item")
	private double shouldmoney_item;
	
	@Column(name = "actualmoney_item")
	private double actualmoney_item;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBillinfoid() {
		return billinfoid;
	}

	public void setBillinfoid(int billinfoid) {
		this.billinfoid = billinfoid;
	}

	public String getReceiptno() {
		return receiptno;
	}

	public void setReceiptno(String receiptno) {
		this.receiptno = receiptno;
	}

	public String getExecdept() {
		return execdept;
	}

	public void setExecdept(String execdept) {
		this.execdept = execdept;
	}

	public String getFeestype() {
		return feestype;
	}

	public void setFeestype(String feestype) {
		this.feestype = feestype;
	}

	public String getFeesitem() {
		return feesitem;
	}

	public void setFeesitem(String feesitem) {
		this.feesitem = feesitem;
	}

	public String getItemid() {
		return itemid;
	}

	public void setItemid(String itemid) {
		this.itemid = itemid;
	}

	public String getItemname() {
		return itemname;
	}

	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	public String getItemunit() {
		return itemunit;
	}

	public void setItemunit(String itemunit) {
		this.itemunit = itemunit;
	}

	public double getNum() {
		return num;
	}

	public void setNum(double num) {
		this.num = num;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getShouldmoney_item() {
		return shouldmoney_item;
	}

	public void setShouldmoney_item(double shouldmoney_item) {
		this.shouldmoney_item = shouldmoney_item;
	}

	public double getActualmoney_item() {
		return actualmoney_item;
	}

	public void setActualmoney_item(double actualmoney_item) {
		this.actualmoney_item = actualmoney_item;
	}
}
