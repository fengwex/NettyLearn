package com.learn.test.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class DiscardServer {
	private int port;
	public DiscardServer(int port){
		this.port=port;
	}
	public void start(){
		EventLoopGroup boss=new NioEventLoopGroup();
		EventLoopGroup work=new NioEventLoopGroup();
		ServerBootstrap bootstrap=new ServerBootstrap();
		try {
			bootstrap.group(boss, work)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
	
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(new DiscardServerHandler());
				}
			})
			.option(ChannelOption.SO_BACKLOG, 1024)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			try {
				ChannelFuture future=bootstrap.bind(port).sync();
				future.channel().closeFuture().sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} finally {
			work.shutdownGracefully();
			boss.shutdownGracefully();
		}
	}
	public static void main(String[] args) {
		DiscardServer server=new DiscardServer(10001);
		server.start();
	}
}
