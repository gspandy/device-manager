package com.yykj.base.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Collection;

import com.yykj.system.entity.Pager;

public abstract class PublicUtil {

	public PublicUtil() {
	}

	public static boolean isNull(Object obj) {
		return obj == null;
	}

	public static boolean isNull(Collection data) {
		return data == null || data.isEmpty();
	}

	public static boolean isNull(String str) {
		return str == null || "".equals(str.trim());
	}

	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	public static boolean isNotNull(Collection data) {
		return data != null && !data.isEmpty();
	}

	public static boolean isNotNull(String str) {
		return str != null && !"".equals(str.trim());
	}
	
	//分页信息处理
	public static Pager getPager(Integer totalCount,Integer pageSize,Integer pageIndex){
		Integer totalPages = 0;
		if(totalCount % pageSize==0){
			totalPages = totalCount / pageSize;
		}else{
			int pages = (int) Math.floor(totalCount / pageSize);
			pages ++;
			totalPages = pages;
		}		
		Pager pager = new Pager();
		pager.setTotalCount(totalCount);
		pager.setPageSize(pageSize);
		pager.setPageIndex(pageIndex);
		pager.setTotalPages(totalPages);
		return pager;
	}
	
	/**
	 * 字型小数加千分位并保留两位小数
	 * @param str
	 * @return
	 */
	public static String formatStr(String str){
		 BigDecimal b = new BigDecimal(str);
		 DecimalFormat d1 =new DecimalFormat("#,##0.00;(#)");
		 String ss= d1.format(b);
		 return ss;
	}
}
