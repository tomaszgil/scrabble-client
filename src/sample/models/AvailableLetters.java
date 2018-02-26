package sample.models;

public class AvailableLetters {

    private Letter[] letters;

    public AvailableLetters(Character[] letterMap) {
        this.letters = new Letter[7];
        prepareBoard(letterMap);
    }

    public void setLetterMap(Character[] letterMap) {
        this.letters = new Letter[7];
        prepareBoard(letterMap);
    }

    public Character[] getLetterMap() {
        Character[] letterMap = new Character[7];

        for (int i=0; i < 7; i++) {
            if (letters[i] == null) {
                letterMap[i] = '0';
            } else {
                letterMap[i] = letters[i].getCharacter();
            }
        }

        return letterMap;
    }

    public Letter[] getLetters() {
        return letters;
    }

    public void setLetter(Integer i, Letter letter) {
        letters[i] = letter;
    }

    private void prepareBoard(Character[] letterMap) {
        for (int i = 0; i < 7; i++) {
            if (letterMap[i] != '0') { // not an empty character
                letters[i] = new Letter(letterMap[i], true);
            }
        }
    }

}
