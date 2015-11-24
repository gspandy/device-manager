package com.yykj.system.handlerequest.iso8583;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.util.ReadXmlFileUtil;
import com.yykj.base.util.StringUtil;

/**
 * 报文打包
 * @author QinShuJin
 *
 */
@SuppressWarnings("all")
public class MessagePack {
	
	/**
	 * 组装报文0800
	 * @param maps
	 * @throws Exception 
	 */
	public static String pack0800Info(Map<String,String> requestMap) throws Exception{
		String TPDU=requestMap.get("Tpdu");
		String HEADER=requestMap.get("Header");
		String MSG_TYPE=requestMap.get("MsgType");
		
		Map<String,Object> result_amp = explainConfigXml();
		List<BitMap> maps = (List<BitMap>) result_amp.get(MSG_TYPE);
		List<BitMap> maps2 = new ArrayList<BitMap>();
		for (BitMap bitMap : maps) {
			String filedname = bitMap.getFiledname();
			if(!StringUtil.isNotNullOrEmpty(bitMap.getValue())){
				String requestValue = requestMap.get(filedname);
				if(StringUtil.isNotNullOrEmpty(requestValue)){
					bitMap.setValue(requestValue);
					maps2.add(bitMap);
				}
			}else{
				maps2.add(bitMap);
			}
		}
		
		//创建位图
		String bit_map = createBitMap(maps2);
		String msg = "";
		msg += TPDU;
		msg += HEADER;
		msg += MSG_TYPE;
		msg += bit_map;
		for (BitMap bitMap : maps2) {
			msg += handle(bitMap);
		}
		int length = msg.length()/2;
		String hex_length = Integer.toHexString(length);
		int c_length = 4-hex_length.length();
		for (int i = 0; i < c_length; i++) {
			hex_length = "0"+hex_length;
		}
		msg = hex_length+msg;
		return msg;
	}
	
	
	/**
	 * 组装报文0200
	 * @param maps
	 * @throws Exception 
	 */
	public static String pack0200Info(Map<String,String> requestMap) throws Exception{
		String TPDU=requestMap.get("Tpdu");
		String HEADER=requestMap.get("Header");
		String MSG_TYPE=requestMap.get("MsgType");
		
		Map<String,Object> result_amp = explainConfigXml();
		List<BitMap> maps = (List<BitMap>) result_amp.get(MSG_TYPE);
		List<BitMap> maps2 = new ArrayList<BitMap>();
		for (BitMap bitMap : maps) {
			String filedname = bitMap.getFiledname();
			if(!StringUtil.isNotNullOrEmpty(bitMap.getValue())){
				String requestValue = requestMap.get(filedname);
				if(StringUtil.isNotNullOrEmpty(requestValue)){
					bitMap.setValue(requestValue);
					maps2.add(bitMap);
				}
			}else if("ISMUST".equals(bitMap.getValue())){
				bitMap.setValue("");
				maps2.add(bitMap);
			} else{
				maps2.add(bitMap);
			}
		}
		
		//创建位图
		String bit_map = createBitMap(maps2);
		String msg = "";
		msg += TPDU;
		msg += HEADER;
		msg += MSG_TYPE;
		msg += bit_map;
		for (BitMap bitMap : maps2) {
			msg += handle(bitMap);
		}
		int length = msg.length()/2;
		String hex_length = Integer.toHexString(length);
		int c_length = 4-hex_length.length();
		for (int i = 0; i < c_length; i++) {
			hex_length = "0"+hex_length;
		}
		msg = hex_length+msg;
		return msg;
	}
	
	
	/**
	 * 组装报文0400
	 * @param maps
	 * @throws Exception 
	 */
	public static String pack0400Info(Map<String,String> requestMap) throws Exception{
		String TPDU=requestMap.get("Tpdu");
		String HEADER=requestMap.get("Header");
		String MSG_TYPE=requestMap.get("MsgType");
		
		Map<String,Object> result_amp = explainConfigXml();
		List<BitMap> maps = (List<BitMap>) result_amp.get(MSG_TYPE);
		List<BitMap> maps2 = new ArrayList<BitMap>();
		for (BitMap bitMap : maps) {
			String filedname = bitMap.getFiledname();
			if(!StringUtil.isNotNullOrEmpty(bitMap.getValue())){
				String requestValue = requestMap.get(filedname);
				if(StringUtil.isNotNullOrEmpty(requestValue)){
					bitMap.setValue(requestValue);
					maps2.add(bitMap);
				}
			}else if("ISMUST".equals(bitMap.getValue())){
				SimpleDateFormat df = new SimpleDateFormat("mmdd");
				String jylsh = requestMap.get("SerialNum");
				for (int i = 0; i < 6-jylsh.length(); i++) {
					jylsh = "0"+jylsh;
				}
				String bit61 = "000001"+jylsh+"1117"+"00000000000000";
				bitMap.setValue(bit61);
				maps2.add(bitMap);
			}else{
				maps2.add(bitMap);
			}
		}
		
		//创建位图
		String bit_map = createBitMap(maps2);
		String msg = "";
		msg += TPDU;
		msg += HEADER;
		msg += MSG_TYPE;
		msg += bit_map;
		for (BitMap bitMap : maps2) {
			msg += handle(bitMap);
		}
		int length = msg.length()/2;
		String hex_length = Integer.toHexString(length).toUpperCase();
		int c_length = 4-hex_length.length();
		for (int i = 0; i < c_length; i++) {
			hex_length = "0"+hex_length;
		}
		msg = hex_length+msg;
		return msg;
	}
	
	
	/**
	 * 处理类别
	 * @param bitmap
	 * @return
	 */
	public static String handle(BitMap bitmap){
		String datatype = bitmap.getDatatype();
		String format = bitmap.getFormat();
		int length = bitmap.getLength();
		String attribute = bitmap.getAttribute();
		String value = "";
		if("N".equals(attribute)){
			value = Util.leftFillZero(bitmap);
		}else if("ANS".equals(attribute)){
			value = Util.rightFillSpace(bitmap);
		}else if("N_".equals(attribute)){
			value = Util.leftFillZero_N(bitmap);
		}else if("B".equals(attribute)){
			value = bitmap.getValue();
		}
		return value;
	}

	
	
