package com.dimassdoubles.sqlite;

import androidx.annotation.NonNull;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.List;

public class MainActivity extends ListActivity {
    String dataSiswa[] = null;
    String dS[] = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);

        // Tambah Siswa
        Log.d("Mainctivity", "Mencoba buat database");
        DatabaseHandler db = new DatabaseHandler(this);
        Log.d("MainActivity", "Database berhasil dibuat");

        // Membaca semua siswa
        Log.d("Baca siswa: ", "Membaca Semua Data Siswa...");
        List<Siswa> siswa = db.GetSemuaSiswa();
        dataSiswa = new String[siswa.size()];
        dS = new String[siswa.size()];
        int i = 0;
        for (Siswa s : siswa) {
            String log = "NIS: " + s.getNis() + ", Nama: " + s.getNama();
            Log.d("Name ", log);
//            Log.d("Main Activity", "Sampai Di Sini Bro");
            dataSiswa[i] = s.getNis() + " - " + s.getNama();
//            Log.d("Main Activity", "Sebentar lagi ketemu masalahnya");
            dS[i] = s.getNis();
            i++;
//            Log.d("Main Activity", "Sampai Di sini");
        }
        if (i == 0) {
            Log.d("Tambah Siswa: ", "Menambah Data Siswa...");
            db.AddSiswa(new Siswa("001", "Dimas Saputro"));
            db.AddSiswa(new Siswa("002", "Zaidan Rahadian Huda"));
            db.AddSiswa(new Siswa("003", "Fajar Utama"));
            db.AddSiswa(new Siswa("004", "David Kurniawan"));
        }
        Log.d("MainActivity", "Sampai Di Sini Akhirnya.");
        setListAdapter(new ArrayAdapter<Object>(this, android.R.layout.simple_list_item_1, dataSiswa));
        registerForContextMenu(getListView());
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Action");
        menu.add(0, 0, 0, "Tambah");
        menu.add(0, 1, 1, "Hapus");
        menu.add(0, 2, 2, "Update");
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        try {
            switch (item.getItemId()) {
                case 0: {
                    Class c = Class.forName("com.dimassdoubles.sqlite.TambahActivity");
                    Intent i = new Intent(MainActivity.this, c);
                    startActivity(i);
                    break;
                }
                case 1: {
                    DatabaseHandler db = new DatabaseHandler(this);
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    item.getMenuInfo();
                    String[] args = {String.valueOf(info.id)};
                    int xpos = Integer.parseInt(args[0]);
                    db.DeleteRow(dS[xpos]);
                    Class c = Class.forName("com.dimassdoubles.sqlite.MainActivity");
                    Intent i = new Intent(MainActivity.this, c);
                    startActivity(i);
                    break;
                }
                case 2: {
                    DatabaseHandler db = new DatabaseHandler(this);
                    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                    String[] args = {String.valueOf(info.id)};
                    Log.d("args0 : ", args[0]);
                    int xpos = Integer.parseInt(args[0]);
                    db.GetSiswa(dS[xpos]);
                    String namax = db.GetSiswa(dS[xpos]).getNama();
                    Log.d("Menu Update", "Sampai di sini");
                    Intent i = new Intent(MainActivity.this, UpdateActivity.class);
                    Bundle bun = new Bundle();
                    Log.d("Menu Update", "Sampai di sini 2");
                    bun.putString("nis", dS[xpos]);
                    Log.d("Menu Update", "Sampai di sini 3");
                    bun.putString("nama", namax);
                    Log.d("Menu Update", "Sampai di sini 4");
                    i.putExtras(bun);
                    startActivity(i);
                    finish();
                    Log.d("Menu Update", "Sampai di sini 5");
                    break;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }
}
