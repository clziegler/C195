/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author charlesziegler
 */
public class Error_Handler {
    private static ResourceBundle rb = ResourceBundle.getBundle("Language/lang", Locale.getDefault());
     
     public static boolean checkLoginFields(String name, String password) {
        StringBuilder errors = new StringBuilder();

        if (name.trim().isEmpty()) {
            errors.append(rb.getString("login_error_noname") + "\n");

        }
        if (password.trim().isEmpty()) {
            errors.append(rb.getString("login_error_nopassword") + "\n");
        }
        
        if (errors.length() > 0) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle(rb.getString("login_warning"));
            alert.setHeaderText(rb.getString("missing_fields"));
            alert.setContentText(errors.toString());

            alert.showAndWait();
            return false;
        }
        

        return true;
    } 

   
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
    public static boolean checkAppointmentFields(String startTime, String endTime, String type, LocalDate localDate){
         StringBuilder errors = new StringBuilder();

        if (startTime ==null) {
            errors.append("-Please Choose Start Time \n");

        }
        if (endTime == null) {
            errors.append("-Please Choose End Time \n");
        }
        if (type == null) {
            errors.append("-Please Choose Appointment Type \n");

        }
         if (localDate == null) {
            errors.append("-Please Choose Date \n");

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
    public static String verifyTimes(String startTime, String endTime) {
        StringBuilder errors = new StringBuilder();
        // Ensure timestamps are valid
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        try {
            LocalTime startLocal = LocalTime.parse(startTime, timeFormatter);
            LocalTime endLocal = LocalTime.parse(endTime, timeFormatter);

            // Ensure start time occurs before end time
            if(startLocal.isAfter(endLocal)) {
                errors.append("Start time occurs AFTER end time, please fix.");
            }
        } catch(Exception e) {
            errors.append("Issue parsing time.  Very broken");
        }
        return errors.toString();
    }
    
}
