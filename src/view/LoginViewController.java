package view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import viewmodel.LoginViewModel;

import java.io.IOException;

public class LoginViewController {

  @FXML private Label errorLabel;
  @FXML private TextField usernameField;
  private LoginViewModel viewModel;
  private Region root;
  private ViewHandler viewHandler;

  public void init(ViewHandler viewHandler, LoginViewModel loginViewModel,
      Region root) {
    this.viewHandler = viewHandler;
    this.viewModel = loginViewModel;
    this.root = root;

    errorLabel.textProperty().bind(viewModel.getErrorProperty());
    usernameField.textProperty()
        .bindBidirectional(viewModel.getUsernameProperty());
    reset();
  }

  public void reset() {
    viewModel.reset();
  }

  public Region getRoot() {
    return root;
  }

  @FXML private void submitButton() throws IOException {
    System.out.println("trying to log in...");
    if (viewModel.accept()) {
      System.out.println("successful login ?");
      viewHandler.openView("chatView");
    }
  }

  @FXML void enterPressed(KeyEvent event) throws IOException {
    if (event.getCode().toString().equals("ENTER")) {
      submitButton();
    }
  }
}
