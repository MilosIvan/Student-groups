package com.example.enumeracije;

public enum Spol {
    MUSKO(1, "M"),
    ZENSKO(2, "Ž");

    final int redniBroj;
    final String oznaka;

    Spol(int redniBroj, String oznaka) {
        this.redniBroj = redniBroj;
        this.oznaka = oznaka;
    }



    public int getRedniBroj() {
        return redniBroj;
    }
    public String getOznaka() {
        return oznaka;
    }

    @Override
    public String toString() {
        return oznaka;
    }
}
