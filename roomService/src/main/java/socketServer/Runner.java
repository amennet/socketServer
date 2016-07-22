package socketServer;

import socketServer.roomServer.RoomServer;

/**
 * Created by Admin on 2016/7/20.
 */
public class Runner {
    public static void main(String[] args) {
        RoomServer roomServer = new RoomServer();
        roomServer.bind("127.0.0.1", 9879);
    }
}
