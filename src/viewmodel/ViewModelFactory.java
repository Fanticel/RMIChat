package viewmodel;

import model.Model;

import java.io.IOException;
import java.net.Socket;

public class ViewModelFactory {
  private Model model;
  private ChatViewModel chatViewModel;
  private LoginViewModel loginViewModel;
  private Socket socket;

  public ViewModelFactory(Model model){
    this.model = model;
  }

  public ChatViewModel getChatViewModel() throws IOException {
    chatViewModel = new ChatViewModel(model);
    return chatViewModel;
  }

  public LoginViewModel getLoginViewModel() {
    loginViewModel = new LoginViewModel(model);
    return loginViewModel;
  }

}
