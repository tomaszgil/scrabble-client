package sample.utils;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import sample.State;
import sample.controllers.GameController;
import sample.models.AvailableLetters;
import sample.models.Board;
import sample.models.Player;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static sample.Main.connector;

public class ServerCommunicator {
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
                   System.out.println("Oczekuje na dane");
                   data = connector.receiveMessage(2);

                   if(data!=null && data.length > 0 && !data[0].isEmpty())
                   if(data != null && data[0].charAt(0) == '1'){ //New user in room
                       ArrayList<Player> otherPlayers = new ArrayList<>();
                       data = null;
                       data = connector.receiveMessage(102);

                       int numberOfUsers;
                       try{
                           numberOfUsers = Integer.parseInt(data[0]);
                       }catch(NumberFormatException e){
                            numberOfUsers=0;
                       }
                       //char numberOfUsers = data[0].charAt(0);
                       System.out.println(numberOfUsers);

                       if(numberOfUsers!=0){
                           int max = Integer.parseInt(data[0]);
                           for(int i =1; i<max*2; i=i+2){
                               System.out.println("Gracz " + data[i] + " score: " + data[i+1]);
                               otherPlayers.add(new Player(data[i],Integer.parseInt(data[i+1])));
                           }
                       }

                       sample.State.setOtherPlayers(otherPlayers);
                       controller.refreshOpponentsTable();
                       data=null;
                   }else if(data != null && data[0].charAt(0) == '2'){ //Somebody end turn, refresh board and userscore

                       data = null;
                       data = connector.receiveMessage(478);

                       String player = data[0];
                       String newScore = data[1];

                       ArrayList<Player> oppontents = sample.State.getOtherPlayers();
                       for (Player opponent : oppontents) {
                           if (opponent.getName().equals(player)) {
                               opponent.setPoints(Integer.parseInt(newScore));
                           }
                       }

                       if (data[2].charAt(0) == 't') {
                           System.out.println("SETTING MY TURN!");
                           sample.State.setMyTurn(true);
                       } else {
                           sample.State.setMyTurn(false);
                       }

                       int z = 3;
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
                   }else if(data != null && data[0].charAt(0) == '3'){// Refresh my avaible letters
                       data = null;
                       data = connector.receiveMessage(14);


                       Character[] letters = new Character[7];

                       for(int i =0; i<7; i++){
                           letters[i]=data[0].charAt(i);
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
                   System.out.println("EXCEPT");
                   e.printStackTrace();
                   Thread.currentThread().interrupt();
               }
           }
       }
   };
}
