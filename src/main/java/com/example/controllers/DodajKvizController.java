package com.example.controllers;

import com.example.ApplicationProject;
import com.example.dogadaji.Kviz;
import com.example.enumeracije.Grad;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.model.KlubStudenata;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Database;
import com.example.utils.Promjena;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DodajKvizController {
    @FXML
    private TextField nazivTextField;
    @FXML
    private ComboBox<String> organizatorComboBox;
    @FXML
    private ComboBox<Grad> gradComboBox;
    @FXML
    private DatePicker datumPocetkaDatePicker;
    @FXML
    private DatePicker datumZavrsetkaDatePicker;
    @FXML
    private TextField nagradaTextField;
    private List<KlubStudenata> kluboviStudenata;

    public void initialize() {
        kluboviStudenata = Database.getKluboviStudenata();
        List<String> klubovi = kluboviStudenata.stream().map(KlubStudenata::toString).toList();
        organizatorComboBox.setItems(FXCollections.observableList(klubovi));

        gradComboBox.setItems(FXCollections.observableList(Arrays.asList(Grad.values())));
    }

    public void dodajKvizButton() {
        try {
            String naziv;
            Optional<String> klubOrganizatorOptional;
            Optional<Grad> gradOptional;
            Optional<LocalDate> datumPocetkaOptional;
            Optional<LocalDate> datumZavrsetkaOptional;
            String nagradaString;
            if((naziv = nazivTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("naziv");
            if((klubOrganizatorOptional = Optional.ofNullable(organizatorComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("klub organizator");
            if((gradOptional = Optional.ofNullable(gradComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("grad");
            if((datumPocetkaOptional = Optional.ofNullable(datumPocetkaDatePicker.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("datum pocetka");
            if((datumZavrsetkaOptional = Optional.ofNullable(datumZavrsetkaDatePicker.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("datum zavrsetka");
            if((nagradaString = nagradaTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("nagrada(EUR)");

            String klubOrganizatorString = klubOrganizatorOptional.get();
            KlubStudenata klubOrganizator = switch (klubOrganizatorString) {
                case "FER" -> kluboviStudenata.get(1);
                case "FOI" -> kluboviStudenata.get(2);
                case "ETF" -> kluboviStudenata.get(3);
                case "FESB" -> kluboviStudenata.get(4);
                default -> kluboviStudenata.get(0);
            };
            Grad grad = gradOptional.get();
            LocalDate datumPocetka = datumPocetkaOptional.get();
            LocalDate datumZavrsetka = datumZavrsetkaOptional.get();
            Integer nagrada = Integer.valueOf(nagradaString);

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Dodaj kviz");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Kviz kviz = new Kviz(0, naziv, klubOrganizator, grad, datumPocetka, datumZavrsetka, nagrada);

                    Database.dodajKviz(kviz);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste dodali novi kviz");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Dodan kviz " + kviz.getNaziv(),
                            kviz.getId(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );

                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom dodavanja novog kviza");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom unosa novog kviza");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje 'nagrada (EUR)' mora biti brojčana vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
