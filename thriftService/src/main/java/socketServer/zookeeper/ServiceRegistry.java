package socketServer.zookeeper;

/**
 * 服务注册接口
 *
 * @author zhangge
 * @since 1.0.0
 */
public interface ServiceRegistry {

    /**
     * 注册服务名称与服务地址
     *
     * @param serviceName    服务名称
     * @param nodeData 注册服务信息
     */
    void register(String serviceName, Object nodeData);
}