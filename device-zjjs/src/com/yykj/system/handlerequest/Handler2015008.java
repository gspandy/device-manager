package com.yykj.system.handlerequest;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;
import com.yykj.system.socket.socketclient4his.HandlerHis4001;

/**
 * 处理2015008交易 
 * 查询挂号类别
 * @author QinShuJin
 *
 */
public class Handler2015008 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015008.class);
	@Override
	public String handleRequest(Map<String, String> title,String deviceRequestXml) {
		String responseXml = null;
		String ChannelDate = title.get("ChannelDate"); // 业务请求时间
		String trancode = title.get("TranCode"); // 业务代码

		if ("2015008".equals(trancode)) {
			try {
				//1.请求HIS服务器
				List<String> types = HandlerHis4001.handler4001();
				//2.组装返回xml
				responseXml = createResponseXml(types);
			} catch (Exception e) {
				e.printStackTrace();
				responseXml = HandlerUtil.getErrorXml(trancode, "查询失败");
			}
		} else {
			responseXml = this.getSuccessor().handleRequest(title,deviceRequestXml);
		}

		return responseXml;
	}

	
	/**
	 * 组装备返回XML
	 * @param types
	 */
	public String createResponseXml(List<String> types){
		StringBuilder responseXml = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		responseXml.append("<Root>");
			responseXml.append("<Head>");
				responseXml.append("<TranCode>2015008</TranCode>");
				responseXml.append("<RspCode>0</RspCode>");
				responseXml.append("<RspMsg>查询成功</RspMsg>");
			responseXml.append("</Head>");
			responseXml.append("<Data>");
				responseXml.append("<List>");
				for (String type : types) {
					responseXml.append("<Item><CardTypeName>"+type+"</CardTypeName></Item>");
				}
				responseXml.append("</List>");
			responseXml.append("</Data>");
		responseXml.append("</Root>");
		return responseXml.toString();
	}
}
