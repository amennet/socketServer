package socketServer.dispatcherService.bean;

import io.netty.handler.codec.http.HttpResponse;

/**
 * Created by Admin on 2016/7/21.
 */
public class MD5Bean{

    private String ip;
    private int port;
    private String md5;
    private String protocol;

    public MD5Bean(String ip, int port, String md5, String protocol) {
        this.ip = ip;
        this.port = port;
        this.md5 = md5;
        this.protocol = protocol;
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

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    @Override
    public String toString() {
        return "MD5Bean{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", md5='" + md5 + '\'' +
                ", protocol='" + protocol + '\'' +
                '}';
    }
}
