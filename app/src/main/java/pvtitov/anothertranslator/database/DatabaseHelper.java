package pvtitov.anothertranslator.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Павел on 25.06.2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "translator_base.db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + DatabaseWrapper.TranslationsTable.TABLE_NAME + "( _id integer primary key autoincrement, "
        + DatabaseWrapper.TranslationsTable.Columns.WORDS + ", " + DatabaseWrapper.TranslationsTable.Columns.TRANSLATIONS + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
