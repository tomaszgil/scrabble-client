package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.models.Room;
import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Main extends Application {
    public static Connector connector;
    public static int step=0;
    @Override
    public void start(Stage primaryStage) throws Exception{
        connector = new Connector();

        Parent root = FXMLLoader.load(getClass().getResource("views/login.fxml"));
        primaryStage.setTitle("Scrabble Online");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event){
                try{
                    connector.outputStreamWriter.write("x");
                    connector.outputStreamWriter.flush();
                    System.out.println("Closing socket");
                    connector.getClientSocket().shutdownInput();
                    connector.getClientSocket().shutdownOutput();
                    connector.getClientSocket().close();
                }catch (Exception e){}
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
