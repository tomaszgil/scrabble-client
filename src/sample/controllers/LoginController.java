package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    public TextField nameInput;

    public void login(ActionEvent actionEvent) {
        String name = nameInput.getText();

        if (!name.isEmpty()) {
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent newScene = null;

            try {
                newScene = FXMLLoader.load(getClass().getResource("../views/rooms.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }

            currentStage.setScene(new Scene(newScene));
            currentStage.show();
        }
    }
}
