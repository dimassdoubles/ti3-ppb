package com.dimassdoubles.wordlistsqlinteractive;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class EditWordActivity extends AppCompatActivity {

    private static final String TAG = EditWordActivity.class.getSimpleName();

    private static final int NO_ID = -99;
    private static final String NO_WORD = "";

    private EditText mEditWordView;

    // Unique tag for the intent reply
    public static final String EXTRA_REPLY = "REPLY";

    int mId = MainActivity.WORD_ADD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_word);
        Log.i(TAG, "Sampai disini");

        Toolbar myToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mEditWordView = findViewById(R.id.edit_word);

        // Get data sent from calling activity
        Bundle extras = getIntent().getExtras();

        // If we are passed content, fill it in for the user to edit
        if (extras != null) {
            Log.i(TAG, "Sampai Disini");
            int id = extras.getInt(WordListAdapter.EXTRA_ID, NO_ID);
            Log.i(TAG, "ID : " + String.valueOf(id));
            String word = extras.getString(WordListAdapter.EXTRA_WORD, NO_WORD);
            Log.i(TAG, "WORD " + word);
            if ((id != NO_ID) && (word != NO_WORD)) {
                Log.i(TAG, "Sampai 1");
                mId = id;
                mEditWordView.setText(word);
            }

        } // Otherwise, start with empty fields


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

    public void returnReply(View view) {
        String word = ((EditText) findViewById(R.id.edit_word)).getText().toString();

        Log.i(TAG, "Sampai 2");
        Intent replyIntent = new Intent();
        Log.i(TAG, "Sampai 3");
        replyIntent.putExtra(EXTRA_REPLY, word);
        Log.i(TAG, "Sampai 4");
        replyIntent.putExtra(WordListAdapter.EXTRA_ID, mId);
        Log.i(TAG, "Sampai 5");
        setResult(RESULT_OK, replyIntent);
        Log.i(TAG, "Sampai 6");
        finish();
    }
}
