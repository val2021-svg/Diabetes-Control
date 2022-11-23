module com.example.enseignement_integration {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.enseignement_integration to javafx.fxml;
    exports com.example.enseignement_integration;
}