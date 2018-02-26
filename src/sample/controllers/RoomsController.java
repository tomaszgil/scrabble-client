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
import sample.models.*;
import sample.utils.SceneSwitcher;

import java.lang.reflect.Array;
import java.util.ArrayList;


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
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
        freeSlotsColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("freeSlots"));
        roomsData.setAll(State.getRoomList());
        roomList.setItems(roomsData);
    }

    public void play(ActionEvent actionEvent) {
        Room selectedRoom = roomList.getSelectionModel().getSelectedItem();

        // TODO try to get into room
        if (selectedRoom != null) {
            State.setRoom(selectedRoom);

            // TODO get game board
            Character[][] boardLetters = new Character[][] {
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', 'A', 'L', 'F', 'A', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' },
                    { '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0', '0' }
            };
            Board board = new Board(boardLetters);
            State.setBoard(board);

            // TODO get user letters
            Character[] letters = new Character[] {
                    'A', 'D', 'A', 'M', '0', '0', '0'
            };
            AvailableLetters availableLetters = new AvailableLetters(letters);
            State.setAvailableLetters(availableLetters);

            // TODO get other clients
            ArrayList<Player> otherPlayers = new ArrayList<>();
            otherPlayers.add(new Player("mietek", 4));
            otherPlayers.add(new Player("zdzisiek", 20));
            otherPlayers.add(new Player("wacek", 17));
            State.setOtherPlayers(otherPlayers);

            switcher.switchTo("game", actionEvent);
        }
    }
}
