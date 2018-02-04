package pvtitov.anothertranslator.model;


import android.support.annotation.NonNull;

/**
 * Created by Павел on 23.12.2017.
 */

public class Word implements Comparable {
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

    @Override
    public int compareTo(@NonNull Object o) {
        Word wordObject;
        try {
            wordObject = (Word)o;
        } catch (ClassCastException e) {
            throw new ClassCastException(o.toString()
                    + " - is not instance of Word");
        }
        return word.compareTo(wordObject.getWord());
    }
}
