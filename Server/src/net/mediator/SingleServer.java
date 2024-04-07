package net.mediator;

import net.model.LogSingleton;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class SingleServer implements RemoteModel {
  ArrayList<PropertyChangeListener> listeners;
  Dictionary<PropertyChangeListener, String> listenerStringDictionary;

  public SingleServer() throws RemoteException, MalformedURLException {
    listeners = new ArrayList<>();
    listenerStringDictionary = new Hashtable<>();
    RemoteModel stub = (RemoteModel) UnicastRemoteObject.exportObject(this, 0);
    Naming.rebind("Chat", stub);
    System.out.println("Starting server...");
  }

  @Override public boolean disconnect(PropertyChangeListener listener)
      throws RemoteException {
    LogSingleton.getInstance().decreaseUserCount();
    removeListener(listener, "user");
    return true;
  }

  @Override public void sendMessage(PropertyChangeListener oL, String message) throws RemoteException {
    for (PropertyChangeListener l : listeners){
      l.propertyChange(new PropertyChangeEvent(this, "mes", listenerStringDictionary.get(oL), message));
    }
  }

  @Override public String login(PropertyChangeListener listener,
      String nickname) throws RemoteException {
    listenerStringDictionary.put(listener, nickname);
    addListener(listener, "user");
    return "Successfuly logged in as " + nickname;
  }

  @Override public String getNumUsers() throws RemoteException {
    return String.valueOf(LogSingleton.getInstance().getThreadCount());
  }

  @Override public String getIp() throws RemoteException {
    return "1099";
  }

  @Override public String getLog() throws RemoteException {
    return net.LogSingleton.getInstance().getLog();
  }

  @Override public boolean addListener(PropertyChangeListener listener,
      String propertyName) throws RemoteException {
    if (propertyName.equals("user")) {
      listeners.add(listener);
      return true;
    }
    return false;
  }

  @Override public boolean removeListener(PropertyChangeListener listener,
      String propertyName) throws RemoteException {
    if (propertyName.equals("user")) {
      listeners.remove(listener);
      listenerStringDictionary.remove(listener);
      return true;
    }
    return false;
  }
}
