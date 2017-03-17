package com.learn.test.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg){
		ByteBuf in = (ByteBuf) msg;
//		ctx.write(in);
//		ctx.flush();
		  try {
		        while (in.isReadable()) {
		            System.out.print((char) in.readByte());
		            System.out.flush();
		        }
		    } finally {
		        ReferenceCountUtil.release(msg);
		    }
	}
}
