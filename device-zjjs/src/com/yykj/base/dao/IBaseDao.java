package com.yykj.base.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

@SuppressWarnings("all")
public interface IBaseDao {
	
	public Object save(Object o);
	
	public List getByHqlMap(String hql, Map map);

	public List getFromObject(Class clazz, Map map, String... sort);

	public List getFromObject(Object obj, String... sort);

	public void deleteEntityByIdList(String xmlData, String clazz);

	public List getObjectsByNativeSql(String nativeSql);

	public List getObjectsByNativeSql(String nativeSql, String entityName,
			Class entityClass);

	public void batcSaveHQL(List<Object> objects);

	public void executeUpdateSQL(String sql);

	public List json2Objects(String json, String _class);

	public Long findCountSQL(String sql);

	public void executeSQL(String sql);

	public void exeSqlBatch(List sqlList, int num);

	public Long countByInstance(Object instance);

	public List findByInstance(Object instance);

	public List findByInstance(Object instance, String... sorts);

	public List findByExample(Object instance);

	public List findByCriteria(DetachedCriteria query);

	public Long countByCriteria(DetachedCriteria query);

	public List<Map> findBySql(String sql);

	public List findBySql(String sql, Class clazz);

	public Long countBySql(String sql);

	public List findByNativeSql(String sql, String t, Class clz);

	public Long countByNativeSql(String sql, String t, Class clz);
	
	public Object getEntitie(Class clazz,int pk);

	public Session getSessionBean();
}