	/**
	 * 创建位图
	 * @param maps
	 * @return
	 */
	public static String createBitMap(List<BitMap> maps){
		//64位  位图
		String bitmap_str="00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		bitmap_str += "00000000";
		for (BitMap bitMap : maps) {
			int bit = bitMap.getBit();
			bitmap_str = bitmap_str.substring(0,bit-1)+"1"+bitmap_str.substring(bit);
		}
		//将二进制字符串转为16进制字符串
		String hexStr = Util.binaryString2hexString(bitmap_str).toUpperCase();
		return hexStr;
	}
	
	/**
	 * 解析模版文件
	 * @param requestXML
	 * @return
	 */
	private static Map<String,Object> explainConfigXml() throws Exception{
		String tempxml = ReadXmlFileUtil.getXml("ISO8583Config.xml", "com/yykj/system/handlerequest/iso8583/");
		Map<String,Object> result_amp = new HashMap<String,Object>();
		try {
			Document docs = DocumentHelper.parseText(tempxml);
			Element root = docs.getRootElement();
			List<Element> template_eles = root.selectNodes("/j8583cn-config/template");
			for (Element template : template_eles) {
				List<Element> template_infos = template.elements();
				String templateid = template.attributeValue("msgtypeid");
				List<BitMap> maps = new ArrayList<BitMap>();
				for (Element element : template_infos) {
					BitMap bit = new BitMap();
					bit.setBit(Integer.parseInt(element.attributeValue("bit")));
					bit.setDatatype(element.attributeValue("datatype"));
					bit.setFormat(element.attributeValue("format"));
					bit.setLength(Integer.parseInt(element.attributeValue("length")));
					bit.setAttribute(element.attributeValue("attribute"));
					bit.setFiledname(element.attributeValue("filedname"));
					bit.setValue(element.getText());
					maps.add(bit);
				}
				result_amp.put(templateid, maps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_amp;
	}
}
