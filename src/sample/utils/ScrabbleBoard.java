package sample.utils;

import java.util.ArrayList;

public class ScrabbleBoard {

    private M[][] multiplierMap;
    public enum M {
        NONE, DOUBLE_WORD, TRIPLE_WORD, DOUBLE_LETTER, TRIPLE_LETTER
    }

    public ScrabbleBoard() {
        this.multiplierMap = new M[][]{
                { M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.TRIPLE_WORD },
                { M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE,          M.NONE,        M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE },
                { M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE },
                { M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE,        M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER },
                { M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.DOUBLE_WORD, M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.DOUBLE_WORD, M.NONE,          M.NONE,          M.NONE,          M.NONE },
                { M.NONE,          M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,        M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE },
                { M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE },
                { M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.TRIPLE_WORD },
                { M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE },
                { M.NONE,          M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,        M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE },
                { M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.DOUBLE_WORD, M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.NONE,          M.DOUBLE_WORD, M.NONE,          M.NONE,          M.NONE,          M.NONE },
                { M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE,        M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER },
                { M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.DOUBLE_LETTER, M.NONE,          M.NONE,        M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE },
                { M.NONE,          M.DOUBLE_WORD,   M.NONE,          M.NONE,          M.NONE,        M.TRIPLE_LETTER, M.NONE,          M.NONE,          M.NONE,          M.TRIPLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.DOUBLE_WORD,   M.NONE },
                { M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.TRIPLE_WORD },
        };
    }

    public M getMultiplier(Integer i, Integer j) {
        return multiplierMap[i][j];
    }

    public boolean isTripleWord(Integer i, Integer j) {
        return multiplierMap[i][j] == M.TRIPLE_WORD;
    }

    public boolean isDoubleWord(Integer i, Integer j) {
        return multiplierMap[i][j] == M.DOUBLE_WORD;
    }

    public boolean isTripleLetter(Integer i, Integer j) {
        return multiplierMap[i][j] == M.TRIPLE_LETTER;
    }

    public boolean isDoubleLetter(Integer i, Integer j) {
        return multiplierMap[i][j] == M.DOUBLE_LETTER;
    }

}
