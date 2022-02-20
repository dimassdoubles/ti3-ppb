package com.android.dimassdoubles.dashboardadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int PRODUK_EDIT = 1;
    public static final int PRODUK_ADD = -1;

    private ProdukOpenHelper mDB;
    private RecyclerView mRecyclerView;
    private ProdukAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDB = new ProdukOpenHelper(this);

        // create recycler view
        mRecyclerView = findViewById(R.id.recyclerView);

        // create adapter and supply data to be displayed
        mAdapter = new ProdukAdapter(this, mDB);

        // connect adapter with recyclerView
        mRecyclerView.setAdapter(mAdapter);

        // give RecyclerView a default layout manager
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,  menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(getBaseContext(), AddActivity.class);
                startActivityForResult(intent, PRODUK_EDIT);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PRODUK_EDIT) {
            int position = -99;
            int id = data.getIntExtra(ProdukAdapter.EXTRA_ID, -99);
            try {
                position = data.getIntExtra("POSITION", -99);
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
            if (position != -99) {
                mDB.delete(id);
            } else {
                String nama = data.getStringExtra(ProdukAdapter.EXTRA_NAMA);
                int harga = data.getIntExtra(ProdukAdapter.EXTRA_HARGA, -99);
                String gambar = data.getStringExtra(ProdukAdapter.EXTRA_GAMBAR);
                if (id == PRODUK_ADD) {
                    mDB.insert(nama, harga, gambar);
                } else if (id >= 0) {
                    mDB.update(id, nama, harga, gambar);
                }
            }

            // update the UI
            mAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getApplicationContext(), "Gagal Edit", Toast.LENGTH_SHORT).show();
        }
    }
}
