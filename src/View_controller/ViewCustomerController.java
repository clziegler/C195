/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import Model.Customer;
import Model.CustomerDB;
import Utility.Error_Handler;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
    @FXML
    private TextField custIDFiled;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custIDFiled.setDisable(true);
        
        // TODO
    } 
    public void setCustomer(Customer customer){
        custAdd1Field.setText(customer.getAddress());
        custAdd2Field.setText(customer.getAddress2());
        custPhoneField.setText(customer.getPhone());
        custCityField.setText(customer.getCity());
        custCountryField.setText(customer.getCountry());
        custPostCodeField.setText(customer.getPostalCode());
        custNameField.setText(customer.getName());
        custIDFiled.setText(Integer.toString(customer.getCustomerId()));
    }
    

    @FXML
    private void addCustButtinHandler(ActionEvent event) throws IOException {
        
        System.out.println("You clicked view customer.");
        String name = custNameField.getText();
        String add1 = custAdd1Field.getText();
        String add2 = custAdd2Field.getText();
        String phone = custPhoneField.getText();
        String city = custCityField.getText();
        String country = custCountryField.getText();
        String post = custPostCodeField.getText();
        
        if(Error_Handler.checkCustoemrFields(name, add1, add2, phone, city, country, post)){
            Customer customer = new Customer();
            customer.setName(name);
            customer.setAddress(add1);
            customer.setAddress2(add2);
            customer.setPhone(phone);
            customer.setCity(city);
            customer.setCountry(country);
            customer.setPostalCode(post);
            CustomerDB.addCustomer(customer);   
            CustomerDB.refreshCustomerTable();
             
            Node node = (Node)event.getSource();
            Stage stage = (Stage)node.getScene().getWindow();
            stage.close();
                
        }
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
