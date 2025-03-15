package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;

import java.time.LocalDate;

public final class Natjecanje extends Dogadaj implements PoslovniDogadaj {
    private String temaNatjecanja;
    private Integer nagradaEUR;

    public Natjecanje(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, String temaNatjecanja, Integer nagradaEUR) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.temaNatjecanja = temaNatjecanja;
        this.nagradaEUR = nagradaEUR;
    }

    public String getTemaNatjecanja() {
        return temaNatjecanja;
    }
    public void setTemaNatjecanja(String temaNatjecanja) {
        this.temaNatjecanja = temaNatjecanja;
    }
    public Integer getNagradaEUR() {
        return nagradaEUR;
    }
    public void setNagradaEUR(Integer nagradaEUR) {
        this.nagradaEUR = nagradaEUR;
    }
}
