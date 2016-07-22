package socketServer.bean;


import socketServer.constants.Constant;

import java.io.UnsupportedEncodingException;

/**
 * 消息bean
 */
public class Message {

    public Message() {
        this.header = new Header();
        this.tailer = new Tailer();
    }

    public Message(String returnMessage) {
        this();
        this.returnMessage = returnMessage;
    }

    public Message(String setCommand, String setContent) {
        this();
        this.setCommand = setCommand;
        this.setContent = setContent;
        this.set = true;
    }

    private Header header;

    private byte[] content;

    private Tailer tailer;

    private String returnMessage;

    private String setCommand;

    private String setContent;

    private boolean set;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public Tailer getTailer() {
        return tailer;
    }

    public void setTailer(Tailer tailer) {
        this.tailer = tailer;
    }

    public String getReturnMessage() {
        return returnMessage;
    }

    public void setReturnMessage(String returnMessage) {
        this.returnMessage = returnMessage;
    }

    public String getSetCommand() {
        return setCommand;
    }

    public void setSetCommand(String setCommand) {
        this.setCommand = setCommand;
    }

    public String getSetContent() {
        return setContent;
    }

    public void setSetContent(String setContent) {
        this.setContent = setContent;
    }

    @Override
    public String toString() {
        try {
            return  "Header : " + header + "\n" +
                    "Message : content:" + new String(content, Constant.UTF8) + "\n" +
                    "Tailer : " + tailer +
                    "\n" +
                    "-----------------------------------------------------------------------------";
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
