package socketServer.saveService.dao;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import socketServer.constants.Constant;
import socketServer.utils.PropsUtil;
import socketServer.zookeeper.ZooKeeperServiceDiscovery;

import java.util.Properties;

import static javafx.scene.input.KeyCode.J;

/**
 * Created by Admin on 2016/7/21.
 */
public class RedisDao {

    private String hostname;
    private String port;
    private String password;
    private JedisPool jedisPool;
    private Jedis jedis;

    public RedisDao() {
        init();
    }

    private void init() {
        Properties properties = PropsUtil.loadProps("saveService.properties");
        this.hostname = (String) properties.get("redis.hostName");
        this.port = (String) properties.get("redis.port");
        this.password = (String) properties.get("redis.password");
        this.jedisPool = new JedisPool(new GenericObjectPoolConfig(), hostname, Integer.parseInt(port), Constant.REDIS_TIMOUT, password);
        this.jedis = jedisPool.getResource();
    }

    public void set(String key, String value) {
        jedis.set(key, value);
    }

    public String get(String key) {
        return jedis.get(key);
    }
}
