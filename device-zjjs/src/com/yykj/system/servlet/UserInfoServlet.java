package com.yykj.system.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.yykj.base.services.IBaseService;
import com.yykj.base.services.imp.BaseService;
import com.yykj.base.util.Constants;
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.Security_V;
import com.yykj.system.entity.UserInfo;
import com.yykj.system.services.IBasUserInfoService;
import com.yykj.system.services.ISecurityVService;

@SuppressWarnings("all")
public class UserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = LogUtil.getInstance().getLogger(UserInfoServlet.class);
	
	private IBaseService baseServ; //锟斤拷锟斤拷锟�
	private IBasUserInfoService userServ;//锟矫伙拷锟斤拷锟斤拷
	private ISecurityVService securityVService;//锟矫伙拷锟斤拷锟斤拷
	private Integer pageSize = 10;//分页：每页记录数
       
    public UserInfoServlet() {
        super();
    }

    
    /**
     * 用户管理服务
     */
	@Override
	public void init() throws ServletException {
		super.init();
		baseServ= (BaseService) SpringContextUtil.getBean("baseService",IBaseService.class);
		userServ= (IBasUserInfoService) SpringContextUtil.getBean("userServer",IBasUserInfoService.class);
		securityVService = (ISecurityVService) SpringContextUtil.getBean("securityVService",ISecurityVService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");	
		if("saveUser".equals(method)){
			saveUser(request,response);
		}else if("getAllUser".equals(method)){
			getAllUser(request,response);
		}else if("getUserById".equals(method)){
			getUserById(request,response);
		}else if("updateUser".equals(method)){
			updateUser(request,response);
		}else if("deleteUser".equals(method)){
			deleteUserById(request, response);
		}else if("checkUser".equals(method)){
			loginCheck(request, response);
		}else if("getUserByParam".equals(method)){
			getUserByParam(request, response);
		}else if("updatePwd".equals(method)){
			updatePwd(request, response);
		}else if("logout".equals(method)){
			logout(request, response);
		}else if("getSecurity".equals(method)){
			getSecurity(request, response);
		}else if("resetPwd".equals(method)){
			resetPwd(request, response);
		}else if("loadBusiYear".equals(method)){
			loadBusiYear(request, response);
		}
	}
	
	
	/**
	 * 新增用户
	 * @param request
	 * @param response
	 */
	private void saveUser(HttpServletRequest request, HttpServletResponse response) {
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		response.setCharacterEncoding("utf-8");
		UserInfo user = (UserInfo) JSONObject.toBean(json, UserInfo.class);
		try {
			userServ.save(user);
			if(user.getId()!=null){			
				ServletUtil.response_prit(response, "true");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if("existed".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed");
			}else if("existed code".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed code");
			}
		}		
		
	}
	
	/**
	 * 锟斤拷询锟斤拷锟斤拷锟矫伙拷
	 */
	private void getAllUser(HttpServletRequest request, HttpServletResponse response){
			response.setCharacterEncoding("utf-8");
			List list = userServ.getUsers();
			JSONObject JsonObj = new JSONObject();
			JSONArray dataJson = JSONArray.fromObject(list);	
			String j = dataJson.toString();
			try {
				response.getWriter().print(j);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
	}
	
	/**
	 * 锟斤拷锟絠d锟斤拷询锟矫伙拷
	 * @param id
	 */
	private void getUserById(HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("userid"));
		UserInfo user = userServ.getUser(id);
		JSONObject JsonObj = JSONObject.fromObject(user);
		try {
			 response.setCharacterEncoding("utf-8");
			 response.getWriter().print(JsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 锟斤拷锟絠d删锟斤拷锟矫伙拷
	 * @param id
	 * @throws Exception 
	 */
	private void deleteUserById(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		int id = Integer.parseInt(msg);
		response.setCharacterEncoding("utf-8");
		try {
			userServ.deleteUserInfoById(id);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 锟睫革拷锟斤拷锟斤拷
	 * @param id
	 * @param password
	 */
	private void updatePwd(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		String oldpwd = json.getString("oldpwd");
		String newpwd = json.getString("newpwd");		
		int id = Integer.parseInt(json.getString("id"));
		try {
			userServ.updatePassword(id,oldpwd,newpwd);		
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			ServletUtil.response_prit(response, e.getMessage());
		}
	}
	
	/**
	 * 更新用户信息
	 */
	private void updateUser(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		UserInfo user = (UserInfo) JSONObject.toBean(json, UserInfo.class);
		try {
			userServ.update(user);
			if(user.getId()!=null){
				response.setCharacterEncoding("utf-8");
				ServletUtil.response_prit(response, "true");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if("existed".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed");
			}else if("existed code".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed code");
			}
		}		
		
	}
	
	/**
	 * 鐧诲綍鐢ㄦ埛鏍￠獙
	 */
	private void loginCheck(HttpServletRequest request, HttpServletResponse response){
		String code = request.getParameter("code");
		String pwd = request.getParameter("pwd");
		String year = request.getParameter("year");//业务年度
		String admdivcode = request.getParameter("admdivcode");
		//JSONObject json = JSONObject.fromObject(msg);
		UserInfo user = new UserInfo();
		user.setUsercode(code);
		user.setPassword(pwd);
		//user.setAdmdivcode(admdivcode);
		response.setCharacterEncoding("utf-8");
		JSONObject JsonObj = new JSONObject();
		boolean b = false;
		try {
			UserInfo u = userServ.validateUser(user);
			JsonObj = JSONObject.fromObject(u);
			if(!"".equals(JsonObj.toString())){
				request.getSession().setAttribute("user", u);
				request.getSession().setAttribute("BusiYear", year);//业务年度
				ServletUtil.response_prit(response,JsonObj.toString());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if("error".equals(e.getMessage())){
				ServletUtil.response_prit(response, "error");				
			}else if("unabled".equals(e.getMessage())){
				ServletUtil.response_prit(response, "unabled");
			}
		}		
	}
	
	private void getUserByParam(HttpServletRequest request, HttpServletResponse response){
		String type = request.getParameter("type");
		String value = "";
		try {
			value = new String(request.getParameter("value").getBytes("iso-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e1) {
			log.error(e1.getMessage(), e1);
		}
		Integer pageIndex = Integer.parseInt(request.getParameter("pageIndex"));//页码
		//Integer pageIndex = 1;
		response.setCharacterEncoding("utf-8");
		JSONObject JsonObj = new JSONObject();
		List<UserInfo> list = userServ.getUsersByParam(type, value, pageIndex, pageSize);
		JSONArray dataJson = JSONArray.fromObject(list);	
		try {
			response.getWriter().print(dataJson.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	private void logout(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		try{
			UserInfo u = (UserInfo)request.getSession().getAttribute("user");
			request.getSession().removeAttribute("user");
			response.getWriter().print("true");
		}catch(Exception e){
			log.error(e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * 查询用户权限
	 * @param request
	 * @param response
	 */
	private void getSecurity(HttpServletRequest request, HttpServletResponse response){
		String userid=request.getParameter("userid");
		List<Security_V> list = securityVService.getSecurityByUserid(userid);
		JSONArray dataJson = JSONArray.fromObject(list);
		ServletUtil.response_prit(response, dataJson.toString());
	}
	
	private void resetPwd(HttpServletRequest request, HttpServletResponse response){
		JSONArray jsonArray = JSONArray.fromObject(request.getParameter("ids"));  
        List ids = (List) JSONArray.toCollection(jsonArray); 
		response.setCharacterEncoding("utf-8");
		try {
			userServ.resetPwd(ids);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if(e.getMessage().equals("illegal")){
				ServletUtil.response_prit(response, "illegal");
			}else if(e.getMessage().equals("none")){
				ServletUtil.response_prit(response, "none");
			}
		}
	}
	
	/**
	 * 登录加载年度
	 */
	private void loadBusiYear(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		List list = new ArrayList();
		list.add(Constants.Busi_Year);
		JSONObject JsonObj = new JSONObject();
		JSONArray dataJson = JSONArray.fromObject(list);	
		String j = dataJson.toString();
		try {
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
}	
