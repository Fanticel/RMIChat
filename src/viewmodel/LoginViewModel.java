package viewmodel;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

import java.io.IOException;

public class LoginViewModel
{
  private StringProperty errorProperty;
  private StringProperty usernameProperty;
  private Model model;
  public LoginViewModel(Model model)
  {
    this.model = model;
    errorProperty = new SimpleStringProperty();
    usernameProperty = new SimpleStringProperty();
  }

  public void reset()
  {
    errorProperty.set(null);
    usernameProperty.set(null);
  }


  public StringProperty getErrorProperty()
  {
    return errorProperty;
  }


  public StringProperty getUsernameProperty()
  {
    return usernameProperty;
  }

  //Idea is you set the username in model from where you make the server connection
  // with the given username and get the confirmation
  public boolean accept () throws IOException {
    return model.setName(usernameProperty.get());
  }
}

