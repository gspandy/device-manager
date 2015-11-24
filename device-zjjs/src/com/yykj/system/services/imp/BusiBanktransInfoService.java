package com.yykj.system.services.imp;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yykj.base.exception.BusinessException;
import com.yykj.base.util.DateUtil;
import com.yykj.system.dao.IBusiBanktransInfoDao;
import com.yykj.system.dao.IBusiBillDetailDao;
import com.yykj.system.dao.IBusiBillInfoDao;
import com.yykj.system.entity.BusiBanktransInfo;
import com.yykj.system.entity.BusiBillDetail;
import com.yykj.system.entity.BusiBillInfo;
import com.yykj.system.entity.Pager;
import com.yykj.system.entity.requestenity.BillInfo2015004;
import com.yykj.system.entity.requestenity.BilllDetail2015004;
import com.yykj.system.entity.requestenity.RequestInfo2015004;
import com.yykj.system.entity.requestenity.UnionPay2015007;
import com.yykj.system.services.IBusiBanktransInfoService;

@Service("busiBanktransInfoService")
public class BusiBanktransInfoService implements IBusiBanktransInfoService {

	@Resource(name = "busiBillInfoDao")
	private IBusiBillInfoDao busiBillInfoDao;

	@Resource(name = "busiBillDetailDao")
	private IBusiBillDetailDao busiBillDetailDao;

	@Resource(name = "busiBanktransInfoDao")
	private IBusiBanktransInfoDao busiBanktransInfoDao;

	@Override
	public void saveBusiBanktransInfo(RequestInfo2015004 requestInfo,
			UnionPay2015007 unionpay, Date localTransDate, String tranType,String bankfalg)throws Exception {
		// 1.保存银行交易记录
		List<BillInfo2015004> billInfos = requestInfo.getBillInfos();
		//计算本次交费所有单总额
		double shouldmoney = 0.00;	//应收总金额
		double actualmoney = 0.00;	//实收总金额
		for (BillInfo2015004 billInfo20150042 : billInfos) {
			shouldmoney += Double.parseDouble(billInfo20150042.getShouldMoney());
			actualmoney += Double.parseDouble(billInfo20150042.getActualMoney());
		}
		BusiBanktransInfo banktransinfo = new BusiBanktransInfo();
		banktransinfo.setYhmc(requestInfo.getYhmc());
		banktransinfo.setIdcard(requestInfo.getIdCard());
		banktransinfo.setYhzh(requestInfo.getYhzh());
		banktransinfo.setPatientid(requestInfo.getPatientId());
		banktransinfo.setGhid(requestInfo.getGhId());
		banktransinfo.setUserid(requestInfo.getUserId());
		banktransinfo.setCardtypeid(requestInfo.getCardTypeID());
		banktransinfo.setTrantype(tranType);
		banktransinfo.setBankflag(bankfalg);
		banktransinfo.setShouldmoney(shouldmoney);
		banktransinfo.setActualmoney(actualmoney);
		if("0".equals(tranType)){		//正常扣费
			banktransinfo.setBankmoney(Double.valueOf(unionpay.getAmount()));
		}else if("1".equals(tranType)){	//交易冲正
			banktransinfo.setBankmoney(-Double.valueOf(unionpay.getAmount()));
		}else{							//交易异常
			banktransinfo.setBankmoney(0.00);
		}
		banktransinfo.setLocalTransDate(localTransDate);
		banktransinfo.setLocalTransNum(unionpay.getTraceNum());
		banktransinfo.setBankTransDate(DateUtil.parse(DateUtil.formatStr2DateStr(unionpay.getTranDate()+unionpay.getTranTime())));
		banktransinfo.setBankTransNum(unionpay.getRefNum());
		int banktransInfoId  = busiBanktransInfoDao.saveBusiBanktransInfo(banktransinfo);
		
		// 2.保存缴费单据
		for (BillInfo2015004 billInfo2015004 : billInfos) {
			BusiBillInfo bill = new BusiBillInfo();
			bill.setBanktransid(banktransInfoId);
			bill.setReceiptno(billInfo2015004.getReceiptNo());
			bill.setReceipttime(DateUtil.parse(billInfo2015004.getReceiptTime()));
			bill.setBilldept(billInfo2015004.getBillDept());
			bill.setDoctor(billInfo2015004.getDoctor());
			bill.setShouldmoney(Double.parseDouble(billInfo2015004.getShouldMoney()));
			bill.setActualmoney(Double.parseDouble(billInfo2015004.getActualMoney()));
			int billinfoid = busiBillInfoDao.saveBillInfo(bill);
			
			// 3.保存单据名细
			List<BilllDetail2015004> datails = billInfo2015004.getDatails();
			for (BilllDetail2015004 billlDetail : datails) {
				BusiBillDetail detail = new BusiBillDetail();
				detail.setBillinfoid(billinfoid);
				detail.setReceiptno(billInfo2015004.getReceiptNo());
				detail.setExecdept(billlDetail.getExecDept());
				detail.setFeestype(billlDetail.getFeesType());
				detail.setFeesitem(billlDetail.getFeesItem());
				detail.setItemid(billlDetail.getItemID());
				detail.setItemname(billlDetail.getItemName());
				detail.setItemunit(billlDetail.getItemUnit());
				detail.setNum(Double.parseDouble(billlDetail.getNum()));
				detail.setPrice(Double.parseDouble(billlDetail.getPrice()));
				detail.setShouldmoney_item(Double.parseDouble(billlDetail.getShouldMoney()));
				detail.setActualmoney_item(Double.parseDouble(billlDetail.getActualMoney()));
				busiBillDetailDao.saveBillDetail(detail);
			}
		}
	}

	@Override
	public int getBanktransInfoCount(String type, String value)
			throws Exception {
		return busiBanktransInfoDao.getBanktransInfoCount(type, value);
	}

	@Override
	public List<BusiBanktransInfo> getBanktransInfos(String type, String value,Integer pageIndex, Integer pageSize,boolean isPaging)  {
		try {
			return busiBanktransInfoDao.getBanktransInfos(type, value, pageIndex,pageSize,isPaging);
		} catch (Exception e) {
			throw new BusinessException("查询失败:"+e.getMessage());
		}
		
	}

	@Override
	public Pager getBillInfoPager(String type, String value, Integer pageIndex,
			Integer pageSize) {
		Pager page = new Pager();
		try {
			int totalCount = busiBanktransInfoDao.getBanktransInfoCount(type, value);
			int totalPages = totalCount / pageSize.intValue();
			if (totalPages % pageSize.intValue() != 0)
				totalPages++;
			else if (totalCount < pageSize.intValue())
				totalPages++;
			
			page.setPageIndex(pageIndex);
			page.setPageSize(pageSize);
			page.setTotalCount(Integer.valueOf(totalCount));
			page.setTotalPages(Integer.valueOf(totalPages));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return page;
	}
}
