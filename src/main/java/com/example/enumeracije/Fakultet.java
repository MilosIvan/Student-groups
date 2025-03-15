package com.example.enumeracije;

public enum Fakultet {
    FER(1, "FER"),
    TVZ(2, "TVZ"),
    FOI(3, "FOI"),
    LIBERTAS(4, "LIBERTAS"),
    ETF(5, "ETF"),
    FESB(6, "FESB");

    final int redniBroj;
    final String naziv;

    Fakultet(int redniBroj, String naziv) {
        this.redniBroj = redniBroj;
        this.naziv = naziv;
    }

    public int getRedniBroj() {
        return redniBroj;
    }

    @Override
    public String toString() {
        return naziv;
    }
}