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
public class CoordinatorHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Message message = (Message) msg;
        System.out.println("business" + message);
        // 传到业务系统去
    }


}
