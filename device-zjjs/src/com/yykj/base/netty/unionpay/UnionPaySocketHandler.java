package com.yykj.base.netty.unionpay;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import org.apache.log4j.Logger;

import com.yykj.base.socket.SocketTask;
import com.yykj.base.util.LogUtil;

public class UnionPaySocketHandler extends ChannelInboundHandlerAdapter {
	private Logger log = LogUtil.getInstance().getLogger(SocketTask.class);
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String request_msg = msg.toString();
		log.info(" 主机收到信息：" + request_msg);
		// 1.业务处理
		String response_msg = UnionPaySocketClient_Mu.requestBank(request_msg);
		
		// 2.输出信息
		log.info(" 主机返回值：" + response_msg);
		ctx.writeAndFlush(response_msg);

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
