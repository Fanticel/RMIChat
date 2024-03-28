package viewmodel;

import model.Model;

import java.io.IOException;
import java.net.Socket;

public class ViewModelFactory {
  private Model model;
  private ChatViewModel chatViewModel;
  private LoginViewModel loginViewModel;
  private Socket socket;

  public ViewModelFactory(Model model, Socket localSocket){
    this.model = model;
    this.socket = localSocket;
  }

  public ChatViewModel getChatViewModel() throws IOException {
    chatViewModel = new ChatViewModel(model, socket);
    return chatViewModel;
  }

  public LoginViewModel getLoginViewModel() {
    loginViewModel = new LoginViewModel(model);
    return loginViewModel;
  }

}
