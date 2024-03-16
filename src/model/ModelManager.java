package model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;

public class ModelManager implements Model{
  ArrayList<PropertyChangeListener> listenerArrayList;

  public ModelManager() {
    listenerArrayList = new ArrayList<>();
  }

  @Override public void sendMessage(String name, String message) {

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
