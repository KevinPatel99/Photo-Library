package sample.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.SerializeSession;
import sample.object.Album;

import java.io.IOException;


public class CreateAlbumController {

    @FXML Button createButton;
    @FXML Button quitButton;
    @FXML TextField newAlbumNameField;
    @FXML AnchorPane rootPane;

    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void createButtonEvent(ActionEvent actionEvent) {

        Button b = (Button) actionEvent.getSource();
        if(b == this.createButton) {
            String name = newAlbumNameField.getText();

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Album name field can't be blank");
                alert.showAndWait();
                return;
            }

            Album album = new Album(name);
            SerializeSession.getInstance().getCurrentUser().addAlbum(album);


            /*try {

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/openAlbum.fxml"));
                AnchorPane root = loader.load();

                Scene scene = new Scene(parent);

                Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                app_stage.setScene(scene);
                app_stage.show();



            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }*/

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/openAlbum.fxml"));
            AnchorPane root = null;
            try {
                root = loader.load();
            } catch (IOException e) {

            }
            OpenAlbumController controller = loader.getController();
            controller.start(primaryStage, album);
            Scene scene = new Scene(root,600,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Create Album Menu");
            primaryStage.setResizable(false);
            primaryStage.show();

        }

    }


    public void newAlbumNameEvent(ActionEvent actionEvent) {
    }

    public void quitButtonEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.quitButton) {
            try {
                SerializeSession.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }
}
