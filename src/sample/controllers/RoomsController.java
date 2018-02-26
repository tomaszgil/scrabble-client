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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Scanner;
import static sample.Main.connector;
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
    }

    public void play(ActionEvent actionEvent) throws IOException {
        Room selectedRoom = roomList.getSelectionModel().getSelectedItem();

        if (selectedRoom != null && selectedRoom.getFreeSlots() > 0) {
            connector.outputStreamWriter.write(selectedRoom.getName().concat("\0"));
            connector.outputStreamWriter.flush();
            State.setRoom(selectedRoom);

            char bufor[] = new char[450];
            connector.inputStreamReader.read(bufor);

            String message = String.valueOf(bufor);
            System.out.println(message);
            String [] data = message.split("\\_");
            int z =0;
            Character[][] boardLetters = new Character[15][15];
            for(int i =0; i<15;i++){
                for(int j=0; j<15; j++){
                    boardLetters[i][j] = data[z].charAt(0);
                    z++;
                }
            }

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
