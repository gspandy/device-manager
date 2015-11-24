package com.yykj.system.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.yykj.base.services.IBaseService;
import com.yykj.base.services.imp.BaseService;
import com.yykj.base.util.LogUtil;
import com.yykj.base.util.ServletUtil;
import com.yykj.base.util.SpringContextUtil;
import com.yykj.system.entity.SysResource;
import com.yykj.system.entity.SysRole;
import com.yykj.system.services.ISysResourceService;
import com.yykj.system.services.IUser2RoleService;

@SuppressWarnings("all")
public class SysResourceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Logger log = LogUtil.getInstance().getLogger(SysResourceServlet.class);
	
	private IBaseService baseServ;
	private ISysResourceService resServ;
	private IUser2RoleService u2rServ;
       
    public SysResourceServlet() {
        super();
    }

    
    /**
     * 资源管理服务
     */
	@Override
	public void init() throws ServletException {
		super.init();
		baseServ= (BaseService) SpringContextUtil.getBean("baseService",IBaseService.class);
		resServ= (ISysResourceService) SpringContextUtil.getBean("resServer",ISysResourceService.class);
		u2rServ = (IUser2RoleService) SpringContextUtil.getBean("u2rServer",IUser2RoleService.class);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String method = request.getParameter("method");
		if("getAllResource".equals(method)){
			getAllResource(request,response);
		}else if("saveResource".equals(method)){
			saveResource(request,response);
		}else if("getResourceById".equals(method)){
			getResourceById(request,response);
		}else if("deleteResourceById".equals(method)){
			deleteResourceById(request,response);
		}else if("updateResource".equals(method)){
			updateResource(request,response);
		}
	}
	
	/**
	 * 获得所有资源
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
	 * 保存资源
	 */
	private void saveResource(HttpServletRequest request, HttpServletResponse response) {
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		SysResource r = (SysResource) JSONObject.toBean(json, SysResource.class);
		try {
			resServ.save(r);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if("existed".equals(e.getMessage())){
				ServletUtil.response_prit(response, "existed");
			}			
		}		
		if(r.getId()!=null){
			response.setCharacterEncoding("utf-8");
			ServletUtil.response_prit(response, "true");
		}
	}
	
	/**
	 * 根据id获得资源
	 */
	private void getResourceById(HttpServletRequest request, HttpServletResponse response){
		int id = Integer.parseInt(request.getParameter("resid"));
		SysResource r = resServ.getSysResourceById(id);
		JSONObject JsonObj = JSONObject.fromObject(r);
		try {
			 response.setCharacterEncoding("utf-8");
			 response.getWriter().print(JsonObj.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 *删除资源
	 */
	private void deleteResourceById(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		int id = Integer.parseInt(msg);
		response.setCharacterEncoding("utf-8");
		String result = "true";
		try {
			resServ.deleteSysResourceById(id);
			//ServletUtil.response_prit(response, "true");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			if("existed".equals(e.getMessage())){
				result = "existed";
			}				
		}
		ServletUtil.response_prit(response, result);
	}
	
	/**
	 *修改资源
	 */
	private void updateResource(HttpServletRequest request, HttpServletResponse response){
		String msg = request.getParameter("msg");
		JSONObject json = JSONObject.fromObject(msg);
		SysResource r = (SysResource) JSONObject.toBean(json, SysResource.class);
		resServ.update(r);		
		if(r.getId()!=null){
			response.setCharacterEncoding("utf-8");
			ServletUtil.response_prit(response, "true");
		}
	}	
}	
