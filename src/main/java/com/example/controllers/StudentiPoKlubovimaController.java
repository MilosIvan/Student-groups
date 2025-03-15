package com.example.controllers;

import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class StudentiPoKlubovimaController {
    @FXML
    private TableView<KlubStudenata> kluboviStudenataTableView;
    @FXML
    private TableColumn<KlubStudenata, String> idKlubaTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> nazivKlubaTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> fakultetKlubaTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> gradTableColumn;
    @FXML
    private TableView<Student> studentiTableView;
    @FXML
    private TableColumn<Student, String> idStudentaTableColumn;
    @FXML
    private TableColumn<Student, String> imeTableColumn;
    @FXML
    private TableColumn<Student, String> godinaStudijaTableColumn;
    @FXML
    private TableColumn<Student, String> spolTableColumn;
    @FXML
    private TableColumn<Student, String> jmbagTableColumn;
    @FXML
    private TableColumn<Student, String> prosjekTableColumn;
    @FXML
    private TableColumn<Student, String> fakultetTableColumn;

    public void initialize() {
        idKlubaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivKlubaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        fakultetKlubaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getFakultet().toString()));
        gradTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getGrad().toString()));

        idStudentaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        imeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        godinaStudijaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getGodinaStudija().toString()));
        spolTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getSpol().toString()));
        jmbagTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getJmbag()));
        prosjekTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getProsjekOcjena().toString()));
        fakultetTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getFakultet().toString()));

        List<KlubStudenata> kluboviStudenata = Database.getKluboviStudenata();
        kluboviStudenataTableView.setItems(FXCollections.observableList(kluboviStudenata));
    }

    public void prikaziStudente() {
        KlubStudenata klubStudenata = kluboviStudenataTableView.getSelectionModel().getSelectedItem();
        List<Student> studenti = Database.getStudentiIzKluba(klubStudenata.getId());

        klubStudenata.setStudenti(studenti);

        studentiTableView.setItems(FXCollections.observableList(studenti));
    }

}
