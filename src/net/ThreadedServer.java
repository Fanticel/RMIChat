package net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadedServer implements Runnable, PropertyChangeListener {
  private Thread s1;
  private String request;
  private ServerSocket welcomeSocket;
  private ServerModel model;
  private Socket currentSocket;
  private PrintWriter out;
  private BufferedReader in;
  private String userName;
  private int currentThreadNum;
  private boolean working = true;

  public ThreadedServer(ServerSocket welcomeSocket, ServerModel model) {
    this.welcomeSocket = welcomeSocket;
    this.model = model;
    model.addListener("mes", this);
    currentSocket = null;
    out = null;
    in = null;
    userName = "DefaultUserName";
    currentThreadNum = 0;
  }

  @Override public void run() {
    try{
      System.out.println("→Server ip: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("→Waiting for a client...");
      currentSocket = welcomeSocket.accept();
      s1 = new Thread(new ThreadedServer(welcomeSocket, model));
      s1.start();
      System.out.println("→Found a client... connecting to " + currentSocket.getInetAddress().getHostAddress());
      int currentThreadNum = Thread.activeCount()-3;
      System.out.println("\t\tStarting a listening thread number " + (currentThreadNum+1)  + "...");
      in = new BufferedReader(new InputStreamReader(currentSocket.getInputStream()));
      out = new PrintWriter(currentSocket.getOutputStream(), true);
      out.println("->->->Connected with " + currentSocket.getInetAddress().getHostAddress());
      while(working){
        request = in.readLine();
        String[] reqSplit = request.split(";");
        handleRequest(reqSplit);
      }
    }
    catch (IOException e){e.printStackTrace();}
  }
  public void handleRequest(String[] reqSplit) throws IOException {
    switch (reqSplit[0]){
      case "initCall" -> out.println("initCallReply");
      case "ECHO" -> out.println(reqSplit[1]);
      case "^Q" -> {
        working = false;
        currentSocket.close();
        System.out.println(userName + " left");
        model.broadcast((userName + " left the chat"),"\t->System");}
      case "SEND" -> {
        model.broadcast(reqSplit[1], userName);
        System.out.println(userName+"(Thread:"+currentThreadNum+") sent a message.");}
      case "LOG" -> { userName = reqSplit[1];
        out.println("Logged in as " + userName);
      }
      default -> out.println("\nError 01, unrecognised command.\n");
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (currentSocket != null && in != null && out != null){
      out.println("\t"+evt.getOldValue() + ": "+ evt.getNewValue());
    }
  }
}
