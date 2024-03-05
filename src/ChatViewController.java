import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;

public class ChatViewController {
  @FXML TextArea mainTextArea;
  @FXML Button sendBtn;
  @FXML TextField inputField;
  private ViewHandler viewHandler;
  private ChatViewModel chatViewModel;
  private Region root;
  public ChatViewController(){}
  public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root){
    this.viewHandler = viewHandler;
    this.chatViewModel = chatViewModel;
    this.root = root;
    mainTextArea.textProperty().bind(chatViewModel.getMainText());
    inputField.textProperty().bindBidirectional(chatViewModel.getInputText());
    inputField.requestFocus();
  }
  public void reset(){

  }
  public Region getRoot(){
    return root;
  }
  @FXML void sendBtnPress(){
    chatViewModel.messageApproved();
  }
  @FXML void enterPressed(KeyEvent event){
    if (event.getCode().toString().equals("ENTER")) {
      chatViewModel.messageApproved();
    }
  }
}
