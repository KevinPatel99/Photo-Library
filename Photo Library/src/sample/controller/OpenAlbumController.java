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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.SerializeSession;
import sample.object.Album;
import sample.object.Photo;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

public class OpenAlbumController {

    @FXML Button addPhotoButton;
    @FXML Button quitButton;
    @FXML Button copyPhotoButton;
    @FXML Button removePhotoButton;
    @FXML Button movePhotoButton;
    @FXML Button slideshowButton;

    @FXML ListView<String> photoList;
    private ObservableList<String> obsList;

    @FXML ImageView photoDisplay;
    @FXML Label captionLabel;
    @FXML TextField changeCaptionField;
    @FXML Button setCaption;

    private Album album;
    private Stage primaryStage;

    public void start(Stage primaryStage, Album album){
        this.primaryStage = primaryStage;
        this.album = album;

        obsList = FXCollections.observableArrayList();
        album.getPhotos().forEach(photo -> obsList.add(photo.getPhotoPath()));
        refreshPhotoList();
        photoList.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> updateAlbumView());
        if(obsList.size() > 0){
            photoList.getSelectionModel().select(0);
        }
    }

    private void refreshPhotoList(){
        photoList.setItems(obsList);
        photoList.setCellFactory(param -> new ListCell<String>() {
            @Override
            public void updateItem(String name, boolean empty) {
                super.updateItem(name, empty);
                if (!empty) {
                    setText(album.getPhotoFromPath(name).getCaption());
                    ImageView image = new ImageView(new File(name).toURI().toString());
                    image.setFitWidth(120);
                    image.setFitHeight(120);
                    image.setPreserveRatio(true);
                    setGraphic(image);
                }
            }
        });
    }

    private void updateAlbumView(){
        String photoPath = photoList.getSelectionModel().getSelectedItem();
        ImageView imageView = new ImageView();
        Image image = new Image(new File(photoPath).toURI().toString());
        photoDisplay.setImage(image);
        photoDisplay.setPreserveRatio(true);
        photoDisplay.setFitHeight(230);
        photoDisplay.setFitWidth(230);
        photoDisplay.setSmooth(true);
        if(!album.getPhotoFromPath(photoPath).getCaption().equals("")){
            captionLabel.setText(album.getPhotoFromPath(photoPath).getCaption());
        }

    }

    public void addPhotoEvent(ActionEvent actionEvent) {

        Button b = (Button) actionEvent.getSource();
        if(b == this.addPhotoButton) {

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Choose an image for your album");
            fileChooser.setInitialDirectory(new File("data"));
            File file = fileChooser.showOpenDialog(null);
            if (file != null) {

                boolean isDuplicate = false;
                for (Photo p : album.getPhotos()) {
                    if (p.getPhotoPath().equals(file.getAbsolutePath()) && p.getPhotoName().equals(file.getName())) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText("Photo already exists");
                        alert.setContentText("This photo already exists in this album!");
                        alert.showAndWait();
                        isDuplicate = true;
                        break;
                    }
                }
                if(isDuplicate) return;

                Photo photo = new Photo(file.getAbsolutePath(), file.getName());
                Calendar date = Calendar.getInstance();
                date.setTime(new Date(file.lastModified()));
                photo.setDate(date);
                album.addPhoto(photo);

                obsList.add(photo.getPhotoPath());
                refreshPhotoList();
                if(obsList.size() > 0){
                    photoList.getSelectionModel().select(obsList.size() - 1);
                }
            }
        }
    }

    public void copyPhotoEvent(ActionEvent actionEvent) {

        Button b = (Button) actionEvent.getSource();
        if(b == this.copyPhotoButton) {
            int index = photoList.getSelectionModel().getSelectedIndex();
            String photoPath = photoList.getSelectionModel().getSelectedItem();
            Photo photo = album.getPhotoFromPath(photoPath);
            refreshPhotoList();


        }

    }

    public void removePhotoEvent(ActionEvent actionEvent) {

        Button b = (Button) actionEvent.getSource();
        if(b == this.removePhotoButton) {
            int index = photoList.getSelectionModel().getSelectedIndex();
            String photoPath = photoList.getSelectionModel().getSelectedItem();
            album.removePhoto(album.getPhotoFromPath(photoPath));
            refreshPhotoList();
            if(index - 1 >= 0){
                photoList.getSelectionModel().select(index-1);
            }
            try {
                SerializeSession.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void movePhotoEvent(ActionEvent actionEvent) {
    }

    public void setPhotoCaption(ActionEvent actionEvent) {
        Button b = (Button) actionEvent.getSource();
        if(b == this.setCaption) {

            if(changeCaptionField.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Caption field is blank!");
                alert.showAndWait();
                return;
            }

            int index = photoList.getSelectionModel().getSelectedIndex();
            String photoPath = photoList.getSelectionModel().getSelectedItem();
            album.getPhotoFromPath(photoPath).setCaption(changeCaptionField.getText());
            refreshPhotoList();
            photoList.getSelectionModel().select(index);
            try {
                SerializeSession.write();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void quitEvent(ActionEvent actionEvent) {
        Button b = (Button)actionEvent.getSource();
        if (b == this.quitButton) {
            try {
                SerializeSession.write();
            } catch (IOException e) {

            }
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }

    public void slideshowEvent(ActionEvent actionEvent) {
        Parent parent;

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/slideshow.fxml"));
            parent = (Parent) loader.load();

            Scene scene = new Scene(parent);

            Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            app_stage.setScene(scene);
            app_stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
