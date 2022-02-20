package com.android.dimassdoubles.dashboardadmin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Locale;

public class ProdukAdapter extends RecyclerView.Adapter<ProdukAdapter.ProdukViewHolder> {

    private static final String TAG = ProdukAdapter.class.getSimpleName();

    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_NAMA = "NAMA";
    public static final String EXTRA_HARGA = "HARGA";
    public static final String EXTRA_GAMBAR = "GAMBAR";

    private final LayoutInflater mInflater;
    ProdukOpenHelper mDB;
    Context mContext;

    public ProdukAdapter(Context context, ProdukOpenHelper db) {
        this.mInflater = LayoutInflater.from(context);
        this.mDB = db;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ProdukAdapter.ProdukViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.produk_item, parent, false);
        return new ProdukViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdukAdapter.ProdukViewHolder holder, int position) {
        ProdukItem current = mDB.query(position);
        int currentId = current.getId();
        String currentNama = current.getNamaProduk();
        int currentHarga = current.getHargaProduk();
        String currentGambar = current.getGambarProduk();
        holder.tvNama.setText(currentNama);
        holder.tvHarga.setText("Rp " + String.format(Locale.GERMAN, "%,d", currentHarga));
        if (!currentGambar.equals("")) {
            try {
                holder.imgProduk.setImageURI(Uri.parse(currentGambar));
            } catch (Exception e) {
                Log.d(TAG, "SET IMAGE EXCEPTION!" + e.getMessage());
            }
        }

        // keep a reference to the view holder for the click listener
        final ProdukViewHolder h = holder;
        // attach a click listener to the cardview
        holder.tvNama.setOnClickListener(new MyButtonOnClickListener(currentId, currentNama, currentHarga, currentGambar) {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "OnClick VHPos " + h.getAdapterPosition());
                Intent intent = new Intent(mContext, EditActivity.class);
                intent.putExtra("POSITION", h.getAdapterPosition());
                intent.putExtra(EXTRA_ID, id);
                intent.putExtra(EXTRA_NAMA, nama);
                intent.putExtra(EXTRA_HARGA, harga);
                intent.putExtra(EXTRA_GAMBAR, gambar);
                ((Activity) mContext).startActivityForResult(intent, MainActivity.PRODUK_EDIT);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) mDB.count();
    }

    public static class ProdukViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvNama;
        public final TextView tvHarga;
        public final ImageView imgProduk;
        public ProdukViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = (TextView) itemView.findViewById(R.id.tvNama);
            tvHarga = (TextView) itemView.findViewById(R.id.tvHarga);
            imgProduk = (ImageView) itemView.findViewById(R.id.imgProduk);
        }
    }
}
