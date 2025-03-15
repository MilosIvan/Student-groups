package com.example.controllers;

import com.example.enumeracije.Fakultet;
import com.example.filters.KluboviStudenataFilter;
import com.example.model.KlubStudenata;
import com.example.utils.Database;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class KluboviStudenataPregledController {
    @FXML
    private TextField nazivKlubaTextField;
    @FXML
    private ComboBox<Fakultet> fakultetComboBox;
    @FXML
    private TableView<KlubStudenata> kluboviStudenataTableView;
    @FXML
    private TableColumn<KlubStudenata, String> idTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> nazivKlubaTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> fakultetTableColumn;
    @FXML
    private TableColumn<KlubStudenata, String> gradTableColumn;

    public void initialize() {
        idTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getId().toString()));
        nazivKlubaTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getNaziv()));
        fakultetTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getFakultet().toString()));
        gradTableColumn.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().getGrad().toString()));

        fakultetComboBox.setItems(FXCollections.observableList(Arrays.asList(Fakultet.values())));
    }

    public void pretragaButton() {
        String nazivKluba = nazivKlubaTextField.getText();
        Fakultet fakultet = fakultetComboBox.getValue();

        KluboviStudenataFilter filter = new KluboviStudenataFilter(nazivKluba, fakultet);

        List<KlubStudenata> kluboviStudenata = Database.getKluboviStudenataFilter(filter);

        kluboviStudenataTableView.setItems(FXCollections.observableList(kluboviStudenata));
    }

}
