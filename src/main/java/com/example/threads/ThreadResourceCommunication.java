package com.example.threads;

import com.example.utils.Promjena;
import com.example.utils.Serijalizacija;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class ThreadResourceCommunication {
    private static boolean activeConnection = false;

    public synchronized List<Object> getPromjene() {
        while (activeConnection) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        activeConnection = true;
        List<Object> objectList = Serijalizacija.deserijaliziraj();
        activeConnection = false;
        notifyAll();

        return objectList;
    }

    public synchronized void serijaliziraj(Promjena promjena) {
        while (activeConnection) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        activeConnection = true;
        Serijalizacija.serijaliziraj(promjena);
        activeConnection = false;
        notifyAll();
    }

    public synchronized Optional<Promjena> getPosljednjaIzmjena() {
        while (activeConnection) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }

        activeConnection = true;
        List<Object> objectList = Serijalizacija.deserijaliziraj();
        activeConnection = false;

        Optional<Promjena> promjenaOptional = Optional.empty();
        if(!objectList.isEmpty()) {
            if(objectList.getLast() instanceof Promjena pr)
                promjenaOptional = Optional.of(pr);
        }

        return promjenaOptional;
    }

}
