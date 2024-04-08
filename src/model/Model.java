package model;

import util.NamedPropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface Model extends NamedPropertyChangeSubject {
  void sendMessage(String message) throws Exception;
  boolean setName(String name) throws IOException;
   boolean login(String nickname) throws RemoteException;
  String getIp() throws RemoteException ,ServerNotActiveException;
  String getLog() throws RemoteException;
  String getNumUsers() throws RemoteException;
}
