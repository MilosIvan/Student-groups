package com.example.threads;

import com.example.ApplicationProject;
import com.example.utils.Promjena;
import javafx.application.Platform;

import java.util.Optional;

public class RefreshDataThread extends ThreadResourceCommunication implements Runnable {
    @Override
    public void run() {
        while (true) {
            Platform.runLater(() -> {
                ApplicationProject.mainStage.setTitle(
                        "Hello " + ApplicationProject.korisnickiPodaci.usernameInput()
                );
            });

            spavaj(5000);

            Optional<Promjena> promjenaOptional = super.getPosljednjaIzmjena();

            promjenaOptional.ifPresent(promjena -> Platform.runLater(() -> {
                if (promjena.getStaraVrijednost().isEmpty() && promjena.getNovaVrijednost().isEmpty()) {
                    ApplicationProject.mainStage.setTitle(
                            "Posljednja izmjena: " + promjena.getEntitet()
                    );
                } else {
                    ApplicationProject.mainStage.setTitle(
                            "Posljednja izmjena: " + promjena.getEntitet() + ", ID: " + promjena.getId()
                    );
                }
            }));

            spavaj(5000);
        }
    }

    public void spavaj(Integer millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
