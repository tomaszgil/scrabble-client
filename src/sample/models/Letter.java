package sample.models;

import sample.utils.ScrabbleLetter;

public class Letter {

    private Character character;
    private Integer points;
    private boolean draggable;

    public Letter(Character letter, boolean draggable) {
        this.character = letter;
        this.points = ScrabbleLetter.getPointsForLetter(letter);
        this.draggable = draggable;
    }

    public Character getCharacter() {
        return character;
    }

    public Integer getPoints() {
        return points;
    }

    public boolean isDraggable() {
        return draggable;
    }
    
}
