package com.example.controllers;

import com.example.dogadaji.*;
import com.example.enumeracije.Grad;
import com.example.generics.PopisDogadaja;
import com.example.model.KlubStudenata;
import com.example.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class FiltrirajDogadajeController {
    @FXML
    private ComboBox<KlubStudenata> klubStudenataComboBox;
    @FXML
    private ComboBox<Grad> gradComboBox;
    @FXML
    private DatePicker datumDatePicker;

    @FXML
    private TableView<Dogadaj> dogadajiTableView;
    @FXML
    private TableColumn<Dogadaj, String> idTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> nazivTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> organizatorTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> gradTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> datumPocetkaTableColumn;
    @FXML
    private TableColumn<Dogadaj, String> datumZavrsetkaTableColumn;

    public void initialize() {
        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        List<KlubStudenata> kluboviStudenata = Database.getKluboviStudenata();
        klubStudenataComboBox.setItems(FXCollections.observableList(kluboviStudenata));

        gradComboBox.setItems(FXCollections.observableList(Arrays.asList(Grad.values())));
    }

    public void prikaziDogadajeButton() {
        PopisDogadaja<Dogadaj> dogadaji = new PopisDogadaja<>();

        List<Predavanje> predavanja = Database.getPredavanja();
        for(Dogadaj dogadaj : predavanja) {
            dogadaji.add(dogadaj);
        }
        List<Projekt> projekti = Database.getProjekti();
        for(Dogadaj dogadaj : projekti) {
            dogadaji.add(dogadaj);
        }
        List<Natjecanje> natjecanja = Database.getNatjecanja();
        for(Dogadaj dogadaj : natjecanja) {
            dogadaji.add(dogadaj);
        }
        List<Kviz> kvizovi = Database.getKvizovi();
        for(Dogadaj dogadaj : kvizovi) {
            dogadaji.add(dogadaj);
        }
        List<Putovanje> putovanja = Database.getPutovanja();
        for(Dogadaj dogadaj : putovanja) {
            dogadaji.add(dogadaj);
        }
        List<Zabava> zabave = Database.getZabave();
        for(Dogadaj dogadaj : zabave) {
            dogadaji.add(dogadaj);
        }

        Optional<KlubStudenata> klubStudenataOptional = Optional.ofNullable(klubStudenataComboBox.getValue());
        Optional<Grad> gradOptional = Optional.ofNullable(gradComboBox.getValue());
        Optional<LocalDate> datumOptional = Optional.ofNullable(datumDatePicker.getValue());

        klubStudenataOptional.ifPresent(klubStudenata -> dogadaji.setPopisDogadaja(
                dogadaji.getPopisDogadaja().stream()
                        .filter(d -> d.getKlubOrganizator().toString().equals(klubStudenata.toString()))
                        .collect(Collectors.toList()))
        );

        gradOptional.ifPresent(grad -> dogadaji.setPopisDogadaja(
                dogadaji.getPopisDogadaja().stream()
                        .filter(d -> d.getLokacijaOdrzavanja().equals(grad))
                        .collect(Collectors.toList())
        ));

        datumOptional.ifPresent(localDate -> dogadaji.setPopisDogadaja(
                dogadaji.getPopisDogadaja().stream()
                        .filter(d -> d.getDatumPocetka().isBefore(localDate))
                        .filter(d -> d.getDatumZavrsetka().isAfter(localDate))
                        .collect(Collectors.toList()))
        );

        dogadajiTableView.setItems(FXCollections.observableList(dogadaji.getPopisDogadaja()));
    }

    public void prikaziInfo() {
        try {
            Dogadaj dogadaj = dogadajiTableView.getSelectionModel().getSelectedItem();

            if (dogadaj instanceof Predavanje predavanje) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Predavanje " + predavanje.getNaziv());
                alert.setHeaderText("Tema predavanja: " + predavanje.getTemaPredavanja());
                alert.showAndWait();
            }
            else if (dogadaj instanceof Projekt projekt) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Projekt " + projekt.getNaziv());
                alert.setHeaderText("Tema projekta: " + projekt.getTemaProjekta());
                alert.showAndWait();
            }
            else if (dogadaj instanceof Natjecanje natjecanje) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Natjecanje " + natjecanje.getNaziv());
                alert.setHeaderText("Tema natjecanja: " + natjecanje.getTemaNatjecanja()
                        + "\nNagrada: " + natjecanje.getNagradaEUR() + " EUR");
                alert.showAndWait();
            }
            else if (dogadaj instanceof Kviz kviz) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Kviz " + kviz.getNaziv());
                alert.setHeaderText("Nagrada: " + kviz.getNagradaEUR() + " EUR");
                alert.showAndWait();
            }
            else if (dogadaj instanceof Putovanje putovanje) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Putovanje " + putovanje.getNaziv());
                alert.setHeaderText("Povod putovanja: " + putovanje.getPovodPutovanja());
                alert.showAndWait();
            }
            else if (dogadaj instanceof Zabava zabava) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Zabava " + zabava.getNaziv());
                alert.setHeaderText("Naziv lokala: " + zabava.getNazivLokala());
                alert.showAndWait();
            }
        } catch (NullPointerException e) {}
    }

}
