package com.example.desktopapp.controlls;

import com.example.desktopapp.Common;
import com.example.desktopapp.entities.Customers;
import com.example.desktopapp.models.CustomerDAO;
import com.jfoenix.controls.JFXRadioButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegistrationController extends Common implements Initializable {

    @FXML
    private TextField address;

    @FXML
    private JFXRadioButton female;

    @FXML
    private TextField firstName;

    @FXML
    private ImageView imgView;

    @FXML
    private TextField lastName;

    @FXML
    private JFXRadioButton male;

    @FXML
    private Label messageValidation;

    @FXML
    private TextField middleName;

    @FXML
    private TextField phone;

    @FXML
    private ComboBox<String> shift;

    @FXML
    private TextField weight;
    @FXML
    private Label customerId;

    private File selectedFile;

    private BorderPane borderPane;
    private final ObservableList<Control> mandatoryFields;
    private final ToggleGroup genderGroup;

    public RegistrationController() throws SQLException {
        this.mandatoryFields = FXCollections.observableArrayList();
        genderGroup = new ToggleGroup();
        CustomerDAO.nextSqId();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        shift.setItems(super.shift);
        Platform.runLater(() -> {
            customerId.setText(String.valueOf(CustomerDAO.id));
            mandatoryFields.addAll(firstName, middleName, lastName, phone, shift);
        });

    }

    @FXML
    void stepTwoHandler(ActionEvent event) throws FileNotFoundException {


        if (!isValid(mandatoryFields, genderGroup) || !checkPhone()) {

        } else {
            String gander = male.isSelected() ? "Male" : "Female";
            String _address = address.getText() != null ? address.getText().trim() : null;
            double _weight = ((!weight.getText().isEmpty() || !weight.getText().isBlank())) ? Double.parseDouble(weight.getText().trim()) : 65.0;
            String image = selectedFile != null ? selectedFile.getAbsolutePath() : null;
            int _customerId = Integer.parseInt(customerId.getText().trim());

            if (selectedFile == null) {
                Alert alert = message(Alert.AlertType.CONFIRMATION, "Sawir ka maad ilowdey mise ogaaan bad u dhaaftay?\n" +
                        "Sawirku wuxuu kaa cawinya inad si fudud u garan karto kuwa is qariya :)", "No such image");

                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("wan ogahay");
                ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("waan ilabey");
                Optional<ButtonType> confirm = alert.showAndWait();

                if (confirm.get().getButtonData().isDefaultButton()) {
                    System.out.println("Ok selected");
                } else if (confirm.get().getButtonData().isCancelButton()) {
                    alert.close();

                    uploadImage();
                    System.out.println("Canceld...");
                }
            }

            Customers customers = new Customers(_customerId, firstName.getText(), middleName.getText(), lastName.getText()
                    , phone.getText(), gander, shift.getValue(), _address, image, _weight, "Ogleh");

            try {
                message(Alert.AlertType.INFORMATION, "Buuxi formka paymentka", "Created");
                ///  CustomerDAO.insertCustomer(customers);

                FXMLLoader loader = openWindow("/com/example/desktopapp/views/payments.fxml", borderPane, null, null, null);
                PaymentController controller = loader.getController();

                controller.setCustomers(customers);
            } catch (Exception e) {
                e.printStackTrace();
                message(Alert.AlertType.ERROR, "Khalad ba ka dhacay " + e.getMessage(), "Error");
            }


        }

    }

    @FXML
    void imageUploadHandler(ActionEvent event) {
        try {
            uploadImage();
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }


    }

    @FXML
    void resetHandler(ActionEvent event) throws SQLException {

    }


    private boolean checkPhone() {
        boolean isValid;
        if (phone.getText().matches("[0-9]+")) {
            isValid = true;
            messageValidation.setVisible(false);
            phone.setStyle(null);

        } else {
            isValid = false;
            phone.setStyle("-fx-border-color: red;");
            messageValidation.setVisible(true);
            messageValidation.setText("Lanbar qudha lo ogol yahay");
        }
        return isValid;
    }

    private void uploadImage() throws FileNotFoundException {
        FileChooser chooser = new FileChooser();
        selectedFile = chooser.showOpenDialog(null);
        if (selectedFile != null) {
            Image image = new Image(new FileInputStream(selectedFile.getAbsolutePath()));
            imgView.setImage(image);
            imgView.setX(50);
            imgView.setY(25);

            //setting the fit height and width of the image view
            imgView.setFitHeight(150);
            imgView.setFitWidth(200);
            imgView.setPreserveRatio(true);
        }
        //Setting the preserve ratio of the image view

    }

    public void setBorderPane(BorderPane borderPane) {
        this.borderPane = borderPane;
    }
}
