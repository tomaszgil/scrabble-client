package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import sample.State;
import sample.models.Letter;

import java.util.Arrays;

public class RespondToSwapController {
    @FXML
    public Label wantToSwapLetter;
    @FXML
    public Label wantToGetLetter;
    @FXML
    public Label resultLabel;
    @FXML
    public Button declineButton;

    private boolean haveGetLetter;
    private boolean valid;
    private Character swap;
    private Character get;


    public RespondToSwapController() {
        haveGetLetter = false;
        valid = true;
    }

    @FXML
    public void initialize() {
        // TODO get request event
        swap = 'B';
        get = 'E';

        wantToSwapLetter.setText(swap.toString());
        wantToGetLetter.setText(get.toString());

        haveGetLetter = Arrays.asList(State.getAvailableLetters().getLetterMap()).contains(get);
        if (!haveGetLetter) {
            resultLabel.setText("You don't have requested letter.");
        }
    }

    public void onAccept(ActionEvent actionEvent) {
        if (valid && haveGetLetter) {
            // TODO send yes!

            // TODO if succeeds:
            Letter[] letters = State.getAvailableLetters().getLetters();
            for(Integer i = 0; i < 7; i++) {
                if (letters[i] != null && letters[i].getCharacter().equals(get)) {
                    letters[i] = new Letter(swap, true);
                    System.out.println("SWAPPING!");
                }
            }

            // TODO if not succeeds (uncomment)
            // valid = false;
            // resultLabel.setText("Request is no longer valid. Please close the window.");

            // Either way:
            closeStage();
        }
    }

    public void onDecline(ActionEvent actionEvent) {
        // TODO send no!
        closeStage();
    }

    private void closeStage() {
        Stage stage = (Stage) declineButton.getScene().getWindow();
        stage.fireEvent(
                new WindowEvent(
                        stage,
                        WindowEvent.WINDOW_CLOSE_REQUEST
                )
        );
    }
}
