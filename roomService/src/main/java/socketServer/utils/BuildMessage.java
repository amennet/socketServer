package socketServer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.bean.Message;
import socketServer.bean.enums.Command;
import socketServer.bean.enums.MessageSplitPointEnum;
import socketServer.constants.Constant;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.zip.CRC32;

/**
 * bean转换protocol工具类
 */
public class BuildMessage {

    private static final Logger logger = LoggerFactory.getLogger(BuildMessage.class);

    /**
     * 将bean转为数据包字节数组
     * @param message
     * @return
     */
    public static byte[] beanToBytes(Message message) {
        // 设置数据包长度, 包头+包尾共15 byte
        short length = (short) (message.getContent().length + 15);
        message.getHeader().setLength(HexUtil.shortToByteArray(length));

        // 组装byte数据
        byte[] bytes = new byte[length];
        bytes[MessageSplitPointEnum.startSymbol.getStart()] = message.getHeader().getStart();
        System.arraycopy(message.getHeader().getLength(), 0, bytes, MessageSplitPointEnum.protocolLength.getStart(), message.getHeader().getLength().length);

        bytes[MessageSplitPointEnum.command.getStart()] = message.getHeader().getCommand();
        bytes[MessageSplitPointEnum.version.getStart()] = message.getHeader().getVersion();

        System.arraycopy(message.getHeader().getId(), 0, bytes, MessageSplitPointEnum.requestId.getStart(), message.getHeader().getId().length);
        System.arraycopy(message.getHeader().getKeep(), 0, bytes, MessageSplitPointEnum.keep.getStart(), message.getHeader().getKeep().length);
        System.arraycopy(message.getContent(), 0, bytes, MessageSplitPointEnum.content.getStart(), message.getContent().length);

        //抓取报文头和报文体包的长度，进行crc32校验
        int headAndLength = length - MessageSplitPointEnum.proof.getStart();
        CRC32 crc32 = new CRC32();
        crc32.update(Arrays.copyOfRange(bytes, 0, headAndLength));
        int proof = (int) crc32.getValue();
        message.getTailer().setProof(HexUtil.intToByteArray(proof));

        System.arraycopy(message.getTailer().getProof(), 0, bytes, headAndLength, message.getTailer().getProof().length);
        bytes[bytes.length - 1] = message.getTailer().getTail();
        logger.info("protocolBean 数据:{}", message.toString());
        return bytes;
    }

    /**
     * 将数据包字节数组转为bean
     *
     * @param bytes
     * @return
     */
    public static Message bytesToBean(byte[] bytes) {
        Message message = new Message();
        message.getHeader().setStart(bytes[MessageSplitPointEnum.startSymbol.getStart()]);
        message.getHeader().setLength(Arrays.copyOfRange(bytes, MessageSplitPointEnum.protocolLength.getStart(), MessageSplitPointEnum.protocolLength.getEnd()));
        message.getHeader().setCommand(bytes[MessageSplitPointEnum.command.getStart()]);
        message.getHeader().setVersion(bytes[MessageSplitPointEnum.version.getStart()]);
        message.getHeader().setId(Arrays.copyOfRange(bytes, MessageSplitPointEnum.requestId.getStart(), MessageSplitPointEnum.requestId.getEnd()));
        message.getHeader().setKeep(Arrays.copyOfRange(bytes, MessageSplitPointEnum.keep.getStart(), MessageSplitPointEnum.keep.getEnd()));
        message.setContent(Arrays.copyOfRange(bytes, MessageSplitPointEnum.content.getStart(), bytes.length - MessageSplitPointEnum.content.getEnd()));
        message.getTailer().setProof(Arrays.copyOfRange(bytes, bytes.length - MessageSplitPointEnum.proof.getStart(), bytes.length - MessageSplitPointEnum.proof.getEnd()));
        message.getTailer().setTail(bytes[bytes.length - MessageSplitPointEnum.tail.getStart()]);
        logger.debug("Decode message to messageBean :{}", message);
        return message;
    }

    public static Message buildRecalledMessage(Message msg, String content) {
        Message message = buildNewCommonMessage();
        message.getHeader().setCommand(msg.getHeader().getCommand());
        message.getHeader().setId(msg.getHeader().getId());
        try {
            message.setContent(content.getBytes(Constant.UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Message buildNewMessage(Command command, String content) {
        Message message = buildNewCommonMessage();
        message.getHeader().setCommand(command.getByte());
        message.getHeader().setId(IDBuilder.getRequestIdByteArray());
        try {
            message.setContent(content.getBytes(Constant.UTF8));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static Message buildNewCommonMessage() {
        Message message = new Message();
        message.getHeader().setStart((byte) (0xDE & 0xFF));
        message.getHeader().setVersion((byte) (0x00 & 0xFF));
        message.getHeader().setKeep(new byte[]{0x00, 0x00});
        message.getTailer().setTail((byte) (0xDF & 0xFF));
        return message;
    }
}
