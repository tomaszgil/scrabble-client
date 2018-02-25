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
import java.util.ArrayList;
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
        char buf[] = new char[50];
        connector.inputStreamReader.read(buf);
        String message = String.valueOf(buf);
        String [] data = message.split("\\_");

        ArrayList<Room> rooms = new ArrayList<>();
        for(int i=0; i<=10; i=i+2){
            rooms.add(new Room(data[i], Integer.parseInt(data[i+1])));
        }

        State.setRoomList(rooms);
        System.out.println(data[1]);
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
