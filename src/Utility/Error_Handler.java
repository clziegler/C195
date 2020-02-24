/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;

import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author charlesziegler
 */
public class Error_Handler {
//    private static ResourceBundle rb = ResourceBundle.getBundle("lang", Locale.getDefault());
     
     public static boolean checkLoginFields(String name, String password) {
        StringBuilder errors = new StringBuilder();

        if (name.trim().isEmpty()) {
            errors.append("-Please Enter User Name \n");

        }
        if (password.trim().isEmpty()) {
            errors.append("-Please Enter Password \n");
        }
        
        if (errors.length() > 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty:");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }
        

        return true;
    } 

    /**
     *
     * @param name
     * @param add1
     * @param add2
     * @param phone
     * @param city
     * @param country
     * @param postCode
     * @return
     */
    public static boolean checkCustoemrFields(String name, String add1, String add2, String phone, String city, String country, String postCode) {
        StringBuilder errors = new StringBuilder();

        if (name.trim().isEmpty()) {
            errors.append("-Please Enter User Name \n");

        }
        if ((add1.trim().isEmpty()) && (add2.trim().isEmpty())) {
            errors.append("-Please Enter Address \n");
        }
        if (phone.trim().isEmpty()) {
            errors.append("-Please Enter phone number \n");

        }
         if (city.trim().isEmpty()) {
            errors.append("-Please Enter City \n");

        }
          if (country.trim().isEmpty()) {
            errors.append("-Please Enter Country \n");

        }
           if (postCode.trim().isEmpty()) {
            errors.append("-Please Enter Postal code \n");

        }
        
        
        if (errors.length() > 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty:");
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }
        

        return true;
    } 
    static public void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning!");
        alert.setHeaderText("There may be an issue.");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
