package socketServer.bean.enums;

public enum MessageSplitPointEnum {

    startSymbol("起始符", "startSymbol", 0, 1, 1),

    protocolLength("协议长度", "protocolLength", 1, 3, 2),

    command("命令", "command", 3, 4, 1),

    version("命令版本", "version", 4, 5, 1),

    requestId("请求ID", "version", 5, 8, 3),

    keep("保留位", "keep", 8, 10, 2),

    // 从content的end开始。都是从后面开始计算位置的。需要用packetLength来减去
    content("数据内容", "content", 10, 5, 0),

    proof("校验码", "proof", 5, 1, 4),

    tail("结束符", "tail", 1, 0, 1);  // 不用计算，在收取到数据转码的时候就已经处理掉了。

    String memo;
    String key;
    int start;
    int end;
    int length;  // 不是定长的用0标示

    public String getMemo() {
        return memo;
    }

    public String getKey() {
        return key;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public int getLength() {
        return length;
    }

    MessageSplitPointEnum(String memo, String key, int start, int end, int length) {
        this.memo = memo;
        this.key = key;
        this.start = start;
        this.end = end;
        this.length=length;
    }
}
