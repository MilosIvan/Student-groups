package com.example.controllers;

import com.example.threads.PrikaziIzmjeneThread;
import com.example.utils.Promjena;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PrikaziIzmjeneController {
    @FXML
    private ListView<Promjena> listView;

    public void prikaziIzmjeneButton() {
        PrikaziIzmjeneThread thread = new PrikaziIzmjeneThread(listView);
        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(thread);
    }

}
