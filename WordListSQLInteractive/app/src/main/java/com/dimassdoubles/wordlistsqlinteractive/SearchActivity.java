package com.dimassdoubles.wordlistsqlinteractive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    private TextView mResultView;
    private EditText mEditWordView;
    private Button mSearchBtn;
    private DatabaseOpenHelper mDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mDB = new DatabaseOpenHelper(this);

        mResultView = findViewById(R.id.tv_result);
        mEditWordView = findViewById(R.id.search_word);
        mSearchBtn = findViewById(R.id.button_search);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = mEditWordView.getText().toString();
                mResultView.setText("Result for " + word + ":\n\n");

                // Search for the word in the database.
                Cursor cursor = mDB.search(word);

                // Only process a non-null cursor with rows.
                if (cursor != null & cursor.getCount() > 0) {
                    // You must move the cursor to the first item.
                    cursor.moveToFirst();
                    int index;
                    String result;
                    // Iterate over the cursor, while there are entries.
                    do {
                        // Don't guess at the column index.
                        // Get the index for the named column.
                        index = cursor.getColumnIndex(DatabaseOpenHelper.DatabaseContract.DatabaseEntry.COLUMN_WORD);
                        // Get the value from the column for the current cursor.
                        result = cursor.getString(index);
                        // Add result to what's already in the text view.
                        mResultView.append(result + "\n");
                    } while (cursor.moveToNext()); // Returns true or false
                    cursor.close();
                } // You should add some handling of null case. Right now, nothing happens.
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
            // 12497
        }

        return super.onOptionsItemSelected(item);
    }
}
