package com.android.dimassdoubles.wordlistsqlwithcontentprovider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private String[] projection = new String[] {Contract.CONTENT_PATH};
    private String selectionClause = null;
    private String selectionArgs[] = null;
    private String sortOrder = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String uri = Contract.CONTENT_URI + "/" + String.valueOf(0);
        String word = "";
        int id = -1;
        Cursor cursor = this.getContentResolver().query(Uri.parse(uri), projection, selectionClause,
                selectionArgs, sortOrder);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            int indexWord = cursor.getColumnIndex(Contract.WordList.KEY_WORD);
            word = cursor.getString(indexWord);
            Log.d(TAG, "Kata posisi 0 = " + word);
        } else {
            Log.d(TAG, "Kata posisi 0 tidak ditemukan");
        }

        uri = Contract.CONTENT_URI + "/" + String.valueOf(10);
        word = "";
        id = -1;
        cursor = this.getContentResolver().query(Uri.parse(uri), projection, selectionClause,
                selectionArgs, sortOrder);
        if (cursor.moveToFirst() && cursor.getCount() >= 1) {
            int indecWord = cursor.getColumnIndex(Contract.WordList.KEY_WORD);
            word = cursor.getString(indecWord);
            Log.d(TAG, "Kata posisi 10 = " + word);
        } else {
            Log.d(TAG, "Kata posisi 10 tidak ditemukan");
        }
    }
}
