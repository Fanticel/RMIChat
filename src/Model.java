import util.NamedPropertyChangeSubject;

public interface Model extends NamedPropertyChangeSubject {
  void sendMessage(String name, String message);
}
