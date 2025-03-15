package com.example.dogadaji;

import com.example.enumeracije.Grad;
import com.example.model.KlubStudenata;
import com.example.threads.TombolaThread;
import com.example.threads.VukodlakThread;

import java.time.LocalDate;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class Zabava extends Dogadaj implements ZabavniDogadaj {
    private String nazivLokala;

    public Zabava(Integer id, String naziv, KlubStudenata klubOrganizator, Grad lokacijaOdrzavanja, LocalDate datumPocetka, LocalDate datumZavrsetka, String nazivLokala) {
        super(id, naziv, klubOrganizator, lokacijaOdrzavanja, datumPocetka, datumZavrsetka);
        this.nazivLokala = nazivLokala;
    }

    public String getNazivLokala() {
        return nazivLokala;
    }
    public void setNazivLokala(String nazivLokala) {
        this.nazivLokala = nazivLokala;
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
