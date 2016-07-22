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
package socketServer.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import socketServer.codec.ChargeStationMessageDecoder;
import socketServer.codec.ChargeStationMessageEncoder;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;


/**
 * @author zhangge
 * @version 0.1
 * @date 2016年6月15日
 */
public class SocketClient {

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect(final String host, final int port) throws Exception {
        // 配置客户端NIO线程组
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ChargeStationMessageDecoder(1024 * 1024, 1, 2, -3, 0));
                            ch.pipeline().addLast("MessageEncoder", new ChargeStationMessageEncoder());
                            ch.pipeline().addLast("readTimeoutHandler", new ReadTimeoutHandler(100, TimeUnit.SECONDS));
                            ch.pipeline().addLast("ChargeStationEventHandler", new ChargeStationEventHandler());
                        }
                    });
            // 发起异步连接操作
            b.bind(new InetSocketAddress("127.0.0.1", 30302));
            ChannelFuture future = b.connect(new InetSocketAddress(host, port)).sync();
            future.channel().closeFuture().sync();

        } finally {
            // 关闭心跳，清空资源，再次发起重连操作
            try {
                TimeUnit.SECONDS.sleep(10);
                try {
                    connect(host, port);  // 发起重连操作
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
