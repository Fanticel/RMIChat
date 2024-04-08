package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatViewModel implements PropertyChangeListener
{
  private StringProperty mainText;
  private StringProperty inputText;
  private StringProperty textField;
  private StringProperty errorField;
  private Model model;
  private Socket localSocket;

  private String history;
  private Thread askForUpdate = new Thread(() -> {
    while (true) {
      try {
        Thread.sleep(1000);
        textField.set("Number of people: " + model.getNumUsers());
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }catch (Exception ex){
        System.out.println(ex.getMessage());
      }
    }
  });

  public ChatViewModel(Model model) throws IOException {
    history = "";
    mainText = new SimpleStringProperty();
    inputText = new SimpleStringProperty();
    textField = new SimpleStringProperty();
    errorField = new SimpleStringProperty();
    this.model = model;
    this.model.addListener("mes",this);
    history = model.getLog();
    mainText.set(history);
    askForUpdate.start();
  }

  public StringProperty getMainText() {
    return mainText;
  }

  public StringProperty getInputText() {
    return inputText;
  }

  public void messageApproved() {
    String message = inputText.get();
    try{
      if (message.equals("IP"))
      {
        history = "Your ip is: " + model.getIp() + "\n" + history;
      }
      else
      {
        model.sendMessage(message);
      }
    }
    catch (Exception e){
      errorField.set(e.getMessage());
    }
    inputText.set("");
  }
  public StringProperty textFieldProperty() {
    return textField;
  }
  public StringProperty getErrorField(){
    return errorField;
  }

  @Override public void propertyChange(PropertyChangeEvent evt)
  {
    if (evt.getPropertyName().equals("mes")){
      history = evt.getNewValue() + "\n" + history;
      mainText.set(history);
    }
  }
}
