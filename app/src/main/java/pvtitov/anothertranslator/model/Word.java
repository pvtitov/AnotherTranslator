package pvtitov.anothertranslator.model;


/**
 * Created by Павел on 23.12.2017.
 */

public class Word {
    private String word;
    private String translation;

    public Word() {}

    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }

    @Override
    public String toString(){
        return translation;
    }
}
