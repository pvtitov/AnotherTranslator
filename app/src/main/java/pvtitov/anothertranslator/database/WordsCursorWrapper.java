package pvtitov.anothertranslator.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import pvtitov.anothertranslator.model.Word;


/**
 * Created by Павел on 26.06.2017.
 */

public class WordsCursorWrapper extends CursorWrapper {
    public WordsCursorWrapper(Cursor cursor){
        super(cursor);
    }

    public Word getWord(){
        String word = getString(getColumnIndex(DatabaseWrapper.HistoryTable.Columns.WORDS));
        String translation = getString(getColumnIndex(DatabaseWrapper.HistoryTable.Columns.TRANSLATIONS));

        return new Word(word, translation);
    }
}
