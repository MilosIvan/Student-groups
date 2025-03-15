package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.Entitet;
import com.example.model.KlubStudenata;

import java.time.LocalDate;

public abstract class Dogadaj extends Entitet {
    protected KlubStudenata klubOrganizator;
    protected Grad lokacijaOdrzavanja;
    protected LocalDate datumPocetka;
    protected LocalDate datumZavrsetka;

    public Dogadaj(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka) {
        super(id, naziv);
        this.klubOrganizator = klubOrganizator;
        this.lokacijaOdrzavanja = lokacijaOdrzavanja;
        this.datumPocetka = datumPocetka;
        this.datumZavrsetka = datumZavrsetka;
    }

    public KlubStudenata getKlubOrganizator() {
        return klubOrganizator;
    }
    public void setKlubOrganizator(KlubStudenata klubOrganizator) {
        this.klubOrganizator = klubOrganizator;
    }
    public Grad getLokacijaOdrzavanja() {
        return lokacijaOdrzavanja;
    }
    public void setLokacijaOdrzavanja(Grad lokacijaOdrzavanja) {
        this.lokacijaOdrzavanja = lokacijaOdrzavanja;
    }
    public LocalDate getDatumPocetka() {
        return datumPocetka;
    }
    public void setDatumPocetka(LocalDate datumPocetka) {
        this.datumPocetka = datumPocetka;
    }
    public LocalDate getDatumZavrsetka() {
        return datumZavrsetka;
    }
    public void setDatumZavrsetka(LocalDate datumZavrsetka) {
        this.datumZavrsetka = datumZavrsetka;
    }
}
