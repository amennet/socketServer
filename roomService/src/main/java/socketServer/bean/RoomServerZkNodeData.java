package socketServer.bean;

import java.io.Serializable;

/**
 * Created by Admin on 2016/7/21.
 */
public class RoomServerZkNodeData {

    private String ip;
    private int port;

    public RoomServerZkNodeData(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
