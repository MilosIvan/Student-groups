package com.example.controllers;

import com.example.ApplicationProject;
import com.example.dogadaji.Natjecanje;
import com.example.dogadaji.Predavanje;
import com.example.dogadaji.Projekt;
import com.example.utils.Database;
import com.example.utils.OtvaranjeEkrana;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PoslovniDogadajController {
    @FXML
    private Button dodajPredavanjeButton;
    @FXML
    private Button dodajProjektButton;
    @FXML
    private Button dodajNatjecanjeButton;
    @FXML
    private Button ukloniPredavanjeButton;
    @FXML
    private Button ukloniProjektButton;
    @FXML
    private Button ukloniNatjecanjeButton;
    @FXML
    private Button izmjeniPredavanjeButton;
    @FXML
    private Button izmjeniProjektButton;
    @FXML
    private Button izmjeniNatjecanjeButton;

    @FXML
    private TableView<Predavanje> predavanjaTableView;
    @FXML
    private TableColumn<Predavanje, String> idPredavanjaTableColumn;
    @FXML
    private TableColumn<Predavanje, String> nazivPredavanjaTableColumn;
    @FXML
    private TableColumn<Predavanje, String> organizatorPredavanjaTableColumn;
    @FXML
    private TableColumn<Predavanje, String> gradPredavanjaTableColumn;
    @FXML
    private TableColumn<Predavanje, String> datumPocetkaPredavanjaTableColumn;
    @FXML
    private TableColumn<Predavanje, String> datumZavrsetkaPredavanjaTableColumn;

    @FXML
    private TableView<Projekt> projektiTableView;
    @FXML
    private TableColumn<Projekt, String> idProjektaTableColumn;
    @FXML
    private TableColumn<Projekt, String> nazivProjektaTableColumn;
    @FXML
    private TableColumn<Projekt, String> organizatorProjektaTableColumn;
    @FXML
    private TableColumn<Projekt, String> gradProjektaTableColumn;
    @FXML
    private TableColumn<Projekt, String> datumPocetkaProjektaTableColumn;
    @FXML
    private TableColumn<Projekt, String> datumZavrsetkaProjektaTableColumn;

    @FXML
    private TableView<Natjecanje> natjecanjeTableView;
    @FXML
    private TableColumn<Natjecanje, String> idNatjecanjaTableColumn;
    @FXML
    private TableColumn<Natjecanje, String> nazivNatjecanjaTableColumn;
    @FXML
    private TableColumn<Natjecanje, String> organizatorNatjecanjaTableColumn;
    @FXML
    private TableColumn<Natjecanje, String> gradNatjecanjaTableColumn;
    @FXML
    private TableColumn<Natjecanje, String> datumPocetkaNatjecanjaTableColumn;
    @FXML
    private TableColumn<Natjecanje, String> datumZavrsetkaNatjecanjaTableColumn;

    public void initialize() {
        idPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaPredavanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        idProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaProjektaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        idNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaNatjecanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        if (!ApplicationProject.korisnickiPodaci.admin()) {
            dodajPredavanjeButton.setVisible(false);
            dodajProjektButton.setVisible(false);
            dodajNatjecanjeButton.setVisible(false);
            ukloniPredavanjeButton.setVisible(false);
            ukloniProjektButton.setVisible(false);
            ukloniNatjecanjeButton.setVisible(false);
            izmjeniPredavanjeButton.setVisible(false);
            izmjeniProjektButton.setVisible(false);
            izmjeniNatjecanjeButton.setVisible(false);
        }
    }

    public void pretragaPredavanja() {
        List<Predavanje> predavanja = Database.getPredavanja();

        predavanjaTableView.setItems(FXCollections.observableList(predavanja));
    }

    public void pretragaProjekata() {
        List<Projekt> projekti = Database.getProjekti();

        projektiTableView.setItems(FXCollections.observableList(projekti));
    }

    public void pretragaNatjecanja() {
        List<Natjecanje> natjecanja = Database.getNatjecanja();

        natjecanjeTableView.setItems(FXCollections.observableList(natjecanja));
    }

    public void dodajPredavanje() {
        try {
            OtvaranjeEkrana.openDodajPredavanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dodajProjekt() {
        try {
            OtvaranjeEkrana.openDodajProjekt();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dodajNatjecanje() {
        try {
            OtvaranjeEkrana.openDodajNatjecanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniPredavanje() {
        try {
            OtvaranjeEkrana.openIzmjeniPredavanje();
        } catch (IOException e) {
            System.out.println("kaje bkt");
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniProjekt() {
        try {
            OtvaranjeEkrana.openIzmjeniProjekt();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniNatjecanje() {
        try {
            OtvaranjeEkrana.openIzmjeniNatjecanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniPredavanje() {
        try {
            OtvaranjeEkrana.openUkloniPredavanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniProjekt() {
        try {
            OtvaranjeEkrana.openUkloniProjekt();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniNatjecanje() {
        try {
            OtvaranjeEkrana.openUkloniNatjecanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void prikaziInfoPredavanja() {
        try {
            Predavanje predavanje = predavanjaTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Predavanje " + predavanje.getNaziv());
            alert.setHeaderText("Tema predavanja: " + predavanje.getTemaPredavanja());
            alert.showAndWait();
        } catch (NullPointerException e) {}
    }

    public void prikaziInfoProjekta() {
        try {
            Projekt projekt = projektiTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Projekt " + projekt.getNaziv());
            alert.setHeaderText("Tema projekta: " + projekt.getTemaProjekta());
            alert.showAndWait();
        } catch (NullPointerException e) {}
    }

    public void prikaziInfoNatjecanja() {
        try {
            Natjecanje natjecanje = natjecanjeTableView.getSelectionModel().getSelectedItem();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Natjecanje " + natjecanje.getNaziv());
            alert.setHeaderText("Tema natjecanja: " + natjecanje.getTemaNatjecanja()
                    + "\nNagrada: " + natjecanje.getNagradaEUR() + " EUR");
            alert.showAndWait();
        } catch (NullPointerException e) {}
    }

}
