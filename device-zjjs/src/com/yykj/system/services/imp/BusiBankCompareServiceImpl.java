package com.yykj.system.services.imp;

import com.yykj.base.util.PropertiesUtil;
import com.yykj.system.dao.IBusiBankCompareDao;
import com.yykj.system.entity.BusiBankCompare;
import com.yykj.system.entity.Pager;
import com.yykj.system.services.IBusiBankCompareService;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;

@Service("busiBankCompareService")
public class BusiBankCompareServiceImpl implements IBusiBankCompareService {

	@Resource(name="busiBankCompareDao")
	private IBusiBankCompareDao busiBankCompareDao;
	private FtpClient ftpClient;

	
	/**
	 * 保存对账记录
	 */
	public void saveBankCompareRecord(BusiBankCompare compareRecord) {
		try {
			busiBankCompareDao.saveBankCompareRecord(compareRecord);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 下载对账文件
	 */
	public void download() {
		SimpleDateFormat dft;
		String hspCode;
		TelnetInputStream is;
		FileOutputStream os;
		dft = new SimpleDateFormat("yyyyMMdd");
		String ftpHost = PropertiesUtil.getInstance().get("FTP_HOST");
		int port = Integer.parseInt(PropertiesUtil.getInstance().get("FTP_PORT"));
		String userName = PropertiesUtil.getInstance().get("FTP_USER");
		String passWord = PropertiesUtil.getInstance().get("FTP_PASSWORD");
		String path = PropertiesUtil.getInstance().get("FTP_REMOTE_DIR");
		connectServer(ftpHost, port, userName, passWord, path);
		hspCode = PropertiesUtil.getInstance().get("HSP_CODE");
		is = null;
		os = null;
		try {
			Date beginDate = new Date();
			Calendar date = Calendar.getInstance();
			date.setTime(beginDate);
			date.set(5, date.get(5) - 1);
			Date endDate = dft.parse(dft.format(date.getTime()));
			String str_date = dft.format(endDate);
			String fileName = (new StringBuilder(String.valueOf(hspCode))).append("_").append(str_date).append(".txt").toString();
			is = ftpClient.get(fileName);
			String localFilePath = PropertiesUtil.getInstance().get("LOCAL_FILE_PATH");
			File file_in = null;
			if (localFilePath == null){
				file_in = new File(fileName);
			}else{
				file_in = new File((new StringBuilder(String.valueOf(localFilePath))).append(fileName).toString());
			}
			os = new FileOutputStream(file_in);
			byte bytes[] = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			closeConnect();
			readFile(file_in);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		try {
			if (is != null)
				is.close();
			if (os != null)
				os.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 连接FTP服务器
	 * @param ip
	 * @param port
	 * @param user
	 * @param password
	 * @param path
	 */
	public void connectServer(String ip, int port, String user,
			String password, String path) {
		try {
			ftpClient = new FtpClient();
			ftpClient.openServer(ip, port);
			ftpClient.login(user, password);
			System.out.println("login success!");
			if (path.length() != 0){
				ftpClient.cd(path);
			}
			ftpClient.binary();
			ftpClient.sendServer("quote PASV");
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	
	/**
	 * 关闭FTP连接
	 */
	public void closeConnect() {
		try {
			ftpClient.closeServer();
			System.out.println("disconnect success");
		} catch (IOException ex) {
			System.out.println("not disconnect");
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	
	/**
	 * 解析对账文件
	 * @param file
	 * @throws IOException
	 */
	public void readFile(File file) throws IOException {
		String encoding = "UTF-8";
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		for (String lineTxt = null; (lineTxt = bufferedReader.readLine()) != null;) {
			String datas[] = lineTxt.split("\\|");
			BusiBankCompare compareRecord = new BusiBankCompare();
			compareRecord.setHspcode(datas[0]);
			compareRecord.setBankcard(datas[1]);
			compareRecord.setBankdealdate(datas[2]);
			compareRecord.setBankdealnum(datas[3]);
			compareRecord.setDealtime(datas[4]);
			compareRecord.setDealamount(datas[5]);
			compareRecord.setHspdealdate(datas[6]);
			compareRecord.setHspdealnum(datas[7]);
			saveBankCompareRecord(compareRecord);
		}
		read.close();
	}

	
	public List<BusiBankCompare> getBankCompareByParam(String cardno, String startDate, String endDate, Integer pageIndex, Integer pageSize,boolean isPaging) {
		try {
			List<BusiBankCompare> list = busiBankCompareDao.getBankCompareByParam(cardno, startDate,endDate, pageIndex, pageSize,isPaging);
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<BusiBankCompare>();
		}
	}

	public Pager getBankComparePager(String cardno, String startDate,
			String endDate, Integer pageIndex, Integer pageSize) {
		Pager page = new Pager();
		try {
			int totalCount = busiBankCompareDao.getBillInfoCount(cardno, startDate,endDate);
			int totalPages = totalCount / pageSize.intValue();
			if (totalPages % pageSize.intValue() != 0){
				totalPages++;
			}else if (totalCount < pageSize.intValue()){
				totalPages++;
			}
			
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
