package sample.utils;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class SceneSwitcher {
    public SceneSwitcher() { }

    private Parent loadScene(String scene) {
        String path = "../views/" + scene + ".fxml";
        Parent newScene = null;

        try {
            newScene = FXMLLoader.load(getClass().getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newScene;
    }

    public void switchTo(String scene, ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Parent newScene = loadScene(scene);

        if (newScene != null) {
            currentStage.setScene(new Scene(newScene));
            currentStage.show();
        }
    }

    public void openInModal(String scene, String windowName, EventHandler<WindowEvent> closeHandler) {
        Stage newStage = new Stage();
        newStage.setTitle(windowName);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent secondScene = loadScene(scene);

        if (secondScene != null) {
            newStage.setScene(new Scene(secondScene));
            newStage.show();
            newStage.setOnCloseRequest(closeHandler);
        }
    }
}