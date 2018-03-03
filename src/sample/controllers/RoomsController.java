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
import static sample.Main.step;

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
        if(step==0){
            String [] data = connector.receiveMessage(50);
            ArrayList<Room> rooms = new ArrayList<>();
            for(int i=0; i<=10; i=i+2){
                rooms.add(new Room(data[i], Integer.parseInt(data[i+1])));
            }

            State.setRoomList(rooms);
            step++;
        }else{
            String [] data = connector.receiveMessage(1);
            while(data == null || data.length < 1|| data[0].length() < 1 || !data[0].equals("p")){
                data = connector.receiveMessage(1);
            }

            String [] message = connector.receiveMessage(48);

            message[0] = "Alp" + message[0];
            ArrayList<Room> rooms = new ArrayList<>();
            for(int i=0; i<=10; i=i+2){
                rooms.add(new Room( message[i], Integer.parseInt(message[i+1])));
            }
            State.setRoomList(rooms);
        }
    }

    public void play(ActionEvent actionEvent) throws IOException {
        Room selectedRoom = roomList.getSelectionModel().getSelectedItem();

        if (selectedRoom != null && selectedRoom.getFreeSlots() > 0) { // TODO verify on server
            connector.outputStreamWriter.write(selectedRoom.getName().concat("\0"));
            connector.outputStreamWriter.flush();
            State.setRoom(selectedRoom);

            String [] message = connector.receiveMessage(1);
//            while(message == null || message.length < 1|| message[0].length() < 1){
            while(true){
                if(message.length == 1 && message[0].length() ==1) {
                    if (message[0].charAt(0) == 't' || message[0].charAt(0) == 'f')
                        break;
                }
                else{
                    message = connector.receiveMessage(1);
                }
            }

            String [] data = connector.receiveMessage(479);

            if(message[0].charAt(0) == 't'){
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
            data = connector.receiveMessage(104);

            char numberOfUsers = data[0].charAt(0);

            if(numberOfUsers!='0'){
                int max = Integer.parseInt(data[0]);
                for(int i =1; i<max*2; i=i+2){
                    System.out.println(data[i] + " " + data[i+1]);
                    otherPlayers.add(new Player(data[i],Integer.parseInt(data[i+1])));
                }
            }
            State.setOtherPlayers(otherPlayers);

            switcher.switchTo("game", actionEvent);
        }
    }
}