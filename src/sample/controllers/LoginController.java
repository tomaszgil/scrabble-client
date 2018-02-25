package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import sample.State;
import sample.models.Player;
import sample.utils.SceneSwitcher;

import java.io.IOException;

import static sample.Main.connector;


public class LoginController {

    public TextField nameInput;
    private SceneSwitcher switcher;

    public LoginController() {
        switcher = new SceneSwitcher();
    }

    public void login(ActionEvent actionEvent) throws IOException{
        String name = nameInput.getText().concat("\0");

        connector.outputStreamWriter.write(name);
        connector.outputStreamWriter.flush();

        if (!name.isEmpty()) {
            State.setPlayer(new Player(name));
            switcher.switchTo("rooms", actionEvent);
        }
    }
}
