package socketServer.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.net.SocketAddress;

/**
 * Created by Admin on 2016/7/20.
 */
public class InitConnectHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        if (bytes.length == 36 && bytes[0] == 0xE && bytes[1] == 0x0 && bytes[34] == 0xF && bytes[35] == 0xF) {
            // TODO 获取IP地址,校验安全,验证MD5编号
            SocketAddress socketAddress = ctx.channel().remoteAddress();
        }
    }
}
