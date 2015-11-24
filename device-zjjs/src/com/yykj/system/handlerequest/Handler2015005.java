package com.yykj.system.handlerequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;

/**
 * 处理2015005交易 
 * 检查网络是否畅通
 * @author QinShuJin
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015005 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015005.class);

	@Override
	public String handleRequest(Map<String,String> title,String deviceRequestXml) {
		String responseXml = null;
			String ChannelDate = title.get("ChannelDate");	//业务请求时间
			String trancode  =title.get("TranCode");		//业务代码
			
			if ("2015005".equals(trancode)) {
				SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				Date date  = new Date();
				StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
				xml.append("<Root><Head>")
				.append("<TranCode>2015005</TranCode>")
				.append("<RspCode>0</RspCode>")
				.append("<RspMsg>"+df.format(date)+"</RspMsg>")
				.append("</Head></Root>");
				responseXml = xml.toString();
			} else {
				responseXml = getSuccessor().handleRequest(title,deviceRequestXml);
			}
		return responseXml.toString();
	}
}
