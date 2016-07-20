package socketServer.nettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import socketServer.codec.RpcDecoder;
import socketServer.codec.RpcEncoder;

import java.io.IOException;

public class NettyServer {
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	try {
		// 创建并初始化 Netty 服务端 Bootstrap 对象
		ServerBootstrap bootstrap = new ServerBootstrap();
		bootstrap.group(bossGroup, workerGroup);
		bootstrap.channel(NioServerSocketChannel.class);
		bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
			@Override
			public void initChannel(SocketChannel channel) throws Exception {
				ChannelPipeline pipeline = channel.pipeline();
				pipeline.addLast(new RpcDecoder(RpcRequest.class)); // 解码 RPC 请求
				pipeline.addLast(new RpcEncoder(RpcResponse.class)); // 编码 RPC 响应
				pipeline.addLast(new RpcServerHandler(handlerMap)); // 处理 RPC 请求
			}
		});
		bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
		bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
		// 获取 RPC 服务器的 IP 地址与端口号
		String[] addressArray = StringUtil.split(serviceAddress, ":");
		String ip = addressArray[0];
		int port = Integer.parseInt(addressArray[1]);
		// 启动 RPC 服务器
		ChannelFuture future = bootstrap.bind(ip, port).sync();
		// 注册 RPC 服务地址
		if (serviceRegistry != null) {
			for (String interfaceName : handlerMap.keySet()) {
				serviceRegistry.register(interfaceName, serviceAddress);
				LOGGER.debug("register service: {} => {}", interfaceName, serviceAddress);
			}
		}
		LOGGER.debug("rpc.server started on port {}", port);
		// 关闭 RPC 服务器
		future.channel().closeFuture().sync();
	} finally {
		workerGroup.shutdownGracefully();
		bossGroup.shutdownGracefully();
	}


    public void bind() throws Exception {
		// 配置服务端的NIO线程组
	EventLoopGroup bossGroup = new NioEventLoopGroup();
	EventLoopGroup workerGroup = new NioEventLoopGroup();
	ServerBootstrap b = new ServerBootstrap();
	b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
		.option(ChannelOption.SO_BACKLOG, 100)
		.handler(new LoggingHandler(LogLevel.INFO))
		.childHandler(new ChannelInitializer<SocketChannel>() {
		    @Override
		    public void initChannel(SocketChannel ch)
			    throws IOException {
			ch.pipeline().addLast(
				new NettyMessageDecoder(1024 * 1024, 4, 4));
			ch.pipeline().addLast(new NettyMessageEncoder());
			ch.pipeline().addLast("readTimeoutHandler",
				new ReadTimeoutHandler(50));
			ch.pipeline().addLast(new com.phei.netty.protocol.netty.server.LoginAuthRespHandler());
			ch.pipeline().addLast("HeartBeatHandler",
				new com.phei.netty.protocol.netty.server.HeartBeatRespHandler());
		    }
		});

	// 绑定端口，同步等待成功
	b.bind(NettyConstant.REMOTEIP, NettyConstant.PORT).sync();
	System.out.println("Netty server start ok : "
		+ (NettyConstant.REMOTEIP + " : " + NettyConstant.PORT));
    }

    public static void main(String[] args) throws Exception {
	new NettyServer().bind();
    }
}