package com.example.enumeracije;

public enum Grad {
    ZAGREB(10360, 1, "Zagreb"),
    SPLIT(21000, 2, "Split"),
    VARAZDIN(42000, 3, "Varaždin"),
    RIJEKA(51000, 4, "Rijeka"),
    OSIJEK(48000, 5, "Osijek");

    final Integer postanskiBroj, redniBr;
    final String naziv;

    Grad(Integer postanskiBroj, Integer redniBr, String naziv) {
        this.postanskiBroj = postanskiBroj;
        this.redniBr = redniBr;
        this.naziv = naziv;
    }

    public Integer getPostanskiBroj() {
        return postanskiBroj;
    }

    public Integer getRedniBr() {
        return redniBr;
    }

    public String getNaziv() {
        return naziv;
    }

    @Override
    public String toString() {
        return naziv;
    }
}
