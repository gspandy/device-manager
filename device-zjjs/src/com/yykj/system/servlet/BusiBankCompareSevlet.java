package com.yykj.system.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.yykj.base.util.DateValueProcessor;
import com.yykj.base.util.FileDownloadUtil;
import com.yykj.base.util.JxlTableUtil;
import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.BusiBankCompare;
import com.yykj.system.entity.Pager;
import com.yykj.system.services.IBusiBankCompareService;

public class BusiBankCompareSevlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private IBusiBankCompareService busiBankCompareService;
	private Integer pageSize;

	public BusiBankCompareSevlet() {
		pageSize = Integer.valueOf(10);
	}

	public void init() throws ServletException {
		super.init();
		busiBankCompareService = (IBusiBankCompareService) SpringContextUtil.getBean("busiBankCompareService", IBusiBankCompareService.class);
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if ("getBankComparePager".equals(method)){
			getBankComparePager(request, response);
		}else if ("exportExcel".equals(method)) {
			exportExcel(request, response);
		}
	}

	public void getBankComparePager(HttpServletRequest request,HttpServletResponse response) {
		JSONObject jsonobj = new JSONObject();
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String cardno = request.getParameter("cardno");
		Integer pageIndex = Integer.valueOf(Integer.parseInt(request.getParameter("pageIndex")));
		Pager page = busiBankCompareService.getBankComparePager(cardno, startDate, endDate, pageIndex,pageSize);
		List<BusiBankCompare> list = busiBankCompareService.getBankCompareByParam(cardno, startDate, endDate, pageIndex, pageSize,true);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new DateValueProcessor());
		JSONObject pagerjson = JSONObject.fromObject(page);
		JSONArray dataJson = JSONArray.fromObject(list, jsonConfig);
		jsonobj.put("page", pagerjson.toString());
		jsonobj.put("data", dataJson.toString());
		ServletUtil.response_prit(response, jsonobj.toString());
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		String rootpath = request.getSession().getServletContext().getRealPath("excelfile");
		try {
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			String cardno = request.getParameter("cardno");
			Integer pageIndex = 0;
			List<BusiBankCompare> list = busiBankCompareService.getBankCompareByParam(cardno, startDate, endDate, pageIndex, pageSize,false);

			String fileName = UUID.randomUUID() + ".xls";
			String filePath = rootpath + "/" + fileName;
			String header = "医院编码,银行账号,银行交易日期,银行交易流水号,交易金额,医院交易日期,医院交易流水号";
			List<String> bodys = new ArrayList<String>();
			for (BusiBankCompare compare : list) {
				StringBuilder body = new StringBuilder();
				body.append(compare.getHspcode()).append(",");
				body.append(compare.getBankcard()).append(",");
				body.append(compare.getBankdealdate()).append(",");
				body.append(compare.getBankdealnum()).append(",");
				body.append(compare.getDealamount()).append(",");
				body.append(compare.getHspdealdate()).append(",");
				body.append(compare.getHspdealnum());
				bodys.add(body.toString());
			}

			JxlTableUtil.getInstance().createTable(header, bodys, filePath);
			FileDownloadUtil.downLoad(response, filePath, "银医对账明细.xls", true);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
