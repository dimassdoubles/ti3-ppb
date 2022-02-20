package com.dimassdoubles.gudangtoko;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    public static final int REQUEST_EDIT = 1;
    public static int REQUEST_ADD = -1;

    private ArrayList<BarangDagang> listBarang;
    private RecyclerView mRecyclerView;
    private BarangAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new ReadTask().execute();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddBarangActivity.class);
                startActivityForResult(intent, REQUEST_EDIT);
            }
        });

    }

    private class ReadTask extends AsyncTask<Void, Void, Void> {
        ArrayList<BarangDagang> listBarang;

        @Override
        protected Void doInBackground(Void... voids) {
            String hasil = null;
            InputStream is = null;

            // httppost
            try {
                // create a neat value objet to hold the URL
                URL url = new URL("http://192.168.43.160/tabelbarang/api/read.php");

                // open a connection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                // set the request method
                connection.setRequestMethod("POST");

                // set the request content-type header parameter
                connection.setRequestProperty("Content-Type", "application/json; utf-8");

                // set response format type
                connection.setRequestProperty("Accept", "application/json");

                // ensure the connection will be used to send content
                connection.setDoOutput(true);

                // read the response from input stream
                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "iso-8859-1"), 8);
                    StringBuilder response = new StringBuilder();
                    String responseLine = "0";
                    while ((responseLine = reader.readLine()) != null) {
                        response.append(responseLine + "\n");
                    }
                    hasil = response.toString();
                    Log.d(TAG, hasil);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }


            } catch(Exception e) {
                Log.d(TAG, e.getMessage());
            }

            // parse json
            JSONArray jArr;
            BarangDagang barangDagang;
            String kodeBarang = null;
            String namaBarang = null;
            int hargaBeli = 0;
            int hargaJual = 0;
            int stok = 0;

            try {
                jArr = new JSONArray(hasil);
                listBarang = new ArrayList<BarangDagang>();
                for (int i=0; i<jArr.length(); i++) {
                    JSONObject json_data = jArr.getJSONObject(i);
                    kodeBarang = json_data.getString("KdBrg");
                    namaBarang = json_data.getString("NmBrg");
                    hargaBeli = json_data.getInt("HrgBeli");
                    hargaJual = json_data.getInt("HrgJual");
                    stok = json_data.getInt("Stok");

                    Log.d(TAG, "doInBackground: " + namaBarang);

                    listBarang.add(new BarangDagang(kodeBarang, namaBarang, hargaBeli, hargaJual, stok));
                }

            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(getBaseContext(), "Data Tidak Ditemukan", Toast.LENGTH_SHORT).show();
            }




            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            Log.d(TAG, "onPostExecute: " + listBarang.get(0).getNamaBarang());
            Log.d(TAG, "onPostExecute: " + listBarang.get(1).getNamaBarang());

             // Create recycler view
            mRecyclerView = findViewById(R.id.recyclerView);

            // create adapter and supply data to be dissplayed
            mAdapter = new BarangAdapter(MainActivity.this, listBarang );

            // connect Adapter with RecyclerView
            mRecyclerView.setAdapter(mAdapter);

            // give RecyclerView a default layout manager
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        }
    }
    

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_EDIT) {
            if (resultCode == RESULT_OK) {
                    // mendapatkan extra
                    int requestAdd = data.getIntExtra(AddBarangActivity.EXTRA_REQUEST, 0);

                if (REQUEST_ADD == requestAdd) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else if (DetailActivity.DATA_UPDATED == 1) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                } else if (DetailActivity.DATA_REMOVED == 1) {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();

                }
            }
        }
    }

}
