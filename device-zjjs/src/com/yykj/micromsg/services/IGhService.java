package com.yykj.micromsg.services;

import java.util.List;
import java.util.Map;

import com.yykj.system.entity.hisentity.Dept4002;
import com.yykj.system.entity.hisentity.Doctor4003;

public interface IGhService {
	
	/**
	 * 获取挂号类别
	 * @return
	 */
	public List<String> getGhType();
	
	
	/**
	 * 获取挂号科室
	 * @param map
	 * @return
	 */
	public List<Dept4002> getGhDept(Map<String,String> map);
	
	
	/**
	 * 获取挂号医生
	 * @param map
	 * @return
	 */
	public List<Doctor4003> getDoctor(Map<String,String> map);

}
