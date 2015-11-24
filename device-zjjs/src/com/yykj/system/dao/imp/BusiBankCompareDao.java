package com.yykj.system.dao.imp;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.util.StringUtil;
import com.yykj.system.dao.IBusiBankCompareDao;
import com.yykj.system.entity.BusiBankCompare;

@Repository("busiBankCompareDao")
public class BusiBankCompareDao extends BaseDao implements IBusiBankCompareDao {

	@Override
	public void saveBankCompareRecord(BusiBankCompare compareRecord) throws Exception {
		save(compareRecord);
	}

	@Override
	public int getBillInfoCount(String cardno, String startDate, String endDate)throws Exception {
		String hql = "select count(*) from BusiBankCompare u where 1=1";
		String where = "";
		if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and bankcard='"+cardno+"'";
		else if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and hspdealdate >='"+startDate+"'";
		else if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and hspdealdate <='"+endDate+"'";
		where += "  order by id desc";
		hql += where;
		Query query = getSession().createQuery(hql);
		Integer totalCount = Integer.valueOf(Integer.parseInt(query.uniqueResult().toString()));
		return totalCount.intValue();
	}

	@Override
	public List<BusiBankCompare> getBankCompareByParam(String cardno, String startDate, String endDate, Integer pageIndex, Integer pageSize,boolean isPaging) throws Exception {
		int firstResult = (pageIndex.intValue() - 1) * pageSize.intValue();
		int maxResult = pageSize.intValue();
		String hql = "from BusiBankCompare where 1=1";
		String where = "";
		if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and bankcard='"+cardno+"'";
		else if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and hspdealdate >='"+startDate+"'";
		else if (StringUtil.isNotNullOrEmpty(cardno))
			where += " and hspdealdate <='"+endDate+"'";
		where += "  order by id desc";
		hql += where;
		Query query = getSession().createQuery(hql);
		if(isPaging){
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
		}
		List<BusiBankCompare> list = query.list();
		return list;
	}
}