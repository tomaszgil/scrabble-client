package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import sample.models.Room;
import sample.utils.SceneSwitcher;


public class RoomsController {

    public Button playButton;
    public TableView<Room> roomList;
    public TableColumn<Room, String> nameColumn;
    public TableColumn<Room, Integer> freeSlotsColumn;
    private SceneSwitcher switcher;

    public RoomsController() {
        switcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {

    }

    public void play(ActionEvent actionEvent) {
        // TODO try to get info a room

        switcher.switchTo("game", actionEvent);
    }
}
