package com.yykj.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ReadXmlFileUtil {
	
	public static String getXml(String fileName,String packagePath) {
		String path= ReadXmlFileUtil.class.getClassLoader().getResource("").getPath();
		path=URLDecoder.decode(path)+packagePath;
		File file = new File(path+fileName);
		StringBuffer docs = new StringBuffer();
		try {
			if (file.isFile() && file.exists()) {
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader( new FileInputStream(file), "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					docs.append(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return docs.toString();
	}
}
