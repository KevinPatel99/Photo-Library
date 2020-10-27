package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.SerializeSession;
import sample.object.Album;
import sample.object.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserSubsystemController {

    @FXML Button openAlbumButton;
    @FXML Button createAlbumButton;
    @FXML Button deleteAlbumButton;
    @FXML Button renameAlbumButton;
    @FXML Button logoutButton;
    @FXML Button searchPhotoButton;
    @FXML Button quitButton;
    @FXML AnchorPane rootPane;
    @FXML ListView<String> albumList;
    @FXML TextField renameAlbum;

    private ObservableList<String> obsList;


    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
        obsList = FXCollections.observableArrayList();
        SerializeSession.getInstance().getCurrentUser().getAlbums().forEach(album ->
            obsList.add(album.getAlbumName()));
        albumList.setItems(obsList);
        if(obsList.size() > 0){
            albumList.getSelectionModel().select(0);
        }

    }

    public void openAlbumEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.openAlbumButton) {

            String string = this.albumList.getSelectionModel().getSelectedItem();
            if(string.equals("")) return;

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/openAlbum.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {

            }
            OpenAlbumController controller = loader.getController();
            controller.start(primaryStage,SerializeSession.getInstance().getCurrentUser().getAlbum(string));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Album Menu");
            primaryStage.setResizable(false);
            primaryStage.show();
        }
    }

    public void createAlbumEvent(ActionEvent actionEvent) throws IOException {
        Button b = (Button) actionEvent.getSource();
        if(b == this.createAlbumButton) {
            /*AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/createAlbum.fxml"));
            rootPane.getChildren().setAll(pane);*/

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxml/createAlbum.fxml"));
            Parent root = loader.load();
            CreateAlbumController controller = loader.getController();
            controller.start(primaryStage);
            Scene scene = new Scene(root,600,500);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Create Album");
            primaryStage.setResizable(false);
            primaryStage.show();

        }

    }

    public void deleteAlbumEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.deleteAlbumButton) {
            String string = this.albumList.getSelectionModel().getSelectedItem();
            if(string.equals("")) return;

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm deletion");
            alert.setHeaderText("Are you sure you want to delete the album " + string + "?");
            alert.setContentText("Press OK to proceed, Press Cancel to cancel this action");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.CANCEL) {
                    return;
                }
            }
            obsList.remove(string);
            refreshAlbumList();

            if(obsList.size() > 0){
                albumList.getSelectionModel().select(0);
            }

            SerializeSession.getInstance().getCurrentUser().removeAlbum(string);

            try {
                SerializeSession.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void refreshAlbumList(){
        albumList.setItems(obsList);
    }

    public void renameAlbumEvent(ActionEvent actionEvent) {

        String string = this.albumList.getSelectionModel().getSelectedItem();
        Button b = (Button)actionEvent.getSource();
        if (b == this.renameAlbumButton) {

            if(string.equals("")) return;

            if(renameAlbum.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Album name field can't be blank");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm rename");
            alert.setHeaderText("Are you sure you want to rename the album " + string + " to " + renameAlbum.getText()  + "?");
            alert.setContentText("Press OK to proceed, Press Cancel to cancel this action");
            alert.showAndWait();
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()) {
                if (result.get() == ButtonType.CANCEL) {
                    return;
                }
            }
            obsList.remove(string);
            obsList.add(renameAlbum.getText());
            refreshAlbumList();

            if(obsList.size() > 0){
                albumList.getSelectionModel().select(0);
            }

            SerializeSession.getInstance().getCurrentUser().getAlbum(string).setAlbumName(renameAlbum.getText());

            try {
                SerializeSession.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void logoutEvent(ActionEvent actionEvent) throws IOException {

        try {
            SerializeSession.write();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxml/loginWindow.fxml"));
        Parent root = loader.load();
        LoginWindowController controller = loader.getController();
        controller.start(primaryStage);
        Scene scene = new Scene(root,600,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Photos");
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    public void searchPhotoEvent(ActionEvent actionEvent) throws IOException {
        Button b = (Button) actionEvent.getSource();
        if(b == this.searchPhotoButton) {
            AnchorPane pane = FXMLLoader.load(getClass().getResource("../fxml/searchPhoto.fxml"));
            rootPane.getChildren().setAll(pane);
        }
    }

    public void quitEvent(ActionEvent actionEvent) {
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
