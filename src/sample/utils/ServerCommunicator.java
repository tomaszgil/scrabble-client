package sample.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sample.State;
import sample.controllers.GameController;
import sample.models.AvailableLetters;
import sample.models.Board;
import sample.models.Player;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static sample.Main.connector;

public class ServerCommunicator  implements Runnable{
    private GameController controller;
    private boolean running;

    public ServerCommunicator() {
        this.running = true;
    }

    public void setController(GameController gameController){
        this.controller = gameController;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public Thread thread = new Thread() {

       @Override public void run() {
           while (running){
               String [] data = null;
               try {
                   char buffer [] = new char [1];
                   connector.inputStreamReader.read(buffer);
                   data = String.valueOf(buffer).split("\\_");

                   while(data.length <1 || data[0].isEmpty()){
                       if(buffer[0] == '1'){
                           connector.inputStreamReader.read(buffer);
                           if(buffer[0] == '_')
                               break;
                       }else if(buffer[0] == '2') {
                           connector.inputStreamReader.read(buffer);
                           if (buffer[0] == '_')
                               break;
                       }else if(buffer[0] == '3') {
                           connector.inputStreamReader.read(buffer);
                           if (buffer[0] == '_')
                               break;
                       }
                       connector.inputStreamReader.read(buffer);
                       data = String.valueOf(buffer).split("\\_");

                   }
                   if(data[0].charAt(0) == '1'){ //New user in room
                       System.out.println("New user in room");

                       ArrayList<Player> otherPlayers = new ArrayList<>();
                       data = null;
                       data = connector.receiveMessage(100);

                       int numberOfUsers;
                       try{
                           numberOfUsers = Integer.parseInt(data[1]);
                       }catch(NumberFormatException e){
                            numberOfUsers=0;
                       }

                       if(numberOfUsers!=0){
                           int max = Integer.parseInt(data[1]);
                           for(int i=0,j=2; i<max; i++, j=j+2){
                               System.out.println("Gracz " + data[j] + " score: " + data[j+1]);
                               otherPlayers.add(new Player(data[j],Integer.parseInt(data[j+1])));
                           }
                       }

                       sample.State.setOtherPlayers(otherPlayers);
                       controller.refreshOpponentsTable();
                       data=null;
                   }else if(data[0].charAt(0) == '2'){ //Somebody end turn, refresh board and userscore

                       data = null;
                       data = connector.receiveMessage(478);

                       String player = data[1];
                       String newScore = data[2];

                       ArrayList<Player> oppontents = sample.State.getOtherPlayers();
                       for (Player opponent : oppontents) {
                           if (opponent.getName().equals(player)) {
                               opponent.setPoints(Integer.parseInt(newScore));
                           }
                       }

                       if (data[3].charAt(0) == 't') {
                           System.out.println("SETTING MY TURN!");
                           sample.State.setMyTurn(true);
                       } else {
                           sample.State.setMyTurn(false);
                       }

                       int z = 4;
                       Character[][] boardLetters = new Character[15][15];
                       for(int i=0; i<15;i++){
                           for(int j=0; j<15; j++){
                               boardLetters[i][j]=data[z].charAt(0);
                               z++;
                           }
                       }
                        
                       Board board = new Board(boardLetters);
                       sample.State.setBoard(board);

                       Platform.runLater(new Runnable() {
                           @Override
                           public void run() {
                               controller.updateEditability();
                               controller.refreshUserLabels();
                               controller.refreshGameBoard();
                               controller.refreshOpponentsTable();
                           }
                       });
                       data=null;
                   }else if(data[0].charAt(0) == '3'){// Refresh my avaible letters
                       data = null;
                       data = connector.receiveMessage(8);

                       Character[] letters = new Character[7];

                       for(int i =0; i<7; i++){
                           letters[i]=data[1].charAt(i);
                       }

                       AvailableLetters availableLetters = new AvailableLetters(letters);
                       sample.State.setAvailableLetters(availableLetters);
                       data =null;


                       Platform.runLater(new Runnable() {
                           @Override
                           public void run() {
                               controller.refreshAvailableLetters();
                           }
                       });
                   }
               } catch (Exception e) {
                   Thread.currentThread().interrupt();
                   connector.serverCommunicator.setRunning(false);
                   System.out.println("EXCEPT");
                   e.printStackTrace();
               }
           }
       }
   };

    public void run() {
        while (running){
            String [] data = null;
            try {
                char buffer [] = new char [1];
                connector.inputStreamReader.read(buffer);
                data = String.valueOf(buffer).split("\\_");

                while(data.length <1 || data[0].isEmpty()){
                    if(buffer[0] == '1'){
                        connector.inputStreamReader.read(buffer);
                        if(buffer[0] == '_')
                            break;
                    }else if(buffer[0] == '2') {
                        connector.inputStreamReader.read(buffer);
                        if (buffer[0] == '_')
                            break;
                    }else if(buffer[0] == '3') {
                        connector.inputStreamReader.read(buffer);
                        if (buffer[0] == '_')
                            break;
                    }
                    connector.inputStreamReader.read(buffer);
                    data = String.valueOf(buffer).split("\\_");
                }
                if(data[0].charAt(0) == '1'){ //New user in room
                    System.out.println("New user in room");

                    ArrayList<Player> otherPlayers = new ArrayList<>();
                    data = null;
                    data = connector.receiveMessage(100);

                    for(int i =0; i<data.length; i++){
                        System.out.println(i + ": " + data[i]);
                    }

                    int numberOfUsers;
                    try{
                        numberOfUsers = Integer.parseInt(data[1]);
                    }catch(NumberFormatException e){
                        numberOfUsers=0;
                    }

                    if(numberOfUsers!=0){
                        int max = Integer.parseInt(data[1]);
                        for(int i=0,j=2; i<max; i++, j=j+2){
                            System.out.println("Gracz " + data[j] + " score: " + data[j+1]);
                            otherPlayers.add(new Player(data[j],Integer.parseInt(data[j+1])));
                        }
                    }

                    sample.State.setOtherPlayers(otherPlayers);
                    controller.refreshOpponentsTable();
                    data=null;
                }else if(data[0].charAt(0) == '2'){ //Somebody end turn, refresh board and userscore
                    data = null;
                    data = connector.receiveMessage(478);

                    String player = data[1];
                    String newScore = data[2];

                    ArrayList<Player> oppontents = sample.State.getOtherPlayers();
                    for (Player opponent : oppontents) {
                        if (opponent.getName().equals(player)) {
                            opponent.setPoints(Integer.parseInt(newScore));
                        }
                    }

                    if (data[3].charAt(0) == 't') {
                        System.out.println("SETTING MY TURN!");
                        sample.State.setMyTurn(true);
                    } else {
                        sample.State.setMyTurn(false);
                    }

                    int z = 4;
                    Character[][] boardLetters = new Character[15][15];
                    for(int i=0; i<15;i++){
                        for(int j=0; j<15; j++){
                            boardLetters[i][j]=data[z].charAt(0);
                            z++;
                        }
                    }

                    Board board = new Board(boardLetters);
                    sample.State.setBoard(board);

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.updateEditability();
                            controller.refreshUserLabels();
                            controller.refreshGameBoard();
                            controller.refreshOpponentsTable();
                        }
                    });
                    data=null;
                }else if(data[0].charAt(0) == '3'){// Refresh my avaible letters
                    data = null;
                    data = connector.receiveMessage(8);

                    Character[] letters = new Character[7];

                    for(int i =0; i<7; i++){
                        letters[i]=data[1].charAt(i);
                    }

                    AvailableLetters availableLetters = new AvailableLetters(letters);
                    sample.State.setAvailableLetters(availableLetters);
                    data =null;


                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            controller.refreshAvailableLetters();
                        }
                    });
                }
            } catch (Exception e) {
                Thread.currentThread().interrupt();
                connector.serverCommunicator.setRunning(false);
                System.out.println("EXCEPT");
                e.printStackTrace();
            }
        }
    }
}
