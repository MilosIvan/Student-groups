package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;

import java.time.LocalDate;

public final class Projekt extends Dogadaj implements PoslovniDogadaj {
    private String temaProjekta;

    public Projekt(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, String temaProjekta) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.temaProjekta = temaProjekta;
    }

    public String getTemaProjekta() {
        return temaProjekta;
    }
    public void setTemaProjekta(String temaProjekta) {
        this.temaProjekta = temaProjekta;
    }
}
