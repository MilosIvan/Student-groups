package com.example.controllers;

import com.example.ApplicationProject;
import com.example.dogadaji.Natjecanje;
import com.example.iznimke.NepostojeciIDException;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Database;
import com.example.utils.Promjena;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UkloniNatjecanjeController {
    @FXML
    private TextField idTextField;

    public void ukloniButton() {
        try {
            String idString;
            if((idString = idTextField.getText()).isEmpty()) throw new NepotpunUnosPodatakaException("ID studenta");
            Integer id = Integer.valueOf(idString);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Ukloni natjecanje");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Natjecanje natjecanje = Database.getNatjecanjebyID(id);
                    Database.ukloniDogadaj("NATJECANJA", id);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste uklonili natjecanje");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Uklonjeno natjecanje " + natjecanje.getNaziv(),
                            natjecanje.getId(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom uklanjanja natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom uklanjanja natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom uklanjanja natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
