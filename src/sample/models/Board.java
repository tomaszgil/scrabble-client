package sample.models;

public class Board {

    private Letter[][] letters;

    public Board(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
    }

    public void setLetterMap(Character[][] letterMap) {
        this.letters = new Letter[15][15];
        prepareBoard(letterMap);
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

}
