package com.example.model;

import java.io.Serializable;

public abstract class Entitet implements Serializable {
    protected Integer id;
    protected String naziv;

    public Entitet(Integer id, String naziv) {
        this.id = id;
        this.naziv = naziv;
    }

    public String getNaziv() {
        return naziv;
    }
    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
}
