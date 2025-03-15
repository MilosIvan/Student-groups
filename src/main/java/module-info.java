module com.example.projekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;
    requires java.xml;


    exports com.example;
    opens com.example to javafx.fxml;
    exports com.example.utils;
    opens com.example.utils to javafx.fxml;
    exports com.example.controllers;
    opens com.example.controllers to javafx.fxml;
    exports com.example.filters;
    opens com.example.filters to javafx.fxml;
    exports com.example.model;
    opens com.example.model to javafx.fxml;
    exports com.example.dogadaji;
    opens com.example.dogadaji to javafx.fxml;
    exports com.example.iznimke;
    opens com.example.iznimke to javafx.fxml;
}