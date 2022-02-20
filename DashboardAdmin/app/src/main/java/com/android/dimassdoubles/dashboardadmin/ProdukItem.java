package com.android.dimassdoubles.dashboardadmin;

public class ProdukItem {
    int id;
    String namaProduk;
    int hargaProduk;
    String gambarProduk;

    ProdukItem() {
    }

    ;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public int getHargaProduk() {
        return hargaProduk;
    }

    public void setHargaProduk(int hargaProduk) {
        this.hargaProduk = hargaProduk;
    }

    public String getGambarProduk() { return gambarProduk; }

    public void setGambarProduk(String gambarProduk) { this.gambarProduk = gambarProduk; }
}
