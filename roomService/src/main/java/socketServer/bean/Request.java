package socketServer.bean;

import socketServer.bean.enums.MessageType;

/**
 * 封装请求消息
 */
public class Request {

    private MessageType header;
    private byte[] body;
    private boolean tailer;

    public MessageType getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = MessageType.getMessageType(header);
    }

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public boolean isTailer() {
        return tailer;
    }

    public void setTailer(boolean tailer) {
        this.tailer = tailer;
    }
}
