package com.dimassdoubles.wordlistsqlinteractive;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = DatabaseOpenHelper.class.getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "WordList.db";

    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;

    public final class DatabaseContract {
        private DatabaseContract() {}

        public class DatabaseEntry implements BaseColumns {
            // A11.2020.12497
            public static final String TABLE_NAME = "WordList";
            public static final String COLUMN_WORD = "word";
        }
    }

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.DatabaseEntry.TABLE_NAME + " (" +
                    DatabaseContract.DatabaseEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.DatabaseEntry.COLUMN_WORD + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.DatabaseEntry.TABLE_NAME;

    public DatabaseOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct DatabaseOpenHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "ONCREATE DATABASE");
        db.execSQL(SQL_CREATE_ENTRIES);
        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Menambahkan kata ke database
    public long addWord(String xword) {
        long newRowId = 0;
        try {
            // Gets the data repository in write mode
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }

            // Create a new map of values, where column names are the keys
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.DatabaseEntry.COLUMN_WORD, xword);

            //        // Insert the new row, returning the primary key value of the new row
            //        long newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
            newRowId = mWritableDB.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION! " + e.getMessage());
        }
        return newRowId;
    }

    // Query by position, position = Nth row
    // 12497
    public WordItem query(int position) {
        String query = "SELECT  * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME +
                " ORDER BY " + DatabaseContract.DatabaseEntry.COLUMN_WORD + " ASC " +
                "LIMIT " + position + ",1";
        Cursor cursor = null;
        WordItem entry = new WordItem();
        try {
            if (mReadableDB == null) {
                mReadableDB = this.getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.DatabaseEntry._ID)));
            entry.setWord(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DatabaseEntry.COLUMN_WORD)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            // Must close cursor and db now that we are done with it.
            // dimas
            cursor.close();
            return entry;
        }
    }

    public List<String> getAllWord() {
        List<String> wordList = new ArrayList<String>();
        String query_select_word = "SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME;
        if (mWritableDB == null) {mWritableDB = this.getWritableDatabase();}
        Cursor cursor = mWritableDB.rawQuery(query_select_word, null);
        if (cursor.moveToFirst()) {
            do {
                String word = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DatabaseEntry.COLUMN_WORD));
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    // get the number of rows in the word list table
    // return the number of entries in table
    // dimas saputro
    public long count() {
        if (mReadableDB == null) {mReadableDB = this.getReadableDatabase();}
        long count = DatabaseUtils.queryNumEntries(mReadableDB, DatabaseContract.DatabaseEntry.TABLE_NAME);
        return count;
    }

    // Delete kata
    public void delWord(int id) {
        try {
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }
            // Define 'where' part of query.
            String selection = DatabaseContract.DatabaseEntry._ID + " LIKE ?";
            // Specify rgument in placeholder order
            String[] selectionArgs = {String.valueOf(id)};

            mWritableDB.delete(
                    DatabaseContract.DatabaseEntry.TABLE_NAME,
                    selection,
                    selectionArgs);
        } catch (Exception e) {
            Log.d (TAG, "DELETE EXCEPTION! " + e.getMessage());        }
    }

    // Update kata
    public void updateWord(int id, String newWord) {
        try {
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }

            // New value for one column
            ContentValues values = new ContentValues();
            values.put(DatabaseContract.DatabaseEntry.COLUMN_WORD, newWord);

            // Which row to update, based on the oldWord
            //A11202012497
            String selection = DatabaseContract.DatabaseEntry._ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(id)};

            mWritableDB.update(
                    DatabaseContract.DatabaseEntry.TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        } catch (Exception e) {
            Log.d (TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }

    }

    public Cursor search (String searchString) {
        String[] columns = new String[]{DatabaseContract.DatabaseEntry.COLUMN_WORD};
        searchString = "%" + searchString + "%";
        String where = DatabaseContract.DatabaseEntry.COLUMN_WORD + " LIKE ?";
        String[]whereArgs = new String[]{searchString};

        Cursor cursor = null;

        try {
            if (mReadableDB == null) {mReadableDB = getReadableDatabase();}
            cursor = mReadableDB.query(DatabaseContract.DatabaseEntry.TABLE_NAME, columns, where, whereArgs, null, null, null);
        } catch (Exception e) {
            Log.d(TAG, "SEARCH EXCEPTION! " + e);
        }

        return cursor;
    }

    public void fillDatabase(SQLiteDatabase db) {
        String[] words = {"Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio", "SQLiteDatabase", "SQLOpenHelper",
                "Data model", "ViewHolder", "Android Performance",
                "OnClickListener"};
        ContentValues values = new ContentValues();

        for(int i=0; i<words.length; i++) {
            String info = "Menambahkan " + words[i];
            Log.i("Tambah Kata", info);
            values.put(DatabaseContract.DatabaseEntry.COLUMN_WORD, words[i]);
            db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
        }
    }

}
