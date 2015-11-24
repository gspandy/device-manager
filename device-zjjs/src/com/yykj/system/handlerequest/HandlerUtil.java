package com.yykj.system.handlerequest;

import com.yykj.base.util.SpringContextUtil;
public class HandlerUtil {
	
	/**
	 * 请求处理异常后获取异常的XML
	 * @param trancode
	 * @param erro_msg
	 * @return
	 */
	public static String getErrorXml(String trancode,String erro_msg){
		String errorXml="<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>"+
						"<Root>"+ 
							"<Head>"+
								"<TranCode>"+trancode+"</TranCode>"+
								"<RspCode>1</RspCode>"+
								"<RspMsg>"+erro_msg+"</RspMsg>"+
							"</Head>"+
						"</Root>";
		return errorXml;
	}
	
	
	/**
	 * 成功后封装头部HEAD信息
	 * @param trancode
	 * @param msg
	 * @return
	 */
	public static String getHandlerXml(String trancode,String flag,String msg){
		String errorXml="<Head>"+
							"<TranCode>"+trancode+"</TranCode>"+
							"<RspCode>"+flag+"</RspCode>"+
							"<RspMsg>"+msg+"</RspMsg>"+
						"</Head>";
		return errorXml;
	}
}
