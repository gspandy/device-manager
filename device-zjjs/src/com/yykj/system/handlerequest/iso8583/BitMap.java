package com.yykj.system.handlerequest.iso8583;


/**
 * 域配置
 * @author QinShuJin
 *
 */
public class BitMap {
	private int bit;		//位图
	private String datatype;//数据类型
	private String format;	//格式
	private int length;		//长充
	private String attribute; //属性
	private String value;	//值 
	private String filedname; //字段值
	
	
	public int getBit() {
		return bit;
	}
	public void setBit(int bit) {
		this.bit = bit;
	}
	public String getDatatype() {
		return datatype;
	}
	public void setDatatype(String datatype) {
		this.datatype = datatype;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getFiledname() {
		return filedname;
	}
	public void setFiledname(String filedname) {
		this.filedname = filedname;
	}
}

