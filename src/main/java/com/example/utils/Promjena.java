package com.example.utils;

import com.example.KorisnickiPodaci;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Promjena implements Serializable {
    private String entitet;
    private Integer id;
    private String staraVrijednost;
    private String novaVrijednost;
    private KorisnickiPodaci korisnickiPodaci;
    private LocalDateTime datumIVrijeme;

    public Promjena(String entitet, Integer id, String staraVrijednost, String novaVrijednost, KorisnickiPodaci korisnickiPodaci, LocalDateTime datumIVrijeme) {
        this.entitet = entitet;
        this.id = id;
        this.staraVrijednost = staraVrijednost;
        this.novaVrijednost = novaVrijednost;
        this.korisnickiPodaci = korisnickiPodaci;
        this.datumIVrijeme = datumIVrijeme;
    }

    public Promjena(String entitet, Integer id, KorisnickiPodaci korisnickiPodaci, LocalDateTime datumIVrijeme) {
        this.entitet = entitet;
        this.id = id;
        this.korisnickiPodaci = korisnickiPodaci;
        this.datumIVrijeme = datumIVrijeme;
        this.staraVrijednost = "";
        this.novaVrijednost = "";
    }

    public KorisnickiPodaci getKorisnickiPodaci() {
        return korisnickiPodaci;
    }

    public void setKorisnickiPodaci(KorisnickiPodaci korisnickiPodaci) {
        this.korisnickiPodaci = korisnickiPodaci;
    }

    public LocalDateTime getDatumIVrijeme() {
        return datumIVrijeme;
    }

    public void setDatumIVrijeme(LocalDateTime datumIVrijeme) {
        this.datumIVrijeme = datumIVrijeme;
    }

    public String getStaraVrijednost() {
        return staraVrijednost;
    }

    public void setStaraVrijednost(String staraVrijednost) {
        this.staraVrijednost = staraVrijednost;
    }

    public String getNovaVrijednost() {
        return novaVrijednost;
    }

    public void setNovaVrijednost(String novaVrijednost) {
        this.novaVrijednost = novaVrijednost;
    }

    public String getEntitet() {
        return entitet;
    }

    public void setEntitet(String entitet) {
        this.entitet = entitet;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        String str;
        if(!staraVrijednost.isEmpty() && !novaVrijednost.isEmpty()) {
            str = entitet
                    + "\nStara vrijednost: " + staraVrijednost
                    + "\nNova vrijednost: " + novaVrijednost
                    + "\nKorisnik: " + korisnickiPodaci.usernameInput()
                    + "\nDatum: " + datumIVrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm"));
        }
        else {
            str = entitet
                    + "\nID = " + id
                    + "\nKorisnik: " + korisnickiPodaci.usernameInput()
                    + "\nDatum: " + datumIVrijeme.format(DateTimeFormatter.ofPattern("dd.MM.yyyy. hh:mm"));
        }
        return str;
    }
}
