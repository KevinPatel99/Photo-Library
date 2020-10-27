package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;


public class CopyOrMoveController {

    @FXML Button quitButton;

    public void logoutEvent(ActionEvent actionEvent) {
        Parent parent;

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/loginWindow.fxml"));
            parent = (Parent) loader.load();

            Scene scene = new Scene(parent);

            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void quitEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.quitButton) {
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void confirmEvent(ActionEvent actionEvent) {
    }
}
