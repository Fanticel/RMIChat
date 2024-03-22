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
import java.net.UnknownHostException;

public class ThreadedServer implements Runnable, PropertyChangeListener {
  Thread s1;
  String request;
  String answer;
  ServerSocket welcomeSocket;
  ServerModel model;
  String message;
  Socket currentSocket;
  PrintWriter out;
  BufferedReader in;

  public ThreadedServer(ServerSocket welcomeSocket, ServerModel model) {
    this.welcomeSocket = welcomeSocket;
    this.model = model;
    message = "";
    model.addListener("mes", this);
    currentSocket = null;
    out = null;
    in = null;
  }

  @Override public void run() {
    try
    {
      System.out.println(
          "→Server ip: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("→Waiting for a client...");
      currentSocket = welcomeSocket.accept();
      s1 = new Thread(new ThreadedServer(welcomeSocket, model));
      s1.start();
      System.out.println(
          "→Found a client... connecting to " + currentSocket.getInetAddress()
              .getHostAddress());
      int currentThreadNum = Thread.activeCount() - 3;
      System.out.println(
          "\t\tStarting a listening thread number " + (currentThreadNum + 1)
              + "...");
      in = new BufferedReader(
          new InputStreamReader(currentSocket.getInputStream()));
      out = new PrintWriter(currentSocket.getOutputStream(), true);
      out.println("->->->Connected with " + currentSocket.getInetAddress()
          .getHostAddress());
      String userName = in.readLine();
      out.println("\tLogged in as " + userName + "\n______________________");
      model.sendMessage((userName + " entered the chat"), this, "\t->System: ");
      while (true)
      {
        message = in.readLine();
        if (message.equals("^Q"))
        {
          currentSocket.close();
          System.out.println(userName + " left");
          model.sendMessage((userName + " left the chat"), this, "\t->System");
          break;
        }
        else
        {
          model.sendMessage(message, this, userName);
          System.out.println(
              userName + "(Thread:" + currentThreadNum + ") sent a message.");
        }
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (currentSocket != null && in != null && out != null){
      out.println("\t"+evt.getOldValue() + ": "+ evt.getNewValue());
    }
  }
}
