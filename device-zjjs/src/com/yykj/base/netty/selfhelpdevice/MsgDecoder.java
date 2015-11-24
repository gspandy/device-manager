package com.yykj.base.netty.selfhelpdevice;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码器 读取客户端消息时使用
 * 
 * @author QinShuJin 2015年7月21日 上午10:21:48
 */
public class MsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
		//8为我们XML头的长度
		if (in.readableBytes() < 8) {
			return;
		}
		in.markReaderIndex(); // 我们标记一下当前的readIndex的位置
		byte[] data_length = new byte[8];
		in.readBytes(data_length);
		int dataLength = Integer.parseInt(new String(data_length));
		if (dataLength < 0) { // 我们读到的消息体长度为0，这是不应该出现的情况，这里出现这情况，关闭连接。
			ctx.close();
		}
		if (in.readableBytes() < dataLength) { 
			// 读到的消息体长度如果小于我们传送过来的消息长度，则resetReaderIndex.
			// 这个配合markReaderIndex使用的。把readIndex重置到mark的地方
			in.resetReaderIndex();
			return;
		}
		byte[] body = new byte[dataLength];
		in.readBytes(body); //

		String msg = new String(body, "GBK");
		list.add(msg);
	}
}
