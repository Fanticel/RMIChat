package net.mediator;

import java.beans.PropertyChangeListener;
import java.rmi.RemoteException;

public interface RemoteModel extends RemoteSubject{
  boolean disconnect(PropertyChangeListener listener) throws RemoteException;
  void sendMessage(PropertyChangeListener oL,String message) throws RemoteException;
  String login(PropertyChangeListener listener,String nickname) throws RemoteException;
  String getNumUsers() throws RemoteException;
  String getIp() throws RemoteException;
  String getLog() throws RemoteException;
}
