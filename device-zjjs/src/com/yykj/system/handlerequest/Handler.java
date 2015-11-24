package com.yykj.system.handlerequest;

import java.util.Map;

/**
 * 处理者抽象类
 * @author QinShuJin
 * 2015年7月10日 下午2:34:51
 */
public abstract class Handler {

	protected Handler successor;

	public abstract String handleRequest(Map<String,String> title,String deviceRequestXml);

	public Handler getSuccessor() {
		return successor;
	}
	public void setSuccessor(Handler successor) {
		this.successor = successor;
	}

}
