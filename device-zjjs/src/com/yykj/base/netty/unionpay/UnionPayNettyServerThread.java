package com.yykj.base.netty.unionpay;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import org.apache.log4j.Logger;

import com.yykj.base.util.LogUtil;
import com.yykj.base.util.PropertiesUtil;

/**
 * 创建netty 服务
 * 用于接收自助设备发送的8583包
 * 并转发给银联
 * @author QinShuJin 
 * 2015年7月21日 上午10:22:56
 */
public class UnionPayNettyServerThread extends Thread {
	private static Logger log = LogUtil.getInstance().getLogger(UnionPayNettyServerThread.class);
	// CPU数量
	private static int CPU_CONT = Integer.parseInt(PropertiesUtil.getInstance().get("CPU_COUNT", "2"));
	// 单颗CPU线程数量
	private static int ONE_CPU_THRED_COUNT = Integer.parseInt(PropertiesUtil.getInstance().get("ONE_CPU_THRED_COUNT", "50"));
	// 队列大小
	private static int QUEUE_SIZE = Integer.parseInt(PropertiesUtil.getInstance().get("QUEUE_SIZE", "500"));
	// 服務端口
	private static int PORT = Integer.parseInt(PropertiesUtil.getInstance().get("ZZSB_UNIONPAY_PORT", "8899").toString());
	// 超时时间
	private static int TIME_OUT = Integer.parseInt(PropertiesUtil.getInstance().get("TIME_OUT", "60").toString());
	// 工作线程大小
	//CPU密集型应用线程计算公式   N+1
	//I/O密集型应用线程计算公式  2N+1
	private static int WORK_SIZE = 2*(CPU_CONT * ONE_CPU_THRED_COUNT)+1;

	// 用来接收请求,相当于队列
	private static final EventLoopGroup bossGroup = new NioEventLoopGroup(QUEUE_SIZE);
	// 处理请求的线程
	private static final EventLoopGroup workerGroup = new NioEventLoopGroup(WORK_SIZE);
	
	@Override
	public void run() {
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					// .option(ChannelOption.SO_BACKLOG, 128)
					//.handler(new LoggingHandler(LogLevel.WARN))
					.childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch)throws Exception {
							ChannelPipeline p = ch.pipeline();
							//p.addLast(new LoggingHandler(LogLevel.WARN));
							p.addLast(new IdleStateHandler(50, 50, TIME_OUT));
							p.addLast(new UnionPayMsgEncoder(),
									new UnionPayMsgDecoder(),
									new UnionPaySocketHandler());
						}
					});

			// 启动服务
			ChannelFuture f = b.bind(PORT).sync();
			System.out.println("银联Netty服务创建成功,队列数："+QUEUE_SIZE +"  处理线程数："+WORK_SIZE +"个 ,服务端口："+PORT);
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			log.error("银联创建Netty服务出错", e);
			e.printStackTrace();
		} finally {
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}
