package com.example.controllers;

import com.example.filters.StudentFilter;
import com.example.enumeracije.Fakultet;
import com.example.model.Student;
import com.example.utils.Database;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Arrays;
import java.util.List;

public class StudentiPregledController {
    @FXML
    private TextField imeTextField;
    @FXML
    private ComboBox<Fakultet> fakultetComboBox;
    @FXML
    private ComboBox<String> godinaStudijaComboBox;
    @FXML
    private TableView<Student> studentiTableView;
    @FXML
    private TableColumn<Student, String> idTableColumn;
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
        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        imeTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        godinaStudijaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getGodinaStudija().toString()));
        spolTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getSpol().toString()));
        jmbagTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getJmbag()));
        prosjekTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getProsjekOcjena().toString()));
        fakultetTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getFakultet().toString()));

        fakultetComboBox.setItems(FXCollections.observableList(Arrays.asList(Fakultet.values())));

        String[] godineStudijaNiz = {"1", "2", "3", "4"};
        List<String> godineStudija = Arrays.asList(godineStudijaNiz);
        godinaStudijaComboBox.setItems(FXCollections.observableList(godineStudija));
    }

    public void pretragaButton() {
        String imeInput = imeTextField.getText();
        Fakultet fakultet = fakultetComboBox.getValue();
        String godinaStudijaString = godinaStudijaComboBox.getValue();

        StudentFilter filter = new StudentFilter(imeInput, fakultet, godinaStudijaString);
        List<Student> studenti = Database.getStudentiFilter(filter);

        studentiTableView.setItems(FXCollections.observableList(studenti));
    }
}
