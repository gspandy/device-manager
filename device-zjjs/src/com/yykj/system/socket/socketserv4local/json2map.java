package com.yykj.system.socket.socketserv4local;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class json2map {

	public static void main(String[] args) {
		String json = "{\"agencyid\":\"00919\",\"agencyname\":\"孝感市广场环境卫生管理站\",\"colTradeDetails\":[{\"amt\":33,\"num\":1,\"proid\":\"10304335004\",\"proname\":\"环卫费（限于将垃圾处理单位作为事业单位管理的地方）\",\"rules\":\"0-0\",\"ruleunit\":\"\"},{\"amt\":334,\"num\":12,\"proid\":\"10304335005\",\"proname\":\"环卫费1（限于将垃圾处理单位作为事业单位管理的地方）\",\"rules\":\"1-2\",\"ruleunit\":\"\"}],\"drawer\":\"test\",\"guid\":\"009190000JKZ20150804000001\",\"jkqx\":\"5\",\"noticeno\":\"1234567890\",\"paybillno\":\"00000003\",\"payer\":\"test\",\"remark\":\"\",\"sxmc\":\"\",\"totamt\":33,\"tradedate\":\"2015-08-04\",\"tzjkrq\":\"2015-08-04\",\"ywdh\":\"\"}";
		 JSONObject jsonObject = JSONObject.fromObject(json);  
		
		 
		 JSONArray details = (JSONArray) jsonObject.get("colTradeDetails");
		 for (Object object : details) {
			 JSONObject jsonobj = (JSONObject) object;
			 System.out.println(jsonobj.get("proname"));
		}
	}
}
