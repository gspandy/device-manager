package com.yykj.system.handlerequest.iso8583;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.yykj.base.util.ReadXmlFileUtil;

/**
 * 包解析
 * @author QinShuJin
 *
 */
public class MessageParse {
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
			List<Element> template_eles = root.selectNodes("/j8583cn-config/parseinfo");
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
					maps.add(bit);
				}
				result_amp.put(templateid, maps);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result_amp;
	}
	
	
	/**
	 * 解析位图
	 * @param msg 响应报文
	 * @throws Exception 
	 */
	public static List<BitMap> parseBitMap(String msg,String msgtype) throws Exception{
		//1.//位图占16个字符  从26到42位为位图信息
		String bitmap = msg.substring(26,42);
		//2.将16进制的位图信息转换为二进制字符
		String binary_bitmap = Util.hexString2binaryString(bitmap); 
		//3.得到解析模板
		Map<String,Object> result_amp = explainConfigXml();
		List<BitMap> template = (List<BitMap>) result_amp.get(msgtype);
		
		List<BitMap> lists = new ArrayList<BitMap>();
		char [] maps = binary_bitmap.toCharArray();
		int j=0;
		for (int i = 0; i < maps.length; i++) {
			j++;
			char bit = maps[i];
			if(bit=='1'){
				for (BitMap bitmap2 : template) {
					if(bitmap2.getBit()==j){
						lists.add(bitmap2);
					}
				}
			}
		}
		return lists;
	}
	
	public static String parseMsg(String msg) throws Exception{
		String tpdu = msg.substring(0,10);		//TPDU头
		String header = msg.substring(10,22);	//报文头
		String msgtype = msg.substring(22,26);	//消息类型
		List<BitMap> maps = parseBitMap(msg,msgtype);
		
		String rsp_bitmap="";
		String rsp_value="";
		
		String msg_body = msg.substring(42,msg.length());
		InputStream in = new ByteArrayInputStream(msg_body.getBytes());
		for (BitMap bitMap : maps) {
			String format = bitMap.getFormat();
			String attribute = bitMap.getAttribute();
			String datatype= bitMap.getDatatype();
			int length = 0;
			if(datatype.equals("ASC")&&StringUtils.isEmpty(format)){ //如果值是asc码 长度翻倍
				length = bitMap.getLength()*2;
			}else{
				length = bitMap.getLength();
			}
			String value="";
			byte []  value_byte = null;
			if("LLVAR".equals(format)){
				byte [] buflen = new byte[2];
				in.read(buflen);
				String length_ =  new String(buflen);
				int size = Integer.parseInt(length_);
				if(size%2!=0){
					size++;
				}
				value_byte = new byte[size];
				in.read(value_byte);
			}else if("LLLVAR".equals(format)){
				byte [] buflen = new byte[4];
				in.read(buflen);
				String length_ =  new String(buflen);
				int size = Integer.parseInt(length_);
				if(size%2!=0){
					size++;
				}
				value_byte = new byte[size];
				in.read(value_byte);
			}else if("N".equals(attribute)){
				if(length%2!=0){
					length++;
				}
				value_byte = new byte[length];
				in.read(value_byte);
			}else{
				value_byte = new byte[length];
				in.read(value_byte);
			}
			
			
			
			if("N".equals(attribute)){
				value = new String(value_byte);
				rsp_bitmap += bitMap.getBit()+",";
				rsp_value += value+",";
				//System.out.println(bitMap.getBit()+":"+value);
			}else if("N_".equals(attribute)){
				if("ASC".equals(datatype)){
					String asc_value=new String(value_byte);
					value = Util.decodeASSII(asc_value);
				}else if("BCD".equals(datatype)){
					value = new String(value_byte);
				}
				
				rsp_bitmap += bitMap.getBit()+",";
				rsp_value += value+",";
				//System.out.println(bitMap.getBit()+":"+value);
			}else if("ANS".equals(attribute)){
				String asc_value=new String(value_byte);
				value = Util.decodeASSII(asc_value);
				
				rsp_bitmap += bitMap.getBit()+",";
				rsp_value += value+",";
				
				//System.out.println(bitMap.getBit()+":"+value);
			}else if("B".equals(attribute)){
				value=new String(value_byte);

				rsp_bitmap += bitMap.getBit()+",";
				rsp_value += value+",";
				
				//System.out.println(bitMap.getBit()+":"+value);
			}
					
		}
		String rsp_msg = rsp_bitmap.substring(0,rsp_bitmap.length()-1)+"#"+rsp_value.substring(0,rsp_value.length()-1);
		return rsp_msg;
	}
	
}
