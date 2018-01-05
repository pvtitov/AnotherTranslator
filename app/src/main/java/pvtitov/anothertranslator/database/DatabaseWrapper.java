package pvtitov.anothertranslator.database;

/**
 * Created by Павел on 25.06.2017.
 */

public class DatabaseWrapper {
    public static final class TranslationsTable {
        public static final String TABLE_NAME = "words_with_translations";

        public static final class Columns {
            public static final String WORDS = "words";
            public static final String TRANSLATIONS = "translations";
        }
    }
}
