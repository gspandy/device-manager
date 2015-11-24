package com.yykj.base.netty.selfhelpdevice;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import com.yykj.base.util.SocketUtil;


/**
 * 消息编码器
 * 给客户端写消息时进行消息编码
 * @author QinShuJin
 * 2015年7月21日 上午10:21:04
 */

public class MsgEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		String str = msg.toString();
		byte [] byte_out = str.getBytes("GBK");
		//byte [] byte_= SocketUtil.addLenHead(byte_out, 8, "GBK");
        out.writeBytes(byte_out);
	}
}
