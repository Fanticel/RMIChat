package mediator;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteListener extends Remote {
  void propertyChange(Object oldValue, Object newValue) throws RemoteException;
}
