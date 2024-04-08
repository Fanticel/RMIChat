package model;

import mediator.Client;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class ModelManager implements Model, PropertyChangeListener{
  private PropertyChangeListener changeListener;


  private Client client;


  public ModelManager(Client client) throws IOException {
    this.client = client;
    this.changeListener = null;

  }

  @Override public void sendMessage(String message) throws Exception {
    if (!(message == null)){
      if (!message.isEmpty()){
         client.sendMessage( message);
      }
    }
    else {
      throw new Exception("Message cannot be empty");
    }
  }

  @Override public boolean setName(String name) throws IOException
  {
    return false;
  }

  @Override public boolean login(String nickname) throws RemoteException
  {
    String response = client.login( nickname);

    return response.equals("\t\tSystem: Logged in as " + nickname);
  }

  @Override public String getIp() throws RemoteException, ServerNotActiveException
  {
    return client.getIp();
  }


  @Override public String getLog() throws RemoteException
  {
    return client.getLog();
  }

  @Override public String getNumUsers() throws RemoteException
  {
    return client.getNumUsers();
  }

  @Override public void addListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")){
      changeListener = listener;
    }
  }

  @Override public void removeListener(String name,
      PropertyChangeListener listener) {
    if (name.equals("mes")){
      changeListener = null;
    }
  }


  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    changeListener.propertyChange(evt);


  }

}
