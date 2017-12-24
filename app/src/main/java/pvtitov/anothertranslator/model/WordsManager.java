package pvtitov.anothertranslator.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Павел on 23.12.2017.
 */

public class WordsManager {

    // WordsManager - singleton
    private WordsManager(){}

    static private WordsManager instance;

    static public WordsManager getInstance() {
        if (instance != null) {
            return instance;
        }
        else {
            return instance = new WordsManager();
        }
    }

    // В этом поле хранятся ссылки на все объекты Word
    private List<Word> words = new ArrayList<>();

    public Word get(int position) {
        return words.get(position);
    }

    public void add(Word word) {
        words.add(word);
    }

    public List<Word> getAll() {
        return words;
    }
}
