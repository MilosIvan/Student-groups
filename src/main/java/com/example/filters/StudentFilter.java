package com.example.filters;

import com.example.enumeracije.Fakultet;

public class StudentFilter {
    String ime;
    Fakultet fakultet;
    String godinaStudijaString;

    public StudentFilter(String ime, Fakultet fakultet, String godinaStudijaString) {
        this.ime = ime;
        this.fakultet = fakultet;
        this.godinaStudijaString = godinaStudijaString;
    }

    public String getIme() {
        return ime;
    }

    public Fakultet getFakultet() {
        return fakultet;
    }

    public String getGodinaStudijaString() {
        return godinaStudijaString;
    }
}
