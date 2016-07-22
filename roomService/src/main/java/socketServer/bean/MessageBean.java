package socketServer.bean;

import socketServer.bean.enums.MessageType;

import java.util.Arrays;

/**
 * Created by Admin on 2016/7/21.
 */
public class MessageBean {

    private byte byte0;
    private byte byte1;
    private byte[] body;
    private byte byte_1;
    private byte byte_2;
    private boolean tailer;
    private boolean validate;
    private MessageType messageType;

    public MessageBean(byte[] bytes) {
        parseMessage(bytes);
    }

    public void parseMessage(byte[] bytes) {
        this.body = bytes;
        this.byte0 = bytes[0];
        this.byte1 = bytes[1];

        this.byte_1 = bytes[bytes.length - 1];
        this.byte_2 = bytes[bytes.length - 2];

        if (byte_1 == 0xF && byte_2 == 0xF) {
            tailer = true;
        } else {
            tailer = false;
        }

        if (byte0 == 0xE && byte1 == 0xE) {
            this.messageType = MessageType.initConnectRequest;
            cutConnectMessage();
        } else if (byte0 == 0xE && byte1 == 0xF) {
            this.messageType = MessageType.heartbeat;
            cutConnectMessage();
        } else if (byte0 == 0xF && byte1 == 0xE) {
            this.messageType = MessageType.initConnectResponse;
            cutConnectMessage();
        } else if (byte0 == 0xF && byte1 == 0xF) {
            this.messageType = MessageType.heartbeatAck;
            cutConnectMessage();
        } else {
            this.messageType = MessageType.business;
        }

        if (this.tailer) {
            this.validate = true;
        }
    }

    private void cutConnectMessage() {
        int length = this.body.length;
        byte[] bytes = new byte[length - 4];
        System.arraycopy(this.body, 2, bytes, 0, length - 2);
        this.body = bytes;
    }

    public byte[] getBody() {
        return this.body;
    }

    public MessageType getMessageType() {
        return this.messageType;
    }

    @Override
    public String toString() {
        return "MessageBean{" +
                "byte0=" + byte0 +
                ", byte1=" + byte1 +
                ", body=" + Arrays.toString(body) +
                ", byte_1=" + byte_1 +
                ", byte_2=" + byte_2 +
                ", tailer=" + tailer +
                ", validate=" + validate +
                ", messageType=" + messageType +
                '}';
    }
}
