package com.yykj.micromsg.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.micromsg.services.IGhService;
import com.yykj.micromsg.services.INopaymetnRecordService;
import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Doctor4003;
/**
 * 挂号
 * @author QinShuJin
 *
 */
public class GhServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private INopaymetnRecordService nopaymetnRecordService;
	private IGhService ghService;
	

	public void init() throws ServletException {
		super.init();
		nopaymetnRecordService = (INopaymetnRecordService) SpringContextUtil.getBean("nopaymetnRecordService",INopaymetnRecordService.class);
		ghService = (IGhService) SpringContextUtil.getBean("ghService",IGhService.class);
	}

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		if ("getNopayMentRecord".equals(method)){
			getNopayMentRecord(request, response);
		}else if ("getGhType".equals(method)){
			getGhType(request, response);
		}else if ("getGhDept".equals(method)){
			getGhDept(request, response);
		}else if ("getGhDoctor".equals(method)){
			getGhDoctor(request, response);
		}
	}

	/**
	 * 查询未缴费记录
	 * @param request
	 * @param response
	 */
	public void getNopayMentRecord(HttpServletRequest request,HttpServletResponse response) {
		String idcardNum = request.getParameter("idcardNum");
		String wxappid = "wxapp";
		String records = nopaymetnRecordService.getNopayMentRecord(idcardNum,wxappid);
		System.out.println(records);
		ServletUtil.response_prit(response, records);
	}
	
	
	/**
	 * 获取挂号类别
	 */
	public void getGhType(HttpServletRequest request,HttpServletResponse response){
		List<String> list = ghService.getGhType();
		JSONArray array = new JSONArray();
		for (String type : list) {
			JSONObject obj = new JSONObject();
			obj.put("type", type);
			array.add(obj);
		}
		ServletUtil.response_prit(response, array.toString());
	}
	
	/**
	 * 获取挂号科室
	 */
	public void getGhDept(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> requestMap = new HashMap<String,String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//1. 接收值
		String day = df.format(new Date());
		String ghtype = request.getParameter("ghtype");
		String userid = "wxapp";
		
		//2.组装Map
		requestMap.put("RegType", "1");
		requestMap.put("Day", day);
		requestMap.put("RigsterType", ghtype);
		requestMap.put("Start", "0");
		requestMap.put("RequestQty", "1000");
		requestMap.put("RegType", "1");
		requestMap.put("UserId", userid);
		
		
		List<Dept4002> list = ghService.getGhDept(requestMap);
		JSONArray dataJson = JSONArray.fromObject(list);
		ServletUtil.response_prit(response, dataJson.toString());
	}
	
	

	/**
	 * 获取挂号医生
	 */
	public void getGhDoctor(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> requestMap = new HashMap<String,String>();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		//1. 接收值
		String day = df.format(new Date());
		String ghtype = request.getParameter("ghtype");
		String ghdeptid = request.getParameter("deptid");
		String userid = "wxapp";
		
		//2.组装Map
		requestMap.put("RegType", "1");
		requestMap.put("Day", day);
		requestMap.put("RigsterType", ghtype);
		requestMap.put("DeptID", ghdeptid);
		requestMap.put("Start", "0");
		requestMap.put("RequestQty", "1000");
		requestMap.put("RegType", "1");
		requestMap.put("UserId", userid);
		
		
		List<Doctor4003> list = ghService.getDoctor(requestMap);
		JSONArray dataJson = JSONArray.fromObject(list);
		ServletUtil.response_prit(response, dataJson.toString());
	}
	
}
