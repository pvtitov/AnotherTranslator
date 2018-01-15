package pvtitov.anothertranslator.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import pvtitov.anothertranslator.database.DatabaseHelper;
import pvtitov.anothertranslator.database.WordsCursorWrapper;

import static pvtitov.anothertranslator.database.DatabaseWrapper.TranslationsTable;

/**
 * Created by Павел on 23.12.2017.
 */

public class WordsManager {

    //Singleton
    private static WordsManager instance;
    public static WordsManager getInstance(Context context) {
        if (instance == null) instance = new WordsManager(context.getApplicationContext());
        return instance;
    }


    private SQLiteDatabase mDatabase;
    private WordsManager(Context context){
        mDatabase = new DatabaseHelper(context)
                .getWritableDatabase();
    }

    // Метод, описывающий универсальный запрос к базе данных
    private WordsCursorWrapper query(String whereClause, String[] whereArgs){
        Cursor cursor = mDatabase.query(
                TranslationsTable.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null, null, null);
        return new WordsCursorWrapper(cursor);
    }

    // Возвращает список всех слов из базы данных
    public List<Word> extractAll() {
        List<Word> words = new ArrayList<>();
        try (WordsCursorWrapper cursor = query(null, null)) {
            cursor.moveToLast();
            while (!cursor.isBeforeFirst()) {
                words.add(cursor.getWord());
                cursor.moveToPrevious();
            }
        }
        return words;
    }

    // Осуществляет поиск слова по базе - возвращает объект Word
    public Word findByWord(String word) {
        try(WordsCursorWrapper cursor = query(TranslationsTable.Columns.WORDS + " = ? ", new String[] {word})){
            if (cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getWord();
        }
    }

    private static ContentValues getContentValues(Word word){
        ContentValues values = new ContentValues();
        values.put(TranslationsTable.Columns.WORDS, word.getWord());
        values.put(TranslationsTable.Columns.TRANSLATIONS, word.getTranslation());
        return values;
    }

    public void addNew(Word word){
        mDatabase.insert(
                TranslationsTable.TABLE_NAME,
                null,
                getContentValues(word));
    }

    public void update(Word word){
        mDatabase.update(
                TranslationsTable.TABLE_NAME,
                getContentValues(word),
                TranslationsTable.Columns.WORDS + " = ? ",
                new String[]{word.getWord()});
    }

    public void remove(String word){
        mDatabase.delete(
                TranslationsTable.TABLE_NAME,
                TranslationsTable.Columns.WORDS + " = ? ",
                new String[]{word});
    }
}
