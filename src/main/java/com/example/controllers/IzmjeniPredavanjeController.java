package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Promjena;
import com.example.dogadaji.Predavanje;
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

public class IzmjeniPredavanjeController {
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
            dialog.setHeaderText("Izmjeni grad predavanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Predavanje predavanje = Database.getPredavanjebyID(id);

                    Database.izmjeniGrad("PREDAVANJA", id, grad.getRedniBr());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili grad predavanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Predavanje: grad",
                            predavanje.getId(),
                            predavanje.getLokacijaOdrzavanja().toString(),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene grada predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene grada predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene predavanja");
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
            dialog.setHeaderText("Izmjeni datum početka predavanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Predavanje predavanje = Database.getPredavanjebyID(id);

                    Database.izmjeniDatumPocetka("PREDAVANJA", id, datumPocetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum početka predavanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Predavanje: datum početka",
                            predavanje.getId(),
                            predavanje.getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma početka predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma početka predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene predavanja");
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
                throw new NepotpunUnosPodatakaException("datum zavrsetka");

            Integer id = Integer.valueOf(idString);
            LocalDate datumZavrsetka = datumZavrsetkaOptional.get();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni datum završetka predavanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Predavanje predavanje = Database.getPredavanjebyID(id);

                    Database.izmjeniDatumZavrsetka("PREDAVANJA", id, datumZavrsetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum završetka predavanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Predavanje: datum završetka",
                            predavanje.getId(),
                            predavanje.getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma završetka predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma završetka predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene predavanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}