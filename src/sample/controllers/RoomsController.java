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
import sample.utils.ServerCommunicator;

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
        System.out.println("INITIALIZER");
        getRooms();
        nameColumn.setCellValueFactory(new PropertyValueFactory<Room, String>("name"));
        freeSlotsColumn.setCellValueFactory(new PropertyValueFactory<Room, Integer>("freeSlots"));
        roomsData.setAll(State.getRoomList());
        roomList.setItems(roomsData);
    }

    private void getRooms() throws IOException{
        System.out.println("ROOOOOOOOOOOOMS");
        String [] data = connector.receiveMessage(50);
        System.out.println("ROOOOOOOOOOOOOOOOMS");
        ArrayList<Room> rooms = new ArrayList<>();
        for(int i=0; i<=10; i=i+2){
            rooms.add(new Room(data[i], Integer.parseInt(data[i+1])));
        }

        State.setRoomList(rooms);
    }

    public void play(ActionEvent actionEvent) throws IOException {
        Room selectedRoom = roomList.getSelectionModel().getSelectedItem();

        if (selectedRoom != null && selectedRoom.getFreeSlots() > 0) { // TODO verify on server
            connector.outputStreamWriter.write(selectedRoom.getName().concat("\0"));
            connector.outputStreamWriter.flush();
            State.setRoom(selectedRoom);

            String [] data = connector.receiveMessage(452);

            if(data[0].charAt(0) == 't'){
                State.setMyTurn(true);
            }else{
                State.setMyTurn(false);
            }

            int z =1;
            Character[][] boardLetters = new Character[15][15];
            for(int i =0; i<15;i++){
                for(int j=0; j<15; j++){
                    boardLetters[i][j] = data[z].charAt(0);
                    z++;
                }
            }

            Board board = new Board(boardLetters);
            State.setBoard(board);
            
            data = connector.receiveMessage(14);
            Character[] letters = new Character[7];

            for(int i =0; i<7; i++){
                letters[i]=data[i].charAt(0);
            }

            AvailableLetters availableLetters = new AvailableLetters(letters);
            State.setAvailableLetters(availableLetters);

            ArrayList<Player> otherPlayers = new ArrayList<>();
            data = null;
            data = connector.receiveMessage(102);
            System.out.println("TO SOM Z ROOM CONTROLLERA");

            char numberOfUsers = data[0].charAt(0);

            if(numberOfUsers!='0'){
                int max = Integer.parseInt(data[0]);
                for(int i =1; i<max*2; i=i+2){
                    otherPlayers.add(new Player(data[i],Integer.parseInt(data[i+1])));
                }
            }
            State.setOtherPlayers(otherPlayers);

            switcher.switchTo("game", actionEvent);
        }
    }
}