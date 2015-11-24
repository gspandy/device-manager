package com.yykj.system.webservice;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yykj.base.util.LogUtil;
import com.yykj.system.handlerequest.BusiHandler;
import com.yykj.system.handlerequest.Handler3015001;

/**
 * 设备请求处理ws服务
 * @author QinShuJin 
 * 2015年10月8日 下午4:12:33
 */
@Service
public class DeviceRequestHandleWS {

	private Logger log = LogUtil.getInstance().getLogger(DeviceRequestHandleWS.class);
	public String handleRequest(String request_xml) {
		String responseXml = "";
		log.info("客户端请求：" + request_xml);
		// 1.业务处理
		responseXml = BusiHandler.handler(request_xml);
		log.info("服务端返回：" + responseXml);

		return responseXml;
	}
}