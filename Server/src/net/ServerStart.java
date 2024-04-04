package net;

import java.io.IOException;
import java.net.ServerSocket;

public class ServerStart {
  public static void main(String[] args) throws IOException {
    int PORT = 1234;
    ThreadedServer threadedServer = ThreadedServerFactory.createThreadedServer(new ServerSocket(PORT));
    Thread s0 = new Thread(threadedServer);
    s0.start();
  }
}
