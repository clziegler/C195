/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utility;


import Model.Appointment;
import Model.AppointmentDB;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.ResourceBundle;

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
        alert.setTitle(rb.getString("login_warning"));
        alert.setHeaderText(rb.getString("login_error_fail"));
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static Boolean checkStartEndTimes(String startTime, String endTime) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
        try {
            LocalTime start = LocalTime.parse(startTime, timeFormatter);
            LocalTime end = LocalTime.parse(endTime, timeFormatter);

            // Ensure start time occurs before end time
            if(start.isBefore(end)) {
                
                return true;
            }
        } catch(Exception e) {
            System.out.println("Something strange haopoened here...");
            
        }
            Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Start Time is After End TIme:");
                alert.setContentText("Please Choose a Start Time Before End Time");
        return false;
    }
    
    /**
     *
     * @param start
     * @param end
     * @return
     */
    public static Boolean checkOverlap(Timestamp start, Timestamp end){
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
        for (Appointment check : AppointmentDB.appointments){
            LocalDateTime newStart = start.toLocalDateTime();
            LocalDateTime newEnd = end.toLocalDateTime();
            LocalDateTime checkStartParsed = LocalDateTime.parse(check.getLocalStart(), formatter1);
            LocalDateTime checkEndParsed =  LocalDateTime.parse(check.getLocalEnd(), formatter1);
            LocalDateTime checkStart = AppointmentDB.convertLDTToUTC(checkStartParsed);
            LocalDateTime checkEnd = AppointmentDB.convertLDTToUTC(checkEndParsed);
           
            try{
                if(newStart.isBefore(checkEnd) && newEnd.isAfter(checkStart)){
                        
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Overbooked Appointment Time:");
                alert.setContentText("Appointment Times Overlap");

            alert.showAndWait();
                   return false;
                }
            }catch(Exception e){
                 System.out.println("Something strange haopoened here...");          
            }   
            
        } 
    
        return true;
    }
    
}
