module com.example.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires AnimateFX;
    requires com.jfoenix;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.desktopapp to javafx.fxml;
    opens com.example.desktopapp.controllers to javafx.fxml;
    exports com.example.desktopapp;
    opens com.example.desktopapp.controllers.services to javafx.fxml;
}