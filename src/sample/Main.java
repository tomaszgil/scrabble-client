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
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Connector connector = new Connector();

        BufferedReader stdIn =  new BufferedReader(new InputStreamReader(System.in));
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(connector.getClientSocket().getOutputStream(), "UTF-8");

        String userInput;

        userInput = stdIn.readLine();
        outputStreamWriter.write(userInput);
        outputStreamWriter.flush();

        /*primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
    }


    public static void main(String[] args) {
        launch(args);
    }
}
