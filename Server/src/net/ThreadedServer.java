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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ThreadedServer implements Runnable, PropertyChangeListener {
  private Thread s1;
  private String request;
  private ServerSocket welcomeSocket;
  private ServerModel model;
  private Socket currentSocket;
  private PrintWriter out;
  private BufferedReader in;
  Scanner systemIn;
  private String userName;
  private int currentThreadNum;
  private boolean working = true;
  private LogSaver logSaver;
  Thread adminLogRequest = new Thread(() -> {
    while (true) {
      if (systemIn.nextLine().toLowerCase().equals("log")){
        System.out.println(LogSingleton.getInstance().getLog());
      }
    }
  });

  public ThreadedServer(ServerSocket welcomeSocket, ServerModel model) {
    this.welcomeSocket = welcomeSocket;
    this.model = model;
    model.addListener("mes", this);
    currentSocket = null;
    out = null;
    in = null;
    systemIn = new Scanner(System.in);
    logSaver = new LogSaver();
    logSaver.start();
    userName = "DefaultUserName";
    currentThreadNum = 0;
    adminLogRequest.start();
  }

  @Override public void run() {
    try {
      System.out.println(
          "→Server ip: " + InetAddress.getLocalHost().getHostAddress());
      System.out.println("→Waiting for a client...");
      currentSocket = welcomeSocket.accept();
      s1 = new Thread(new ThreadedServer(welcomeSocket, model));
      s1.start();
      System.out.println(
          "→Found a client... connecting to " + currentSocket.getInetAddress()
              .getHostAddress());
      LogSingleton.getInstance().increaseThreadCount();
      int currentThreadNum = Thread.activeCount() - 3;
      System.out.println(
          "\t\tStarting a listening thread number " + (currentThreadNum + 1)
              + "...");
      in = new BufferedReader(
          new InputStreamReader(currentSocket.getInputStream()));
      out = new PrintWriter(currentSocket.getOutputStream(), true);
      out.println("->->->Connected with " + currentSocket.getInetAddress()
          .getHostAddress());
      while (working) {
        request = in.readLine();
        String[] reqSplit = request.split(";");
        handleRequest(reqSplit);
      }
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void handleRequest(String[] reqSplit) throws IOException {
    switch (reqSplit[0]) {
      case "initCall" -> {
        out.println("initCallReply");
      }
      case "ECHO" -> out.println(reqSplit[1]);
      case "^Q" -> {
        LogSingleton.getInstance().decreaseThreadCount();
        working = false;
        logSaver.closeFile();
        currentSocket.close();
        System.out.println(userName + " left");
        model.broadcast((userName + " left the chat"), "\t->System");
      }
      case "SEND" -> {
        try {
          model.broadcast(reqSplit[1],
              (LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                  + "\t" + userName));
          LogSingleton.getInstance().addLog(
              LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                  + "\t" + userName + ": " + reqSplit[1] + "\n");
          logSaver.addToLog(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"))
              + "\t" + userName + ": " + reqSplit[1] + "\n");
          System.out.println(
              userName + "(Thread:" + currentThreadNum + ") sent a message.");
        }
        catch (Exception e) {
          model.privateAnswer(this, ("\nError 02, unrecognised command.\n"),
              "\tSystem");
        }
      }
      case "LOG" -> {
        try {
          userName = reqSplit[1];
          model.privateAnswer(this, ("Logged in as " + userName), "\tSystem");
        }
        catch (Exception e) {
          model.privateAnswer(this, ("\nError 02, unrecognised command.\n"),
              "\tSystem");
        }
      }
      case "SIZE" -> {
        model.privateAnswer(this,
            String.valueOf(LogSingleton.getInstance().getThreadCount()),
            "SIZE");
      }
      case "GETLOG" -> {
        model.privateAnswer(this, LogSingleton.getInstance().getLog(), "Past messages\n");
      }
      case "IP" -> {
        model.privateAnswer(this,
            currentSocket.getRemoteSocketAddress().toString().replace("/", ""),
            "Private");
      }
      default ->
          model.privateAnswer(this, ("\nError 01, unrecognised command.\n"),
              "\tSystem");
    }
  } //out.println("\nError 01, unrecognised command.\n")

  @Override public void propertyChange(PropertyChangeEvent evt) {
    if (currentSocket != null && in != null && out != null) {
      out.println("\t" + evt.getOldValue() + ": " + evt.getNewValue());
    }
  }
}
