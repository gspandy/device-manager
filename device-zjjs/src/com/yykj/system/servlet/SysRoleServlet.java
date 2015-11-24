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
import com.yykj.system.entity.SysRole;
import com.yykj.system.services.ISysRoleService;

@SuppressWarnings("all")
public class SysRoleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = LogUtil.getInstance().getLogger(SysRoleServlet.class);
	
	private IBaseService baseServ;
	private ISysRoleService roleServ;
       
    public SysRoleServlet() {
        super();
    }

    
    /**
     * 角色管理服务
     */
	@Override
	public void init() throws ServletException {
		super.init();
		baseServ= (BaseService) SpringContextUtil.getBean("baseService",IBaseService.class);
		roleServ= (ISysRoleService) SpringContextUtil.getBean("roleServer",ISysRoleService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");		
		if("getAllRoles".equals(method)){
			getAllRoles(request,response);
		}else if("saveRole".equals(method)){
			saveRole(request,response);
		}else if("getRoleById".equals(method)){
			getRoleById(request,response);
		}else if("updateRole".equals(method)){
			updateRole(request,response);
		}else if("deleteRoleById".equals(method)){
			deleteRoleById(request,response);
		}
	}
	
	/**
	 * 获得所有角色
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
			e.printStackTrace();
		}
	}

	/**
	 * 保存角色
	 */
	private void saveRole(HttpServletRequest request, HttpServletResponse response) {
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		SysRole role = (SysRole) JSONObject.toBean(json, SysRole.class);
		try {
			roleServ.save(role);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			if("existed".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed");
			}			
		}		
		if(role.getId()!=null){
			response.setCharacterEncoding("utf-8");
			ServletUtil.response_prit(response, "true");
		}
	}
	
	/**
	 * 根据id获得角色
	 */
	private void getRoleById(HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("roleid"));
		SysRole role = roleServ.getSysRoleById(id);
		JSONObject JsonObj = JSONObject.fromObject(role);
		try {
			 response.setCharacterEncoding("utf-8");
			 response.getWriter().print(JsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 *删除角色
	 */
	private void deleteRoleById(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		int id = Integer.parseInt(msg);
		 response.setCharacterEncoding("utf-8");
		try {
			roleServ.deleteSysRoleById(id);
			ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
	
	/**
	 *修改角色
	 */
	private void updateRole(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		SysRole role = (SysRole) JSONObject.toBean(json, SysRole.class);
		roleServ.update(role);		
		if(role.getId()!=null){
			response.setCharacterEncoding("utf-8");
			ServletUtil.response_prit(response, "true");
		}
	}
}	
