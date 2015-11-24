package com.yykj.base.util;

import java.net.URLDecoder;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

@SuppressWarnings("all")
public class LogUtil {
	// 将Log类封装为单例模式
	private static LogUtil logutil;

	// 构造函数，用于初始化Logger配置需要的属性
	private LogUtil() {
		// 获得当前目录路径
		String filePath = this.getClass().getResource("/").getPath();
		// 找到log4j.properties配置文件所在的目录(已经创建好)
		filePath = filePath.substring(1).replace("bin", "src");
		filePath = URLDecoder.decode(filePath);
		//设置日志文件输出路径
		System.setProperty ("WORKDIR", filePath);
		// logger所需的配置文件路径
		PropertyConfigurator.configure(filePath + "log4j.properties");
	}

	public static LogUtil getInstance () {
		if (logutil != null)
			return logutil;
		else
			return new LogUtil();
	}
	
	public <T> Logger getLogger(Class<T> clazz) {
		Logger logger = Logger.getLogger(clazz);
		return logger;
	}
}
