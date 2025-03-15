package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Promjena;
import com.example.enumeracije.Fakultet;
import com.example.iznimke.NeispravanProsjekException;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.model.Student;
import com.example.utils.Database;
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

public class IzmjeniStudentaController {
    @FXML
    private TextField idTextField;
    @FXML
    private ComboBox<String> godinaStudijaComboBox;
    @FXML
    private TextField prosjekOcjenaTextField;
    @FXML
    private ComboBox<String> fakultetComboBox;

    public void initialize() {
        String[] godineStudijaNiz = {"1", "2", "3", "4"};
        List<String> godineStudija = Arrays.asList(godineStudijaNiz);
        godinaStudijaComboBox.setItems(FXCollections.observableList(godineStudija));

        Fakultet[] fakultetiNiz = Fakultet.values();
        List<Fakultet> listaFakulteta = Arrays.asList(fakultetiNiz);
        List<String> fakulteti = listaFakulteta.stream().map(Fakultet::toString).toList();
        fakultetComboBox.setItems(FXCollections.observableList(fakulteti));
    }

    public void izmjeniGodinuStudijaButton() {
        try {
            String idString;
            Optional<String> godinaStudijaOptional;

            if((idString = idTextField.getText()).isEmpty()) throw new NepotpunUnosPodatakaException("ID");
            if((godinaStudijaOptional = Optional.ofNullable(godinaStudijaComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("godina studija");


            Integer id = Integer.valueOf(idString);
            Integer godinaStudija = Integer.valueOf(godinaStudijaOptional.get());

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni godinu studija studenta");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Student student = Database.getStudentByID(id);

                    Database.izmjeniGodinuStudija(id, godinaStudija);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili godinu studija studenta");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Student: godina studija",
                            student.getId(),
                            student.getGodinaStudija().toString(),
                            godinaStudija.toString(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene godine studija studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje 'ID' mora biti cjelobrojna vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniProsjekButton() {
        try {
            String idString;
            String prosjekOcjenaString;

            if((idString = idTextField.getText()).isEmpty()) throw new NepotpunUnosPodatakaException("ID");
            if((prosjekOcjenaString = prosjekOcjenaTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("prosjek ocjena");

            Integer id = Integer.valueOf(idString);
            BigDecimal prosjekOcjena = new BigDecimal(prosjekOcjenaString);

            if(prosjekOcjena.compareTo(BigDecimal.valueOf(1)) < 0 || prosjekOcjena.compareTo(BigDecimal.valueOf(5)) > 0)
                throw new NeispravanProsjekException();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Izmjeni prosjek ocjena studenta");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Student student = Database.getStudentByID(id);

                    Database.izmjeniProsjekOcjena(id, prosjekOcjena);

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili prosjek ocjena studenta");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Student: prosjek ocjena",
                            student.getId(),
                            student.getProsjekOcjena().toString(),
                            prosjekOcjena.toString(),
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene prosjeka ocjena studenta");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NeispravanProsjekException e) {
            ApplicationProject.logger.error("Neispravan unos prosjeka ocjena prilikom izmjene prosjeka studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unešen je neispravan prosjek ocjena");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void izmjeniFakultetButton() {
        try {
            String idString;
            Optional<String> fakultetOptional;

            if((idString = idTextField.getText()).isEmpty()) throw new NepotpunUnosPodatakaException("ID");
            if((fakultetOptional = Optional.ofNullable(fakultetComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("fakultet");

            Integer id = Integer.valueOf(idString);
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
            dialog.setHeaderText("Izmjeni fakultet studenta");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Student student = Database.getStudentByID(id);

                    Database.izmjeniFakultet(id, fakultet.getRedniBroj());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText("Uspješno ste izmjenili fakultet studenta");
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Student: fakultet",
                            student.getId(),
                            student.getFakultet().toString(),
                            fakultetString,
                            ApplicationProject.korisnickiPodaci,
                            LocalDateTime.now()
                    );
                    SerijalizirajThread thread = new SerijalizirajThread(promjena);
                    Executor executor = Executors.newSingleThreadExecutor();
                    executor.execute(thread);
                }
            }

        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom izmjene fakulteta studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesite brojčanu vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom izmjene studenta");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

}
