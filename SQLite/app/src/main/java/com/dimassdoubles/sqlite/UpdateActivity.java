package com.dimassdoubles.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {
    private String xnis, xnama;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Log.d("UpdateActivity", "Sampai di sini");
        final DatabaseHandler db = new DatabaseHandler(this);
        final EditText editNis = (EditText) findViewById(R.id.editNis);
        final EditText editNama = (EditText) findViewById(R.id.editNama);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnBatal = (Button) findViewById(R.id.btnBatal);

        // ambil data siswa dari extras
        Log.d("UpdateActivity", "Sampai di sini 2");
        Bundle bun = this.getIntent().getExtras();
        xnis = bun.getString("nis");
        xnama = bun.getString("nama");
        Log.d("UpdateActivity", "Sampai di sini 3");

        // masukan data-data siswa tersebut ke field editor
        editNama.setText(xnama);
        editNis.setText(xnis);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nis = editNis.getText().toString();
                String nama = editNama.getText().toString();

                db.UpdateMethod(nis, nama);
                editNis.setText("");
                editNama.setText("");
                try {
                    Class c = Class.forName("com.dimassdoubles.sqlite.MainActivity");
                    Intent i = new Intent(UpdateActivity.this, c);
                    startActivity(i);
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generateed method stub
                try {
                    Class c = Class.forName("com.dimassdoubles.sqlite.MainActivity");
                    Intent i = new Intent(UpdateActivity.this, c);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
