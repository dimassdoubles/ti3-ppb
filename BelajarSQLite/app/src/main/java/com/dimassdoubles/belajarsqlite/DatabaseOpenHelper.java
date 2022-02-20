package com.dimassdoubles.belajarsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseOpenHelper extends SQLiteOpenHelper {
   public static final int DATABASE_VERSION = 1;
   public static final String DATABASE_NAME = "WordList.db";

   public final class DatabaseContract {
       private DatabaseContract() {}

       public class DatabaseEntry implements BaseColumns {
           public static final String TABLE_NAME = "WordList";
//           public static final String COLUMN_ID
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
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        fillDatabase();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Menambahkan kata ke database
    public long addWord(String xword) {
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.COLUMN_WORD, xword);

//        // Insert the new row, returning the primary key value of the new row
//        long newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
        long newRowId = db.insert(DatabaseContract.DatabaseEntry.TABLE_NAME, null, values);
        return newRowId;
    }

    // Query by position, position = Nth row
    public String query(int position) {

        String num_row = "SELECT ROW_NUMBER() OVER() AS num_row, " +
                DatabaseContract.DatabaseEntry.COLUMN_WORD +
                " FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME;
        String query = "SELECT * FROM (" + num_row + ") WHERE num_row = " + position + ";";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        String word = "Tidak ada apa-apa";
        if (cursor.moveToFirst()) {
            word = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.DatabaseEntry.COLUMN_WORD));
        }
        cursor.close();
        return word;
    }

    public List<String> getAllWord() {
        List<String> wordList = new ArrayList<String>();
        String query_select_word = "SELECT * FROM " + DatabaseContract.DatabaseEntry.TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_select_word, null);
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
    public long count() {
        SQLiteDatabase db = this.getReadableDatabase();
        return DatabaseUtils.queryNumEntries(db, DatabaseContract.DatabaseEntry.TABLE_NAME);
    }

    // Delete kata
    public void delWord(int id) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Define 'where' part of query.
        String selection = DatabaseContract.DatabaseEntry.COLUMN_WORD + " LIKE ?";
        // Specify rgument in placeholder order
        String[] selectionArgs = {String.valueOf(id)};

        db.delete(
                DatabaseContract.DatabaseEntry.TABLE_NAME,
                selection,
                selectionArgs);
    }

    // Update kata
    public void updateWord(int id, String newWord) {
        SQLiteDatabase db = this.getWritableDatabase();

        // New value for one column
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.DatabaseEntry.COLUMN_WORD, newWord);

        // Which row to update, based on the oldWord
        String selection = DatabaseContract.DatabaseEntry._ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(id)};

        db.update(
                DatabaseContract.DatabaseEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

    }

    public void fillDatabase() {
        String[] words = {"Android", "Adapter", "ListView", "AsyncTask",
                "Android Studio", "SQLiteDatabase", "SQLOpenHelper",
                "Data model", "ViewHolder", "Android Performance",
                "OnClickListener"};
        for(int i=0; i<words.length; i++) {
            String info = "Menambahkan " + words[i];
            Log.i("Tambah Kata", info);
            addWord(words[i]);
        }
    }

}
