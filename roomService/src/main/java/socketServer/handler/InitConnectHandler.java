package socketServer.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import socketServer.bean.Message;
import socketServer.bean.MessageBean;
import socketServer.bean.enums.Command;
import socketServer.bean.enums.MessageType;

import java.net.SocketAddress;

/**
 * Created by Admin on 2016/7/20.
 */
public class InitConnectHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("init" + msg);
        Message message = (Message) msg;
        if (message.getHeader().getCommand() == Command.EE.getByte()) {
            System.out.println("init" + message);
            // TODO 获取IP地址,校验安全,验证MD5编号
            SocketAddress socketAddress = ctx.channel().remoteAddress();
        } else {
            ctx.fireChannelRead(message);
        }

    }
}
