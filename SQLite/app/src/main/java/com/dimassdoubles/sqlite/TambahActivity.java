package com.dimassdoubles.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class TambahActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        final DatabaseHandler db = new DatabaseHandler(this);
        final EditText editNis = (EditText) findViewById(R.id.editNis);
        final EditText editNama = (EditText) findViewById(R.id.editNama);
        Button btnTambah = (Button) findViewById(R.id.btnTambah);
        Button btnBatal = (Button) findViewById(R.id.btnBatal);

        btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nis = editNis.getText().toString();
                String nama = editNama.getText().toString();
                db.AddSiswa(new Siswa(nis, nama));
                editNis.setText("");
                editNama.setText("");
                try {
                    Class c = Class.forName("com.dimassdoubles.sqlite.MainActivity");
                    Intent i = new Intent(TambahActivity.this, c);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        btnBatal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO auto-generated method stub
                try {
                    Class c = Class.forName("com.dimassdoubles.sqlite.MainActivity");
                    Intent i = new Intent(TambahActivity.this, c);
                    startActivity(i);
                } catch (ClassNotFoundException e) {
                    // TODO auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
    }
}
