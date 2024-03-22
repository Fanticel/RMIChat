package net;

import util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ServerModel implements NamedPropertyChangeSubject {
  ArrayList<PropertyChangeListener> listenerList;

  public ServerModel() {
    listenerList = new ArrayList<>();
  }

  public void broadcast(String message, String name) {
    for (PropertyChangeListener l : listenerList) {
      l.propertyChange(new PropertyChangeEvent(this, "message", name, message));

    }
  }

  public void broadcastToElse(PropertyChangeListener oL, String message,
      String name) {
    for (PropertyChangeListener l : listenerList) {
      if (!l.equals(oL)) {
        l.propertyChange(
            new PropertyChangeEvent(this, "message", name, message));
      }
    }
  }

  public void privateAnswer(PropertyChangeListener l, String message,
      String name) {
    l.propertyChange(new PropertyChangeEvent(this, "message", name, message));
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
