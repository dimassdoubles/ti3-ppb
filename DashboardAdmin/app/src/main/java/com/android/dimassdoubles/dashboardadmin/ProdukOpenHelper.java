package com.android.dimassdoubles.dashboardadmin;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import androidx.annotation.Nullable;

public class ProdukOpenHelper extends SQLiteOpenHelper implements BaseColumns {
    private static final String TAG = ProdukOpenHelper.class.getSimpleName();

    // database version
    public static final int DATABASE_VERSION = 1;

    // database name
    public static final String DATABASE_NAME = "ProdukToko.db";

    // table name
    public static final String TABLE_NAME = "DataProduk";

    // column name
    public static final String COLUMN_NAMA = "nama";
    public static final String COLUMN_HARGA = "harga";
    public static final String COLUMN_GAMBAR = "gambar";

    private SQLiteDatabase mReadableDB;
    private SQLiteDatabase mWritableDB;


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" + ProdukOpenHelper._ID + " INTEGER PRIMARY KEY," +
                    COLUMN_NAMA + " TEXT," +
                    COLUMN_HARGA + " INTEGER," +
                    COLUMN_GAMBAR + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DATABASE_NAME;

    public ProdukOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d(TAG, "Construct ProdukOpenHelper");
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
        fillDatabase(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // CRUD = CREATE, READ, UPDATE, DELETE
    // CREATE
    public long insert(String nama, int harga, String gambar) {
        long newRowId = 0;
        try {
            // gets the data repository in write mode
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }

            // create a new map of values, where where column names are the keys
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAMA, nama);
            values.put(COLUMN_HARGA, harga);
            values.put(COLUMN_GAMBAR, gambar);

            // insert the new row, returning the primary key value of the new row
            newRowId = mWritableDB.insert(TABLE_NAME, null, values);
        } catch (Exception e) {
            Log.d(TAG, "INSERT EXCEPTION1 " + e.getMessage());
        }

        return newRowId;
    }

    // READ
    public ProdukItem query(int position) {
        String query = "SELECT  * FROM " + TABLE_NAME +
                " ORDER BY " + COLUMN_NAMA + " ASC " +
                "LIMIT " + position + ",1";
        Cursor cursor = null;
        ProdukItem entry = new ProdukItem();
        try {
            if (mReadableDB == null) {
                mReadableDB = this.getReadableDatabase();
            }
            cursor = mReadableDB.rawQuery(query, null);
            cursor.moveToFirst();
            entry.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
            entry.setNamaProduk(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAMA)));
            entry.setHargaProduk(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_HARGA)));
            entry.setGambarProduk(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GAMBAR)));
        } catch (Exception e) {
            Log.d(TAG, "QUERY EXCEPTION! " + e.getMessage());
        } finally {
            cursor.close();
            return entry;
        }
    }

    // UPDATE
    public void update(int id, String namaBaru, int hargaBaru, String gambarBaru) {
        try {
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }

            // new value of one column
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAMA, namaBaru);
            values.put(COLUMN_HARGA, hargaBaru);
            values.put(COLUMN_GAMBAR, gambarBaru);

            // which row to update, based on the id
            String selection = _ID + " LIKE ?";
            String[] selectionArgs = {String.valueOf(id)};

            mWritableDB.update(
                    TABLE_NAME,
                    values,
                    selection,
                    selectionArgs);
        } catch (Exception e) {
            Log.d(TAG, "UPDATE EXCEPTION! " + e.getMessage());
        }
    }


    // DELETE
    public void delete(int id) {
        try {
            if (mWritableDB == null) {
                mWritableDB = this.getWritableDatabase();
            }
            // define 'where' part of query
            String selection = _ID + " LIKE ?";
            // specify argument in placeholder order
            String[] slectionArgs = {String.valueOf(id)};

            mWritableDB.delete(
                    TABLE_NAME,
                    selection,
                    slectionArgs);
        } catch (Exception e) {
            Log.d(TAG, "DELETE EXCEPTION! " + e.getMessage());
        }
    }

    // get the number of rows in table
    // return the number of entries in table
    public long count() {
        if (mReadableDB == null) {
            mReadableDB = this.getReadableDatabase();
        }
        long count = DatabaseUtils.queryNumEntries(mReadableDB, TABLE_NAME);
        return count;
    }

    public void fillDatabase(SQLiteDatabase db) {
        try {
            String[] nama = {"Beras", "Gula Pasir", "Minyak Goreng", "Sarden", "Mie Goreng", "Mie Rebus"};
            int[] harga = {50000, 25000, 32000, 8000, 3000, 3000};

            ContentValues values = new ContentValues();

            for (int i=0; i<nama.length; i++) {
//            String info = "Menambahkan " + nama[i] + ": " + harga[i];
//            Log.d(TAG, info);
                values.put(COLUMN_NAMA, nama[i]);
                values.put(COLUMN_HARGA, harga[i]);
                values.put(COLUMN_GAMBAR, "");
                db.insert(TABLE_NAME, null, values);
            }

        } catch (Exception e) {
            Log.d(TAG, "FILL DATABASE EXCEPTION! " + e.getMessage());
        }
    }


}
