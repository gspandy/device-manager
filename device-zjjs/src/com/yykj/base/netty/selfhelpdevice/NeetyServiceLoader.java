package com.yykj.base.netty.selfhelpdevice;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * netty服务  随tomcat启动
 * @author QinShuJin
 * 2015年7月21日 上午10:29:27
 */
public class NeetyServiceLoader implements ServletContextListener {
	private NettyServerThread nettyServer;

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		if (null == nettyServer) {
			nettyServer = new NettyServerThread();
			nettyServer.start();
		}else{
			nettyServer.start();
		}
	}
}
