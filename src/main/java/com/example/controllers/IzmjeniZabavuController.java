package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Promjena;
import com.example.dogadaji.Zabava;
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

public class IzmjeniZabavuController {
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<Grad> gradComboBox;
    @FXML
    private DatePicker datumPocetkaDatePicker;
    @FXML
    private DatePicker datumZavrsetkaDatePicker;
    @FXML
    private TextField nazivLokalaTextField;


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
            dialog.setHeaderText("Izmjeni grad zabave");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Zabava zabava = Database.getZabaveByID(id);

                    Database.izmjeniGrad("ZABAVE", id, grad.getRedniBr());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili grad zabave");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Zabava: grad",
                            zabava.getId(),
                            zabava.getLokacijaOdrzavanja().toString(),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene grada zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene grada zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene zabave");
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
            dialog.setHeaderText("Izmjeni datum početka zabave");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Zabava zabava = Database.getZabaveByID(id);

                    Database.izmjeniDatumPocetka("ZABAVE", id, datumPocetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum početka zabave");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Zabava: datum početka",
                            zabava.getId(),
                            zabava.getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma početka zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma početka zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene zabave");
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
            dialog.setHeaderText("Izmjeni datum završetka zabave");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Zabava zabava = Database.getZabaveByID(id);

                    Database.izmjeniDatumZavrsetka("ZABAVE", id, datumZavrsetka);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili datum završetka zabave");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Zabava: datum završetka",
                            zabava.getId(),
                            zabava.getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy.")),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene datuma završetka zabava");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene datuma završetka zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniNazivLokalaButton() {
        try {
            String idString;
            String nazivLokala;

            if((idString = idTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ID");
            if((nazivLokala = nazivLokalaTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("naziv lokala");

            Integer id = Integer.valueOf(idString);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni naziv lokala zabave");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Zabava zabava = Database.getZabaveByID(id);

                    Database.izmjeniNazivLokalaZabave(id, nazivLokala);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili naziv lokala zabave");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Zabava: naziv lokala",
                            zabava.getId(),
                            zabava.getNazivLokala(),
                            nazivLokala,
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene naziva lokala zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene naziva lokala zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene zabave");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
