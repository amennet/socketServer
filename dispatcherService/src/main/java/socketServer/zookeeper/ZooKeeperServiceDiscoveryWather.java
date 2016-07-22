package socketServer.zookeeper;


import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import socketServer.constants.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * zookeeper发现服务
 *
 * @author zhangge
 * @since 0.0.1
 */
public class ZooKeeperServiceDiscoveryWather implements ServiceDiscovery {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);
    private ZkClient zkClient;
    private String zkAddress;
    private static List<String> md5Beans = new ArrayList<String>();

    public ZooKeeperServiceDiscoveryWather(String zkAddress) {
        if (!zkAddress.startsWith("zookeeper")) {
            throw new RuntimeException("zookeeper协议不正确");
        }
        zkAddress = zkAddress.substring("zookeeper://".length());
        this.zkAddress = zkAddress;
    }

    /**
     * 连接zookeeper
     */
    public void connectZookeeper(final String name) {
            zkClient = new ZkClient(zkAddress, Constant.ZK_SESSION_TIMEOUT, Constant.ZK_CONNECTION_TIMEOUT);
            String servicePath = Constant.ZK_REGISTRY_PATH + "/" + name;
            zkClient.subscribeChildChanges(servicePath, new IZkChildListener() {
                @Override
                public void handleChildChange(String parentPath, List currentChilds) throws Exception {
                    updateRoomServerList(parentPath, currentChilds);
                }
            });
        }

        /**
         * 更新server列表
         */

    private void updateRoomServerList(String servicePath, List<String> currentChilds) {
        List<String> serverList = new ArrayList<>();
        for (String subNode : currentChilds) {
            // 获取每个子节点下关联的server地址
            String address = zkClient.readData(servicePath + "/" + subNode);
            serverList.add(address);
        }
        // 替换server列表
        md5Beans = serverList;
        System.out.println("server list updated: " + serverList);
    }

    @Override
    public List<String> discover(String serviceName) {
        connectZookeeper(serviceName);
        return null;
    }

    public static List<String> getMd5Beans() {
        return md5Beans;
    }
}