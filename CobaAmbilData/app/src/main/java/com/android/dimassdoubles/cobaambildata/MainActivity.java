package com.android.dimassdoubles.cobaambildata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportLoaderManager().initLoader(0, null, this);

        Log.d(TAG, "Halo Hai 1");
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        Log.d(TAG, "Halo Hai 2");
        mAdapter = new WordListAdapter(this);
        Log.d(TAG, "Halo Hai 3");
        mRecyclerView.setAdapter(mAdapter);
        Log.d(TAG, "Halo Hai 4");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        Log.d(TAG, "Halo Hai 5");
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        String queryUri = Contract.CONTENT_URI.toString();
        String[] projection = new String[] {Contract.CONTENT_PATH};
        return new CursorLoader(this, Uri.parse(queryUri), projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mAdapter.setData(null);
    }

}
