package net;

import net.mediator.SingleServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.ExportException;

public class ServerStart {
  public static void main(String[] args) throws IOException {
    try{
      Registry reg = LocateRegistry.createRegistry(1099);
      System.out.println("registry started");
    }
    catch (ExportException ex){
      System.out.println("Registry already present Message:" + ex.getMessage());
    }
    SingleServer server = new SingleServer();
//    int PORT = 1234;
//    ThreadedServer threadedServer = ThreadedServerFactory.createThreadedServer(new ServerSocket(PORT));
//    Thread s0 = new Thread(threadedServer);
//    s0.start();
  }
}
