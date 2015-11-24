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
import com.yykj.system.entity.hisentity.Info4002;
import com.yykj.system.socket.socketclient4his.HandlerHis4002;

/**
 * 处理2015009交易 
 * 查询可挂号科室
 * 
 * @author QinShuJin 
 * 2015年9月21日 下午3:47:48
 */
@SuppressWarnings("all")
public class Handler2015009 extends Handler {
	private Logger log = LogUtil.getInstance().getLogger(Handler2015009.class);

	@Override
	public String handleRequest(Map title, String deviceRequestXml) {
		String responseXml = null;
		String trancode = (String) title.get("TranCode");
		try {
			if ("2015009".equals(trancode)) {
				Map map_deviceRequest = explainDeviceRequestXml(deviceRequestXml);
				Info4002 info = HandlerHis4002.handler4002(map_deviceRequest);
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

	public String createResponseXml(Info4002 info) {
		StringBuilder responseXml = new StringBuilder("<?xml version=\"1.0\" encoding=\"GBK\" standalone=\"no\"?>");
		responseXml.append("<Root>");
			responseXml.append("<Head>");
				responseXml.append("<TranCode>2015009</TranCode>");
				responseXml.append("<RspCode>0</RspCode>");
				responseXml.append("<RspMsg>查询成功</RspMsg>");
			responseXml.append("</Head>");
			responseXml.append("<Data>");
				responseXml.append("<Count>").append(info.getCount()).append("</Count>");
				responseXml.append("<ReturnQty>").append(info.getReturnQty()).append("</ReturnQty>");
				responseXml.append("<List>");
					List<Dept4002> depts = info.getDepts();
					for (Dept4002 dept : depts) {
						responseXml.append("<Item><DeptId>").append(dept.getDeptId()).append("</DeptId><DeptName>").append(dept.getDeptName()).append("</DeptName></Item>");
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
