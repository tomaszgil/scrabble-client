package sample.utils;

import sample.State;
import sample.models.Letter;

import java.util.ArrayList;

public class ScrabbleScoreCounter {

    private Letter[][] letters;
    private Integer rowIdx;
    private Integer colIdx;
    private static Integer allLettersUsedBonus = 50;

    public Integer countCurrentScore() {
        ArrayList<Integer> firstUserLetterCoords = State.getBoard().getFirstUserLetterCoords();
        letters = State.getBoard().getLetters();
        Integer score = 0;

        if (firstUserLetterCoords.size() != 2) {
            return score;
        }

        rowIdx = firstUserLetterCoords.get(0);
        colIdx = firstUserLetterCoords.get(1);

        if (State.getBoard().wordInColumn()) {
            System.out.println("CHECKING COLUMN!");
            score += calculateMainColumn();
            score += calculateAdditionalRows();
        } else if (State.getBoard().wordInRow()) {
            System.out.println("CHECKING ROW!");
            score += calculateMainRow();
            score += calculateAdditionalColumns();
        }

        if (countUsedLetters() == 7) {
            score += allLettersUsedBonus;
        }

        return score;
    }

    private Integer calculateMainColumn() {
        Integer score = 0;
        Integer multiplier = 1;

        Integer i = rowIdx - 1;
        Integer j = colIdx;
        while (i >= 0 && letters[i][j] != null) {
            score += letters[i][j].getPoints();
            i--;
        }

        i = rowIdx;
        j = colIdx;
        while (i < 15 && letters[i][j] != null) {
            score += calculateLetterScore(i, j);
            multiplier *= calculateMultiplier(i, j);
            i++;
        }

        return score * multiplier;
    }

    private Integer calculateMainRow() {
        Integer score = 0;
        Integer multiplier = 1;

        Integer i = rowIdx;
        Integer j = colIdx - 1;
        while (j >= 0 && letters[i][j] != null) {
            score += letters[i][j].getPoints();
            j--;
        }

        i = rowIdx;
        j = colIdx;
        while (j < 15 && letters[i][j] != null) {
            score += calculateLetterScore(i, j);
            multiplier *= calculateMultiplier(i, j);
            j++;
        }

        return score * multiplier;
    }

    private Integer calculateAdditionalRows() {
        Integer score = 0;
        Integer i = rowIdx;
        Integer j = colIdx;

        while (i < 15 && letters[i][j] != null && letters[i][j].isDraggable()) {
            Integer rowScore = 0;
            Integer rowMultiplier = 1;
            boolean word = false;

            j--;
            while (j >= 0 && letters[i][j] != null) {
                rowScore += letters[i][j].getPoints();
                word = true;
                j--;
            }

            j = colIdx + 1;
            while (j < 15 && letters[i][j] != null) {
                rowScore += letters[i][j].getPoints();
                word = true;
                j++;
            }

            if (word) {
                rowScore += calculateLetterScore(i, j);
                rowMultiplier = calculateMultiplier(i, j);
            }

            score += rowScore * rowMultiplier;
            i++;
        }

        return score;
    }

    private Integer calculateAdditionalColumns() {
        Integer score = 0;
        Integer i = rowIdx;
        Integer j = colIdx;

        while (j < 15 && letters[i][j] != null && letters[i][j].isDraggable()) {
            Integer colScore = 0;
            Integer colMultiplier = 1;
            boolean word = false;

            i--;
            while (i >= 0 && letters[i][j] != null) {
                colScore += letters[i][j].getPoints();
                word = true;
                i--;
            }

            i = rowIdx + 1;
            while (i < 15 && letters[i][j] != null) {
                colScore += letters[i][j].getPoints();
                word = true;
                i++;
            }

            if (word) {
                colScore += calculateLetterScore(i, j);
                colMultiplier = calculateMultiplier(i, j);
            }

            score += colScore * colMultiplier;
            j++;
        }

        return score;
    }

    private Integer calculateMultiplier(Integer i, Integer j) {
        Integer multiplier = 1;

        if (letters[i][j] != null && letters[i][j].isDraggable()) {
            if (ScrabbleBoard.isDoubleWord(i, j)) {
                multiplier *= 2;
            } else if (ScrabbleBoard.isTripleWord(i, j)) {
                multiplier *= 3;
            }
        }

        return multiplier;
    }

    private Integer calculateLetterScore(Integer i, Integer j) {
        Integer score = 0;

        if (letters[i][j] != null && letters[i][j].isDraggable()) {
            if (ScrabbleBoard.isDoubleLetter(i, j)) {
                score += 2 * letters[i][j].getPoints();
            } else if (ScrabbleBoard.isTripleLetter(i, j)) {
                score += 3 * letters[i][j].getPoints();
            } else {
                score += letters[i][j].getPoints();
            }
        } else {
            score += letters[i][j].getPoints();
        }

        return score;
    }

    private Integer countUsedLetters() {
        Integer count = 0;
        for (Integer i = 0; i < 15; i++) {
            for (Integer j = 0; j < 15; j++) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    count++;
                }
            }
        }

        return count;
    }
}
