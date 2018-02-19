package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import sample.State;
import sample.models.Player;
import sample.utils.SceneSwitcher;


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
            State.setPlayer(new Player(name));
            switcher.switchTo("rooms", actionEvent);
        }
    }
}
