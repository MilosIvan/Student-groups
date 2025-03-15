package com.example.controllers;

import com.example.ApplicationProject;
import com.example.enumeracije.Fakultet;
import com.example.enumeracije.Spol;
import com.example.iznimke.NeispravanProsjekException;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.model.Student;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Database;
import com.example.utils.Promjena;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DodajStudentaController {
    @FXML
    private TextField imeTextField;
    @FXML
    private TextField jmbagTextField;
    @FXML
    private TextField prosjekOcjenaTextField;
    @FXML
    private ComboBox<String> spolComboBox;
    @FXML
    private ComboBox<String> godinaStudijaComboBox;
    @FXML
    private ComboBox<String> fakultetComboBox;

    public void initialize() {
        Spol[] spoloviNiz = Spol.values();
        List<Spol> spoloviList = Arrays.asList(spoloviNiz);
        List<String> spolovi = spoloviList.stream().map(Spol::toString).toList();
        spolComboBox.setItems(FXCollections.observableList(spolovi));

        String[] godineStudijaNiz = {"1", "2", "3", "4"};
        List<String> godineStudija = Arrays.asList(godineStudijaNiz);
        godinaStudijaComboBox.setItems(FXCollections.observableList(godineStudija));

        Fakultet[] fakultetiNiz = Fakultet.values();
        List<Fakultet> listaFakulteta = Arrays.asList(fakultetiNiz);
        List<String> fakulteti = listaFakulteta.stream().map(Fakultet::toString).toList();
        fakultetComboBox.setItems(FXCollections.observableList(fakulteti));
    }

    public void dodajStudentaButton() {
        try {
            String ime;
            String jmbag;
            String prosjekString;
            Optional<String> spolOptional;
            Optional<String> godinaStudijaOptional;
            Optional<String> fakultetOptional;

            if((ime = imeTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("ime i prezime");
            if((jmbag = jmbagTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("JMBAG");
            if((prosjekString = prosjekOcjenaTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("prosjek ocjena");
            if((spolOptional = Optional.ofNullable(spolComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("spol");
            if((godinaStudijaOptional = Optional.ofNullable(godinaStudijaComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("godina studija");
            if((fakultetOptional = Optional.ofNullable(fakultetComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("fakultet");

            BigDecimal prosjek = new BigDecimal(prosjekString);
            if(prosjek.compareTo(BigDecimal.valueOf(1)) < 0 || prosjek.compareTo(BigDecimal.valueOf(5)) > 0)
                throw new NeispravanProsjekException();

            Spol spol = Spol.MUSKO;
            if(spolOptional.get().equals("Ž")) spol = Spol.ZENSKO;

            Integer godinaStudija = Integer.valueOf(godinaStudijaOptional.get());

            String fakultetString = fakultetOptional.get();
            Fakultet fakultet = switch (fakultetString) {
                case "TVZ" -> Fakultet.TVZ;
                case "FOI" -> Fakultet.FOI;
                case "LIBERTAS" -> Fakultet.LIBERTAS;
                case "ETF" -> Fakultet.ETF;
                case "FESB" -> Fakultet.FESB;
                default -> Fakultet.FER;
            };

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Dodaj novog studenta");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Student student = new Student.Builder(0, ime)
                            .godinaStudijaBuilder(godinaStudija)
                            .spolBuilder(spol)
                            .jmbagBuilder(jmbag)
                            .prosjekOcjenaBuilder(prosjek)
                            .fakultetBuilder(fakultet)
                            .build();

                    Database.saveStudent(student);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste dodali novog studenta");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Dodan student " + student.getNaziv(),
                            student.getId(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom dodavanja novog studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom unosa novog studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje 'Prosjek ocjena' mora biti brojčana vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NeispravanProsjekException e) {
            ApplicationProject.logger.error("Neispravan unos prosjeka ocjena prilikom dodavanja novog studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unešen je neispravan prosjek ocjena");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }
}
