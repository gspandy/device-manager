package com.yykj.base.netty.unionpay;

import org.apache.commons.codec.binary.Hex;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * 消息编码器
 * 给客户端写消息时进行消息编码
 * @author QinShuJin
 * 2015年7月21日 上午10:21:04
 */

public class UnionPayMsgEncoder extends MessageToByteEncoder<String> {

	@Override
	protected void encode(ChannelHandlerContext ctx, String msg, ByteBuf out)
			throws Exception {
		String str = msg.toString();
		byte [] byte_out = Hex.decodeHex(str.toCharArray());
		
        out.writeBytes(byte_out);
	}
}
