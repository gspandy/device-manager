package com.yykj.base.netty.selfhelpdevice;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;
import org.dom4j.DocumentException;

import com.yykj.base.socket.SocketTask;
import com.yykj.base.util.LogUtil;
import com.yykj.system.handlerequest.BusiHandler;

public class SocketHandler extends ChannelInboundHandlerAdapter {
	private Logger log = LogUtil.getInstance().getLogger(SocketTask.class);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws DocumentException {
		String requestXml = msg.toString();
		log.info(" 主机收到信息：" + requestXml);
		// 1.业务处理
		String responseXml = BusiHandler.handler(requestXml);
		
		// 2.输出信息
		log.info(" 主机返回值：" + responseXml);
		ctx.writeAndFlush(responseXml);

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt)
			throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				System.out.println("读取超时");
				ctx.close();
			} else if (event.state() == IdleState.WRITER_IDLE) {
				System.out.println("写入超时");
				ctx.close();
			} else if (event.state() == IdleState.ALL_IDLE) {
				System.out.println("整个操作超时");
				ctx.close();
			}
		}
	}
}
