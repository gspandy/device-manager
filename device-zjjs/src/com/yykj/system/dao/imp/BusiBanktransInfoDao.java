package com.yykj.system.dao.imp;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.system.dao.IBusiBanktransInfoDao;
import com.yykj.system.entity.BusiBanktransInfo;


@Repository("busiBanktransInfoDao")
public class BusiBanktransInfoDao extends BaseDao implements IBusiBanktransInfoDao {

	@Override
	public int saveBusiBanktransInfo(BusiBanktransInfo banktransInfo) throws Exception {
		int id = 0;
		try {
			Object obj = save(banktransInfo);
			if (obj != null)
				id = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("保存银行交易记录失败");
		}
		return id;
	}


	@Override
	public int getBanktransInfoCount(String type, String value) {
		String hql = "select count(*) from BusiBanktransInfo u where 1=1";
		String where = "";
		if ("0".equals(type)){
			where = "";
		}else if ("1".equals(type)){
			where += " and yhmc like '%"+value+"%'";
		}else if ("2".equals(type)){
			where += " and idcard = '"+value+"'";
		}else if ("3".equals(type)){
			where += " and yhzh = '"+value+"'";
		}else if ("4".equals(type)){
			where += " and ghid = '"+value+"'";
		}else if ("5".equals(type)){
			where += " and patientid = '"+value+"'";
		}
		where += "  order by id desc";
		hql += where;
		Query query = getSession().createQuery(hql);
		Integer totalCount = Integer.valueOf(Integer.parseInt(query
				.uniqueResult().toString()));
		return totalCount.intValue();
	}

	@Override
	public List<BusiBanktransInfo> getBanktransInfos(String type, String value, Integer pageIndex,Integer pageSize,boolean isPaging) {
		String hql = "from BusiBanktransInfo u where 1=1";
		String where = "";
		if ("0".equals(type)){
			where = "";
		}else if ("1".equals(type)){
			where += " and yhmc like '%"+value+"%'";
		}else if ("2".equals(type)){
			where += " and idcard = '"+value+"'";
		}else if ("3".equals(type)){
			where += " and yhzh = '"+value+"'";
		}else if ("4".equals(type)){
			where += " and ghid = '"+value+"'";
		}else if ("5".equals(type)){
			where += " and patientid = '"+value+"'";
		}
		where += "  order by id desc";
		hql += where;
		Query query = getSession().createQuery(hql);
		if(isPaging){
			int firstResult = (pageIndex.intValue() - 1) * pageSize.intValue();
			int maxResult = pageSize.intValue();
			query.setFirstResult(firstResult);
			query.setMaxResults(maxResult);
		}
		List<BusiBanktransInfo> list = query.list();
		return list;
	}
}
