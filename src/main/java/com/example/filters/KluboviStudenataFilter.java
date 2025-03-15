package com.example.filters;

import com.example.enumeracije.Fakultet;

public class KluboviStudenataFilter {
    private String nazivKluba;
    private Fakultet fakultet;

    public KluboviStudenataFilter(String nazivKluba, Fakultet fakultet) {
        this.nazivKluba = nazivKluba;
        this.fakultet = fakultet;
    }

    public String getNazivKluba() {
        return nazivKluba;
    }

    public Fakultet getFakultet() {
        return fakultet;
    }
}
