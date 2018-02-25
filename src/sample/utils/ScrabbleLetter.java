package sample.utils;

import java.util.Map;
import java.util.TreeMap;

public final class ScrabbleLetter {

    private static Character[] letters;
    private static Map<Character, Integer> lettersPoints;
    
    static {
        letters = new Character[]{
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
                'W', 'X', 'Y', 'Z'
        };

        lettersPoints = new TreeMap<>();

        lettersPoints.put('A', 1);
        lettersPoints.put('E', 1);
        lettersPoints.put('I', 1);
        lettersPoints.put('O', 1);
        lettersPoints.put('N', 1);
        lettersPoints.put('R', 1);
        lettersPoints.put('T', 1);
        lettersPoints.put('L', 1);
        lettersPoints.put('S', 1);
        lettersPoints.put('U', 1);

        lettersPoints.put('D', 2);
        lettersPoints.put('G', 2);

        lettersPoints.put('B', 3);
        lettersPoints.put('C', 3);
        lettersPoints.put('M', 3);
        lettersPoints.put('P', 3);

        lettersPoints.put('F', 4);
        lettersPoints.put('H', 4);
        lettersPoints.put('V', 4);
        lettersPoints.put('W', 4);
        lettersPoints.put('Y', 4);

        lettersPoints.put('K', 5);

        lettersPoints.put('J', 8);
        lettersPoints.put('X', 8);

        lettersPoints.put('Q', 10);
        lettersPoints.put('Z', 10);

        System.out.println(lettersPoints.get('Z'));
    }

    public static Integer getPointsForLetter(Character letter) {
        return lettersPoints.get(letter);
    }

}
