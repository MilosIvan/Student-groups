package com.example.threads;

import com.example.ApplicationProject;
import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.utils.Database;

import java.util.*;

public class TombolaThread implements Runnable {
    private final KlubStudenata klubOrganizator;
    private final String nazivDogadaja;

    public TombolaThread(KlubStudenata klubOrganizator, String nazivDogadaja) {
        this.klubOrganizator = klubOrganizator;
        this.nazivDogadaja = nazivDogadaja;
    }

    @Override
    public void run() {
        Random random = new Random();
        Map<Student, Set<Integer>> mapa = new HashMap<>();

        List<Student> studenti = Database.getStudentiIzKluba(klubOrganizator.getId());
        for(Student student : studenti) {
            mapa.put(student, new HashSet<>());
            while (mapa.get(student).size() < 5) {
                mapa.get(student).add(random.nextInt(100) + 1);
            }
        }

        System.out.println("Započela je igra tombole na događaju: " + nazivDogadaja +
                "\nOrganizator: " + klubOrganizator.getNaziv());

        List<Integer> kutijaBrojeva = new ArrayList<>();
        for(int i = 1; i <= 100; i++) kutijaBrojeva.add(i);
        boolean igraGotova = false;
        int broj;
        Optional<Student> pobjednikOptional = Optional.empty();
        while(!igraGotova) {
            broj = kutijaBrojeva.get(random.nextInt(kutijaBrojeva.size()));
            System.out.println("Generiran je broj " + broj);
            for(Student student : mapa.keySet()) {
                if (mapa.get(student).remove(broj)) {
                    System.out.println("Pogodak student: " + student.getNaziv()
                            + ". Ostalo još: " + mapa.get(student).size());
                    if(mapa.get(student).isEmpty()) {
                        igraGotova = true;
                        pobjednikOptional = Optional.of(student);
                    }
                }
            }
            kutijaBrojeva.remove((Integer) broj);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                ApplicationProject.logger.error(e.getMessage());
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Igra gotova. Pobjednik je student " + pobjednikOptional.get().getNaziv());

        ApplicationProject.logger.info("Odigrana tombola na događaju: " + nazivDogadaja +
                ". Organizator: " + klubOrganizator.getNaziv() + ". Pobjednik: " + pobjednikOptional.get().getNaziv());
    }
}
