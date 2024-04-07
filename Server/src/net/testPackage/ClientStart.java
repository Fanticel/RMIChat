package net.testPackage;

import java.rmi.RemoteException;

public class ClientStart {
  public static void main(String[] args) throws RemoteException {
    ClientTest clientTest = new ClientTest();
    System.out.println(clientTest.login("Fantic"));
  }
}
