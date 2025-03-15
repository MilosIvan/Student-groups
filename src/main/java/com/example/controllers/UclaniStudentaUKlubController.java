package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepostojeciIDException;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.model.KlubStudenata;
import com.example.model.Student;
import com.example.threads.SerijalizirajThread;
import com.example.utils.Database;
import com.example.utils.Promjena;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UclaniStudentaUKlubController {
    @FXML
    private TextField idStudentaTextField;
    @FXML
    private ComboBox<KlubStudenata> klubComboBox;

    public void initialize() {
        List<KlubStudenata> kluboviStudenata = Database.getKluboviStudenata();
        klubComboBox.setItems(FXCollections.observableList(kluboviStudenata));
    }

    public void uclaniButton() {
        try {
            String idString;
            Optional<KlubStudenata> klubStudenataOptional;

            if((idString = idStudentaTextField.getText()).isEmpty()) throw new NepotpunUnosPodatakaException("ID");
            if((klubStudenataOptional = Optional.ofNullable(klubComboBox.getValue())).isEmpty())
                throw new NepotpunUnosPodatakaException("klub studenata");

            Integer id = Integer.valueOf(idString);
            KlubStudenata klubStudenata = klubStudenataOptional.get();

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Učlani studenta u klub");
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {
                    Student student = Database.getStudentByID(id);

                    Database.uclaniStudentaUKlub(id, klubStudenata.getId());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Pokušaj uspješan");
                    alert.setHeaderText(
                            "Uspješno ste učlanili studenta " + student.getNaziv() + " u " + klubStudenata.getNaziv()
                    );
                    alert.showAndWait();

                    Promjena promjena = new Promjena(
                            "Student " + student.getNaziv() + " učlanjen u " + klubStudenata.getNaziv(),
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
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom učlanjenja studenta u klub studenata");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NumberFormatException e) {
            ApplicationProject.logger.error("Number format exception prilikom učlanjenja studenta u klub studenata");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje 'ID' mora biti cjelobrojna vrijednost");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepostojeciIDException e) {
            ApplicationProject.logger.error("Unesen nepostojeći ID prilikom učlanjenja studenta u klub studenata");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Unesen je nepostojeći ID");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }
}