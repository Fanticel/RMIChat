package model;

import util.NamedPropertyChangeSubject;

import java.io.IOException;

public interface Model extends NamedPropertyChangeSubject {
  void sendMessage(String message) throws Exception;
  boolean setName(String name) throws IOException;
}
