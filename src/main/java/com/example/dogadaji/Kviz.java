package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.threads.TombolaThread;
import com.example.threads.VukodlakThread;
import com.example.utils.Database;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Kviz extends Dogadaj implements ZabavniDogadaj {
    private Integer nagradaEUR;

    public Kviz(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, Integer nagradaEUR) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.nagradaEUR = nagradaEUR;
    }

    public Integer getNagradaEUR() {
        return nagradaEUR;
    }
    public void setNagradaEUR(Integer nagradaEUR) {
        this.nagradaEUR = nagradaEUR;
    }

    @Override
    public void tombola() {
        TombolaThread thread = new TombolaThread(klubOrganizator, naziv);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);
    }

    @Override
    public void vukodlak() {
        VukodlakThread thread = new VukodlakThread(klubOrganizator, naziv);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);
    }
}
