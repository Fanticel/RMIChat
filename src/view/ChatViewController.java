package view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import viewmodel.ChatViewModel;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ChatViewController {
  @FXML TextArea mainTextArea;
  @FXML Button sendBtn;
  @FXML TextField inputField;
  @FXML ImageView imageView;
  @FXML Text textField;
  @FXML Text errorBox;
  private ViewHandler viewHandler;
  private ChatViewModel chatViewModel;
  private Region root;
  public ChatViewController(){}
  public void init(ViewHandler viewHandler, ChatViewModel chatViewModel, Region root)
      throws FileNotFoundException {
    this.viewHandler = viewHandler;
    this.chatViewModel = chatViewModel;
    this.root = root;
    mainTextArea.textProperty().bind(chatViewModel.getMainText());
    inputField.textProperty().bindBidirectional(chatViewModel.getInputText());
    textField.textProperty().bindBidirectional(chatViewModel.textFieldProperty());
    errorBox.textProperty().bindBidirectional(chatViewModel.getErrorField());
    inputField.requestFocus();
    FileInputStream imageInputStream = new FileInputStream("src\\view\\img\\defaultimage.jpg");
    imageView.setImage(new Image(imageInputStream));
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
