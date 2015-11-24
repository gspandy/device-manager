package com.yykj.system.dao.imp;


import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.system.dao.IBusiBillInfoDao;
import com.yykj.system.entity.BusiBillInfo;


@Repository("busiBillInfoDao")
public class BusiBillInfoDao extends BaseDao implements IBusiBillInfoDao {

	@Override
	public int saveBillInfo(BusiBillInfo bill) throws Exception {
		int id = 0;
		try {
			Object obj = save(bill);
			if (obj != null)
				id = Integer.parseInt(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("保存缴费记录失败");
		}
		return id;
	}

	@Override
	public void updateBillInfo(String receiptNo) throws Exception {
		String hql = "update BusiBillInfo set state='1' where receiptno='"+receiptNo+"'";
		updateOrDelete(hql);
	}

	@Override
	public int getBillInfoCount(String type, String value) {
		String hql = "select count(*) from BusiBillInfo u where 1=1";
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
			where += " and receiptno = '"+value+"'";
		}else if ("6".equals(type)){
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
	public List<BusiBillInfo> getBillInfos(String type, String value, Integer pageIndex,
			Integer pageSize) {
		int firstResult = (pageIndex.intValue() - 1) * pageSize.intValue();
		int maxResult = pageSize.intValue();
		String hql = "from BusiBillInfo u where 1=1";
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
			where += " and receiptno = '"+value+"'";
		}else if ("6".equals(type)){
			where += " and patientid = '"+value+"'";
		}
		where += "  order by id desc";
		hql += where;
		Query query = getSession().createQuery(hql);
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);
		List<BusiBillInfo> list = query.list();
		return list;
	}

	@Override
	public List<BusiBillInfo> getBillInfoByBankTransId(int banktransId)
			throws Exception {
		String hql = "from BusiBillInfo u where banktransid="+banktransId;
		List<BusiBillInfo> list = this.getEntities(hql);
		return list;
	}
	
}
