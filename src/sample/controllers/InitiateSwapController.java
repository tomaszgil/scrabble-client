package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import sample.State;
import sample.models.Letter;
import sample.utils.SceneSwitcher;
import sample.utils.ScrabbleLetter;

public class InitiateSwapController {
    @FXML
    public ComboBox<Letter> myLetterCombo;
    @FXML
    public ComboBox<Character> wantedLetterCombo;

    private SceneSwitcher switcher;
    private ObservableList<Character> allLettersList = FXCollections.observableArrayList();
    private ObservableList<Letter> availableLettersList = FXCollections.observableArrayList();

    public InitiateSwapController() {
        switcher = new SceneSwitcher();
    }

    @FXML
    public void initialize() {
        allLettersList.setAll(ScrabbleLetter.getLetters());
        wantedLetterCombo.setItems(allLettersList);
        availableLettersList.setAll(State.getAvailableLetters().getLetters());
        myLetterCombo.setItems(availableLettersList);
    }

    public void onSwap(ActionEvent actionEvent) {
        Letter myLetter = myLetterCombo.getSelectionModel().getSelectedItem();
        Character wantedLetter = wantedLetterCombo.getSelectionModel().getSelectedItem();

        if (myLetter != null && wantedLetter != null) {
            String swap = myLetter.getCharacter().toString();
            String get = wantedLetter.toString();

            // TODO send swap request with swap and get
            System.out.println("Swap request");
            System.out.println(swap);
            System.out.println(get);

            if (swap.equals(get)) {
                return;
            }

            switcher.switchTo("waitForSwap", actionEvent);

            // TODO if it succeeds swap these two letters in State
        }
    }
}
