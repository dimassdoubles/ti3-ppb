package com.dimassdoubles.gudangtoko;

public class BarangDagang {
    private String KodeBarang;
    private String NamaBarang;
    private int HargaBeli;
    private int HargaJual;
    private int Stok;

    public BarangDagang(String kodeBarang, String namaBarang, int hargaBeli, int hargaJual, int stok) {
        KodeBarang = kodeBarang;
        NamaBarang = namaBarang;
        HargaBeli = hargaBeli;
        HargaJual = hargaJual;
        Stok = stok;
    }

    public String getKodeBarang() {
        return KodeBarang;
    }

    public void setKodeBarang(String kodeBarang) {
        KodeBarang = kodeBarang;
    }

    public String getNamaBarang() {
        return NamaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        NamaBarang = namaBarang;
    }

    public int getHargaBeli() {
        return HargaBeli;
    }

    public void setHargaBeli(int hargaBeli) {
        HargaBeli = hargaBeli;
    }

    public int getHargaJual() {
        return HargaJual;
    }

    public void setHargaJual(int hargaJual) {
        HargaJual = hargaJual;
    }

    public int getStok() {
        return Stok;
    }

    public void setStok(int stok) {
        Stok = stok;
    }
}
