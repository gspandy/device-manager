package com.yykj.base.netty.unionpay;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.commons.codec.binary.Hex;

/**
 * 消息解码器 读取客户端消息时使用
 * 
 * @author QinShuJin 2015年7月21日 上午10:21:48
 */
public class UnionPayMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {
		//2为我们XML头的长度
		if (in.readableBytes() < 2) {
			return;
		}
		in.markReaderIndex(); // 我们标记一下当前的readIndex的位置
		byte[] data_length = new byte[2];
		in.readBytes(data_length);
		
		String length =  new String(Hex.encodeHex(data_length)).toUpperCase();
		int dataLength = Integer.parseInt(length,16);
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
		
		String msg = new String(Hex.encodeHex(body)).toUpperCase();
		list.add(msg);
	}
}
