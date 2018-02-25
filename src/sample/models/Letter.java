package sample.models;

import sample.utils.ScrabbleLetter;

public class Letter {

    private Character character;
    private Integer points;

    public Letter(Character letter) {
        this.character = letter;
        this.points = ScrabbleLetter.getPointsForLetter(letter);
    }

    public Character getCharacter() {
        return character;
    }

    public Integer getPoints() {
        return points;
    }
    
}
