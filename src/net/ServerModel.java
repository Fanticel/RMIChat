package net;

import util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerModel implements NamedPropertyChangeSubject {


  ArrayList<PropertyChangeListener> listenerList;
  private static ServerModel instance;

  private ServerModel() {
    listenerList = new ArrayList<>();
  }

  public static synchronized ServerModel getInstance(){
    if (instance == null) {
      instance = new ServerModel();
    }
    return instance;
  }

  public void sendMessage(String message,
      PropertyChangeListener originalListener, String name) {
    for (PropertyChangeListener l : listenerList) {
      l.propertyChange(new PropertyChangeEvent(this, "message", name, message));
    }
  }

  @Override public void addListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")) {
      listenerList.add(listener);
    }
  }

  @Override public void removeListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")) {
      listenerList.remove(listener);
    }
  }
}
