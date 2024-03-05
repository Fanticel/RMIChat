import javafx.application.Application;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.net.Socket;

public class MyApplication extends Application {
  private final int PORT = 1234;
  private final String HOST = "localhost";
  PrintWriter out;
  private Socket localSocket;
  @Override public void start(Stage primaryStage) throws Exception {
    localSocket = new Socket(HOST, PORT);
    Model model = new ModelManager();
    ViewModelFactory viewModelFactory = new ViewModelFactory(model, localSocket);
    ViewHandler viewHandler = new ViewHandler(viewModelFactory);
    viewHandler.start(primaryStage);
  }

  @Override public void stop() throws Exception {
    System.out.println("stopping the application");
    out = new PrintWriter(localSocket.getOutputStream(), true);
    out.println("^Q");
    localSocket.close();
  }
}
