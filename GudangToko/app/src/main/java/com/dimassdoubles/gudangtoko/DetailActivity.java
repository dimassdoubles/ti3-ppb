package com.dimassdoubles.gudangtoko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = DetailActivity.class.getSimpleName();

    public static int POSITION = -99;
    public static int DATA_UPDATED = -99;
    public static int DATA_REMOVED = -99;

    private TextView tvKode;
    private EditText etNama;
    private EditText etHrgBeli;
    private EditText etHrgJual;
    private EditText etStok;
    private LinearLayout buttonContainer;
    private Button btnDelete;
    private Button btnEdit;
    private Button btnSave;
    private boolean bisaSimpan;

    String kode;
    String nama;
    int hrgBeli;
    int hrgJual;
    int stok;

    String namaBarangBaru;
    String hargaBeliBaru;
    String hargaJualBaru;
    String stokBaru;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        AndroidNetworking.initialize(getApplicationContext());

        // back navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        kode = getIntent().getStringExtra(BarangAdapter.EXTRA_KODE);
        nama = getIntent().getStringExtra(BarangAdapter.EXTRA_NAMABARANG);
        hrgBeli = getIntent().getIntExtra(BarangAdapter.EXTRA_HARGABELI, 0);
        hrgJual = getIntent().getIntExtra(BarangAdapter.EXTRA_HARGAJUAL, 0);
        stok = getIntent().getIntExtra(BarangAdapter.EXTRA_STOK, 0);
        Log.d(TAG, "BARANG = " + nama);

        POSITION = getIntent().getIntExtra("POSITION", -99);

        tvKode = findViewById(R.id.tvKode);
        etNama = findViewById(R.id.etNama);
        etHrgBeli = findViewById(R.id.etHrgBeli);
        etHrgJual = findViewById(R.id.etHrgJual);
        etStok = findViewById(R.id.etStok);
        btnDelete = findViewById(R.id.btnDelete);
        btnEdit = findViewById(R.id.btnEdit);
        buttonContainer = findViewById(R.id.buttonContainer);

        tvKode.setText(kode);
        etNama.setText(nama);
        etHrgBeli.setText("Rp " + String.format(Locale.GERMAN,"%,d",hrgBeli));
        etHrgJual.setText("Rp " + String.format(Locale.GERMAN,"%,d",hrgJual));
        etStok.setText(String.valueOf(stok));

        bisaSimpan = false;

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bisaSimpan) {
                    editSession();
                } else {
                    returnReply(v);
                    DATA_UPDATED = 1;
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Barang
                new DeleteTask().execute();
                DATA_REMOVED = 1;
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }

    public void editSession() {
        etNama.setEnabled(true);
        etHrgBeli.setEnabled(true);
        etHrgJual.setEnabled(true);
        etStok.setEnabled(true);

        etHrgBeli.setText(String.valueOf(hrgBeli));
        etHrgJual.setText(String.valueOf(hrgJual));
        etStok.setText(String.valueOf(stok));

        // ubah textStyle
        etNama.setTypeface(Typeface.DEFAULT);
        etHrgBeli.setTypeface(Typeface.DEFAULT);
        etHrgBeli.setText(String.valueOf(hrgBeli));
        etHrgJual.setTypeface(Typeface.DEFAULT);
        etStok.setTypeface(Typeface.DEFAULT);

        // ubah ui button
        btnEdit.setBackgroundColor(ContextCompat.getColor(this, R.color.biru));
        btnEdit.setText("SAVE");
        bisaSimpan = true;
    }

    public void returnReply(View view) {
        namaBarangBaru = etNama.getText().toString();
        hargaBeliBaru = etHrgBeli.getText().toString();
        hargaJualBaru = etHrgJual.getText().toString();
        stokBaru = etStok.getText().toString();
        int hrgBeliBaruInt = 0;
        int hrgJualBaruInt = 0;
        int stokBaruInt = 0;
        boolean cekAngka = false;


        try {
            hrgBeliBaruInt = Integer.parseInt(hargaBeliBaru);
            hrgJualBaruInt = Integer.parseInt(hargaJualBaru);
            stokBaruInt = Integer.parseInt(stokBaru);
            cekAngka = true;
        } catch (Exception e) {
            Log.d(TAG, "RETURN REPLY EXCEPTION! " + e.getMessage());
            Toast.makeText(this, "Pastikan input harga dan stok hanya berisi angka !", Toast.LENGTH_SHORT).show();
        }

        // memastikan form tidak ada yang kosong
        if (!kode.equals(AddBarangActivity.NO_TEXT) && !namaBarangBaru.equals(AddBarangActivity.NO_TEXT) && cekAngka) {

            // memastikan angka input lebih dari 0
            if (hrgBeliBaruInt > 0 && hrgJualBaruInt > 0 && stokBaruInt > 0) {
                Log.d(TAG,"BISA EDIT");
                new UpdateTask().execute();
                Intent replyIntent = new Intent();
                setResult(RESULT_OK, replyIntent);
                finish();

            } else {
                Toast.makeText(this, "Input tidak boleh kurang dari 0 !", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Input tidak boleh kosong !", Toast.LENGTH_SHORT).show();
        }
    }

    private class DeleteTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            AndroidNetworking.post("http://192.168.43.160/tabelbarang/api/delete.php")
                    .addBodyParameter("kode_barang", kode)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Toast.makeText(getApplicationContext(), "Berhasil Menghapus Barang", Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onError(ANError error) {
                            // handle error
                        }
                    });

            return null;
        }
    };

    private class UpdateTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            AndroidNetworking.post("http://192.168.43.160/tabelbarang/api/update.php")
                    .addBodyParameter("kode_barang", kode)
                    .addBodyParameter("nama_barang", namaBarangBaru)
                    .addBodyParameter("harga_beli", hargaBeliBaru)
                    .addBodyParameter("harga_jual", hargaJualBaru)
                    .addBodyParameter("stok", stokBaru)
                    .setTag("test")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d(TAG, "onResponse: Berhasil Update");
                            Toast.makeText(getBaseContext(), "Berhasil Update Barang", Toast.LENGTH_SHORT).show();
                            
                        }
                        @Override
                        public void onError(ANError error) {
                        }
                    });
            return null;
        }
    }
}
