/*
 * Copyright 2013-2018 Lilinfeng.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package socketServer.dispatcherService.dispatcherServer.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.constants.Constant;
import socketServer.zookeeper.ZooKeeperServiceDiscoveryFactory;
import socketServer.zookeeper.ZooKeeperServiceDiscovery;


/**
 * @author zhangge
 */
public class HttpServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpServer.class);

    public void run(final String ip, final int port) {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("http-decoder", new HttpRequestDecoder());
                            ch.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
                            ch.pipeline().addLast("http-encoder", new HttpResponseEncoder());
                            ch.pipeline().addLast("jsonServerHandler", new HttpServerHandler());
                        }
                    });
            ChannelFuture future = b.bind(ip, port).sync();
            System.out.println("HTTP服务器启动，网址是 : " + "http://" + ip + ":" + port);

            // 定时发现roomServer
            // new Timer().schedule(new zkDiscovery(), 0, 10000);
            zkDiscover();

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /*private class zkDiscovery extends TimerTask {
        @Override
        public void run() {
            List<String> address = zkDiscover();
            HttpServerHandler.setMd5Beans(zkDiscover());
            System.out.println(new Date() + " roomServer address : " + GsonUtil.toJson(address));
        }

        private List<String> zkDiscover() {
            ZooKeeperServiceDiscovery zooKeeperServiceDiscovery = ZooKeeperServiceDiscoveryFactory.newInstance();
            return zooKeeperServiceDiscovery.discover(Constant.RoomServer);
        }
    }*/

    private void zkDiscover() {
        ZooKeeperServiceDiscovery zooKeeperServiceDiscoveryWather = ZooKeeperServiceDiscoveryFactory.newInstanceWather();
        zooKeeperServiceDiscoveryWather.discover(Constant.RoomServer);
    }

}
