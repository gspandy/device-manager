package com.yykj.base.netty.unionpay;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * netty服务  随tomcat启动
 * 用于接收自助设备发送的8583包
 * 并转发给银联
 * @author QinShuJin
 * 2015年7月21日 上午10:29:27
 */
public class UnionPayNeetyServiceLoader implements ServletContextListener {
	private UnionPayNettyServerThread nettyServer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (null == nettyServer) {
			nettyServer = new UnionPayNettyServerThread();
			nettyServer.start();
		}else{
			nettyServer.start();
		}
	}
}
