package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;
import com.example.threads.TombolaThread;
import com.example.threads.VukodlakThread;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Putovanje extends Dogadaj implements ZabavniDogadaj {
    private String povodPutovanja;

    public Putovanje(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, String povodPutovanja) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.povodPutovanja = povodPutovanja;
    }

    public String getPovodPutovanja() {
        return povodPutovanja;
    }
    public void setPovodPutovanja(String povodPutovanja) {
        this.povodPutovanja = povodPutovanja;
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
