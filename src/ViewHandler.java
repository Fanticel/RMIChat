import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class ViewHandler {
  private ViewModelFactory viewModelFactory;
  private Scene currentScene;
  private Stage primaryStage;
  private ChatViewController chatViewController;
  public ViewHandler(ViewModelFactory viewModelFactory) {
    this.viewModelFactory = viewModelFactory;
    currentScene = new Scene(new Region());
  }
  public void start(Stage primaryStage){
    this.primaryStage = primaryStage;
    openView("chatView");
  }
  public void openView(String id){
    Region root = null;
    switch(id){
      case "chatView" -> root = LoadChatViewController("ChatView.fxml");
    }
    currentScene.setRoot(root);
    String title = "";
    if (root.getUserData() != null) {
      title += root.getUserData();
    }
    primaryStage.setTitle(title);
    primaryStage.setScene(currentScene);
    primaryStage.setWidth(root.getPrefWidth());
    primaryStage.setHeight(root.getPrefHeight());
    primaryStage.show();
  }
  public Region LoadChatViewController(String fxmlFile){
    if (chatViewController == null){
      try{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(fxmlFile));
        Region root = loader.load();
        chatViewController = loader.getController();
        chatViewController.init(this, viewModelFactory.getChatViewModel(), root);
      }catch (Exception e){
        e.printStackTrace();
      }
    }
    else
    {
      chatViewController.reset();
    }
    return chatViewController.getRoot();
  }
}
