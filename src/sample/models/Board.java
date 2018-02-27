package sample.models;

import sample.State;

import java.util.ArrayList;

public class Board {

    private Letter[][] letters;
    private boolean firstMove;

    public Board(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
        this.firstMove = checkFirstMove();
    }

    public void setLetterMap(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
        this.firstMove = checkFirstMove();
    }

    public Character[][] getLetterMap() {
        Character[][] letterMap = new Character[15][15];

        for (int i=0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (letters[i][j] == null) {
                    letterMap[i][j] = '0';
                } else {
                    letterMap[i][j] = letters[i][j].getCharacter();
                }
            }
        }

        return letterMap;
    }

    public Letter[][] getLetters() {
        return letters;
    }

    private void prepareBoard(Character[][] letterMap) {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (letterMap[i][j] != '0') { // not an empty character
                    letters[i][j] = new Letter(letterMap[i][j], false);
                }
            }
        }
    }

    public void setLetter(Integer row, Integer col, Letter letter) {
        letters[row][col] = letter;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    private boolean checkFirstMove() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (letters[i][j] != null) {
                    return false;
                }
            }
        }

        return true;
    }

    private ArrayList<Integer> getFirstUserLetterCoords() {
        ArrayList<Integer> coords = new ArrayList<>();
        Integer i, j = 0;
        boolean foundFirstLetter = false;

        for (i = 0; i < 15; i++) {
            for (j = 0; j < 15; j++) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    foundFirstLetter = true;
                    break;
                }
            }

            if (foundFirstLetter) {
                break;
            }
        }

        if (foundFirstLetter) {
            coords.add(i);
            coords.add(j);
        }

        return coords;
    }

    public boolean wordInRow() {
        boolean word = false;
        boolean onlyOneWord = true;
        boolean otherRowsClear = true;
        ArrayList<Integer> coords = getFirstUserLetterCoords();

        if (coords.size() == 2) {
            Integer i = coords.get(0);
            Integer j = coords.get(1) + 1;

            while (j < 15) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    word = true;
                    j++;
                } else {
                    j++;
                    break;
                }
            }

            while (j < 15) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    onlyOneWord = false;
                    break;
                }

                j++;
            }

            for (Integer a = 0; a < 15; a++) {
                if (a != i) {
                    for (Integer b = 0; b < 15; b++) {
                        if (letters[a][b] != null && letters[a][b].isDraggable()) {
                            otherRowsClear = false;
                            break;
                        }
                    }
                }

                if (!otherRowsClear) {
                    break;
                }
            }
        }

        return word && onlyOneWord && otherRowsClear;
    }

    public boolean wordInColumn() {
        boolean word = false;
        boolean onlyOneWord = true;
        boolean otherColumnsClear = true;
        ArrayList<Integer> coords = getFirstUserLetterCoords();

        if (coords.size() == 2) {
            Integer i = coords.get(0) + 1;
            Integer j = coords.get(1);

            while (i < 15) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    word = true;
                    i++;
                } else {
                    i++;
                    break;
                }
            }

            while (i < 15) {
                if (letters[i][j] != null && letters[i][j].isDraggable()) {
                    onlyOneWord = false;
                    break;
                }

                i++;
            }

            for (Integer a = 0; a < 15; a++) {
                for (Integer b = 0; b < 15; b++) {
                    if (b != j && letters[a][b] != null && letters[a][b].isDraggable()) {
                        otherColumnsClear = false;
                        break;
                    }
                }

                if (!otherColumnsClear) {
                    break;
                }
            }
        }

        return word && onlyOneWord && otherColumnsClear;
    }

    public boolean isMiddleSlotFree() {
        return letters[7][7] == null;
    }

}
