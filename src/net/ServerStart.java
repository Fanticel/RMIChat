package net;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerStart {
  public static void main(String[] args) throws IOException {
    int PORT = 1234;
    ServerModel serverModel = new ServerModel();
    ThreadedServer threadedServer = new ThreadedServer(new ServerSocket(PORT), serverModel);
    Thread s0 = new Thread(threadedServer);
    s0.start();
  }
}
