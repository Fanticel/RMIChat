import java.io.IOException;
import java.net.Socket;

public class ViewModelFactory {
  private Model model;
  private ChatViewModel chatViewModel;
  public ViewModelFactory(Model model, Socket localSocket) throws IOException {
    this.model = model;
    chatViewModel = new ChatViewModel(model, localSocket);
  }

  public ChatViewModel getChatViewModel(){
    return chatViewModel;
  }
}
