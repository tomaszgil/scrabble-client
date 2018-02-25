package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import sample.State;
import sample.models.Player;
import sample.utils.WordVerifier;

public class GameController {
    @FXML
    public Label userScore;
    @FXML
    public Label userName;
    @FXML
    public TableView<Player> opponentsResults;
    @FXML
    public TableColumn<Player, String> opponentNameColumn;
    @FXML
    public TableColumn<Player, Integer> opponentScoreColumn;
    @FXML
    public AnchorPane boardPane;
    @FXML
    public AnchorPane lettersPane;
    @FXML
    public Button exchangeButton;
    @FXML
    public Button passButton;
    @FXML
    public Button confirmButton;
    @FXML
    public GridPane boardGrid;
    @FXML
    public GridPane lettersGrid;

    @FXML
    public void initialize() {
        userName.setText(State.getPlayer().getName());
        userScore.setText(State.getPlayer().getPoints().toString());
        setupGameBoard();
    }

    private void setupGameBoard() {
        WordVerifier wv = new WordVerifier();
        System.out.println(wv.verify("Correct"));
        System.out.println(wv.verify("Cwrong"));
    }

    public void onExchange(ActionEvent actionEvent) {
    }

    public void onPass(ActionEvent actionEvent) {
    }

    public void onConfirm(ActionEvent actionEvent) {
    }
}
