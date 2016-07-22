package socketServer.constants;

import java.nio.charset.Charset;

import static com.oracle.jrockit.jfr.DataType.UTF8;

/**
 * 常量
 *
 * @author huangyong
 * @since 1.0.0
 */
public interface Constant {

    int ZK_SESSION_TIMEOUT = 5000;
    int ZK_CONNECTION_TIMEOUT = 1000;
    int REDIS_TIMOUT = 5000;

    String ZK_REGISTRY_PATH = "/socketServer/registry";

    String RoomServer = "roomServer";
    String DispatcherServer = "dispatcherServer";
    String SaveServer = "saveServer";

    String UTF8 = "utf-8";

    String socketServer = "socketServer";

    String UTF8_CHARSET_NAME = "UTF-8";
    Charset UTF_8 = Charset.forName("UTF-8");

    String thriftServer = "thriftServer";
    String thriftClient = "thriftClient";
}
