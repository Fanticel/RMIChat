package mediator;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public interface RemoteModel extends RemoteSubject
{
  boolean disconnect(PropertyChangeListener listener) throws RemoteException;
  void sendMessage(PropertyChangeListener oL,String message) throws RemoteException;
  String login(PropertyChangeListener listener,String nickname) throws RemoteException;
  String getNumUsers() throws RemoteException;
  String getIp() throws RemoteException, ServerNotActiveException;
  String getLog() throws RemoteException;

}
