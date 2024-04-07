package net.testPackage;

import java.beans.PropertyChangeEvent;
import java.rmi.Naming;
import java.rmi.RemoteException;

public class ClientTest implements RemoteClientModel{
  RemoteClientModel remoteModel;
  public ClientTest(){
    try {
      remoteModel = (RemoteClientModel) Naming.lookup("rmi://localhost:1099/Chat");
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  @Override public boolean disconnect() throws RemoteException {
    return false;
  }

  @Override public boolean sendMessage(String message) throws RemoteException {
    return false;
  }

  @Override public String login(String nickname) throws RemoteException {
    if (remoteModel == null){
      return "Something went wrong UwU";
    }
    else {
      remoteModel.login(nickname);
      return "Logged in as "+nickname;
    }
  }

  @Override public String getNumUsers() throws RemoteException {
    return null;
  }

  @Override public String getIp() throws RemoteException {
    return null;
  }

  @Override public void propertyChange(PropertyChangeEvent evt) {

  }
}
