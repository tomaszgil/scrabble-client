package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.State;
import sample.models.Letter;
import sample.models.Player;

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
        setupAvailableLetters();
    }

    private void setupGameBoard() {
        Letter[][] letters = State.getBoard().getLetters();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                VBox box = new VBox(3);
                box.setAlignment(Pos.CENTER);
                Text letter = new Text("");
                Text points = new Text("");
                letter.setFont(Font.font(22));
                points.setFont(Font.font(12));

                if (letters[i][j] != null) {
                    letter.setText(letters[i][j].getCharacter().toString());
                    points.setText(letters[i][j].getPoints().toString());
                }
                box.getChildren().addAll(letter, points);
                boardGrid.add(box, i, j);
            }
        }
    }

    private void setupAvailableLetters() {
        Letter[] letters = State.getAvailableLetters().getLetters();

        for (int i = 0; i < 7; i++) {
            VBox box = new VBox(3);
            box.setAlignment(Pos.CENTER);
            Text letter = new Text("");
            Text points = new Text("");
            letter.setFont(Font.font(22));
            points.setFont(Font.font(12));

            if (letters[i] != null) {
                letter.setText(letters[i].getCharacter().toString());
                points.setText(letters[i].getPoints().toString());
            }
            box.getChildren().addAll(letter, points);
            lettersGrid.add(box, i, 0);
        }
    }

    public void onExchange(ActionEvent actionEvent) {
    }

    public void onPass(ActionEvent actionEvent) {
    }

    public void onConfirm(ActionEvent actionEvent) {
    }
}
