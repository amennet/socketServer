package socketServer.roomServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.bean.RoomServerZkNodeData;
import socketServer.codec.MessageDecoder;
import socketServer.constants.Constant;
import socketServer.handler.CoordinatorHandler;
import socketServer.handler.HeartBeatHeadler;
import socketServer.handler.InitConnectHandler;
import socketServer.zookeeper.ZooKeeperServiceRegistry;
import socketServer.zookeeper.ZooKeeperServiceRegistryFactory;

import java.net.SocketAddress;

public class RoomServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RoomServer.class);

    public void bind(String ip, int port) {
        // 配置服务端的NIO线程组
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
                    pipeline.addLast(new MessageDecoder(1024 * 1024, 1, 2, -3, 0)); // 解码 MessageBean 请求
                    pipeline.addLast(new InitConnectHandler()); // 处理 初始化链接 请求
                    pipeline.addLast(new HeartBeatHeadler()); // 处理 心跳 请求
                    pipeline.addLast(new CoordinatorHandler()); // 处理 业务 请求
                }
            });
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture future = bootstrap.bind(ip, port).sync();

            // 注册到zookeeper
            registerZk(ip, port);

            LOGGER.debug("rpc.server started on port {}", port);
            // 关闭 RPC 服务器
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    private void registerZk(String ip, int port) {
        ZooKeeperServiceRegistry zooKeeperServiceRegistry = ZooKeeperServiceRegistryFactory.newInstance();
        zooKeeperServiceRegistry.register(Constant.RoomServer, new RoomServerZkNodeData(ip, port));
        LOGGER.debug("register service: {} => {}", ip, port);
    }
}