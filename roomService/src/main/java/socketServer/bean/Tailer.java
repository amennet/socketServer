package socketServer.bean;


import socketServer.utils.HexUtil;

/**
 * Created by ZhangGe on 2016/6/20.
 */
public class Tailer {

    private byte[] proof;

    private byte tail;

    public byte[] getProof() {
        return proof;
    }

    public void setProof(byte[] proof) {
        this.proof = proof;
    }

    public byte getTail() {
        return tail;
    }

    public void setTail(byte tail) {
        this.tail = tail;
    }

    @Override
    public String toString() {
        return  "proof:|" + HexUtil.bytesToHexString(proof) +
                "| tail:|" + HexUtil.bytesToHexString(new byte[]{tail}) + "|";
    }
}
