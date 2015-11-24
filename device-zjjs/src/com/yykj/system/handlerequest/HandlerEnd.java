package com.yykj.system.handlerequest;

import java.util.Map;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;

/**
 * 终结责任链
 * @author QinShuJin
 *
 */
public class HandlerEnd extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(HandlerEnd.class);

	@Override
	public String handleRequest(Map<String, String> title,String deviceRequestXml) {
		String responseXml  = (new StringBuilder(
				"<Root><Head><TranCode>0000000</TranCode><RspCode>"))
				.append("1").append("</RspCode>")
				.append("<RspMsg>").append("不存在的交易代码")
				.append("</RspMsg>").append("</Head>")
				.append("</Root>").toString();
		return responseXml;
	}
}
