package com.yykj.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



/**
 * 字符串工具类
 * @author xiajun
 *
 */
public class StringUtil {


    /**
     * 获取分隔符左边字符串
     * @param str
     * @param split
     * @return
     */
	public static String getStringLeft(String str,String split){
           return str.split(split)[0];		
	}
	
    /**
     * 获取分隔符右边字符串
     * @param str
     * @param split
     * @return
     */
	public static String getStringRight(String str,String split){
		   return str.split(split)[1];	
    }
	/**
	 * 获取多层分隔符中左边字符串数组
	 * @param str
	 * @param split
	 * @return
	 */
	public static String[]  getStringLeft(String str,String[] split){
		String[] oneArrays=str.split(split[0]);
		String[] result=new String[oneArrays.length];
		for(int i=0;i<oneArrays.length;i++){
			String twoStr=oneArrays[i].split(split[1])[0];
			result[i]=twoStr;
		}
		return result;
	}
	/**
	 * 获取多层分隔符中右边字符串数组
	 * @param str
	 * @param split
	 * @return
	 */
	public static String[]  getStringRight(String str,String[] split){
		String[] oneArrays=str.split(split[0]);
		String[] result=new String[oneArrays.length];
		for(int i=0;i<oneArrays.length;i++){
			String twoStr=oneArrays[i].split(split[1])[1];
			result[i]=twoStr;
		}
		return result;
	}
	
	
	 /**
     * 切割字符串
     * @param str
     * @param splitsign
     * @return
     */
	public static String[] split(String str, String splitsign) {   
	    int index;   
	    if (str == null || splitsign == null||"".equals(str)||"".equals(splitsign))   
	        return null;   
		    ArrayList<Object> al = new ArrayList<Object>();   
		    while ((index = str.indexOf(splitsign)) != -1) {   
		      al.add(str.substring(0, index));   
		      str = str.substring(index + splitsign.length());   
		    }   
		    return (String[]) al.toArray(new String[0]);   
	  }  
	
	
	
	public static boolean isNotNullOrEmpty(String s){
		if (null==s){
			return false;
		}
		if (s.trim().equals("")){
			return false;
		}
		return true;
	} 
	
	
	
	/**
	 * 返回分隔符前的字符串
	 * @param separetor
	 * @return
	 */
	public static String getBefore(String target,String separetor){
		if(separetor == null || target == null){
			return "";
		}else {
			return  target.substring(0,  target.indexOf(separetor));
		}
	}
	
	/**
	 * 返回分隔符后的字符串
	 * @param separetor
	 * @return
	 */
	public static String getAfter(String target,String separetor){
		if(separetor == null || target == null){
			return "";
		}else {
			int i=target.indexOf(separetor);  // 分隔符不存在的时候 是-1
			if(i==-1){
				return "";   // 分隔符在字符串当中都不存在
			}else{
				return  target.substring(target.indexOf(separetor)+separetor.length());
			}
		}
	}
	
	/**
	 * 返回分隔符后的字符串
	 * @param separetor
	 * @return
	 */
	public static String getLastAfter(String target,String separetor){
		if(separetor == null || target == null){
			return "";
		}else {
			int i=target.lastIndexOf(separetor);  // 分隔符不存在的时候 是-1
			if(i==-1){
				return "";   // 分隔符在字符串当中都不存在
			}else{
				return  target.substring(target.lastIndexOf(separetor)+separetor.length());
			}
		}
	}
	
	
	
	/**
	 * 判断指定字符串是否为整数
	 * 注意0开头的数字，如 012认为不是合法数字所以也不是整数 ，将返回false
	 * @param target-要判断的字符串
	 * @return - Boolean 是否为整数
	 */
	public static Boolean isInteger(String target){
		if(target == null||"".equals(target)){
			return false;
		}else{
			Pattern p = Pattern.compile("^-?[1-9]\\d*$");        
			Matcher m = p.matcher(target);        
			boolean b = m.matches();
			return b; 
		}
	}
	
	public  static boolean containsAny(String str, String searchChars) 
	{
	  if(str.length()!=str.replace(searchChars,"").length())
	  {
	    return true;
	  }
	  return false;
	}
	
	/**
	 * 一个字符串集合包含另外一个字符串
	 * @param str
	 * @param list
	 * @return
	 */
	public  static boolean contain(List<String> list,String str) {
		if (list != null) {
			return list.contains(str);
		} else {
			return false;
		}
	}
	
	/**
	 * 把字符串 依据分隔符 转换为 List
	 * @param ckListStr
	 * @param separetor
	 * @return
	 */
	public static List<Integer> convert2List(String ckListStr,String separetor){
		String[] ckStrArray = split(ckListStr, separetor);
		List<Integer> ids = new ArrayList();
		for (int i = 0; i < ckStrArray.length; i++) {
			String uidStr = ckStrArray[i];
			Integer uid = Integer.parseInt(uidStr);
			ids.add(uid);
		}
		return ids;
	}
	
	
	/**
	 * 字符串转Date
	 * @throws ParseException
	 */
	public static Date getDate(String s) throws ParseException {
		if (s.indexOf("-") >= 0 && s.indexOf(" ") >= 0) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			return format.parse(s);
		}
		if (s.indexOf("/") >= 0 && s.indexOf(" ") >= 0) {
			SimpleDateFormat format = new SimpleDateFormat(
					"yyyy/MM/dd HH:mm:ss");
			return format.parse(s);
		}
		if (s.indexOf("-") >= 0 && s.indexOf(" ") < 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			return format.parse(s);
		}
		if (s.indexOf("/") >= 0 && s.indexOf(" ") < 0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			return format.parse(s);
		}
		return null;
	}

	/**
	 * Date转字符串
	 * @param date
	 * @return
	 */
	public static String getDateString(Date date) {
		String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(date);
		return s;
	}
	
	
	/**
	 * 两个字符后添加一个空格
	 * @param msg
	 * @return
	 */
	public static String addSpace (String msg) {
        String regex = "(.{2})";
        msg = msg.replaceAll (regex, "$1 ");
        return msg;
    }
}
