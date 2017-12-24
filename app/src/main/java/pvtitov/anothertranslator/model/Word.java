package pvtitov.anothertranslator.model;


/**
 * Created by Павел on 23.12.2017.
 */

public class Word {
    private String word;
    private String translation;

    public Word(String word, String translation) {
        this.word = word;
        this.translation = translation;
    }

    public String getWord() {
        return word;
    }

    public String getTranslation() {
        return translation;
    }
}
