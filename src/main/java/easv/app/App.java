package easv.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MovieManager.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 830);
        //scene.getStylesheets().add(String.valueOf(ContextMenu.class.getResource("styles/ContextMenu.css")));
        stage.setTitle("Popkernel Time");
        stage.setMinHeight(830);
        stage.setMinWidth(900);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}