package com.example;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.IOException;

public class ApplicationProject extends javafx.application.Application {
    public static Stage mainStage;
    public static KorisnickiPodaci korisnickiPodaci;
    public static final Logger logger = LoggerFactory.getLogger(ApplicationProject.class);

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(ApplicationProject.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Hello");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}