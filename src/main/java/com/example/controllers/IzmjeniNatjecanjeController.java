package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Promjena;
import com.example.dogadaji.Natjecanje;
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

public class IzmjeniNatjecanjeController {
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<Grad> gradComboBox;
    @FXML
    private DatePicker datumPocetkaDatePicker;
    @FXML
    private DatePicker datumZavrsetkaDatePicker;
    @FXML
    private TextField nagradaTextField;

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
            dialog.setHeaderText("Izmjeni grad natjecanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Natjecanje natjecanje = Database.getNatjecanjebyID(id);

                    Database.izmjeniGrad("NATJECANJA", id, grad.getRedniBr());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili grad natjecanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Natjecanje: grad",
                            natjecanje.getId(),
                            natjecanje.getLokacijaOdrzavanja().toString(),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene grada natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene grada natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene natjecanja");
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
            dialog.setHeaderText("Izmjeni datum početka natjecanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Natjecanje natjecanje = Database.getNatjecanjebyID(id);

                    Database.izmjeniDatumPocetka("NATJECANJA", id, datumPocetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum početka natjecanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Natjecanje: datum početka",
                            natjecanje.getId(),
                            natjecanje.getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma početka natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma početka natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene natjecanja");
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
            dialog.setHeaderText("Izmjeni datum završetka natjecanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Natjecanje natjecanje = Database.getNatjecanjebyID(id);

                    Database.izmjeniDatumZavrsetka("NATJECANJA", id, datumZavrsetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum završetka natjecanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Natjecanje: datum završetka",
                            natjecanje.getId(),
                            natjecanje.getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma završetka natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma završetka natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniNagraduButton() {
        try {
            String idString;
            String nagradaString;

            if((idString = idTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ID");
            if((nagradaString = nagradaTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("nagrada(EUR)");

            Integer id = Integer.valueOf(idString);
            Integer nagrada = Integer.valueOf(nagradaString);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni nagradu natjecanja");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Natjecanje natjecanje = Database.getNatjecanjebyID(id);

                    Database.izmjeniNagradu("NATJECANJA", id, nagrada);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili nagradu natjecanja");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Natjecanje: nagrada",
                            natjecanje.getId(),
                            natjecanje.getNagradaEUR().toString(),
                            nagradaString,
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene nagrade natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene nagrade natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene natjecanja");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
