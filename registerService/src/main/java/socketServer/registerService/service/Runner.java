package socketServer.registerService.service;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

/**
 * Created by Admin on 2016/7/21.
 */
public class Runner {
    public static void main(String[] args) {
        try {
            TServerTransport serverTransport = new TServerSocket(9090);

            // Use this for a multithreaded server

            System.out.println("Starting the simple server...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
