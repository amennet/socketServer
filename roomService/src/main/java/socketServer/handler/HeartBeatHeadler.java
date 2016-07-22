package socketServer.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import socketServer.bean.Message;
import socketServer.bean.MessageBean;
import socketServer.bean.enums.Command;
import socketServer.bean.enums.MessageType;

/**
 * Created by Admin on 2016/7/20.
 */
public class HeartBeatHeadler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println(msg);
        Message message = (Message) msg;
        if (message.getHeader().getCommand() == Command.EF.getByte()) {
            System.out.println("heart" + message);
            // TODO 更新topic时间
        }
    }
}
