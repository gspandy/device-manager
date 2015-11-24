package com.yykj.system.services;

import java.util.List;

import com.yykj.base.services.IBaseService;
import com.yykj.system.entity.SysZddm;

public interface ISysZddmService extends IBaseService {
	public SysZddm getZddmByid(int id);
	
	public List<SysZddm> getZddmByClassType(String classname);
	
	public List<SysZddm> getAllZddm();
	
	
	/**
	 * 更新交易流水号
	 * @param lsh
	 */
	public boolean updateJylsh(String lsh);
	
	/**
	 * 获取交易流水号
	 * @return
	 */
	public String getJylsh();
}
