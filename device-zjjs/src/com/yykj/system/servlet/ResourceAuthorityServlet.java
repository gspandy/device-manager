package com.yykj.system.servlet;

import java.io.IOException;
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
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.SysResource;
import com.yykj.system.entity.SysRole;
import com.yykj.system.entity.UserInfo;
import com.yykj.system.services.IBasUserInfoService;
import com.yykj.system.services.IRole2ResourceService;
import com.yykj.system.services.ISysResourceService;
import com.yykj.system.services.ISysRoleService;
import com.yykj.system.services.IUser2RoleService;

@SuppressWarnings("all")
public class ResourceAuthorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = LogUtil.getInstance().getLogger(ResourceAuthorityServlet.class);
	
	private IBaseService baseServ;
	private ISysRoleService roleServ;
	private IUser2RoleService u2rServ;
	private IBasUserInfoService userServ;
	private ISysResourceService resServ;
	private IRole2ResourceService r2rServ;
       
    public ResourceAuthorityServlet() {
        super();
    }

    
    /**
     * 权限处理服务
     */
	@Override
	public void init() throws ServletException {
		super.init();
		baseServ= (BaseService) SpringContextUtil.getBean("baseService",IBaseService.class);
		roleServ= (ISysRoleService) SpringContextUtil.getBean("roleServer",ISysRoleService.class);
		u2rServ= (IUser2RoleService) SpringContextUtil.getBean("u2rServer",IUser2RoleService.class);
		userServ= (IBasUserInfoService) SpringContextUtil.getBean("userServer",IBasUserInfoService.class);
		resServ= (ISysResourceService) SpringContextUtil.getBean("resServer",ISysResourceService.class);
		r2rServ= (IRole2ResourceService) SpringContextUtil.getBean("r2rServer",IRole2ResourceService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");		
		if("saveUserRole".equals(method)){
			saveUserRole(request,response);
		}else if("getAllUsers".equals(method)){
			getAllUsers(request,response);
		}else if("getAllResource".equals(method)){
			getAllResource(request,response);
		}else if("getAllRoles".equals(method)){
			getAllRoles(request,response);
		}else if("getRoleByUser".equals(method)){
			getRoleByUser(request,response);
		}else if("getResourceByRole".equals(method)){
			getResourceByRole(request,response);
		}else if("saveRoleResource".equals(method)){
			saveRoleResource(request,response);
		}else if("getUserResource".equals(method)){
			getUserResource(request,response);
		}else if("saveUsersRole".equals(method)){
			saveUsersRole(request,response);
		}else if("getUsersByRole".equals(method)){
			getUsersByRole(request,response);
		}else if("getUsersByOrg".equals(method)){
			getUsersByOrg(request,response);
		}
	}
	
	/**
	 * 查询所有用户
	 */
	private void getAllUsers(HttpServletRequest request, HttpServletResponse response){
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
	 * 查询所有角色
	 */
	private void getAllRoles(HttpServletRequest request, HttpServletResponse response){
		List list = roleServ.getAllSysRole();
		JSONObject JsonObj = new JSONObject();
		JSONArray dataJson = JSONArray.fromObject(list);
		String j = dataJson.toString();
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询所有资源
	 */
	private void getAllResource(HttpServletRequest request, HttpServletResponse response){
		List list = resServ.getAllSysResource();
		JSONObject JsonObj = new JSONObject();
		JSONArray dataJson = JSONArray.fromObject(list);
		String j = dataJson.toString();
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 保存用户对应角色
	 */
	private void saveUserRole(HttpServletRequest request, HttpServletResponse response){
		Integer userid = Integer.parseInt(request.getParameter("userid"));
		Integer roleid = null;
		String r = request.getParameter("roleid");
		if(!"".equals(r)){
			roleid = Integer.parseInt(r);
		}
		response.setCharacterEncoding("utf-8");
		try {
			u2rServ.saveUser2Role(userid, roleid);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询用户对应角色
	 */
	private void getRoleByUser(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		Integer userid = Integer.parseInt(request.getParameter("userid"));
		SysRole role = u2rServ.getRoleByUserId(userid);
		JSONObject JsonObj = JSONObject.fromObject(role);		 
		try {
			response.getWriter().print(JsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询角色对应资源
	 */
	private void getResourceByRole(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		Integer roleid = Integer.parseInt(request.getParameter("roleid"));
		List<SysResource> res = r2rServ.getResourceByRole(roleid);
		JSONArray dataJson = JSONArray.fromObject(res);
		String j = dataJson.toString();		
		try {
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 保存角色对应资源
	 */
	private void saveRoleResource(HttpServletRequest request, HttpServletResponse response){
		Integer roleid = Integer.parseInt(request.getParameter("roleid"));
		JSONArray jsonArray = JSONArray.fromObject(request.getParameter("ids"));  
        List ids = (List) JSONArray.toCollection(jsonArray); 
		response.setCharacterEncoding("utf-8");
		try {
			r2rServ.saveRole2Resource(roleid, ids);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 查询用户对应资源
	 */
	private void getUserResource(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		Integer userid = Integer.parseInt(request.getParameter("userid"));
		List<SysResource> res = resServ.getUserResource(userid);
		JSONArray dataJson = JSONArray.fromObject(res);
		String j = dataJson.toString();		
		try {
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}		
	}
	
	/**
	 * 保存多用户对应角色
	 */
	private void saveUsersRole(HttpServletRequest request, HttpServletResponse response){
		JSONArray jsonArray = JSONArray.fromObject(request.getParameter("ids")); 
		Integer roleid = Integer.parseInt(request.getParameter("roleid"));		 
        List<Integer> ids = (List<Integer>) JSONArray.toCollection(jsonArray); 
		response.setCharacterEncoding("utf-8");
		try {
			u2rServ.saveUsersRole(ids,roleid);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 根据角色查询用户
	 */
	private void getUsersByRole(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		Integer roleid = Integer.parseInt(request.getParameter("roleid"));
		List<UserInfo> users = u2rServ.getUsersByRole(roleid);
		JSONArray dataJson = JSONArray.fromObject(users);
		String j = dataJson.toString();		
		try {
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}		
	}
	
	/**
	 * 根据组织机构查询用户
	 */
	private void getUsersByOrg(HttpServletRequest request, HttpServletResponse response){
		response.setCharacterEncoding("utf-8");	
		List<UserInfo> users = null;
		String orgid = request.getParameter("orgid");
		if("".equals(orgid) || orgid == null){
			 users = userServ.getUsers();
		}else{
			 users = u2rServ.getUsersByOrg(orgid);
		}		
		JSONArray dataJson = JSONArray.fromObject(users);
		String j = dataJson.toString();		
		try {
			response.getWriter().print(j);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}		
	}
}	
