package com.example.controllers;

import com.example.ApplicationProject;
import com.example.KorisnickiPodaci;
import com.example.iznimke.NepotpunUnosPodatakaException;
import com.example.iznimke.PostojeceKorisnickoImeException;
import com.example.utils.Enkripcija;
import com.example.utils.OtvaranjeEkrana;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.*;
import java.nio.file.Files;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.nio.file.StandardOpenOption.APPEND;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField passwordField;

    private KorisnickiPodaci procitajPodatke() throws NepotpunUnosPodatakaException {
        String usernameInput = usernameTextField.getText();
        String passwordInput = passwordField.getText();
        if(usernameInput.isEmpty()) {
            throw new NepotpunUnosPodatakaException("korisnicko ime");
        }
        if(passwordInput.isEmpty()) {
            throw new NepotpunUnosPodatakaException("lozinka");
        }

        return new KorisnickiPodaci(usernameInput, passwordInput);
    }

    public void prijavaButton() {
        try (BufferedReader reader = new BufferedReader(new FileReader("dat//userData.txt"))) {
            KorisnickiPodaci podaci = procitajPodatke();

            Optional<String> optional;
            boolean usernamePostoji = false;
            while ((optional = Optional.ofNullable(reader.readLine())).isPresent()) {
                String username = optional.get();
                if(podaci.usernameInput().equals(username)) {
                    usernamePostoji = true;
                    String password = reader.readLine();
                    if(Enkripcija.encryptString(podaci.passwordInput()).equals(password)) {
                        podaci.setAdmin(Boolean.parseBoolean(reader.readLine()));
                        ApplicationProject.korisnickiPodaci = podaci;
                        OtvaranjeEkrana.openFirstscreen();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Neuspješna prijava");
                        alert.setHeaderText("Pogrešna lozinka");
                        alert.setContentText("Pokušajte ponovno");
                        alert.showAndWait();
                        break;
                    }
                }
                reader.readLine();
                reader.readLine();
            }
            if(!usernamePostoji) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Neuspješna prijava");
                alert.setHeaderText("Korisničko ime ne postoji");
                alert.setContentText("Pokušajte ponovno");
                alert.showAndWait();
            }

        } catch (IOException | NoSuchAlgorithmException e) {
            ApplicationProject.logger.error("Greška prilikom čitanja datoteke userData");
            System.out.println(e.getMessage());
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom prijave u aplikaciju");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neuspješna prijava");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }

    public void registracijaButton() {
        Optional<KorisnickiPodaci> optionalPodaci;
        try (Stream<String> stream = Files.lines(new File("dat//userData.txt").toPath())) {
            optionalPodaci = Optional.of(procitajPodatke());

            List<String> lista = stream.toList();
            for (String line : lista) {
                if (optionalPodaci.get().usernameInput().equals(line)) {
                    throw new PostojeceKorisnickoImeException();
                }
            }

            String hashedPass = Enkripcija.encryptString(optionalPodaci.get().passwordInput());

            Files.writeString(new File("dat//userData.txt").toPath(),
                    optionalPodaci.get().usernameInput() + "\n"
                            + hashedPass + "\n"
                            + optionalPodaci.get().admin() + "\n", APPEND);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registracija uspješna");
            alert.setHeaderText("Registracija uspješna");
            alert.showAndWait();

        } catch (IOException | NoSuchAlgorithmException e) {
            ApplicationProject.logger.error("Greška prilikom registracije");
            System.out.println(e.getMessage());
        } catch (PostojeceKorisnickoImeException e) {
            ApplicationProject.logger.error("Uneseno postojece korisnicko ime prilikom registracije");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neuspješna registracija");
            alert.setHeaderText("Korisničko ime je zauzeto");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        } catch (NepotpunUnosPodatakaException e) {
            ApplicationProject.logger.error("Nepotpun unos podataka prilikom registracije");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Neuspješna prijava");
            alert.setHeaderText("Polje '" + e.getMessage() + "' mora biti popunjeno");
            alert.setContentText("Pokušajte ponovno");
            alert.showAndWait();
        }
    }
}