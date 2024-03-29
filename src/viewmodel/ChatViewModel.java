package viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import model.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatViewModel {
  private StringProperty mainText;
  private StringProperty inputText;
  private StringProperty textField;
  private StringProperty errorField;
  private Model model;
  private Socket localSocket;
  private PrintWriter out;
  private BufferedReader in;
  private String history;
  private Thread askForUpdate = new Thread(() -> {
    while (true) {
      try {
        Thread.sleep(1000);
        out.println("SIZE;");
      }
      catch (InterruptedException e) {
        throw new RuntimeException(e);
      }
    }
  });
  private Thread threadedListener = new Thread(() -> {
    while (true) {
      try {
        String request = in.readLine();
        if (request.split("->")[0].contains("SIZE")){
          textField.set("Number of people: " + request.replace("SIZE: ", ""));
        }
        else {
          history += "\n" + request;
        }
        mainText.set(history);
      }
      catch (IOException e) {
        break;
      }
    }
  });

  public ChatViewModel(Model model, Socket localSocket) throws IOException {
    history = "";
    mainText = new SimpleStringProperty();
    inputText = new SimpleStringProperty();
    textField = new SimpleStringProperty();
    errorField = new SimpleStringProperty();
    this.model = model;
    this.localSocket = localSocket;
    out = new PrintWriter(localSocket.getOutputStream(), true);
    in = new BufferedReader(
        new InputStreamReader(localSocket.getInputStream()));
    threadedListener.start();
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
      model.sendMessage(message);
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
}
