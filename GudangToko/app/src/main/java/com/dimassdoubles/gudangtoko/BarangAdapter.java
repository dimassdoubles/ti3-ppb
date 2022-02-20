package com.dimassdoubles.gudangtoko;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Locale;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.BarangViewHolder> {
    private static final String TAG = BarangAdapter.class.getSimpleName();

    public static final String EXTRA_KODE = "KODEBARANG";
    public static final String EXTRA_NAMABARANG = "BARANGDAGANG";
    public static final String EXTRA_HARGABELI = "HARGABELI";
    public static final String EXTRA_HARGAJUAL = "HARGAJUAL";
    public static final String EXTRA_STOK = "STOK";

    private final LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<BarangDagang> listBarang;

    TextView tvNamaBarang;
    TextView tvHargaJual;
    TextView tvStok;

//    NumberFormat numberFormatter;

    public BarangAdapter(Context mContext, ArrayList<BarangDagang> listBarang) {
        this.mInflater = LayoutInflater.from(mContext);
        this.listBarang = listBarang;
        this.mContext = mContext;
    }

    // custom view holder
    class BarangViewHolder extends RecyclerView.ViewHolder {

         CardView cvBarang;

        public BarangViewHolder(@NonNull View itemView) {
            super(itemView);
            cvBarang = (CardView) itemView.findViewById(R.id.cardView);
            tvNamaBarang = (TextView) itemView.findViewById(R.id.tvNamaBarang);
            tvHargaJual = (TextView) itemView.findViewById(R.id.tvHargaJual);
            tvStok = (TextView) itemView.findViewById(R.id.tvStok);
        }
    }

    @NonNull
    @Override
    public BarangAdapter.BarangViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_view, parent, false);
        return new BarangViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BarangAdapter.BarangViewHolder holder, int position) {
        BarangDagang current = listBarang.get(position);
        String currentKode = current.getKodeBarang();
        String currentNama = current.getNamaBarang();
        int currentHrgJual = current.getHargaJual();
        int currentStok = current.getStok();

        tvNamaBarang.setText(currentNama);
        tvHargaJual.setText("Rp " + String.format(Locale.GERMAN,"%,d",currentHrgJual));
        tvStok.setText("Stok : " + String.valueOf(currentStok));
        if (currentStok <= 20) {
            tvStok.setTextColor(ContextCompat.getColor(mContext, R.color.merah));
        }

        // keep a reference to the view holder for the click listener
        final BarangViewHolder h = holder;
        // attach a click listener to the CardView
        holder.cvBarang.setOnClickListener(new BarangOnClick(current) {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: " + "VHPOS " + h.getAdapterPosition());
                Intent intent = new Intent(mContext, DetailActivity.class);

                intent.putExtra(EXTRA_KODE, mKodeBarang);
                intent.putExtra(EXTRA_NAMABARANG, mNamaBarang);
                intent.putExtra(EXTRA_HARGABELI, mHargaBeli);
                intent.putExtra(EXTRA_HARGAJUAL, mHargaJual);
                intent.putExtra(EXTRA_STOK, mStok);

                // start an empty edit activity
                ((Activity) mContext).startActivityForResult(intent, MainActivity.REQUEST_EDIT);

            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) listBarang.size();
    }
}
