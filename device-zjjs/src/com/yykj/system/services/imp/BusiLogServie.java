package com.yykj.system.services.imp;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.system.dao.IBusiCxLogDao;
import com.yykj.system.dao.IBusiDealLogDao;
import com.yykj.system.entity.BusiCxLog;
import com.yykj.system.entity.BusiDealLog;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.services.IBusiLogServie;

@Service("busiLogServie")
public class BusiLogServie implements IBusiLogServie {

	@Resource(name="busiCxLogDao")
	private IBusiCxLogDao busiCxLogDao;
	@Resource(name="busiDealLogDao")
	private IBusiDealLogDao busiDealLogDao;

	public void saveBusiCxLog(RequestInfo2015004 requestInfo,
			BillInfo2015004 billInfo2015004, String requestXml,
			String responseXml, String deviceRequestXml) {
		BusiCxLog log = new BusiCxLog();
		log.setReceiptno(billInfo2015004.getReceiptNo());
		log.setYhmc(requestInfo.getYhmc());
		log.setYhzh(requestInfo.getYhzh());
		log.setGhid(requestInfo.getGhId());
		log.setIdcard(requestInfo.getIdCard());
		log.setPatientid(requestInfo.getPatientId());
		log.setUserid(requestInfo.getUserId());
		log.setRequestxml(requestXml);
		log.setResponsexml(responseXml);
		log.setDevicerequestxml(deviceRequestXml);
		busiCxLogDao.saveBusiCxLog(log);
	}

	public void saveBusiDealLog(RequestInfo2015004 requestInfo,
			BillInfo2015004 billInfo2015004, String requestXml,
			String responseXml, String deviceRequestXml) {
		BusiDealLog log = new BusiDealLog();
		log.setReceiptno(billInfo2015004.getReceiptNo());
		log.setYhmc(requestInfo.getYhmc());
		log.setYhzh(requestInfo.getYhzh());
		log.setGhid(requestInfo.getGhId());
		log.setIdcard(requestInfo.getIdCard());
		log.setPatientid(requestInfo.getPatientId());
		log.setUserid(requestInfo.getUserId());
		log.setRequestxml(requestXml);
		log.setResponsexml(responseXml);
		log.setDevicerequestxml(deviceRequestXml);
		busiDealLogDao.saveBusiDealLog(log);
	}
}
