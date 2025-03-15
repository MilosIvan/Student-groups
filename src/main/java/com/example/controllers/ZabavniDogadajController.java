package com.example.controllers;

import com.example.ApplicationProject;
import com.example.dogadaji.*;
import com.example.utils.Database;
import com.example.utils.OtvaranjeEkrana;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class ZabavniDogadajController {
    @FXML
    private Button dodajKvizButton;
    @FXML
    private Button dodajPutovanjeButton;
    @FXML
    private Button dodajZabavuButton;
    @FXML
    private Button ukloniKvizButton;
    @FXML
    private Button ukloniPutovanjeButton;
    @FXML
    private Button ukloniZabavuButton;
    @FXML
    private Button izmjeniKvizButton;
    @FXML
    private Button izmjeniPutovanjeButton;
    @FXML
    private Button izmjeniZabavuButton;

    @FXML
    private TableView<Kviz> kvizoviTableView;
    @FXML
    private TableColumn<Kviz, String> idKvizaTableColumn;
    @FXML
    private TableColumn<Kviz, String> nazivKvizaTableColumn;
    @FXML
    private TableColumn<Kviz, String> organizatorKvizaTableColumn;
    @FXML
    private TableColumn<Kviz, String> gradKvizaTableColumn;
    @FXML
    private TableColumn<Kviz, String> datumPocetkaKvizaTableColumn;
    @FXML
    private TableColumn<Kviz, String> datumZavrsetkaKvizaTableColumn;

    @FXML
    private TableView<Putovanje> putovanjaTableView;
    @FXML
    private TableColumn<Putovanje, String> idPutovanjaTableColumn;
    @FXML
    private TableColumn<Putovanje, String> nazivPutovanjaTableColumn;
    @FXML
    private TableColumn<Putovanje, String> organizatorPutovanjaTableColumn;
    @FXML
    private TableColumn<Putovanje, String> gradPutovanjaTableColumn;
    @FXML
    private TableColumn<Putovanje, String> datumPocetkaPutovanjaTableColumn;
    @FXML
    private TableColumn<Putovanje, String> datumZavrsetkaPutovanjaTableColumn;

    @FXML
    private TableView<Zabava> zabaveTableView;
    @FXML
    private TableColumn<Zabava, String> idZabaveTableColumn;
    @FXML
    private TableColumn<Zabava, String> nazivZabaveTableColumn;
    @FXML
    private TableColumn<Zabava, String> organizatorZabaveTableColumn;
    @FXML
    private TableColumn<Zabava, String> gradZabaveTableColumn;
    @FXML
    private TableColumn<Zabava, String> datumPocetkaZabaveTableColumn;
    @FXML
    private TableColumn<Zabava, String> datumZavrsetkaZabaveTableColumn;

    public void initialize() {
        idKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaKvizaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        idPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaPutovanjaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        idZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        organizatorZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getKlubOrganizator().toString()));
        gradZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getLokacijaOdrzavanja().toString()));
        datumPocetkaZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumPocetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));
        datumZavrsetkaZabaveTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getDatumZavrsetka().format(DateTimeFormatter.ofPattern("dd.MM.yyyy."))));

        if (!ApplicationProject.korisnickiPodaci.admin()) {
            dodajKvizButton.setVisible(false);
            dodajPutovanjeButton.setVisible(false);
            dodajZabavuButton.setVisible(false);
            ukloniKvizButton.setVisible(false);
            ukloniPutovanjeButton.setVisible(false);
            ukloniZabavuButton.setVisible(false);
            izmjeniKvizButton.setVisible(false);
            izmjeniPutovanjeButton.setVisible(false);
            izmjeniZabavuButton.setVisible(false);
        }

        ButtonType tombola = new ButtonType("TOMBOLA");
        ButtonType vukodlak = new ButtonType("VUKODLAK");
        kvizoviTableView.setOnMouseClicked(event -> {
            Kviz kviz = kvizoviTableView.getSelectionModel().getSelectedItem();

            if (event.getButton() == MouseButton.SECONDARY) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Igre");
                dialog.setHeaderText("Izaberite igru koji želite pokrenuti na kvizu " + kviz.getNaziv());
                dialog.getDialogPane().getButtonTypes().add(tombola);
                dialog.getDialogPane().getButtonTypes().add(vukodlak);
                Optional<ButtonType> result = dialog.showAndWait();

                if(result.isPresent()) {
                    if(result.get() == tombola) {
                        kviz.tombola();
                    }
                    else if(result.get() == vukodlak) {
                        kviz.vukodlak();
                    }
                }
            }
            else {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Kviz " + kviz.getNaziv());
                    alert.setHeaderText("Nagrada: " + kviz.getNagradaEUR() + " EUR");
                    alert.showAndWait();
                } catch (NullPointerException e) {}
            }
        });

        putovanjaTableView.setOnMouseClicked(event -> {
            Putovanje putovanje = putovanjaTableView.getSelectionModel().getSelectedItem();

            if (event.getButton() == MouseButton.SECONDARY) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Igre");
                dialog.setHeaderText("Izaberite igru koji želite pokrenuti na putovanju " + putovanje.getNaziv());
                dialog.getDialogPane().getButtonTypes().add(tombola);
                dialog.getDialogPane().getButtonTypes().add(vukodlak);
                Optional<ButtonType> result = dialog.showAndWait();

                if(result.isPresent()) {
                    if(result.get() == tombola) {
                        putovanje.tombola();
                    }
                    else if(result.get() == vukodlak) {
                        putovanje.vukodlak();
                    }
                }
            }
            else {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Putovanje " + putovanje.getNaziv());
                    alert.setHeaderText("Povod putovanja: " + putovanje.getPovodPutovanja());
                    alert.showAndWait();
                } catch (NullPointerException e) {}
            }
        });

        zabaveTableView.setOnMouseClicked(event -> {
            Zabava zabava = zabaveTableView.getSelectionModel().getSelectedItem();

            if (event.getButton() == MouseButton.SECONDARY) {
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setTitle("Igre");
                dialog.setHeaderText("Izaberite igru koji želite pokrenuti na zabavi " + zabava.getNaziv());
                dialog.getDialogPane().getButtonTypes().add(tombola);
                dialog.getDialogPane().getButtonTypes().add(vukodlak);
                Optional<ButtonType> result = dialog.showAndWait();

                if(result.isPresent()) {
                    if(result.get() == tombola) {
                        zabava.tombola();
                    }
                    else if (result.get() == vukodlak) {
                        zabava.vukodlak();
                    }
                }
            }
            else {
                try {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Zabava " + zabava.getNaziv());
                    alert.setHeaderText("Naziv lokala: " + zabava.getNazivLokala());
                    alert.showAndWait();
                } catch (NullPointerException e) {}
            }
        });
    }

    public void pretragaKvizova() {
        List<Kviz> kvizovi = Database.getKvizovi();

        kvizoviTableView.setItems(FXCollections.observableList(kvizovi));
    }

    public void pretragaPutovanja() {
        List<Putovanje> putovanja = Database.getPutovanja();

        putovanjaTableView.setItems(FXCollections.observableList(putovanja));
    }

    public void pretragaZabava() {
        List<Zabava> zabave = Database.getZabave();

        zabaveTableView.setItems(FXCollections.observableList(zabave));
    }

    public void dodajKviz() {
        try {
            OtvaranjeEkrana.openDodajKviz();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dodajPutovanje() {
        try {
            OtvaranjeEkrana.openDodajPutovanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void dodajZabavu() {
        try {
            OtvaranjeEkrana.openDodajZabavu();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniKviz() {
        try {
            OtvaranjeEkrana.openIzmjeniKviz();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniPutovanje() {
        try {
            OtvaranjeEkrana.openIzmjeniPutovanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void izmjeniZabavu() {
        try {
            OtvaranjeEkrana.openIzmjeniZabavu();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniKviz() {
        try {
            OtvaranjeEkrana.openUkloniKviz();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniPutovanje() {
        try {
            OtvaranjeEkrana.openUkloniPutovanje();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void ukloniZabavu() {
        try {
            OtvaranjeEkrana.openUkloniZabavu();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
