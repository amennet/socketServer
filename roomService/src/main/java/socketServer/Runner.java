package socketServer;

import socketServer.utils.PropsUtil;
import socketServer.zookeeper.ZooKeeperServiceRegistry;

import java.util.Properties;

/**
 * Created by Admin on 2016/7/20.
 */
public class Runner {
    public static void main(String[] args) {
        Properties properties = PropsUtil.loadProps("roomService.properties");
        String zkAddress = (String) properties.get("zookeeper.url");
        ZooKeeperServiceRegistry zooKeeperServiceRegistry = new ZooKeeperServiceRegistry(zkAddress);
        zooKeeperServiceRegistry.register("roomService", "127.0.0.1");
        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
