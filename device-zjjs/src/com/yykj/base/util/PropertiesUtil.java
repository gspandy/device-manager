package com.yykj.base.util;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class PropertiesUtil {
	private Properties properties = null;
	private static PropertiesUtil instance = null;
	
	private static String FILE_PATH="config/system.properties";
	
	public static PropertiesUtil getInstance(){
		if(instance==null){
			instance = new PropertiesUtil();
		}
		return instance;
	}
	
	public PropertiesUtil(){
		properties = new Properties();
		try {
			InputStreamReader input = new InputStreamReader(PropertiesUtil.class.getClassLoader().getResourceAsStream(FILE_PATH), "UTF-8");
			properties.load(input);
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Properties getProperties() {
		return properties;
	}

	public String get(String key) {
		return properties.getProperty(key);
	}

	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}
}
