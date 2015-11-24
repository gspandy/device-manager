package com.yykj.base.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileDownloadUtil {

	/**
	 * 下载文件
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param filePath
	 *            原文件路径
	 * @param showName
	 *            下载框显示名称
	 * @param isDelete
	 *            下载后是否删除原文件
	 */
	public static void downLoad(HttpServletResponse response, String filePath,String showName, boolean isDelete) {
		try {
			showName = URLEncoder.encode(showName, "utf-8");
			response.reset();
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition", "attachment; filename="+ showName);

			File file = new File(filePath);
			ServletOutputStream sos = response.getOutputStream();
			BufferedInputStream fin = new BufferedInputStream(new FileInputStream(file));
			byte[] content = new byte[1024];
			int length;
			while ((length = fin.read(content, 0, content.length)) != -1) {
				sos.write(content, 0, length);
			}
			fin.close();
			sos.flush();
			sos.close();
			file.delete();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
