package net.mediator;

import java.beans.PropertyChangeListener;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteSubject extends Remote {
  boolean addListener(PropertyChangeListener listener, String propertyName) throws RemoteException;
  boolean removeListener(PropertyChangeListener listener, String propertyName) throws RemoteException;
}
