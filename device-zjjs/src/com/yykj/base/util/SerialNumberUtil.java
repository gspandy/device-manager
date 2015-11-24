package com.yykj.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yykj.system.services.ISysZddmService;

public class SerialNumberUtil {

	private static ISysZddmService sysZddmService = (ISysZddmService) SpringContextUtil.getBean("syszddmService", ISysZddmService.class);
	private static SerialNumberUtil primaryGenerater = null;


	private SerialNumberUtil() {
	}

	/**
	 * 取得PrimaryGenerater的单例实现
	 * 
	 * @return
	 */
	public static SerialNumberUtil getInstance() {
		if (primaryGenerater == null) {
			synchronized (SerialNumberUtil.class) {
				if (primaryGenerater == null) {
					primaryGenerater = new SerialNumberUtil();
				}
			}
		}
		return primaryGenerater;
	}

	/**
	 * 生成下一个编号
	 */
	public synchronized String generaterNextNumber(String sno) {
		String id = null;
		Date date = new Date();
		
		//1.从数据库中取得当前流水号
		String jylsh = sysZddmService.getJylsh();
		String date_str = jylsh.substring(0,6);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		String cuurentdate = formatter.format(date);

		// 判断是否同一天
		if (!date_str.equals(cuurentdate)) {
			id = formatter.format(date) + "0001";
		}else {
			id = String.valueOf(Integer.parseInt(jylsh)+1);
		}
		sysZddmService.updateJylsh(id);
		return id;
	}

}
