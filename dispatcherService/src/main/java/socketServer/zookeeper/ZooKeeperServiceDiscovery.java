package socketServer.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.constants.Constant;
import socketServer.utils.CollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * zookeeper发现服务
 *
 * @author zhangge
 * @since 0.0.1
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery{

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);

    private String zkAddress;

    public ZooKeeperServiceDiscovery(String zkAddress) {
        if (!zkAddress.startsWith("zookeeper")) {
            throw new RuntimeException("zookeeper协议不正确");
        }
        zkAddress = zkAddress.substring("zookeeper://".length());
        this.zkAddress = zkAddress;
    }

    public List<String> discover(String name) {
        // 创建 ZooKeeper 客户端
        ZkClient zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
        LOGGER.debug("connect socketServer.zookeeper");
        try {
            // 获取 service 节点
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + name;
            if (!zkClient.exists(servicePath)) {
                throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
            }
            List<String> addressList = zkClient.getChildren(servicePath);
            if (CollectionUtil.isEmpty(addressList)) {
                throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
            }

            List<String> nodeData = new ArrayList<>();
            for (String address : addressList) {
                String addressPath = servicePath + "/" + address;
                String data = zkClient.readData(addressPath);
                nodeData.add(data);
            }
            return nodeData;
        } finally {
            zkClient.close();
        }
    }
}