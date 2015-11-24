package com.yykj.system.services;

import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;

public interface IBusiLogServie {

	public abstract void saveBusiCxLog(RequestInfo2015004 requestInfo,
			BillInfo2015004 billInfo2015004, String requestXml,
			String responseXml, String deviceRequestXml);

	public abstract void saveBusiDealLog(RequestInfo2015004 requestInfo,
			BillInfo2015004 billInfo2015004, String requestXml,
			String responseXml, String deviceRequestXml);
}
