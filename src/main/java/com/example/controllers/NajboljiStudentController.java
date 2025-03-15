package com.example.controllers;

import com.example.enumeracije.Spol;
import com.example.generics.GenerickaMapa;
import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.utils.Database;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.util.*;

public class NajboljiStudentController {
    @FXML
    private ComboBox<KlubStudenata> klubStudenataComboBox;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    private GenerickaMapa<KlubStudenata, Student> mapa;

    public void initialize() {
        List<KlubStudenata> kluboviStudenata = Database.getKluboviStudenata();
        klubStudenataComboBox.setItems(FXCollections.observableList(kluboviStudenata));

        mapa = new GenerickaMapa<>();

        for(KlubStudenata klub : kluboviStudenata) {
            mapa.getMapa().put(klub, new HashSet<>());
        }

        for(KlubStudenata klub : mapa.getMapa().keySet()) {
            Integer klubID = klub.getId();

            List<Student> studenti = Database.getStudentiIzKluba(klubID);
            for(Student student : studenti) {
                mapa.getMapa().get(klub).add(student);
            }
        }
    }

    public void action() {
        KlubStudenata klubStudenata = klubStudenataComboBox.getValue();

        Set<Student> studenti = mapa.getMapa().get(klubStudenata);

        Optional<Student> najboljiStudentOptional = studenti.stream().max(Comparator.comparing(Student::getProsjekOcjena));

        if(najboljiStudentOptional.isPresent()) {
            Student najboljiStudent = najboljiStudentOptional.get();
            if (najboljiStudent.getSpol().equals(Spol.MUSKO))
                label1.setText("najbolji student je " + najboljiStudent.getNaziv());
            else label1.setText("najbolja studentica je " + najboljiStudent.getNaziv());

            label2.setText("s prosjekom ocjena " + najboljiStudent.getProsjekOcjena());
        }
    }

}
