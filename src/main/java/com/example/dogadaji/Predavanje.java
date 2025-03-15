package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;

import java.time.LocalDate;

public non-sealed class Predavanje extends Dogadaj implements PoslovniDogadaj {
    private String temaPredavanja;

    public Predavanje(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, String temaPredavanja) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.temaPredavanja = temaPredavanja;
    }

    public String getTemaPredavanja() {
        return temaPredavanja;
    }
    public void setTemaPredavanja(String temaPredavanja) {
        this.temaPredavanja = temaPredavanja;
    }
}
