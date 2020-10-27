package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminSubsystemController {

    @FXML
    Button LogoutButton;

    @FXML
    Button QuitButton;

    @FXML
    Button DeleteUserButton;

    @FXML
    Button CreateUserButton;

    @FXML
    AnchorPane adminPane;

    public void LogoutEvent(ActionEvent actionEvent) throws IOException {
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

    public void QuitEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.QuitButton) {
            Stage stage = (Stage) QuitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void DeleteUserEvent(ActionEvent actionEvent) {

    }

    public void CreateUserEvent(ActionEvent actionEvent) {
    }
}
