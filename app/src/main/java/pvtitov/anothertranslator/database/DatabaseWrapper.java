package pvtitov.anothertranslator.database;

/**
 * Created by Павел on 25.06.2017.
 */

public class DatabaseWrapper {
    public static final class HistoryTable {
        public static final String TABLE_NAME = "words_and_translations";

        public static final class Columns {
            public static final String WORDS = "words_column";
            public static final String TRANSLATIONS = "translations";
        }
    }
}
