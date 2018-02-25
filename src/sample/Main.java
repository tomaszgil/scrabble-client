package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.models.Room;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Main extends Application {
    public static Connector connector;

    @Override
    public void start(Stage primaryStage) throws Exception{
        connector = new Connector();

        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Scrabble Online");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

        // TODO get rooms
        ArrayList<Room> rooms = new ArrayList<>();
        rooms.add(new Room(1, "Alfa"));
        rooms.add(new Room(2, "Beta"));
        rooms.add(new Room(3, "Gamma"));
        rooms.add(new Room(4, "Delta"));
        State.setRoomList(rooms);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
