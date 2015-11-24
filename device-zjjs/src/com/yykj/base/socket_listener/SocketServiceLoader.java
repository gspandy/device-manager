package com.yykj.base.socket_listener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;

/**
 * socket监听服务  随tomcat启动
 * @author QinShuJin
 * 2015年7月8日 下午4:11:36
 */
public class SocketServiceLoader implements ServletContextListener {
	private static Logger log = LogUtil.getInstance().getLogger(SocketServiceLoader.class);
	
	private HisServer hisserv;	//HIS 服务
	private BankServer bankserv;	//银行服务

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != hisserv && !hisserv.isInterrupted()) {
			hisserv.closeSocketServer();
			hisserv.interrupt();
		}
		
		if (null != bankserv && !bankserv.isInterrupted()) {
			bankserv.closeSocketServer();
			bankserv.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (null == hisserv) {
			hisserv = new HisServer(null);
			hisserv.start();
		}else{
			hisserv.start();
		}
		
		if (null == bankserv) {
			bankserv = new BankServer(null);
			bankserv.start();
		}else{
			bankserv.start();
		}
	}
}
