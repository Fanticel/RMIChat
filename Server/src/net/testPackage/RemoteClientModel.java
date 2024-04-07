package net.testPackage;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteClientModel extends PropertyChangeListener, Remote {
  boolean disconnect() throws RemoteException;
  boolean sendMessage(String message) throws RemoteException;
  String login(String nickname) throws RemoteException;
  String getNumUsers() throws RemoteException;
  String getIp() throws RemoteException;
}
