package socketServer.saveService.service.impl;

import org.apache.thrift.TException;
import socketServer.saveService.dao.RedisDao;
import socketServer.saveService.service.HeartBeatService;

/**
 * Created by Admin on 2016/7/22.
 */
public class HeartBeatServiceImpl implements HeartBeatService.Iface {

    private RedisDao redisDao = new RedisDao();

    @Override
    public String updateHeartBeat(String arg0) throws TException {
        redisDao.set(arg0, "a");
        System.out.println(redisDao.get(arg0));
        return redisDao.get(arg0);
    }
}
