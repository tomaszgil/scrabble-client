package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Connector connector = new Connector();

        BufferedReader stdIn =  new BufferedReader(new InputStreamReader(System.in));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connector.getClientSocket().getOutputStream(), "UTF-8");

        String userInput;

        userInput = stdIn.readLine();
        outputStreamWriter.write(userInput);
        outputStreamWriter.flush();

        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Scrabble Online");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
