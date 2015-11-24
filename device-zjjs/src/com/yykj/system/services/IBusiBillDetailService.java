package com.yykj.system.services;

import java.util.List;

import com.yykj.system.entity.BusiBillDetail;

public interface IBusiBillDetailService {

	public abstract void saveBillDetail(BusiBillDetail busibilldetail);

	public abstract List<BusiBillDetail> getBillDetailByBillId(int billinfoid);
}
