import javafx.application.Application;
import javafx.stage.Stage;
import mediator.Client;
import model.Model;
import model.ModelManager;
import view.ViewHandler;
import viewmodel.ViewModelFactory;

import java.io.PrintWriter;
import java.net.Socket;

public class MyApplication extends Application
{

  @Override public void start(Stage primaryStage) throws Exception
  {
    Client client = new Client();
    Model model = new ModelManager(client);
    ViewModelFactory viewModelFactory = new ViewModelFactory(model);
    ViewHandler view = new ViewHandler(viewModelFactory);

    view.start(primaryStage);
  }

  //  @Override public void stop() throws Exception {
  //   System.out.println("stopping the application");
  //   out = new PrintWriter(localSocket.getOutputStream(), true);
  //     out.println("^Q");
  //  localSocket.close();
  // }
}
