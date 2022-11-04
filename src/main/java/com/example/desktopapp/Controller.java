package com.example.desktopapp;

import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.entities.Payments;
import com.example.desktopapp.models.CustomerDAO;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {
    @FXML
    private ComboBox<String> comboBox;

    @FXML
    private TextField payment;

    @FXML
    private CheckBox poxing;

    private double paymentCost = 12;
    @FXML
    private ImageView imgView;
    private double poxingCost = 3.0;
    private double boxCost = 4.0;

    private File selectedFile;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<String> names = FXCollections.observableArrayList(Arrays.asList("Box1", "Box2", "Box3", "man"));
        comboBox.setItems(names);
        payment.setText(String.valueOf(paymentCost));


        comboBox.valueProperty().addListener(new ChangeListener<String>() {
            double currentVal = boxCost + Double.parseDouble(payment.getText());

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

                if (comboBox.getValue().contains("Box")) {


                    payment.setText(String.valueOf(currentVal));
                } else {
                    currentVal = Double.parseDouble(payment.getText()) - boxCost;
                    payment.setText(String.valueOf(currentVal));
                    currentVal = boxCost + Double.parseDouble(payment.getText());
                }
            }
        });
        poxing.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                if (poxing.isSelected()) {
                    payment.setText(String.valueOf(Double.parseDouble(payment.getText()) + poxingCost));

                } else {
                    payment.setText(String.valueOf(Double.parseDouble(payment.getText()) - poxingCost));
                }
            }
        });
    }


    @FXML
    void fetcher(ActionEvent event) throws SQLException {

        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Forgot image");
            alert.setContentText("Image mad ilowdey");
            Optional<ButtonType> confirm = alert.showAndWait();

            if (confirm.get().getButtonData().isDefaultButton()) {
                System.out.println("Ok selected");
            } else {
                System.out.println("Canceld...");
            }

        } else {
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    void uploadHandler(ActionEvent event) throws FileNotFoundException {

        FileChooser chooser = new FileChooser();
        selectedFile = chooser.showOpenDialog(null);
        //Image image=File;
        Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
        imgView.setImage(image);
        imgView.setX(50);
        imgView.setY(25);

        //setting the fit height and width of the image view
        imgView.setFitHeight(455);
        imgView.setFitWidth(500);

        //Setting the preserve ratio of the image view
        imgView.setPreserveRatio(true);
    }
//
//        System.out.println(CustomerDAO.fetcher() + "\n");
//
//        for (Customers customer : CustomerDAO.fetcher()) {
//            if (customer.getPayments().get(0).getExpDate().isBefore(LocalDate.now())) {
//                System.out.println(customer.getFirstName() + " " + customer.getPayments().get(0).getExpDate() + " Is out dated");
//
//                CustomerDAO.update(customer.getPayments().get(0));
////                System.out.println(LocalDate.now());
//            } else {
//                System.out.println(customer.getFirstName() + " " + customer.getPayments().get(0).getExpDate() + " Is Active");
//            }
//
//        }
//        LocalDate today = LocalDate.now();
//
//        String formattedDate = today.format(DateTimeFormatter.ofPattern("dd-MMM-yy"));
//
//        System.out.println(formattedDate);
//        LocalDate expDate = LocalDate.parse("2022-10-11");
//
//        if (expDate.isBefore(LocalDate.now())) {
//            System.out.println(expDate + " Out dated " + LocalDate.now());
//        } else {
//            System.out.println(expDate + " Active " + LocalDate.now());
//        }
//
//    }
//    }
}
