package mediator;



import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.ServerNotActiveException;

public class Client implements PropertyChangeListener


{
  private RemoteModel server;


  public Client()
  {
    try
    {
      server = (RemoteModel) Naming.lookup("rmi://localhost:1099/Chat");
      server.addListener(this, "user");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }

  public void sendMessage(String message) throws Exception
  {
    server.sendMessage(this, message);
  }

  public boolean disconnect() throws RemoteException{
    return server.disconnect(this);
  }

  public String login(String nickname) throws RemoteException{
    return server.login(this, nickname);
  }

  public String getNumUsers() throws RemoteException{
    return server.getNumUsers();
  }

  public String getIp() throws RemoteException{
    try
    {
      return server.getIp();
    }
    catch (ServerNotActiveException e)
    {
      throw new RuntimeException(e);
    }
  }

  public String getLog() throws RemoteException{
    return server.getLog();
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    System.out.println("\t"+evt.getOldValue() + ": "+ evt.getNewValue());
  }
}
