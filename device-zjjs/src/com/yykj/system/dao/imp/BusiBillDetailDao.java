package com.yykj.system.dao.imp;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.yykj.base.dao.imp.BaseDao;
import com.yykj.base.exception.BusinessException;
import com.yykj.system.dao.IBusiBillDetailDao;
import com.yykj.system.entity.BusiBillDetail;


@Repository("busiBillDetailDao")
public class BusiBillDetailDao extends BaseDao implements IBusiBillDetailDao {

	@Override
	public void saveBillDetail(BusiBillDetail billdetail)throws Exception {
		Object obj;
		try {
			obj = save(billdetail);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException("保存未缴费明细失败");
		}
	}

	@Override
	public List<BusiBillDetail> getBillDetailByBillId(int billinfoid)throws Exception {
		String hql ="from BusiBillDetail where billinfoid="+billinfoid;
		List<BusiBillDetail> details = getEntities(hql);
		return details;
	}
}
