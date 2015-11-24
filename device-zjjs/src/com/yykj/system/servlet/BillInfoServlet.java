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
import com.yykj.system.entity.BusiBanktransInfo;
import com.yykj.system.entity.BusiBillDetail;
import com.yykj.system.entity.BusiBillInfo;
import com.yykj.system.entity.Pager;
import com.yykj.system.services.IBusiBanktransInfoService;
import com.yykj.system.services.IBusiBillDetailService;
import com.yykj.system.services.IBusiBillInfoService;

public class BillInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private IBusiBanktransInfoService busiBanktransInfoService;
	private IBusiBillInfoService busiBillInfoService;
	private IBusiBillDetailService busiBillDetailService;
	private Integer pageSize;

	public BillInfoServlet() {
		pageSize = Integer.valueOf(10);
	}

	public void init() throws ServletException {
		super.init();
		busiBanktransInfoService = (IBusiBanktransInfoService) SpringContextUtil.getBean("busiBanktransInfoService", IBusiBanktransInfoService.class);
		busiBillInfoService = (IBusiBillInfoService) SpringContextUtil.getBean("busiBillInfoService", IBusiBillInfoService.class);
		busiBillDetailService = (IBusiBillDetailService) SpringContextUtil.getBean("busiBillDetailService", IBusiBillDetailService.class);
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if ("getBankTransInfos".equals(method)) {
			getBankTransInfos(request, response);
		} else if ("getBillDetailByBillId".equals(method)) {
			getBillDetailByBillId(request, response);
		} else if ("getBillInfoByBankTransId".equals(method)) {
			getBillInfoByBankTransId(request, response);
		} else if ("exportExcel".equals(method)) {
			exportExcel(request, response);
		}
	}

	public void getBankTransInfos(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			JsonConfig jsonConfig = new JsonConfig();
			jsonConfig.registerJsonValueProcessor(Date.class,new DateValueProcessor());

			JSONObject jsonobj = new JSONObject();
			String type = request.getParameter("type");
			String value = request.getParameter("value");
			Integer pageIndex = Integer.valueOf(Integer.parseInt(request.getParameter("pageIndex")));

			Pager page = busiBanktransInfoService.getBillInfoPager(type, value,pageIndex, pageSize);
			JSONObject pagerjson = JSONObject.fromObject(page);

			List<BusiBanktransInfo> banktransInfos = busiBanktransInfoService.getBanktransInfos(type, value, pageIndex, pageSize, true);
			JSONArray dataJson = JSONArray.fromObject(banktransInfos,jsonConfig);

			jsonobj.put("page", pagerjson.toString());
			jsonobj.put("data", dataJson.toString());
			ServletUtil.response_prit(response, jsonobj.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			String type = request.getParameter("type");
			String value = request.getParameter("value");
			Integer pageIndex = 0;
			List<BusiBanktransInfo> banktransInfos = busiBanktransInfoService.getBanktransInfos(type, value, pageIndex, pageSize, false);

			String fileName = UUID.randomUUID() + ".xls";
			String filePath = rootpath + "/" + fileName;
			String header = "用户姓名,身份证号码,银行账号,病人ID,挂号ID,应收金额,实收金额,银行扣款,发起时间,流水号,银行流水号,交款状态";
			List<String> bodys = new ArrayList<String>();
			for (BusiBanktransInfo info : banktransInfos) {
				StringBuilder body = new StringBuilder();
				body.append(info.getYhmc()).append(",");
				body.append(info.getIdcard()).append(",");
				body.append(info.getYhzh()).append(",");
				body.append(info.getPatientid()).append(",");
				body.append(info.getGhid()).append(",");
				body.append(info.getShouldmoney()).append(",");
				body.append(info.getActualmoney()).append(",");
				body.append(info.getBankmoney()).append(",");
				body.append(info.getLocalTransDate()).append(",");
				body.append(info.getLocalTransNum()).append(",");
				body.append(info.getBankTransNum()).append(",");
				String zt = info.getTrantype();
				if ("0".equals(zt)) {
					body.append("交易成功");
				} else if ("1".equals(zt)) {
					body.append("交易冲销");
				} else if ("2".equals(zt)) {
					body.append("交易失败");
				}
				bodys.add(body.toString());
			}

			JxlTableUtil.getInstance().createTable(header, bodys, filePath);
			FileDownloadUtil.downLoad(response, filePath, "刷卡交易明细.xls", true);

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getBillDetailByBillId(HttpServletRequest request,HttpServletResponse response) {
		int billinfoId = Integer.parseInt(request.getParameter("billinfoid"));
		List<BusiBillDetail> details = busiBillDetailService.getBillDetailByBillId(billinfoId);
		String details_json = JSONArray.fromObject(details).toString();
		ServletUtil.response_prit(response, details_json);
	}

	public void getBillInfoByBankTransId(HttpServletRequest request,HttpServletResponse response) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new DateValueProcessor());

		int banktransid = Integer.parseInt(request.getParameter("banktransid"));
		List<BusiBillInfo> billInfos = busiBillInfoService.getBillInfoByBankTransId(banktransid);
		JSONArray dataJson = JSONArray.fromObject(billInfos, jsonConfig);
		String details_json = dataJson.toString();
		ServletUtil.response_prit(response, details_json);
	}
}
