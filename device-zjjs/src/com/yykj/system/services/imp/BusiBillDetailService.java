package com.yykj.system.services.imp;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.IBusiBillDetailDao;
import com.yykj.system.entity.BusiBillDetail;
import com.yykj.system.services.IBusiBillDetailService;

@Service("busiBillDetailService")
public class BusiBillDetailService implements IBusiBillDetailService {

	@Resource(name="busiBillDetailDao")
	private IBusiBillDetailDao busiBillDetailDao;

	public void saveBillDetail(BusiBillDetail billdetail) {
		try {
			busiBillDetailDao.saveBillDetail(billdetail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<BusiBillDetail> getBillDetailByBillId(int billinfoid) {
		List<BusiBillDetail> list = null;
		try {
			list = busiBillDetailDao.getBillDetailByBillId(billinfoid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?new ArrayList<BusiBillDetail>():list;
	}
}