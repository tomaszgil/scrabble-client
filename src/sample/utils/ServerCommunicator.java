package sample.utils;

import javafx.fxml.FXMLLoader;
import sample.controllers.GameController;
import sample.models.Player;
import sample.State;

import java.io.IOException;
import java.util.ArrayList;

import static sample.Main.connector;

public class ServerCommunicator {
    private GameController gameController;

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }


    public Thread thread = new Thread() {

       @Override public void run(){
           while (true){
               try {
                   System.out.println("Oczekuje na dane");
                   String[] data = connector.receiveMessage(2);
                   if(data[0].charAt(0) == '1'){ //New user in room

                       ArrayList<Player> otherPlayers = new ArrayList<>();
                       data = null;
                       data = connector.receiveMessage(100);

                       char numberOfUsers = data[0].charAt(0);

                       if(numberOfUsers!='0'){
                           int max = Integer.parseInt(data[0]);
                           for(int i =1; i<max*2; i=i+2){
                               System.out.println("Gracz " + data[i] + " score: " + data[i+1]);
                               otherPlayers.add(new Player(data[i],Integer.parseInt(data[i+1])));
                           }
                       }

                       sample.State.setOtherPlayers(otherPlayers);
                   }
               }catch (Exception e){}
           }
       }
   };
}
