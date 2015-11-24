package com.yykj.micromsg.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.micromsg.services.INopaymetnRecordService;

public class NopaymetnRecordServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private INopaymetnRecordService nopaymetnRecordService;

	public void init() throws ServletException {
		super.init();
		nopaymetnRecordService = (INopaymetnRecordService) SpringContextUtil.getBean("nopaymetnRecordService",INopaymetnRecordService.class);
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if ("getNopayMentRecord".equals(method))
			getNopayMentRecord(request, response);
	}

	public void getNopayMentRecord(HttpServletRequest request,HttpServletResponse response) {
		String idcardNum = request.getParameter("idcardNum");
		String wxappid = "wxapp";
		String records = nopaymetnRecordService.getNopayMentRecord(idcardNum,wxappid);
		System.out.println(records);
		ServletUtil.response_prit(response, records);
	}
}
