package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;

public class RoomsController {

    public Button playButton;
    public TableView roomList;


    public void play(ActionEvent actionEvent) {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Parent newScene = null;

        try {
            newScene = FXMLLoader.load(getClass().getResource("../views/game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        currentStage.setScene(new Scene(newScene));
        currentStage.show();
    }
}
