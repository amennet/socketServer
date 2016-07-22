package socketServer.zookeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.utils.PropsUtil;

import java.util.Properties;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 *
 * @author zhangge
 * @since 0.0.1
 */
public class ZooKeeperServiceRegistryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistryFactory.class);

    private ZooKeeperServiceRegistryFactory() {
    }

    public static ZooKeeperServiceRegistry newInstance(String zkAddress) {
        return new ZooKeeperServiceRegistry(zkAddress);
    }

    public static ZooKeeperServiceRegistry newInstance() {
        Properties properties = PropsUtil.loadProps("roomService.properties");
        String zkAddress = (String) properties.get("zookeeper.url");
        return new ZooKeeperServiceRegistry(zkAddress);
    }
}