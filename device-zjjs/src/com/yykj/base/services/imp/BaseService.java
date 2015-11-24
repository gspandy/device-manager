package com.yykj.base.services.imp;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.services.IBaseService;
/**
 * ͨ�÷�����Service��
 * 
 * @author hankqin 2012-12-28
 */
@Repository("baseService")
public class BaseService extends BaseDao implements IBaseService {

	@Override
	public Object save(Object obj) {
		Object o = super.save(obj);
		return o;
	}
	
	@Override
	public List getEntities(String hql) {
		// TODO Auto-generated method stub
		return super.getEntities(hql);
	}

}
