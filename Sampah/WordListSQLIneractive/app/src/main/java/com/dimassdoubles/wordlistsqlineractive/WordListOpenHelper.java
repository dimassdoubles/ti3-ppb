package com.dimassdoubles.wordlistsqlineractive;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class WordListOpenHelper extends SQLiteOpenHelper {
    // It's a good idea to always define a log tag like this.
    private static final String TAG = WordListOpenHelper.class.getSimpleName();

    // has to be 1 first time or app will crash
    private static final int DATABASE_VERSION = 1;
    private static final String WORD_LIST_TABLE = "word_entries";
    private static final String DATABASE_NAME = "wordlist";

    // column names
    public static final String KEY_ID = "_id";
    public static final String KEY_WORD = "word";

    // and a string array of columns
    private static final String[] COLUMNS = {KEY_ID, KEY_WORD};

    // build the sql query that creates the tables
    private static final String WORD_LIST_TABLE_CREATE =
            "CREATE TABLE " + WORD_LIST_TABLE + " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                    // ID WILL AUTO-INCREMENT IF NO VALUE PASSED
                    KEY_WORD + " TEXT );";

    private SQLiteDatabase mWritableDB;
    private SQLiteDatabase mReadableDB;

//    // Membaca kata
//    public WordItem query(int position) {
//        String query = "SELECT * FROM " + WORD_LIST_TABLE +
//                " ORDER BY " + KEY_WORD + " ASC " +
//                "LIMIT " + position + ",1";
//        mReadableDB = getReadableDatabase();
////        Cursor cursor = null;
////        WordItem entry = new WordItem();
////        try {
////            if (mReadableDB == null) {
////                mReadableDB = getReadableDatabase();
////            }
////            cursor = mReadableDB.rawQuery(query, null);
////            cursor.moveToFirst();
////            entry.setId(cursor.getInt(0));
////            entry.setWord(cursor.getString(1));
////        } catch (Exception e) {
////            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
////        } finally {
////            cursor.close();
////            return entry;
////        }
//    }

    public WordListOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + WORD_LIST_TABLE +
                " (" + KEY_ID + " INTEGER PRIMARY KEY, " +
                KEY_WORD + " TEXT );";
        db.execSQL(query);
        fillDatabaseWithData(db);
    }

    private void fillDatabaseWithData(SQLiteDatabase db) {
        Log.d("Menambahkan Data", "Proses menambah data ...");
        String[] words = {"Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio", "SQLiteDatabase", "SQLOpenHelper",
                "Data model", "ViewHolder", "Android Performance",
                "OnClickListener"};

        // Create a container for the data
        ContentValues values = new ContentValues();
        for (int i = 0; i < words.length; i++) {
            values.put(KEY_WORD, words[i]);
            db.insert(WORD_LIST_TABLE, null, values);
        }
        ;
        db.close();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(WordListOpenHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + WORD_LIST_TABLE);
        onCreate(db);
    }
}
