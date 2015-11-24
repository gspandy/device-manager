package com.yykj.base.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * 
 * @author QinShuJin
 * 2015年7月3日 下午1:49:45
 */
public class ServletUtil {
	private static Logger log = LogUtil.getInstance().getLogger( ServletUtil.class);

	/**
	 * 统一页面转发
	 * 
	 * @param request
	 * @param response
	 * @param url
	 */
	public static void request_forward(HttpServletRequest request,
			HttpServletResponse response, String url) {
		try {
			request.getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			log.error(e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * 向页面输入一个字符串
	 * 
	 * @param response
	 * @param msg
	 */
	public static void response_prit(HttpServletResponse response, String msg) {
		try {
			response.getWriter().print(msg);
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

}
