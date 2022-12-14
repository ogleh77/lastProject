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
    opens com.example.desktopapp.controllers.info to javafx.fxml;
    opens com.example.desktopapp.controllers.service to javafx.fxml;
    exports com.example.desktopapp;
    exports com.example.desktopapp.entity;
//    opens com.example.desktopapp.controllers.services to javafx.fxml;
}