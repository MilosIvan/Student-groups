package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Promjena;
import com.example.dogadaji.Putovanje;
import com.example.enumeracije.Grad;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.utils.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class IzmjeniPutovanjeController {
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<Grad> gradComboBox;
    @FXML
    private DatePicker datumPocetkaDatePicker;
    @FXML
    private DatePicker datumZavrsetkaDatePicker;

    public void initialize(){
        gradComboBox.setItems(FXCollections.observableList(Arrays.asList(Grad.values())));
    }

    public void izmjeniGradButton() {
        try {
            String idString;
            Optional<Grad> gradOptional;

            if((idString = idTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ID");
            if((gradOptional = Optional.ofNullable(gradComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("grad");

            Integer id = Integer.valueOf(idString);
            Grad grad = gradOptional.get();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni grad putovanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Putovanje putovanje = Database.getPutovanjeByID(id);

                    Database.izmjeniGrad("PUTOVANJA", id, grad.getRedniBr());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili grad putovanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Putovanje: grad",
                            putovanje.getId(),
                            putovanje.getLokacijaOdrzavanja().toString(),
                            grad.toString(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene grada putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene grada putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniDatumPocetkaButton() {
        try {
            String idString;
            Optional<LocalDate> datumPocetkaOptional;

            if((idString = idTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ID");
            if((datumPocetkaOptional = Optional.ofNullable(datumPocetkaDatePicker.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("datum pocetka");

            Integer id = Integer.valueOf(idString);
            LocalDate datumPocetka = datumPocetkaOptional.get();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni datum početka putovanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Putovanje putovanje = Database.getPutovanjeByID(id);

                    Database.izmjeniDatumPocetka("PUTOVANJA", id, datumPocetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum početka putovanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Putovanje: datum početka",
                            putovanje.getId(),
                            putovanje.getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
                            datumPocetka.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma početka putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma početka putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniDatumZavrsetkaButton() {
        try {
            String idString;
            Optional<LocalDate> datumZavrsetkaOptional;

            if((idString = idTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ID");
            if((datumZavrsetkaOptional = Optional.ofNullable(datumZavrsetkaDatePicker.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("datum završetka");

            Integer id = Integer.valueOf(idString);
            LocalDate datumZavrsetka = datumZavrsetkaOptional.get();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni datum završetka putovanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Putovanje putovanje = Database.getPutovanjeByID(id);

                    Database.izmjeniDatumZavrsetka("PUTOVANJA", id, datumZavrsetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum završetka putovanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Putovanje: datum završetka",
                            putovanje.getId(),
                            putovanje.getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
                            datumZavrsetka.format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma završetka putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma završetka putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene putovanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
