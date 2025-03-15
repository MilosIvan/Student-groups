package com.example.generics;

import com.example.dogadaji.Dogadaj;

import java.util.ArrayList;
import java.util.List;

public class PopisDogadaja<T extends Dogadaj> {
    private List<T> popisDogadaja;

    public PopisDogadaja() {
        this.popisDogadaja = new ArrayList<>();
    }

    public void add(T dogadaj) {
        popisDogadaja.add(dogadaj);
    }
    public void pop() {
        if(!popisDogadaja.isEmpty()) popisDogadaja.remove(popisDogadaja.getLast());
        else System.out.println("Popis je prazan");
    }
    public Integer duljinaPopisa() {
        return popisDogadaja.size();
    }

    public List<T> getPopisDogadaja() {
        return popisDogadaja;
    }
    public void setPopisDogadaja(List<T> popisDogadaja) {
        this.popisDogadaja = popisDogadaja;
    }
}
