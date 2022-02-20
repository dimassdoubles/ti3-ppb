package com.dimassdoubles.gudangtoko;

import android.view.View;

public class BarangOnClick implements View.OnClickListener{
    private static final String TAG = View.OnClickListener.class.getSimpleName();

    String mKodeBarang;
    String mNamaBarang;
    int mHargaBeli;
    int mHargaJual;
    int mStok;

    public BarangOnClick(BarangDagang barangDagang) {
        this.mKodeBarang = barangDagang.getKodeBarang();
        this.mNamaBarang = barangDagang.getNamaBarang();
        this.mHargaBeli = barangDagang.getHargaBeli();
        this.mHargaJual = barangDagang.getHargaJual();
        this.mStok = barangDagang.getStok();
    }

    @Override
    public void onClick(View v) {
        // Implemented in BarangAdapter
    }
}
