package com.example.threads;

import com.example.ApplicationProject;
import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.utils.Database;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class VukodlakThread implements Runnable {
    private final KlubStudenata klubOrganizator;
    private final String nazivDogadaja;

    public VukodlakThread(KlubStudenata klubOrganizator, String nazivDogadaja) {
        this.klubOrganizator = klubOrganizator;
        this.nazivDogadaja = nazivDogadaja;
    }

    public void spavaj(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        Random random = new Random();
        List<Student> igraci = Database.getStudentiIzKluba(klubOrganizator.getId());
        List<Student> vukodlaci = new ArrayList<>();
        List<Student> selo = new ArrayList<>();

        System.out.println("Započela je igra vukodlaka na događaju: " + nazivDogadaja +
                "\nOrganizator: " + klubOrganizator.getNaziv());

        int brojVukodlaka = random.nextInt(2) + 3;   //slučajan broj 3 ili 4
        while (vukodlaci.size() < brojVukodlaka){
            Student vuk = igraci.get(random.nextInt(igraci.size()));
            if(!vukodlaci.contains(vuk)) vukodlaci.add(vuk);
        }
        for(Student student : igraci) {
            if(!vukodlaci.contains(student)) selo.add(student);
        }
        System.out.print("Vukodlaci su studenti: ");
        vukodlaci.forEach(s -> System.out.print(s.getNaziv() + " "));
        spavaj(1000);

        Student doktor = selo.get(random.nextInt(selo.size()));
        System.out.println("\nDoktor je student " + doktor.getNaziv());
        spavaj(1000);
        Student vidovnjak = selo.get(random.nextInt(selo.size()));
        while (doktor.equals(vidovnjak)) {
            vidovnjak = selo.get(random.nextInt(selo.size()));
        }
        System.out.println("Vidovnjak je student " + vidovnjak.getNaziv());
        spavaj(5000);


        System.out.println("\n\nIgra počinje!");
        Optional<Student> zasticeniIgrac = Optional.empty();
        Optional<Student> pogledaniIgrac = Optional.empty();
        Optional<Student> napadnutiIgrac = Optional.empty();
        while(true) {
            System.out.println("\nNoć je...");
            spavaj(1000);
            if(igraci.contains(doktor)) {
                zasticeniIgrac = Optional.of(igraci.get(random.nextInt(igraci.size())));
                System.out.println("Doktor je zaštitio igrača " + zasticeniIgrac.get().getNaziv());
            }
            if(igraci.contains(vidovnjak)) {
                pogledaniIgrac = Optional.of(igraci.get(random.nextInt(igraci.size())));
                System.out.println("Vidovnjak je pogledao igrača " + pogledaniIgrac.get().getNaziv());
            }
            napadnutiIgrac = Optional.of(selo.get(random.nextInt(selo.size())));
            spavaj(2000);

            System.out.println("\nDan je!");
            spavaj(1000);
            if(igraci.contains(doktor) && zasticeniIgrac.get().equals(napadnutiIgrac.get())) {
                System.out.println("Nitko nije ubijen.");
            }
            else {
                System.out.println("Vukovi su ubili igrača: " + napadnutiIgrac.get().getNaziv());
                selo.remove(napadnutiIgrac.get());
                igraci.remove(napadnutiIgrac.get());
            }

            System.out.println("Počinje rasprava");
            spavaj(2000);

            if(vukodlaci.contains(pogledaniIgrac.get())) {
                System.out.println("Vidovnjak je vidio da je " + pogledaniIgrac.get().getNaziv() +
                        " vukodlak. Selo ga je izbacilo iz igre.");
                vukodlaci.remove(pogledaniIgrac.get());
                igraci.remove(pogledaniIgrac.get());
            }
            else {
                Student izbaceniIgrac = igraci.get(random.nextInt(igraci.size()));
                System.out.println("Selo je izbacilo igrača " + izbaceniIgrac.getNaziv());
                vukodlaci.remove(izbaceniIgrac);
                selo.remove(izbaceniIgrac);
                igraci.remove(izbaceniIgrac);
            }
            spavaj(2000);

            if(vukodlaci.isEmpty()) break;
            if(selo.isEmpty()) break;
        }

        System.out.println("Igra je gotova.");
        if(vukodlaci.isEmpty()){
            System.out.print(" Selo je pobijedilo!!");
            ApplicationProject.logger.info("Odigran vukodlak na događaju: " + nazivDogadaja +
                    ". Organizator: " + klubOrganizator.getNaziv() + ". Pobijedilo je selo.");
        }
        else if(selo.isEmpty()) {
            System.out.print(" Vukodlaci su pobijedili!");
            ApplicationProject.logger.info("Odigran vukodlak na događaju: " + nazivDogadaja +
                    ". Organizator: " + klubOrganizator.getNaziv() + ". Pobijedili su vukodlaci.");
        }

    }
}
