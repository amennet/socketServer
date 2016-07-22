package socketServer.codec;


import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import socketServer.utils.BuildMessage;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * MessageBean 解码器
 */
public class MessageDecoder extends LengthFieldBasedFrameDecoder {

    public MessageDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initalBytesToStrip) throws IOException {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initalBytesToStrip);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = (ByteBuf) super.decode(ctx, in);
        if (frame == null) {
            return null;
        }

        ByteBuffer byteBuffer = frame.nioBuffer();

        byte[] bytes  = new byte[frame.readableBytes()];
        frame.readBytes(bytes);

        return BuildMessage.bytesToBean(bytes);
    }
}
