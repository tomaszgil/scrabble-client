package sample.models;

public class Board {

    private Character[][] letterMap;
    private Letter[][] letters;

    public Board(Character[][] letterMap) {
        this.letterMap = letterMap;
        this.letters = new Letter[15][15];
        prepareBoard();
    }

    public Character[][] getLetterMap() {
        return letterMap;
    }

    public void setLetterMap(Character[][] letterMap) {
        this.letterMap = letterMap;
        prepareBoard();
    }

    private void prepareBoard() {
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                letters[i][j] = new Letter(letterMap[i][j]);
            }
        }
    }

}
