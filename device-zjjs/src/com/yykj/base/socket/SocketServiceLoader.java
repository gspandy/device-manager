package com.yykj.base.socket;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * socket监听服务  随tomcat启动
 * @author QinShuJin
 * 2015年7月8日 下午4:11:36
 */
public class SocketServiceLoader implements ServletContextListener {
	private SocketThread socketThread;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		if (null != socketThread && !socketThread.isInterrupted()) {
			socketThread.closeSocketServer();
			socketThread.interrupt();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (null == socketThread) {
			socketThread = new SocketThread(null);
			socketThread.start();
		}else{
			socketThread.start();
		}
	}
}
