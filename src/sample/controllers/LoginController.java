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
        String name = nameInput.getText();
        if (!name.isEmpty()) {
            connector.outputStreamWriter.write(name.concat("\0"));
            connector.outputStreamWriter.flush();
            State.setPlayer(new Player(name));
            switcher.switchTo("rooms", actionEvent);
        }
    }
}
