package com.dimassdoubles.sqlitesederhana;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    String dataWord[] = null;
    String dW[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Tambah Word
        DatabaseHandler db = new DatabaseHandler(this);

        // Membaca semua Word
        Log.d("Baca kata", "Membaca semua data kata ...");
        List<Word> word = db.GetSemuaWord();
        dataWord = new String[word.size()];
        dW = new String[word.size()];
        int i = 0;
        for (Word w : word) {
            String log = "ID: " + w.getId() + ", Kata: " + w.getWord();
            Log.d("Kata", log);
            dataWord[i] = w.getId() + " - " + w.getWord();
            dW[i] = w.getId();
            i++;
        }
        if (i==0) {
            Log.d("Tambah kata", "Menambah data kata ...");
            db.AddWord(new Word("01", "Android"));
            db.AddWord(new Word("02", "Adapter"));
            db.AddWord(new Word("03", "ListView"));
            db.AddWord(new Word("04", "AsyncTask"));
            db.AddWord(new Word("05", "Android Studio"));
            db.AddWord(new Word("06", "SQLiteDatabase"));
            db.AddWord(new Word("07", "SQLOpenHelper"));
            db.AddWord(new Word("08", "Data model"));
            db.AddWord(new Word("09", "ViewHolder"));
            db.AddWord(new Word("10", "Android Performance"));
            db.AddWord(new Word("11", "OnClickListener"));
        }
    }
}
