package com.dimassdoubles.gudangtoko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private EditText mEditSearch;
    private Button mSearchBtn;
    private TextView mResultView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

//        // back navigation
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//
//        mDB = new DatabaseHandler(this);
//
//        mEditSearch = findViewById(R.id.etSearch);
//        mSearchBtn = findViewById(R.id.btnSearch);
//        mResultView = findViewById(R.id.tvResult);
//
//        mSearchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String word = mEditSearch.getText().toString();
//                mResultView.setText("Result for '" + word + "' :\n\n");
//
//                // search for the word in the database
//                Cursor cursor = mDB.search(word);
//
//                // only process a non-null cursor with rows
//                if (cursor != null & cursor.getCount() > 0) {
//                    // You must move the cursor the first item
//                    cursor.moveToFirst();
//                    int index;
//                    String result;
//
//                    do {
//                        // Don't guess at the column index.
//                        // Get the index for the named column.
//                        index = cursor.getColumnIndexOrThrow(DatabaseHandler.COLUMN_NAMA_BARANG);
//                        // Get the value from the column for the current cursor.
//                        result = cursor.getString(index);
//                        // Add result to what's already in the text view.
//                        mResultView.append(result + "\n");
//                    } while (cursor.moveToNext());
//                    cursor.close();
//                }
//            }
//        });

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        // handle arrow click here
//        if (item.getItemId() == android.R.id.home) {
//            mDB.close();
//            finish(); // close this activity and return to preview activity (if there is any)
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
