package com.dimassdoubles.sqlitesederhana;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    // Nama Database
    private static final String DATABASE_NAME = "WordList";

    // Nama Tabel
    private static final String TABLE_KATA = "ListKata";

    // Nama kolom tabel siswa
    private static final String KEY_ID = "_id";
    private static final String KEY_WORD = "kata";

    public DatabaseHandler(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String query_table_kata = "CREATE TABLE " + TABLE_KATA + "(" + KEY_ID + " TEXT PRIMARY KEY, " + KEY_WORD + " TEXT)";
        db.execSQL(query_table_kata);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KATA);
        // Create tables again
        onCreate(db);
    }

    // Menambah kata baru
    public void AddWord(Word word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, word.getId());
        values.put(KEY_WORD, word.getWord());

        // Inserting Row
        db.insert(TABLE_KATA, null, values);
        db.close();
    }

    // Membaca Kata
    public Word GetWord(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KATA, new String[] {KEY_ID, KEY_WORD}, KEY_ID + "=?", new String[] {id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Word word = new Word(cursor.getString(0), cursor.getString(0));
        return word;
    }

    // Membaca semua kata
    public List<Word> GetSemuaWord() {
        List<Word> wordList = new ArrayList<Word>();
        String query_select_word = "SELECT * FROM " + TABLE_KATA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_select_word, null);
        if (cursor.moveToFirst()) {
            do {
                Word word = new Word(cursor.getString(0), cursor.getString(1));
                wordList.add(word);
            } while (cursor.moveToNext());
        }
        return wordList;
    }

    public void DeleteRow(String xid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KATA, KEY_ID + "='" + xid + "'", null);
        db.close();
        System.out.println("Data terhapus " + xid);
    }

    public void UpdateMethod(String id, String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_KATA + " set word='" + word + "' where _id='" + id + "'");
        db.close();
        System.out.println("Data sudah di update " + id);
    }
}
