package model;

import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class ModelManager implements Model{
  ArrayList<PropertyChangeListener> listenerArrayList;
  Socket localSocket;
  private PrintWriter out;
  private BufferedReader in;

  public ModelManager(Socket localSocket) throws IOException {
    this.localSocket = localSocket;
    listenerArrayList = new ArrayList<>();
    out = new PrintWriter(localSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
  }

  @Override public void sendMessage(String message) throws Exception {
    if (!(message == null)){
      if (!message.isEmpty()){
        out.println("SEND;" + message);
      }
    }
    else {
      throw new Exception("Message cannot be empty");
    }
  }

  @Override public boolean setName(String name) throws IOException {
    out.println("LOG;" + name);
    System.out.println(in.readLine());
    return in.readLine().equals("Logged in as " + name);
  }

  @Override public void addListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")){
      listenerArrayList.add(listener);
    }
  }

  @Override public void removeListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")){
      listenerArrayList.remove(listener);
    }
  }
}
