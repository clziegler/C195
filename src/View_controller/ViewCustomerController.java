/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class ViewCustomerController implements Initializable {

    @FXML
    private TextField custAdd1Field;
    @FXML
    private TextField custAdd2Field;
    @FXML
    private TextField custPhoneField;
    @FXML
    private TextField custCityField;
    @FXML
    private TextField custCountryField;
    @FXML
    private TextField custPostCodeField;
    @FXML
    private TextField custNameField;
    @FXML
    private Button addCustButton;
    @FXML
    private Button cancelAddCustButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void addCustButtinHandler(ActionEvent event) {
         System.out.println("You clicked add customer.");
    }

    @FXML
    private void cancelAddCustButtonHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelAddCustButton.getScene().getWindow();
            stage.close();
            
        } else {
            System.out.println("You clicked cancel.");
        }
    }
    
}
