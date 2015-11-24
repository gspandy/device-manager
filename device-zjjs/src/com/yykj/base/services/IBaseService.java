package com.yykj.base.services;

import java.util.List;

import com.yykj.base.dao.IBaseDao;

public interface IBaseService extends IBaseDao {
	public abstract Object save(Object obj);
	public abstract List getEntities(String hql);
	 
}
