package com.android.dimassdoubles.dashboardadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    private static final String TAG = EditActivity.class.getSimpleName();

    public static int POSITION = -99;

    Button btnUpdate;
    Button btnDelete;
    Button btnSelectImage;

    EditText inputNama;
    EditText inputHarga;

    ImageView imgProduk;

    int position = POSITION;
    int id;
    String nama;
    int harga;
    String gambar;

    String uriGambar = "";

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        btnUpdate = findViewById(R.id.btn_update);
        btnDelete = findViewById(R.id.btn_hapus);
        btnSelectImage = findViewById(R.id.btn_select_image);

        inputNama = findViewById(R.id.input_nama);
        inputHarga = findViewById(R.id.input_harga);

        imgProduk = findViewById(R.id.imgProduk);

        int position = getIntent().getIntExtra("POSITION", -99);
        Log.d(TAG, "Hai");
        int id = getIntent().getIntExtra(ProdukAdapter.EXTRA_ID, -99);
        Log.d(TAG, "Hai2");
        String nama = getIntent().getStringExtra(ProdukAdapter.EXTRA_NAMA);
        Log.d(TAG, "Hai3");
        int harga = getIntent().getIntExtra(ProdukAdapter.EXTRA_HARGA, -99);
        Log.d(TAG, "Hai4");
        String gambar= getIntent().getStringExtra(ProdukAdapter.EXTRA_GAMBAR);
        Log.d(TAG, uriGambar);

        inputNama.setText(nama);
        Log.d(TAG, "Hai6");
        inputHarga.setText(String.valueOf(harga));
        Log.d(TAG, "Hai7");
        try {
            imgProduk.setImageURI(Uri.parse(gambar));
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }
        Log.d(TAG, "Hai8");

        Log.d(TAG, "halo");

        // handle the choose image button to trigger
        // the image chooser function
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.putExtra("POSITION", position);
                i.putExtra(ProdukAdapter.EXTRA_ID, id);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = inputNama.getText().toString();
                String harga = inputHarga.getText().toString();
                try {
                    int hargaInt = Integer.parseInt(harga);
                    if (!nama.equals("")) {
                        Intent i = new Intent();
                        i.putExtra(ProdukAdapter.EXTRA_ID, id);
                        i.putExtra(ProdukAdapter.EXTRA_NAMA, nama);
                        i.putExtra(ProdukAdapter.EXTRA_HARGA, hargaInt);
                        i.putExtra(ProdukAdapter.EXTRA_GAMBAR, uriGambar);
                        setResult(RESULT_OK, i);
                        finish();
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                    Toast.makeText(getApplicationContext(), "Ada kesalah input harga!", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    // thi function is triggered when
    // the select image button is clicked
    void imageChooser() {
        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestcode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            // compare the resultcode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // get the url of the image from data
                Uri selectedImageUri = data.getData();
                if(selectedImageUri != null) {
                    // update the preview image in the layout
                    imgProduk.setImageURI(selectedImageUri);
                    uriGambar = selectedImageUri.toString();
                }
            }
        }
    }
}
