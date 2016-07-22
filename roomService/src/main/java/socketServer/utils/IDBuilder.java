package socketServer.utils;


import java.util.Arrays;
import java.util.Date;

/**
 * Created by moon on 2016/1/18.
 */
public class IDBuilder {

    static int sequence = 0;

    static long time = (new Date().getTime()) % 100000;

    /**
     * 生成通信协议中的请求ID。
     * 生成规则。时间毫秒数%100000 +  Random(99)
     *
     * @return
     */
    public final static synchronized int getRequestId() {
        Date dt = new Date();
        Long moment = dt.getTime() % 100000;  //这就是距离1970年1月1日0点0分0秒的毫秒数
        int sq = getSequence(moment);
        String id = time + String.valueOf(sq);
        return Integer.parseInt(id);
    }

    /**
     * 获取时间序列号
     * @param moment
     * @return
     */
    public static synchronized int getSequence(long moment) {
        if (sequence == 99) {
            sequence = 0;
            if (moment == time) {
                ++time;
            }else{
                time = moment;
            }
        } else {
            ++sequence;
        }
        return sequence;
    }

    /**
     * 生成通信协议中的请求ID。
     * 生成规则。时间毫秒数%100000 +  Random(99)
     *
     * @return 返回3个字节长度的ID
     */
    public static byte[] getRequestIdByteArray() {
        byte[] idbytes = HexUtil.intToByteArray(getRequestId());
        return Arrays.copyOfRange(idbytes, 1, 4);
    }


}

