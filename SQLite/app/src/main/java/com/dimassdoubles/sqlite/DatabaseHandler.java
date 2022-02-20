package com.dimassdoubles.sqlite;

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
    private static final String DATABASE_NAME = "Sekolah";

    // Nama Table
    private static final String TABLE_SISWA = "Siswa";

    // Nama Kolom Table Siswa
    private static final String KEY_NIS = "nis";
    private static final String KEY_NAMA = "nama";

    public DatabaseHandler(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        Log.d("Database onCreate", "Hallo, Database sampai sini");
        String query_table_siswa = "CREATE TABLE " + TABLE_SISWA + "(" + KEY_NIS + " TEXT PRIMARY KEY, " + KEY_NAMA + " TEXT)";
        db.execSQL(query_table_siswa);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SISWA);
        // Create tables again
        onCreate(db);
    }

    // Menambah Siswa Baru
    public void AddSiswa(Siswa siswa) {
        Log.d("Menambah Siswa Baru", "Sampai di sini");
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NIS, siswa.getNis());
        values.put(KEY_NAMA, siswa.getNama());

        // Inserting Row
        db.insert(TABLE_SISWA, null, values);
        db.close();
    }

    // Membaca Siswa
    public Siswa GetSiswa(String nis) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_SISWA, new String[] {
                KEY_NIS, KEY_NAMA}, KEY_NIS + "=?", new String[] {nis}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Siswa siswa = new Siswa(cursor.getString(0), cursor.getString(1));
        return siswa;
    }

    // Membaca Semua Siswa
    public List<Siswa> GetSemuaSiswa() {
        List<Siswa> siswaList = new ArrayList<Siswa> ();
        String query_select_siswa = "SELECT * FROM " + TABLE_SISWA;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query_select_siswa, null);
        if (cursor.moveToFirst()) {
            do {
                Siswa siswa = new Siswa(cursor.getString(0), cursor.getString(1));
                siswaList.add(siswa);
            } while (cursor.moveToNext());
        }
        return siswaList;
    }

    // Menghapus siswa
    public void DeleteSiswa(Siswa siswa) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SISWA, KEY_NIS + "='" + siswa.getNis() + "'", null);
        System.out.println("Data terhapus " + siswa.getNama());
    }

    public void DeleteRow(String xnis) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SISWA, KEY_NIS + "='" + xnis + "'", null);
        db.close();
        System.out.println("Data terhapus " + xnis);
    }

    public void UpdateMethod(String nis, String nama) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update " + TABLE_SISWA + " set nama='" + nama + "' where nis='" + nis + "'");
        db.close();
        System.out.println("Data sudah di update " + nis);
    }

}
