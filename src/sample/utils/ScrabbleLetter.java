package sample.utils;

import java.util.HashMap;
import java.util.Map;

public class ScrabbleLetter {

    private String[] letters;
    private Map<String, Integer> lettersPoints;
    
    public ScrabbleLetter() {
        this.letters = new String[]{
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
                "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z", " "
        };

        this.lettersPoints = new HashMap<String, Integer>();
        this.lettersPoints.put(" ", 0);

        this.lettersPoints.put("A", 1);
        this.lettersPoints.put("E", 1);
        this.lettersPoints.put("I", 1);
        this.lettersPoints.put("O", 1);
        this.lettersPoints.put("N", 1);
        this.lettersPoints.put("R", 1);
        this.lettersPoints.put("T", 1);
        this.lettersPoints.put("L", 1);
        this.lettersPoints.put("S", 1);
        this.lettersPoints.put("U", 1);

        this.lettersPoints.put("D", 2);
        this.lettersPoints.put("G", 2);

        this.lettersPoints.put("B", 3);
        this.lettersPoints.put("C", 3);
        this.lettersPoints.put("M", 3);
        this.lettersPoints.put("P", 3);

        this.lettersPoints.put("F", 4);
        this.lettersPoints.put("H", 4);
        this.lettersPoints.put("V", 4);
        this.lettersPoints.put("W", 4);
        this.lettersPoints.put("Y", 4);

        this.lettersPoints.put("K", 5);

        this.lettersPoints.put("J", 8);
        this.lettersPoints.put("X", 8);

        this.lettersPoints.put("Q", 10);
        this.lettersPoints.put("Z", 10);
    }

    public Integer getPointsForLetter(String letter) {
        return lettersPoints.get(letter);
    }

}
