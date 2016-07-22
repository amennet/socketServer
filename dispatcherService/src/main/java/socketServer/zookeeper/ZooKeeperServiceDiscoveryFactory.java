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
public class ZooKeeperServiceDiscoveryFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscoveryFactory.class);

    private ZooKeeperServiceDiscoveryFactory() {
    }

    public static ZooKeeperServiceDiscovery newInstance(String zkAddress) {
        return new ZooKeeperServiceDiscovery(zkAddress);
    }

    public static ZooKeeperServiceDiscovery newInstance() {
        Properties properties = PropsUtil.loadProps("dispatcherService.properties");
        String zkAddress = (String) properties.get("zookeeper.url");
        return new ZooKeeperServiceDiscovery(zkAddress);
    }

    public static ZooKeeperServiceDiscovery newInstanceWather() {
        Properties properties = PropsUtil.loadProps("dispatcherService.properties");
        String zkAddress = (String) properties.get("zookeeper.url");
        return new ZooKeeperServiceDiscovery(zkAddress);
    }
}