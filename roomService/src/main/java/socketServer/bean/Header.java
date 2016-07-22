package socketServer.bean;


import socketServer.utils.HexUtil;

/**
 * Created by ZhangGe on 2016/6/20.
 */
public class Header {

    private byte start;

    private byte[] length;

    private byte command;

    private byte version;

    private byte[] id;

    private byte[] keep;

    public byte getStart() {
        return start;
    }

    public void setStart(byte start) {
        this.start = start;
    }

    public byte[] getLength() {
        return length;
    }

    public void setLength(byte[] length) {
        this.length = length;
    }

    public byte getCommand() {
        return command;
    }

    public void setCommand(byte command) {
        this.command = command;
    }

    public byte getVersion() {
        return version;
    }

    public void setVersion(byte version) {
        this.version = version;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public byte[] getKeep() {
        return keep;
    }

    public void setKeep(byte[] keep) {
        this.keep = keep;
    }

    @Override
    public String toString() {
        return  "start:" + HexUtil.bytesToHexString(new byte[]{start}) +
                "| length:|" + HexUtil.bytesToHexString(length) +
                "| command:|" + HexUtil.bytesToHexString(new byte[]{command}) +
                "| version:|" + HexUtil.bytesToHexString(new byte[]{version}) +
                "| id:|" + HexUtil.bytesToHexString(id) +
                "| keep:|" + HexUtil.bytesToHexString(keep) + "|";
    }
}
