package com.example.threads;

import com.example.utils.Promjena;
import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.List;

public class PrikaziIzmjeneThread extends ThreadResourceCommunication implements Runnable {
    private ListView<Promjena> listView;

    public PrikaziIzmjeneThread(ListView<Promjena> listView) {
        this.listView = listView;
    }

    @Override
    public void run() {
        List<Object> objectList  = super.getPromjene();
        List<Promjena> promjene = new ArrayList<>();
        for(Object obj : objectList) {
            if(obj instanceof Promjena) {
                promjene.add((Promjena) obj);
            }
        }
        listView.setItems(FXCollections.observableList(promjene.reversed()));
    }
}
