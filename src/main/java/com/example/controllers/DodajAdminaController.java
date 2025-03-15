package com.example.controllers;

import com.example.ApplicationProject;
import com.example.iznimke.NepotpunUnosPodatakaException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class DodajAdminaController {
    @FXML
    private TextField korisnickoImeTextField;

    public void dodajAdmina() {
        try (Stream<String> stream = Files.lines(new File("dat//userData.txt").toPath())) {
            String korisnickoImeInput;

            if((korisnickoImeInput = korisnickoImeTextField.getText()).isEmpty())
                throw new NepotpunUnosPodatakaException("korisnicko ime");

            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Potvrda");
            dialog.setHeaderText("Dodaj novog admina: " + korisnickoImeInput);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
            dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
            Optional<ButtonType> result = dialog.showAndWait();

            if(result.isPresent()) {
                if(result.get() == ButtonType.OK) {

                    List<String> lista = new ArrayList<>(stream.toList());
                    boolean korisnickoImePostoji = false;
                    for(int i = 0; i < lista.size(); i += 3) {
                        if(lista.get(i).equals(korisnickoImeInput)) {
                            lista.set(i + 2, "true");
                            korisnickoImePostoji = true;

                            StringBuilder fileContents = new StringBuilder();
                            for(String str : lista) {
                                fileContents.append(str).append("\n");
                            }

                            Files.writeString(new File("dat//userData.txt").toPath(), fileContents);
                        }
                    }

                    if(!korisnickoImePostoji) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Pokušaj neuspješan");
                        alert.setHeaderText("Korisničko ime ne postoji");
                        alert.setContentText("Pokušajte ponovno");
                        alert.showAndWait();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Pokušaj uspješan");
                        alert.setHeaderText("Uspješno je dodan novi admin: " + korisnickoImeInput);
                        alert.showAndWait();
                    }
                }
            }
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom dodavanja novog admina");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Pokušaj neuspješan");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (IOException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja datoteke userData");
            System.out.println(e.getMessage());
        }
    }

}
