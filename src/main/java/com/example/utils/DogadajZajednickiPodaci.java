package com.example.utils;

import com.example.enumeracije.Grad;
import com.example.model.Entitet;
import com.example.model.KlubStudenata;

import java.time.LocalDate;

public class DogadajZajednickiPodaci extends Entitet {
    private KlubStudenata klubOrganizator;
    private Grad lokacijaOdrzavanja;
    private LocalDate datumPocetka;
    private LocalDate datumZavrsetka;

    public DogadajZajednickiPodaci(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka) {
        super(id, naziv);
        this.klubOrganizator = klubOrganizator;
        this.lokacijaOdrzavanja = lokacijaOdrzavanja;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
    }

    public KlubStudenata getKlubOrganizator() {
        return klubOrganizator;
    }

    public Grad getLokacijaOdrzavanja() {
        return lokacijaOdrzavanja;
    }

    public LocalDate getDatumPocetka() {
        return datumPocetka;
    }

    public LocalDate getDatumZavrsetka() {
        return datumZavrsetka;
    }
}
