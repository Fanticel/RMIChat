package net;

import java.net.ServerSocket;

public class ThreadedServerFactory
{
  public static ThreadedServer createThreadedServer(ServerSocket welcomeSocket){
    ServerModel model = ServerModel.getInstance();
    return new ThreadedServer(welcomeSocket, model);
  }
}
