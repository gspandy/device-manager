package com.yykj.system.handlerequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.LogUtil;
import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Doctor4003;
import com.yykj.system.entity.hisentity.Info4002;
import com.yykj.system.entity.hisentity.Info4003;
import com.yykj.system.socket.socketclient4his.HandlerHis4003;

/**
 * 处理2015010交易 
 * 查询可挂号科室
 * 
 * @author QinShuJin 
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015010 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015010.class);

	@Override
	public String handleRequest(Map title, String deviceRequestXml) {
		String responseXml = null;
		String trancode = (String) title.get("TranCode");
		try {
			if ("2015010".equals(trancode)) {
				Map map_deviceRequest = explainDeviceRequestXml(deviceRequestXml);
				Info4003 info = HandlerHis4003.handler4003(map_deviceRequest);
				if ("1".equals(info.getResultCode())) {
					responseXml = HandlerUtil.getErrorXml(trancode,info.getErrorMsg());
				} else {
					responseXml = createResponseXml(info);
				}
			} else {
				responseXml = getSuccessor().handleRequest(title,deviceRequestXml);
			}
		} catch (Exception e) {
			e.printStackTrace();
			responseXml = HandlerUtil.getErrorXml(trancode, e.getMessage());
		}
		return responseXml.toString();
	}

	public String createResponseXml(Info4003 info) {
		StringBuilder responseXml = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		responseXml.append("<Root>");
			responseXml.append("<Head>");
				responseXml.append("<TranCode>2015010</TranCode>");
				responseXml.append("<RspCode>0</RspCode>");
				responseXml.append("<RspMsg>查询成功</RspMsg>");
			responseXml.append("</Head>");
			responseXml.append("<Data>");
				responseXml.append("<Count>").append(info.getCount()).append("</Count>");
				responseXml.append("<ReturnQty>").append(info.getReturnQty()).append("</ReturnQty>");
				responseXml.append("<List>");
					List<Doctor4003> dcotors = info.getDoctors();
					for (Doctor4003 doctor : dcotors) {
						responseXml.append("<Item>");
						responseXml.append("<AsRowid>").append(doctor.getAsRowid()).append("</AsRowid>");
						responseXml.append("<MarkId>").append(doctor.getMarkId()).append("</MarkId>");
						responseXml.append("<MarkDesc>").append(doctor.getMarkDesc()).append("</MarkDesc>");
						responseXml.append("<SessionType>").append(doctor.getSessionType()).append("</SessionType>");
						responseXml.append("<HBTime>").append(doctor.gethBTime()).append("</HBTime>");
						responseXml.append("<RegCount>").append(doctor.getRegCount()).append("</RegCount>");
						responseXml.append("<Price>").append(doctor.getPrice()).append("</Price>");
						responseXml.append("<IsTime>").append(doctor.getIsTime()).append("</IsTime>");
						responseXml.append("</Item>");
					}
				responseXml.append("</List>");
			responseXml.append("</Data>");
		responseXml.append("</Root>");
		return responseXml.toString();
	}

	/**
	 * 解析自助设备请求XML
	 * 
	 * @param requestXML
	 * @return
	 */
	private static Map<String, String> explainDeviceRequestXml(String requestXML)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();
			List<Element> data_eles = root.selectNodes("/Root/Data");
			for (Element head : data_eles) {
				List<Element> headInfos = head.elements();
				for (Element element : headInfos) {
					resultMap.put(element.getName(), element.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析请求2015009请求XML异常");
		}
		return resultMap;
	}
}
