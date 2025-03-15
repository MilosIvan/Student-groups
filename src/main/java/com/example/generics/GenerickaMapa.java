package com.example.generics;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class GenerickaMapa<T, G> {      //mora imat 2 parametra i neznam sta cu s drugim
    private Map<T, Set<G>> mapa;

    public GenerickaMapa() {
        this.mapa = new HashMap<>();
    }

    public Map<T, Set<G>> getMapa() {
        return mapa;
    }

    public void setMapa(Map<T, Set<G>> mapa) {
        this.mapa = mapa;
    }
}
