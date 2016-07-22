package socketServer;

import socketServer.constants.Constant;
import socketServer.dispatcherService.dispatcherServer.server.HttpServer;
import socketServer.utils.PropsUtil;
import socketServer.zookeeper.ZooKeeperServiceDiscovery;

import java.util.Properties;

/**
 * Created by Admin on 2016/7/21.
 */
public class Runner {
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        httpServer.run("127.0.0.1", 9638);
    }
}
