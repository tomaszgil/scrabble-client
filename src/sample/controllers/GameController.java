package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import sample.State;
import sample.models.Board;
import sample.models.Letter;
import sample.models.Player;
import sample.utils.SceneSwitcher;
import sample.utils.WordVerifier;

import java.sql.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

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
    public Label resultLabel;
    @FXML
    public Label roomLabel;

    private ObservableList<Player> opponentsData = FXCollections.observableArrayList();
    private SceneSwitcher switcher;
    private WordVerifier verifier;
    private Integer availableLetterIndex;
    private Integer boardLetterRowIndex;
    private Integer boardLetterColumnIndex;

    public GameController() {
        switcher = new SceneSwitcher();
        verifier = new WordVerifier();
        availableLetterIndex = null;
        boardLetterRowIndex = null;
        boardLetterColumnIndex = null;
    }

    @FXML
    public void initialize() {
        userName.setText(State.getPlayer().getName());
        userScore.setText(State.getPlayer().getPoints().toString());
        roomLabel.setText(State.getRoom().getName());

        opponentNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        opponentScoreColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("points"));
        opponentsData.setAll(State.getOtherPlayers());
        opponentsResults.setItems(opponentsData);

        setupGameBoard();
        setupAvailableLetters();
    }

    private void refreshUserLabels() {
        userName.setText(State.getPlayer().getName());
        userScore.setText(State.getPlayer().getPoints().toString());
    }

    private void refreshOpponentsTable() {
        opponentsData.setAll(State.getOtherPlayers());
        opponentsResults.setItems(opponentsData);
    }

    private void setupGameBoard() {
        Letter[][] letters = State.getBoard().getLetters();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                VBox box = new VBox(3);
                box.setPickOnBounds(false);
                box.setAlignment(Pos.CENTER);
                Text letter = new Text("");
                Text points = new Text("");
                letter.setFont(Font.font(22));
                letter.setPickOnBounds(false);
                points.setFont(Font.font(12));
                points.setPickOnBounds(false);

                if (letters[i][j] != null) {
                    letter.setText(letters[i][j].getCharacter().toString());
                    points.setText(letters[i][j].getPoints().toString());
                }
                box.getChildren().addAll(letter, points);
                boardGrid.add(box, j, i);
            }
        }
    }

    private void refreshGameBoard() {
        Letter[][] letters = State.getBoard().getLetters();

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                ObservableList<Text> boardLetterText = getTextOnLetter(boardLetterRowIndex, boardLetterColumnIndex, boardGrid);

                if (letters[i][j] != null) {
                    Character letterChar = letters[i][j].getCharacter();
                    Integer letterPoints = letters[i][j].getPoints();
                    boardLetterText.get(0).setText(letterChar.toString());
                    boardLetterText.get(1).setText(letterPoints.toString());
                } else {
                    boardLetterText.get(0).setText("");
                    boardLetterText.get(1).setText("");
                }
            }
        }
    }

    private void setupAvailableLetters() {
        Letter[] letters = State.getAvailableLetters().getLetters();

        for (int i = 0; i < 7; i++) {
            VBox box = new VBox(3);
            box.setPickOnBounds(false);
            box.setAlignment(Pos.CENTER);
            Text letter = new Text("");
            Text points = new Text("");
            letter.setFont(Font.font(22));
            letter.setPickOnBounds(false);
            points.setFont(Font.font(12));
            points.setPickOnBounds(false);

            if (letters[i] != null) {
                letter.setText(letters[i].getCharacter().toString());
                points.setText(letters[i].getPoints().toString());
            }
            box.getChildren().addAll(letter, points);
            lettersGrid.add(box, i, 0);
        }
    }

    private void refreshAvailableLetters() {
        Letter[] letters = State.getAvailableLetters().getLetters();

        for (int i = 0; i < 7; i++) {
            ObservableList<Text> availableLetterText = getTextOnLetter(0, availableLetterIndex, lettersGrid);

            if (letters[i] != null) {
                Character letterChar = letters[i].getCharacter();
                Integer letterPoints = letters[i].getPoints();
                availableLetterText.get(0).setText(letterChar.toString());
                availableLetterText.get(1).setText(letterPoints.toString());
            } else {
                availableLetterText.get(0).setText("");
                availableLetterText.get(1).setText("");
            }
        }
    }

    public void onExchange(ActionEvent actionEvent) {
    }

    public void onPass(ActionEvent actionEvent) {
        // TODO send pass to the servers
    }

    public void onConfirm(ActionEvent actionEvent) {
        resultLabel.setText("");

        Board board = State.getBoard();
        if (!board.wordInRow() && !board.wordInColumn()) {
            resultLabel.setText("Letter tiles must be aligned in either one row or column.");
            return;
        }

        if (board.isFirstMove() && board.isMiddleSlotFree()) {
            resultLabel.setText("In first move you have to put a letter in the middle of the board.");
            return;
        }

        ArrayList<String> words = board.getAddedWords();
        boolean allValid = verifier.verifyAll(words);

        if (!allValid) {
            ArrayList<String> invalid = verifier.getInvalid();
            resultLabel.setText("Invalid word: " + invalid.get(0));
            return;
        }

        resultLabel.setText("Correct. Sending the board!");
    }

    public void onBoardRect(MouseEvent mouseEvent) {
        Node source = (Node)mouseEvent.getSource() ;
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);

        if (colIndex == null) {
            colIndex = 0; // rakfx
        }

        if (rowIndex == null) {
            rowIndex = 0; // rakfx
        }

        boardLetterColumnIndex = colIndex;
        boardLetterRowIndex = rowIndex;

        if (availableLetterIndex != null) {
            transferLetterOntoBoard();
        } else {
            withdrawLetterFromBoard();
        }

    }

    private void transferLetterOntoBoard() {
        boolean emptySlot = State.getBoard().getLetters()[boardLetterRowIndex][boardLetterColumnIndex] == null;
        Letter letter = State.getAvailableLetters().getLetters()[availableLetterIndex];

        if (emptySlot && letter != null) {
            Character letterChar = letter.getCharacter();
            Integer letterPoints = letter.getPoints();
            State.getBoard().setLetter(boardLetterRowIndex, boardLetterColumnIndex, letter);
            State.getAvailableLetters().setLetter(availableLetterIndex, null);

            ObservableList<Text> boardLetterText = getTextOnLetter(boardLetterRowIndex, boardLetterColumnIndex, boardGrid);
            ObservableList<Text> availableLetterText = getTextOnLetter(0, availableLetterIndex, lettersGrid);
            boardLetterText.get(0).setText(letterChar.toString());
            boardLetterText.get(1).setText(letterPoints.toString());
            availableLetterText.get(0).setText("");
            availableLetterText.get(1).setText("");

            resetIndexes();
        }
    }

    private void withdrawLetterFromBoard() {
        Letter letter = State.getBoard().getLetters()[boardLetterRowIndex][boardLetterColumnIndex];

        if (letter != null && letter.isDraggable()) {
            Character letterChar = letter.getCharacter();
            Integer letterPoints = letter.getPoints();
            Integer slotNumber = State.getAvailableLetters().setLetterFirstFree(letter);
            State.getBoard().setLetter(boardLetterRowIndex, boardLetterColumnIndex, null);

            ObservableList<Text> boardLetterText = getTextOnLetter(boardLetterRowIndex, boardLetterColumnIndex, boardGrid);
            ObservableList<Text> availableLetterText = getTextOnLetter(0, slotNumber, lettersGrid);
            boardLetterText.get(0).setText("");
            boardLetterText.get(1).setText("");
            availableLetterText.get(0).setText(letterChar.toString());
            availableLetterText.get(1).setText(letterPoints.toString());

            resetIndexes();
        }
    }

    private ObservableList<Text> getTextOnLetter(Integer row, Integer col, GridPane pane) {
        ObservableList<Node> children = pane.getChildren();
        VBox box = null;

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof VBox) {
                box = (VBox)node;
                break;
            }
        }

        ObservableList<Node> textNodes = box.getChildren();
        ObservableList<Text> texts = FXCollections.observableArrayList();

        for (Node node : textNodes) {
            if (node instanceof Text) {
                texts.add((Text) node);
            }
        }

        return texts;
    }

    private void resetIndexes() {
        boardLetterColumnIndex = null;
        boardLetterRowIndex = null;
        availableLetterIndex = null;
    }

    public void onAvailableRect(MouseEvent mouseEvent) {
        Node source = (Node)mouseEvent.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);

        if (colIndex == null) {
            colIndex = 0; // rakfx
        }

        availableLetterIndex = colIndex;
    }
}
