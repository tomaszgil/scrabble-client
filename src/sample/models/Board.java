package sample.models;

import sample.State;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    private Letter[][] letters;
    private boolean firstMove;
    private ArrayList<String> initialWords;

    public Board(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
        this.firstMove = checkFirstMove();
        this.initialWords = getAllWords();
    }

    public void setLetterMap(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
        this.firstMove = checkFirstMove();
        this.initialWords = getAllWords();
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

    public void saveBoard() {
        Character[][] map = getLetterMap();
        setLetterMap(map);
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

    public ArrayList<Integer> getFirstUserLetterCoords() {
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
            Integer k = coords.get(1) - 1;

            if (k >= 0 && letters[i][k] != null) {
                word = true;
            }

            while (j < 15) {
                if (letters[i][j] != null) {
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
            Integer k = coords.get(0) - 1;

            if (k >= 0 && letters[k][j] != null) {
                word = true;
            }

            while (i < 15) {
                if (letters[i][j] != null) {
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

    public ArrayList<String> getAllWords() {
        ArrayList<String> words = new ArrayList<String>();
        Character[][] map = getLetterMap();

        for (int i = 0; i < 15; i++) {
            String s = "";
            for (int j = 0; j < 15; j++) {
                s += map[i][j];
            }

            ArrayList<String> rowWords = new ArrayList<>(Arrays.asList(s.split("0")));
            rowWords.removeIf(word -> word.length() < 2);
            words.addAll(rowWords);
        }

        for (int j = 0; j < 15; j++) {
            String s = "";
            for (int i = 0; i < 15; i++) {
                s += map[i][j];
            }

            ArrayList<String> columnWords = new ArrayList<>(Arrays.asList(s.split("0")));
            columnWords.removeIf(word -> word.length() < 2);
            words.addAll(columnWords);
        }

        return words;
    }

    public ArrayList<String> getAddedWords() {
        ArrayList<String> all = getAllWords();
        ArrayList<String> added = new ArrayList<>(all);
        added.removeAll(initialWords);

        System.out.println(added);
        return added;
    }

}
