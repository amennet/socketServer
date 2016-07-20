package socketServer.bean.enums;

/**
 * Created by Admin on 2016/7/20.
 */
public enum MessageType {
    heartbeat("EF", "心跳"),
    initConnectRequest("EE", "初始化"),
    initConnectResponse("FE", "初始化返回"),
    business("", "业务消息");

    private String code;
    private String desc;

    MessageType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getName() {
        return this.name();
    }

    public String getCode() {
        return this.code;
    }

    public static MessageType getMessageType(String header) {
        for (MessageType messageType : MessageType.values()) {
            if (messageType.getCode().equals(header)) {
                return messageType;
            }
        }
        return business;
    }
}
