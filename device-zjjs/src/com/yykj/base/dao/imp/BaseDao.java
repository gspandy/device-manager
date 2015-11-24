package com.yykj.base.dao.imp;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.IBaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.BeanUtil;
import com.yykj.base.util.JsonParser;

/**
 * 通用方法的Dao类
 * 
 * @author hankqin 2012-12-28
 */
@SuppressWarnings("all")
@Repository("baseDao")
public class BaseDao extends HibernateDaoSupport implements IBaseDao {

	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	/**
	 * 通过Map中保存的条件来查询
	 * 
	 * @param hql
	 *            查询语句
	 * @param map
	 *            条件集合
	 */

	@Override
	public List getByHqlMap(String hql, Map map) {
		List list = new ArrayList();
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (null == map.get(key)) {
				iter.remove();
				continue;
			}

			list.add(map.get(key));
		}
		return this.getEntities(hql, map.keySet().toArray(), list.toArray());
	}

	@Override
	public void executeSQL(String sql) {
		try {
			Session session = this.getSession1();
			Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			Statement st = null;

			st = connection.createStatement();
			try {
				st.execute(sql);
			} catch (SQLException sqle) {
				throw new BusinessException("授权执行SQL语句时出错！", sqle);
			} finally {
				st.close();
			}
			releaseSession(session);
		} catch (Exception e) {
			throw new BusinessException("授权执行SQL语句时出错！", e);
		}
	}

	@Override
	public void exeSqlBatch(List sqlList, int num) {
		try {
			Session session = getSession1();
			Connection connection = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
			Statement stmt = null;
			stmt = connection.createStatement();
			try {
				for (int i = 0; i < sqlList.size(); i++) {
					String sql = sqlList.get(i).toString();
					System.out.println(sql);
					stmt.addBatch(sql);

					if ((i + 1) % num == 1)
						stmt.executeBatch();
				}
				stmt.executeBatch();
				connection.commit();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取某张表的全部数据 通过Map中保存的条件来查询
	 * 
	 * @param clazz
	 *            实体类名
	 * @param map
	 *            条件集合
	 */
	@Override
	public List getFromObject(Class clazz, Map map, String... sort) {
		StringBuffer hql = new StringBuffer();
		hql.append("select o from ").append(clazz.getName()).append(" o ");
		Iterator iter = map.keySet().iterator();
		int i = 0;
		while (iter.hasNext()) {
			String key = (String) iter.next();

			if (null != map.get(key)) {
				if (i > 0)
					hql.append(" and ");
				else
					hql.append(" where ");

				hql.append(" o.").append(key).append("= :").append(key)
						.append(" ");
				i++;
			}
		}

		for (String sor : sort) {
			if (sor != null && !"".equals(sor))
				hql.append(" ORDER BY ").append(sor);
		}

		return getByHqlMap(hql.toString(), map);
	}

	/**
	 * 获取某张表的全部数据 通过实体对象中保存的条件来查询
	 * 
	 * @param obj
	 *            实体的实例
	 */
	@Override
	public List getFromObject(Object obj, String... sort) {
		return getFromObject(obj.getClass(), BeanUtil.getPMap(obj), sort);
	}

	/**
	 * 通过Id中保存的条件来查询分页数据
	 * 
	 * @param xmlData
	 *            条件集合
	 * @param clazz
	 *            操作的类
	 */
	@Override
	public void deleteEntityByIdList(String xmlData, String clazz) {
		List list = JsonParser.json2List(xmlData);
		String hql = "delete from " + clazz + " o where o.id in (";
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			String param = obj.toString();
			if (list.size() - 1 > i) {
				hql = hql + "'" + param + "',";
			} else {
				hql = hql + "'" + param + "'";
			}
		}
		hql = hql + ")";
		updateOrDelete(hql);
	}

	/**
	 * 得到记录总数
	 * 
	 * @param sql
	 * @return
	 * @author ym
	 */
	@Override
	public Long findCountSQL(String sql) {
		List list = this.getSession1().createSQLQuery(
				"SELECT count(*) from (" + sql + ")").list();
		return new Long(list.get(0).toString());
	}

	@Override
	public List getObjectsByNativeSql(String nativeSql) {
		return this.getSession1().createSQLQuery(nativeSql).list();
	}

	@Override
	public List getObjectsByNativeSql(String nativeSql, String entityName,
			Class entityClass) {
		return this.getSession1().createSQLQuery(nativeSql)
				.addEntity(entityName, entityClass).list();
	}

	/**
	 * 批量添加对象（仅用此类于内部调用）
	 * 
	 * @param objects
	 */
	private void batcSave(final List<Object> objects) {
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				for (Object o : objects) {
					int i = 0;
					i += 1;
					session.save(o);
					if (i % 25 == 1) {
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
	}

	@Override
	public void batcSaveHQL(List<Object> objects) {
		this.batcSave(objects);
	}

	@Override
	public void executeUpdateSQL(String sql) {
		this.executeUpdateS(sql);
	}

	@Override
	public List json2Objects(String json, String _class) {
		List list = JsonParser.json2List(json);
		List objs = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			objs.add(JsonParser.json2Object(list.get(i).toString(), _class));
		}
		return objs;
	}

	// ////////////////////////////////////////////

	@Override
	public Long countByCriteria(DetachedCriteria query) {
		return ((Integer) query.getExecutableCriteria(getSession1())
				.setProjection(Projections.rowCount()).uniqueResult())
				.longValue();
	}

	@Override
	public List findByCriteria(DetachedCriteria query) {
		return query.getExecutableCriteria(getSession1()).list();
	}

	@Override
	public List<Map> findBySql(String sql) {
		return findBySql(sql, null);
	}

	@Override
	public List findBySql(String sql, Class clazz) {
		org.hibernate.SQLQuery query = getSession1().createSQLQuery(sql).addEntity(clazz);
		List<Map> result = query.list();
		return result;
	}

	@Override
	public Long countBySql(String sql) {

		String countSql = "select count(0) from (" + sql + ")";
		org.hibernate.SQLQuery query = getSession1().createSQLQuery(countSql);
		Long count = ((BigDecimal) query.uniqueResult()).longValue();
		return count;
	}

	@Override
	public List findByNativeSql(String sql, String t, Class clz) {
		return getSession1().createSQLQuery(sql).addEntity(t, clz).list();
	}

	@Override
	public Long countByNativeSql(String sql, String t, Class clz) {
		String countSql = "select count(0) from (" + sql + ")";
		Long count = ((BigDecimal) getSession1().createSQLQuery(countSql)
				.uniqueResult()).longValue();
		return count;
	}

	@Override
	public Session getSessionBean() {
		return this.getSession1();
	}

	@Override
	public List findByInstance(Object instance) {
		return this.findByInstance(instance, null);
	}

	@Override
	public List findByInstance(Object instance, String... sorts) {
		return this.findByExample(instance, false, sorts);
	}

	@Override
	public List findByExample(Object instance) {
		return this.findByExample(instance, new String[] {});
	}

	/**
	 * 查询与实例匹配的结果数量
	 * 
	 * @param instance
	 *            查询实例，按属性值匹配对象
	 * @return 与查询实例匹配的结果数量
	 */
	@Override
	public Long countByInstance(Object instance) {
		return countByExample(instance, false);
	}

	@Override
	public Object save(Object o) {
		Object obj = this.getHibernateTemplate().save(o);
		return obj;
	}

	/*****************************内部调用方法******************************************/

	public List findByExample(Object instance, String... sorts) {
		return this.findByExample(instance, true, sorts);
	}

	public List findByExample(Object instance, Boolean enableLike, String... sorts) {
		Criteria criteria = getSession1().createCriteria( instance.getClass().getName());
		if (sorts != null) {
			for (String s : sorts) {
				String[] order = s.trim().split(" +");
				if (order.length >= 2) {
					criteria.addOrder("desc".equals(order[1]) ? Order
							.desc(order[0]) : Order.asc(order[1]));
				} else {
					criteria.addOrder(Order.asc(s));
				}
			}
		}
		Example example = Example.create(instance);
		if (enableLike) {
			example.enableLike();
		}
		example.excludeZeroes();
		return criteria.add(example).list();
	}

	/**
	 * 执行一条用于更新的SQL语句 （仅用此类于内部调用）
	 * 
	 * @param hql
	 * @return
	 */

	private Object executeUpdateS(final String sql) {
		return getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException {
				Query queryObject = session.createSQLQuery(sql);
				return queryObject.executeUpdate();
			}
		});
	}

	private Query genQuery(String hql, Object conditionValues[]) {
		Query query = genQuery(hql);
		setQueryConditionValue(hql, conditionValues, query);
		return query;
	}

	private Query genQuery(String hql) {
		return getSession1().createQuery(hql);
	}

	public Query getQuery(String hql, Object conditionNames[],
			Object conditionValues[]) {
		if (conditionNames.length != conditionValues.length)
			throw new BusinessException(
					"参数和值的数组长度不匹配，长度必需相等！");
		getHibernateTemplate().setCacheQueries(true);
		Query query = getSession1().createQuery(hql);
		if (conditionValues != null) {
			for (int i = 0; i < conditionValues.length; i++) {
				Object param = conditionValues[i];
				if (param == null)
					throw new BusinessException(
							(new StringBuilder("执行HQL为："))
									.append(hql)
									.append("查询的时候验证参数出错，第 ")
									.append(i + 1)
									.append("个参数值为null！ ")
									.toString());
				if (param instanceof Object[])
					query.setParameterList((String) conditionNames[i],
							(Object[]) param);
				else if (param instanceof Collection)
					query.setParameterList((String) conditionNames[i],
							(Collection) param);
				else
					query.setParameter((String) conditionNames[i], param);
			}

		}
		return query;
	}

	public List getEntities(String hql, Object conditionValues[]) {
		Query query = genQuery(hql, conditionValues);
		return query.list();
	}

	public List getEntities(String hql) {
		Query query = genQuery(hql);
		return query.list();
	}

	public List getEntities(String hql, Object conditionNames[],
			Object conditionValues[]) {
		Query query = getQuery(hql, conditionNames, conditionValues);
		return query.list();
	}

	public Object update(Object entity) {
		getHibernateTemplate().update(entity);
		return entity;
	}

	public void updateOrDelete(String hql) {
		Session session = getSession1();
		Query query = session.createQuery(hql);
		query.executeUpdate();
		session.flush();
		session.clear();
	}

	public void updateOrDelete(String hql, Object conditionValues[]) {
		Session session = getSession1();
		Query query = session.createQuery(hql);
		setQueryConditionValue(hql, conditionValues, query);
		query.executeUpdate();
		session.flush();
		session.clear();
	}

	public void updateOrDetete(String hql, Map map) {
		Query query = getQuery(hql, map.keySet().toArray(), map.values()
				.toArray());
		query.executeUpdate();
		getSession1().flush();
		getSession1().clear();
	}

	private void setQueryConditionValue(String hql, Object conditionValues[],
			Query query) {
		if (conditionValues != null) {
			for (int i = 0; i < conditionValues.length; i++) {
				Object param = conditionValues[i];
				if (param == null)
					throw new BusinessException(
							(new StringBuilder("执行HQL为："))
									.append(hql)
									.append("验证参数时出错，第 ")
									.append(i + 1)
									.append("个参数值为null！")
									.toString());
				if (param instanceof Object[])
					throw new BusinessException(
							(new StringBuilder("执行HQL为："))
									.append(hql)
									.append("验证参数时出错，第 ")
									.append(i + 1)
									.append("个参数值为数组！")
									.toString());
				if (param instanceof Collection)
					throw new BusinessException(
							(new StringBuilder("执行HQL为："))
									.append(hql)
									.append(" 验证参数时出错，第 ")
									.append(i + 1)
									.append(" 个参数值为列表！")
									.toString());
				query.setParameter(i, param);
			}

		}
	}

	public Long countByExample(Object instance) {
		return countByExample(instance, true);
	}

	public Long countByExample(Object instance, Boolean enableLike) {
		Criteria criteria = getSession1().createCriteria(
				instance.getClass().getName());
		Example example = Example.create(instance);
		if (enableLike) {
			example.enableLike();
		}
		Long count = ((Integer) criteria.add(example)
				.setProjection(Projections.rowCount()).uniqueResult())
				.longValue();
		return count;
	}

	@Override
	public Object getEntitie(Class clazz, int pk) {
		return this.getSession1().get(clazz, pk);
	}
	
	public Session getSession1(){
		return super.getSession();
	}
	
	
}
