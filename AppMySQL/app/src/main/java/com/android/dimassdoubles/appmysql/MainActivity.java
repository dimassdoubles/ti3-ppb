package com.android.dimassdoubles.appmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;

public class MainActivity extends ListActivity {
    private static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        int idkota;
        String[] kota = null;

        @Override
        protected Void doInBackground(Void... voids) {
            String hasil = null;
            InputStream is = null;

            // httppost
            try {
                // Create a neat value object to hold the URL
                URL url = new URL("http://192.168.43.160/api/tampil.php");

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
                    System.out.println(hasil);
                    Log.d(TAG, hasil);
                    is.close();
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                }

            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }

            // parse json
            JSONArray jArr;
            try {
                jArr = new JSONArray(hasil);
                kota = new String[jArr.length()];
                for (int i = 0; i < jArr.length(); i++) {
                    JSONObject json_data = jArr.getJSONObject(i);
                    idkota = json_data.getInt("id");
                    kota[i] = json_data.getString("kota");
                    Log.d(TAG, kota[i]);
                }
            } catch (JSONException e) {
                Log.d(TAG, e.getMessage());
                Toast.makeText(getBaseContext(), "No Id Found", Toast.LENGTH_SHORT).show();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            // tampilkan
            setListAdapter(new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, kota));
            ListView lv;
            lv = getListView();
            lv.setTextFilterEnabled(true);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getApplicationContext(), kota[position]+" was clicked", Toast.LENGTH_SHORT).show();

                }
            });
            super.onPostExecute(unused);
        }
    }


}
