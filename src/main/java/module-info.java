module com.example.desktopapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires AnimateFX;
    requires com.jfoenix;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens com.example.desktopapp.entity to javafx.fxml;
    opens com.example.desktopapp to javafx.fxml;
    opens com.example.desktopapp.controllers to javafx.fxml;
    opens com.example.desktopapp.controllers.service to javafx.fxml;
    exports com.example.desktopapp;
    exports com.example.desktopapp.entity;


}