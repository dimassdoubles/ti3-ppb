package com.dimassdoubles.gudangtoko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AddBarangActivity extends AppCompatActivity {
    private static final String TAG = AddBarangActivity.class.getSimpleName();

    public static final String NO_TEXT = "";
    public static final String EXTRA_REQUEST = "extra_request";

    private EditText etKode;
    private EditText etNama;
    private EditText etHrgBeli;
    private EditText etHrgJual;
    private EditText etStok;
    private Button btnSave;

    boolean success = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_barang);

        // back navigation
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        etKode = findViewById(R.id.tvKode);
        etNama = findViewById(R.id.etNama);
        etHrgBeli = findViewById(R.id.etHrgBeli);
        etHrgJual = findViewById(R.id.etHrgJual);
        etStok = findViewById(R.id.etStok);
        btnSave = findViewById(R.id.btnSave);

        AndroidNetworking.initialize(getApplicationContext());

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new InsertTask().execute();
                if (!success) {
                    Toast.makeText(getBaseContext(), "Gagal Menambah Barang", Toast.LENGTH_SHORT).show();
                }
                returnReply(v);
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

    public void returnReply(View view) {
        MainActivity.REQUEST_ADD = 1;
        Intent replyIntent = new Intent();
        replyIntent.putExtra(EXTRA_REQUEST, MainActivity.REQUEST_ADD);
        setResult(RESULT_OK, replyIntent);
        finish();
    }

    private class InsertTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            String kodeBarang = etKode.getText().toString();
            String namaBarang = etNama.getText().toString();
            String hargaBeli = etHrgBeli.getText().toString();
            String hargaJual = etHrgJual.getText().toString();
            String stok = etStok.getText().toString();

            // httppost
            try {
                if (!kodeBarang.equals("")&&!namaBarang.equals("")&&!hargaBeli.equals("")&&!hargaJual.equals("")&&!stok.equals("")) {
                    AndroidNetworking.post("http://192.168.43.160/tabelbarang/api/insert.php")
                            .addBodyParameter("kode_barang", kodeBarang)
                            .addBodyParameter("nama_barang", namaBarang)
                            .addBodyParameter("harga_beli", hargaBeli)
                            .addBodyParameter("harga_jual", hargaJual)
                            .addBodyParameter("stok", stok)
                            .setTag("test")
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONObject(new JSONObjectRequestListener() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    Toast.makeText(getApplicationContext(), "Berhasil Menambah Barang", Toast.LENGTH_SHORT).show();
                                    success = true;
                                }
                                @Override
                                public void onError(ANError error) {
                                    // handle error
                                }
                            });
                }

            } catch(Exception e) {
                Log.d(TAG, "doInBackground: " + e.getMessage());
            }

            return null;
        }
    }

}
