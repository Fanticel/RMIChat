package util;

import java.beans.PropertyChangeListener;

public interface NamedPropertyChangeSubject {
  void addListener(String name, PropertyChangeListener listener);
  void removeListener(String name, PropertyChangeListener listener);
}
