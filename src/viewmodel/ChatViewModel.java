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
  private Model model;
  private Socket localSocket;
  private PrintWriter out;
  private BufferedReader in;
  private String history;
  private Thread threadedListener = new Thread(()->{
    while(true){
      try {
        history += "\n" + in.readLine();
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
    this.model = model;
    this.localSocket = localSocket;
    out = new PrintWriter(localSocket.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(localSocket.getInputStream()));
    out.println("Name");
    threadedListener.start();
  }
  public StringProperty getMainText(){
    return mainText;
  }
  public StringProperty getInputText(){
    return inputText;
  }
  public void messageApproved(){
    out.println(inputText.get());
    inputText.set("");
  }
}
