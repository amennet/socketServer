package socketServer.handler;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import static javafx.scene.input.KeyCode.F;

/**
 * Created by Admin on 2016/7/20.
 */
public class HeartBeatHeadler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        byte[] bytes = (byte[]) msg;
        if (bytes.length == 36 && bytes[0] == 0xE && bytes[1] == 0x1 && bytes[34] == 0xF && bytes[35] == 0xF) {
            // TODO 更新topic时间
        }
    }
}
