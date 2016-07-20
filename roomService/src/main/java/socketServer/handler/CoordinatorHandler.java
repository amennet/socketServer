package socketServer.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 2016/7/20.
 */
public class CoordinatorHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        if (bytes[0] == 0 && bytes[1] == 1) {
            // TODO 更新topic时间
        }
    }


}
