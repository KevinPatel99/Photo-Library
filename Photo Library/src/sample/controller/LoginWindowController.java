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
import sample.object.User;

import java.io.*;
import java.io.IOException;

public class LoginWindowController {

    @FXML Button loginButton;
    @FXML Button quitButton;
    @FXML TextField userNameTextField;
    @FXML AnchorPane rootPane;

    private String userName;
    private Stage primaryStage;

    public void start(Stage primaryStage){
        this.primaryStage = primaryStage;
    }



    public void loginButtonEvent(ActionEvent actionEvent) throws IOException {
        Button b = (Button) actionEvent.getSource();
        if(b == this.loginButton) {
            String user = userNameTextField.getText();


           //error window if username input is empty
            if (user.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Error Logging In");
                alert.setContentText("Username field can't be blank");
                alert.showAndWait();
                return;
            }


            //user is the admin
            if (user.compareTo("admin") == 0) {
                Parent parent;
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/adminSubsystem.fxml"));
                    parent = (Parent) loader.load();

                    Scene scene = new Scene(parent);

                    Stage app_stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    app_stage.setScene(scene);
                    app_stage.show();



                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }else {

                this.userName = user;

                if(SerializeSession.getInstance().getUser(userName) == null){
                    User user1 = new User(userName);
                    SerializeSession.getInstance().addToUsers(user1);
                    SerializeSession.getInstance().setCurrentUser(user1);
                }else{
                    SerializeSession.getInstance().setCurrentUser(SerializeSession.getInstance().getUser(userName));
                }

                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../fxml/userSubsystem.fxml"));
                AnchorPane root = loader.load();
                UserSubsystemController controller = loader.getController();
                controller.start(primaryStage);
                Scene scene = new Scene(root,600,500);
                primaryStage.setScene(scene);
                primaryStage.setTitle("User Menu");
                primaryStage.setResizable(false);
                primaryStage.show();

            }
        }
    }

    public void quitButtonEvent(ActionEvent event) throws IOException {
        Button b = (Button)event.getSource();
        if (b == this.quitButton) {
            SerializeSession.write();
            Stage stage = (Stage) quitButton.getScene().getWindow();
            stage.close();
        }
    }
}
