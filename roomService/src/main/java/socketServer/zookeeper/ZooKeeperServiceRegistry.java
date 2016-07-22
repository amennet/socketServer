package socketServer.zookeeper;

import org.I0Itec.zkclient.IZkConnection;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.Runner;
import socketServer.constants.Constant;
import socketServer.utils.GsonUtil;

/**
 * 基于 ZooKeeper 的服务注册接口实现
 *
 * @author zhangge
 * @since 0.0.1
 */
public class ZooKeeperServiceRegistry implements ServiceRegistry{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceRegistry.class);

    private final ZkClient zkClient;

    public ZooKeeperServiceRegistry(String zkAddress) {
        if (!zkAddress.startsWith("zookeeper")) {
            throw new RuntimeException("zookeeper协议不正确");
        }
        zkAddress = zkAddress.substring("zookeeper://".length());
        // 创建 ZooKeeper 客户端
        zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect socketServer.zookeeper");
    }



    public void register(String serviceName, Object nodeData) {
        // 创建 rpc.registry 节点（持久）
        String registryPath = Constant.ZK_REGISTRY_PATH;
        if (!zkClient.exists(registryPath)) {
            zkClient.createPersistent(registryPath);
            LOGGER.debug("create socketServer.registry node: {}", registryPath);
        }
        // 创建 service 节点（持久）
        String servicePath = registryPath + "/" + serviceName;
        if (!zkClient.exists(servicePath)) {
            zkClient.createPersistent(servicePath);
            LOGGER.debug("create service node: {}", servicePath);
        }
        // 创建 address 节点（临时）
        String addressPath = servicePath + "/address-";
        String addressNode = zkClient.createEphemeralSequential(addressPath, GsonUtil.toJson(nodeData));
        LOGGER.debug("create address node: {}", addressNode);
    }
}