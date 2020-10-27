package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.controller.LoginWindowController;

public class Photos extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("fxml/loginWindow.fxml"));
        Parent root = loader.load();
        LoginWindowController controller = loader.getController();
        controller.start(primaryStage);
        Scene scene = new Scene(root,600,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photos");
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
