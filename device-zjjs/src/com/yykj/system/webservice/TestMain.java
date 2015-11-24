package com.yykj.system.webservice;

/**
 * @author QinShuJin
 * 2015年10月9日 下午5:17:46
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.ReadXmlFileUtil;

public class TestMain {

	public static void main(String args[]) throws Exception {

		// 使用 RPC 方式调用 WebService
		int sucess=0;
		int fail = 0;
		for (int i = 0; i < 1; i++) {
			String msg = getRPCServiceClient();
			System.out.println("第"+(i+1)+"次:"+msg);
			Map<String,String> map = explainDeviceRequestXml(msg);
			if(map.get("RspCode").equals("0")){
				sucess++;
			}else{
				fail++;
			}
			//Thread.sleep(500);
		}
		
		double cgl = (fail/Double.valueOf(100))*100;
		System.out.println("失败率:"+cgl+"%");
	}

	public static String getRPCServiceClient() throws Exception {
		RPCServiceClient serviceClient;
		String responseMsg = "";
		try {
			serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();

			// 指定调用 WebService 的 URL
			EndpointReference targetEPR = new EndpointReference(

			// 是浏览器中的访问地址
			"http://localhost:7070/device-zjjs/services/deviceRequestHandleWS?wsdl");
			options.setTo(targetEPR);
			// 指定 sum 方法的参数值
			String xml =ReadXmlFileUtil.getXml("2015012_request.xml","com/yykj/system/handlerequest/xml/");
			Object[] opAddEntryArgs = new Object[] { xml};
			// 指定 sum 方法返回值的数据类型的 Class 对象
			Class[] classes = new Class[] { String.class};
			// 指定要调用的 sum 方法及 WSDL 文件的命名空间
			// 第一个参数浏览器中看到 targetNamespace 的值
			// targetNamespace="http://service.axis.lcb.com" 第二个参数是方法名

			QName opAddEntry = new QName("http://webservice.system.yykj.com", "handleRequest");
			// 调用 sum 方法并输出该方法的返回值
			responseMsg = serviceClient.invokeBlocking(opAddEntry,opAddEntryArgs, classes)[0].toString();

		} catch (AxisFault e) {
			e.printStackTrace();
		}
		return responseMsg;
	}
	
	private static Map<String, String> explainDeviceRequestXml(String requestXML)
			throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();

		try {
			Document docs = DocumentHelper.parseText(requestXML);
			Element root = docs.getRootElement();
			List<Element> data_eles = root.selectNodes("/Root/Head");
			for (Element head : data_eles) {
				List<Element> headInfos = head.elements();
				for (Element element : headInfos) {
					resultMap.put(element.getName(), element.getText());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("解析请求3015001请求XML异常");
		}
		return resultMap;
	}
}