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

public class AddActivity extends AppCompatActivity {
    private static final String TAG = AddActivity.class.getSimpleName();

    // select button
    Button btnSelectImage;
    Button btnSave;

    // preview image
    ImageView imgPreview;

    EditText inputNama;
    EditText inputHarga;

    String uriGambar = "";

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        // register the ui widget with their appropriate IDs
        btnSelectImage = findViewById(R.id.btn_select_image);
        btnSave = findViewById(R.id.btn_update);
        imgPreview = findViewById(R.id.img_preview);
        inputNama = findViewById(R.id.input_nama);
        inputHarga = findViewById(R.id.input_harga);

        // handle the choose image button to trigger
        // the image chooser function
        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nama = inputNama.getText().toString();
                String harga = inputHarga.getText().toString();
                String gambar = uriGambar;
                try {
                    int hargaInt = Integer.parseInt(harga);
                    if (!nama.equals("")) {
                        Intent i = new Intent();
                        i.putExtra(ProdukAdapter.EXTRA_ID, MainActivity.PRODUK_ADD);
                        i.putExtra(ProdukAdapter.EXTRA_NAMA, nama);
                        i.putExtra(ProdukAdapter.EXTRA_HARGA, hargaInt);
                        i.putExtra(ProdukAdapter.EXTRA_GAMBAR, gambar);
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
                    imgPreview.setImageURI(selectedImageUri);
                    uriGambar = selectedImageUri.toString();
                }
            }
        }
    }
}
