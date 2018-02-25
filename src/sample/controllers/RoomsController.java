package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.State;
import sample.models.Room;
import sample.utils.SceneSwitcher;

import java.io.IOException;
import java.util.Scanner;

import static sample.Main.connector;


public class RoomsController {

    public Button playButton;
    public TableView<Room> roomList;
    public TableColumn<Room, String> nameColumn;
    public TableColumn<Room, Integer> freeSlotsColumn;
    private ObservableList<Room> roomsData = FXCollections.observableArrayList();
    private SceneSwitcher switcher;

    public RoomsController() {
        switcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() throws IOException{
        getRooms();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
        freeSlotsColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("freeSlots"));
        roomsData.setAll(State.getRoomList());
        roomList.setItems(roomsData);
    }

    private void getRooms() throws IOException{
        char buffor[] = new char[500];
        connector.inputStreamReader.read(buffor);
        System.out.println(buffor);
    }

    public void play(ActionEvent actionEvent) {
        Room selectedRoom = roomList.getSelectionModel().getSelectedItem();

        // TODO try to get into room
        if (selectedRoom != null && selectedRoom.occupySlot()) {
            State.setRoom(selectedRoom);
            switcher.switchTo("game", actionEvent);
        }
    }
}
