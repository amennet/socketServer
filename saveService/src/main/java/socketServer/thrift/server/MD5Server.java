package socketServer.thrift.server;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import socketServer.saveService.service.HeartBeatService;
import socketServer.saveService.service.impl.HeartBeatServiceImpl;

public class MD5Server {

    public static void StartsimpleServer(HeartBeatService.Processor<HeartBeatServiceImpl> processor) {
        try {
            TServerTransport serverTransport = new TServerSocket(9092);
            TServer server = new TSimpleServer(
                    new TServer.Args(serverTransport).processor(processor));

            // Use this for a multithreaded server
            // TServer server = new TThreadPoolServer(new
            // TThreadPoolServer.Args(serverTransport).processor(processor));

            System.out.println("Starting the simple server...");
            server.serve();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        StartsimpleServer(new HeartBeatService.Processor<HeartBeatServiceImpl>(new HeartBeatServiceImpl()));
    }

}