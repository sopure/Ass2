package database.DBStructure;

import android.provider.BaseColumns;

public class DBStructure {
    public static abstract class tableNationality implements BaseColumns {
        public static final String TABLE_NAME = "nation_table";
        public static final String COLUMN_NAME = "nationname";
    }
    public static abstract class tableNativeLanguage implements BaseColumns {
        public static final String TABLE_NAME = "nativelanguage_table";
        public static final String COLUMN_NAME = "nativeLanguagename";
    }
}
