package sample.utils;

import java.util.ArrayList;

public final class ScrabbleBoard {

    private static M[][] multiplierMap;
    public enum M {
        NONE, DOUBLE_WORD, TRIPLE_WORD, DOUBLE_LETTER, TRIPLE_LETTER
    }

    static {
        multiplierMap = new M[][]{
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
                { M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.DOUBLE_LETTER, M.NONE,        M.NONE,          M.NONE,          M.TRIPLE_WORD,   M.NONE,          M.NONE,          M.NONE,        M.DOUBLE_LETTER, M.NONE,          M.NONE,          M.TRIPLE_WORD }
        };
    }

    public static M getMultiplier(Integer i, Integer j) {
        return multiplierMap[i][j];
    }

    public static boolean isTripleWord(Integer i, Integer j) {
        return multiplierMap[i][j] == M.TRIPLE_WORD;
    }

    public static boolean isDoubleWord(Integer i, Integer j) {
        return multiplierMap[i][j] == M.DOUBLE_WORD;
    }

    public static boolean isTripleLetter(Integer i, Integer j) {
        return multiplierMap[i][j] == M.TRIPLE_LETTER;
    }

    public static boolean isDoubleLetter(Integer i, Integer j) {
        return multiplierMap[i][j] == M.DOUBLE_LETTER;
    }

}
