package sample.utils;

import sample.models.Letter;

public class ScrabbleScoreCounter {

    private Letter[][] letters;
    private Integer availableLettersNumber;

    private static Integer allLettersUsedBonus = 50;

    public ScrabbleScoreCounter(Letter[][] letters, Integer availableLettersNumber) {
        this.letters = letters;
        this.availableLettersNumber = availableLettersNumber;
    }

    public Integer countPoints() {
        return 0;
    }
}
