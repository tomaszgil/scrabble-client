package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.WindowEvent;
import sample.State;
import sample.models.Board;
import sample.models.Letter;
import sample.models.Player;
import sample.utils.SceneSwitcher;
import sample.utils.ScrabbleScoreCounter;
import sample.utils.WordVerifier;
import java.io.IOException;
import java.sql.Array;
import java.sql.SQLOutput;

import java.util.ArrayList;

import static sample.Main.connector;

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
    @FXML
    public Label turnLabel;

    private ObservableList<Player> opponentsData = FXCollections.observableArrayList();
    private SceneSwitcher switcher;
    private WordVerifier verifier;
    private ScrabbleScoreCounter counter;
    private Integer availableLetterIndex;
    private Integer boardLetterRowIndex;
    private Integer boardLetterColumnIndex;
    private boolean editable;

    public GameController() {
        switcher = new SceneSwitcher();
        verifier = new WordVerifier();
        counter = new ScrabbleScoreCounter();
        availableLetterIndex = null;
        boardLetterRowIndex = null;
        boardLetterColumnIndex = null;
        editable = State.isMyTurn();
    }

    @FXML
    public void initialize() {
        connector.serverCommunicator.thread.setDaemon(true);
        connector.serverCommunicator.setController(this);
        if(!connector.serverCommunicator.isRunning()){
            connector.serverCommunicator.setRunning(true);
        }else{
            connector.serverCommunicator.thread.start();
        }

        userName.setText(State.getPlayer().getName());
        userScore.setText(State.getPlayer().getPoints().toString());
        roomLabel.setText(State.getRoom().getName());

        if (State.isMyTurn()) {
            turnLabel.setText("Your turn!");
        } else {
            turnLabel.setText("");
        }

        opponentNameColumn.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
        opponentScoreColumn.setCellValueFactory(new PropertyValueFactory<Player, Integer>("points"));
        opponentsData.setAll(State.getOtherPlayers());
        opponentsResults.setItems(opponentsData);

        setupGameBoard();
        setupAvailableLetters();
    }

    public void updateEditability() {
        if (State.isMyTurn()) {
            editable = true;
        } else {
            editable = false;
        }
    }

    public void refreshUserLabels() {
        userName.setText(State.getPlayer().getName());
        userScore.setText(State.getPlayer().getPoints().toString());

        if (State.isMyTurn()) {
            System.out.println("MY TURN");
            turnLabel.setText("Your turn!");
        } else {
            System.out.println("NOT MY TURN");
            turnLabel.setText("");
        }
    }

    public void refreshOpponentsTable() {
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

                    if (letters[i][j] != null && letters[i][j].isDraggable()) {
                        getRectangle(i, j, boardGrid).setFill(Color.valueOf("#f5f2ea"));
                    } else if (letters[i][j] != null) {
                        getRectangle(i, j, boardGrid).setFill(Color.valueOf("#f4e2b0"));
                    }
                }
                box.getChildren().addAll(letter, points);
                boardGrid.add(box, j, i);
            }
        }
    }

    public void refreshGameBoard() {
        Letter[][] letters = State.getBoard().getLetters();

        for (Integer i = 0; i < 15; i++) {
            for (Integer j = 0; j < 15; j++) {
                ObservableList<Text> boardLetterText = getTextOnLetter(i, j, boardGrid);

                if (letters[i][j] != null) {
                    Character letterChar = letters[i][j].getCharacter();
                    Integer letterPoints = letters[i][j].getPoints();
                    boardLetterText.get(0).setText(letterChar.toString());
                    boardLetterText.get(1).setText(letterPoints.toString());

                    if (letters[i][j] != null && letters[i][j].isDraggable()) {
                        getRectangle(i, j, boardGrid).setFill(Color.valueOf("#f5f2ea"));
                    } else if (letters[i][j] != null) {
                        getRectangle(i, j, boardGrid).setFill(Color.valueOf("#f4e2b0"));
                    }
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

    public void refreshAvailableLetters() {
        Letter[] letters = State.getAvailableLetters().getLetters();

        for (int i = 0; i < 7; i++) {
            ObservableList<Text> availableLetterText = getTextOnLetter(0, i, lettersGrid);

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
        if (!editable) {
            return;
        }

        switcher.openInModal("initiateSwap", "Send a swap request", null);
    }

    public void onPass(ActionEvent actionEvent) {
        if (!editable) {
            return;
        }

        // TODO send pass to the servers
        if (State.getBoard().userMadeMove()) {
            resultLabel.setText("You are currently trying to form a word. Cannot pass.");
            return;
        }

        State.setMyTurn(false);
        updateEditability();

        try {
            sendData();
            refreshUserLabels();
            resultLabel.setText("Passed.");
        } catch (IOException e) {
            e.printStackTrace();
            resultLabel.setText("Passing failed. Try again.");
        }

        // Temporary
//        EventHandler<WindowEvent> closeHandler = new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent we) {
//                refreshAvailableLetters();
//            }
//        };
//
//        switcher.openInModal("respondToSwap", "Request incoming", closeHandler);
    }

    public void onConfirm(ActionEvent actionEvent) {
        if (!editable) {
            return;
        }

        resultLabel.setText("");

        Board board = State.getBoard();
        if (!board.wordInRow() && !board.wordInColumn()) {
            resultLabel.setText("Letter tiles must be aligned in either one row or column.");
            return;
        }

        if (board.isFirstMove() && board.isMiddleSlotFree()) {
            resultLabel.setText("In first move you have to put a letter in the middle of the board.");
            return;
        } else if (!board.isFirstMove() && !board.isNewWordConnected()) {
            resultLabel.setText("Use at least one letter already placed on the board.");
            return;
        }

        ArrayList<String> words = board.getAddedWords();
        boolean allValid = verifier.verifyAll(words);

        if (!allValid) {
            ArrayList<String> invalid = verifier.getInvalid();
            resultLabel.setText("Invalid word: " + invalid.get(0));
            return;
        }

        Integer score = counter.countCurrentScore();
        System.out.println(score);

        State.getPlayer().addPoints(score);
        State.setMyTurn(false);
        updateEditability();
        board.saveBoard();
        refreshGameBoard();
        resetIndexes();
        try {
            sendData();
            refreshUserLabels();
            resultLabel.setText("Correct. Sending the board!");
        } catch (IOException e) {
            e.printStackTrace();
            resultLabel.setText("Sending failed. Try again.");
        }
    }

    private void sendData() throws IOException {
        String message="1_";

        message = message.concat(State.getPlayer().getName()).concat("_");

        int length = String.valueOf(State.getPlayer().getPoints()).length();
        if (length < 2) {
            message = message.concat("00").concat(String.valueOf(State.getPlayer().getPoints())).concat("_");
        } else if (length < 3) {
            message = message.concat("0").concat(String.valueOf(State.getPlayer().getPoints())).concat("_");
        } else {
            message = message.concat(String.valueOf(State.getPlayer().getPoints())).concat("_");
        }

        for(Character x: State.getAvailableLetters().getLetterMap()){
            message = message.concat(x.toString());
        }
        message = message.concat("_");

        Character[][] letterMap = State.getBoard().getLetterMap();

        for(int i=0; i<15;i++){
            for(int j=0; j<15; j++){
                message = message.concat(letterMap[i][j].toString()).concat("_");
            }
        }

        connector.outputStreamWriter.write(message.concat("\0"));
        connector.outputStreamWriter.flush();
    }

    public void onBoardRect(MouseEvent mouseEvent) {
        if (!editable) {
            return;
        }

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

    private Rectangle getRectangle(Integer row, Integer col, GridPane pane) {
        ObservableList<Node> children = pane.getChildren();
        Rectangle rect = null;

        if (row == 0) {
            row = null; // inverse rakfx
        }

        if (col == 0) {
            col = null; // inverse rakfx
        }

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col && node instanceof Rectangle) {
                rect = (Rectangle)node;
                break;
            }
        }

        return rect;
    }

    private void resetIndexes() {
        boardLetterColumnIndex = null;
        boardLetterRowIndex = null;
        availableLetterIndex = null;
    }

    public void onAvailableRect(MouseEvent mouseEvent) {
        if (!editable) {
            return;
        }

        Node source = (Node)mouseEvent.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);

        if (colIndex == null) {
            colIndex = 0; // rakfx
        }

        availableLetterIndex = colIndex;
    }

    public void onRoomQuit(ActionEvent actionEvent) throws IOException, InterruptedException {
        connector.serverCommunicator.setRunning(false);
        connector.serverCommunicator.thread.interrupt();

        System.out.println("QUITUJE");
        connector.outputStreamWriter.write("2");
        connector.outputStreamWriter.flush();
        switcher.switchTo("rooms", actionEvent);
    }
}
