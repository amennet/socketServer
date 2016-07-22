package socketServer.saveService.service.impl;

import org.apache.thrift.TException;
import socketServer.saveService.service.OnlineService;

/**
 * Created by Admin on 2016/7/22.
 */
public class OnlineServiceImpl implements OnlineService.Iface {
    @Override
    public boolean online(String arg0) throws TException {
        return false;
    }

    @Override
    public boolean offline(String arg0) throws TException {
        return false;
    }

    @Override
    public boolean isOnline(String arg0) throws TException {
        return false;
    }
}
