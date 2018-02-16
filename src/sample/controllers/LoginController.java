package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.utils.SceneSwitcher;

import java.io.IOException;

public class LoginController {

    public TextField nameInput;
    private SceneSwitcher switcher;

    public LoginController() {
        switcher = new SceneSwitcher();
    }

    public void login(ActionEvent actionEvent) {
        String name = nameInput.getText();

        // TODO send name to server

        if (!name.isEmpty()) {
            switcher.switchTo("rooms", actionEvent);
        }
    }
}
