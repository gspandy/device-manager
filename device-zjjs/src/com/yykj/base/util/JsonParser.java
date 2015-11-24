package com.yykj.base.util;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@SuppressWarnings("all")
public class JsonParser {

	private static JsonConfig jconfig = null;

	public JsonParser() {
	}

	private static JsonConfig getJConfig() {
		if (jconfig == null) {
			jconfig = new JsonConfig();
			jconfig.registerJsonValueProcessor(Date.class,
					new DateValueProcessor());
			jconfig.registerJsonValueProcessor(Timestamp.class,
					new DateValueProcessor());
			jconfig.registerJsonValueProcessor(java.util.Date.class,
					new DateValueProcessor());
		}
		return jconfig;
	}

	private static String getJsonData(String json) {
		if (json.startsWith("{") || json.startsWith("[")) {
			return json;
		} else {
			int place = Math.max(json.lastIndexOf("}"), json.lastIndexOf("]"));
			int len = json.length() - place;
			return json.substring(len - 1, place + 1);
		}
	}

	public static String map2Json(Map map) {
		JSONObject json = JSONObject.fromObject(map, getJConfig());
		return json.toString();
	}

	public static String Obj2Json(Object obj) {
		return map2Json(BeanUtil.getPMap(obj));
	}

	public static String listMap2Json(List list) {
		JSONArray jsonArray = new JSONArray();
		Map map;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); jsonArray
				.add(JSONObject.fromObject(map, getJConfig()))) {
			Object obj = iterator.next();
			map = (Map) obj;
		}

		return jsonArray.toString();
	}

	public static String list2Json(List list) {
		JSONArray jsonArray = new JSONArray();
		Object obj;
		for (Iterator iterator = list.iterator(); iterator.hasNext(); jsonArray
				.add(JSONObject.fromObject(BeanUtil.getPMap(obj), getJConfig())))
			obj = iterator.next();

		return jsonArray.toString();
	}

	public static Map json2Map(String json) {
		JSONObject jsonObject = JSONObject.fromObject(getJsonData(json));
		return jsonObject;
	}

	public static Object json2Object(String json, String _class) {
		JSONObject jsonObject = JSONObject.fromObject(getJsonData(json));
		return BeanUtil.map2Object(jsonObject, _class);
	}

	public static List json2List(String json) {
		JSONArray jsonArray = JSONArray.fromObject(getJsonData(json));
		return jsonArray;
	}

	public static String listValue2Json(List list) {
		StringBuffer jsonBuffer = new StringBuffer();
		jsonBuffer.append("[");
		int i = 0;
		for (Iterator iterator = list.iterator(); iterator.hasNext();) {
			Object obj = iterator.next();
			if (i > 0)
				jsonBuffer.append(",");
			JSONArray jArray = JSONArray.fromObject(BeanUtil.getVList(obj));
			jsonBuffer.append(jArray.toString());
			i++;
		}

		jsonBuffer.append("]");
		return jsonBuffer.toString();
	}
}
