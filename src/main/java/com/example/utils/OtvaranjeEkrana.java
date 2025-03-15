package com.example.utils;

import com.example.ApplicationProject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.io.IOException;
import java.util.Optional;

public class OtvaranjeEkrana {
    @FXML
    private Menu prikaziIzmjeneMenu;
    @FXML
    private Menu adminMenu;
    @FXML
    private MenuItem dodajStudenta;
    @FXML
    private MenuItem izmjeniStudenta;
    @FXML
    private MenuItem ukloniStudenta;
    @FXML
    private MenuItem uclaniStudentaUKlubMenuItem;

    public void initialize() {
        if(!ApplicationProject.korisnickiPodaci.admin()) {
            prikaziIzmjeneMenu.setVisible(false);
            adminMenu.setVisible(false);
            dodajStudenta.setVisible(false);
            izmjeniStudenta.setVisible(false);
            ukloniStudenta.setVisible(false);
            uclaniStudentaUKlubMenuItem.setVisible(false);
        }
    }

    public static void openFirstscreen() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openStudentiPregled() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "studentiPregled.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openKluboviStudenataPregled() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "kluboviStudenataPregled.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openPoslovniDogadaj() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "poslovniDogadaj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openZabavniDogadaj() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "zabavniDogadaj.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openDodajStudenta() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(ApplicationProject.class.getResource(
                        "dodajStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openUkloniStudenta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                        "ukloniStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openIzmjeniStudenta() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                        "izmjeniStudenta.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajPredavanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                        "dodajPredavanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajProjekt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajProjekt.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajNatjecanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajNatjecanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajKviz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajKviz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajPutovanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajPutovanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openDodajZabavu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajZabavu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniPredavanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniPredavanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniProjekt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniProjekt.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniNatjecanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniNatjecanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniKviz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniKviz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniPutovanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniPutovanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openIzmjeniZabavu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "izmjeniZabavu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openPrikaziIzmjene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "prikaziIzmjene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniPredavanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniPredavanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniProjekt() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniProjekt.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniNatjecanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniNatjecanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniKviz() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniKviz.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniPutovanje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniPutovanje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public static void openUkloniZabavu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "ukloniZabavu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openStudentiPoKlubovima() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "studentiPoKlubovima.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void uclaniStudentaUKlub() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "uclaniStudentaUKlub.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openFiltrirajDogadaje() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "filtrirajDogadaje.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void openNajboljiStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "najboljiStudent.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }

    public void odjaviSe() throws IOException {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Potvrda");
        dialog.setHeaderText("Želite li se odjaviti");
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);
        Optional<ButtonType> result = dialog.showAndWait();

        if(result.isPresent()) {
            if(result.get() == ButtonType.OK) {
                FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                        "login.fxml"));
                Scene scene = new Scene(fxmlLoader.load(), 500, 500);
                ApplicationProject.mainStage.setScene(scene);
                ApplicationProject.mainStage.show();
            }
        }
    }

    public void dodajAdmina() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource(
                "dodajAdmina.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        ApplicationProject.mainStage.setScene(scene);
        ApplicationProject.mainStage.show();
    }
}
