/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import C195.Main;
import View_controller.LoginController;
import java.time.Instant;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.TemporalAccessor;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author charlesziegler
 */
public class AppointmentDB {
     public static  ObservableList<Appointment> appointments = FXCollections.observableArrayList();
//     private static ZonedDateTime zdt = ZonedDateTime.now();
     private static DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime( FormatStyle.SHORT ).withZone(ZoneId.of("Z"));
     static DateFormat FORMATTER = new SimpleDateFormat("MM/dd hh:mma");
    
    
     public static void addAppointment(Appointment appointment) {
         PreparedStatement stmt;
         String query;
         
        try {
            if (appointment.getAppointmentId() > 0) {
                System.out.println("KnownAppointment");
                query = "UPDATE appointment SET title=?, descrption=?, type=?, start=?, end=? lastUpdate=?, lastUpdateBy=? "
                        + "WHERE appointmentId=?";
                stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, appointment.getType());
                stmt.setString(2, appointment.getDescription());
                stmt.setString(3, appointment.getType());
                stmt.setTimestamp(4, Timestamp.valueOf(appointment.getStart()));
                stmt.setTimestamp(5, Timestamp.valueOf(appointment.getEnd()));
                stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(7, LoginController.loggedinUser.getName());
                stmt.setInt(8, appointment.getAppointmentId());
                
                int row = stmt.executeUpdate();
                System.out.println(row + "updated in appointments");
                         
            } else {
                 //create new appointment
                query = "INSERT INTO appointment (appointmentId, customerId, userId, title, description, location, contact, type, url, start, " + 
                        "end, createDate, createdBy, lastUpdate, lastUpdateBy)" 
                        + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setInt(1, appointment.getAppointmentId());
                stmt.setInt(2, appointment.getCustomerId());
                stmt.setInt(3, LoginController.loggedinUser.getID());
                stmt.setString(4, appointment.getType());
                stmt.setString(5, appointment.getDescription());
                stmt.setString(6, "");
                stmt.setString(7,LoginController.loggedinUser.getName());
                stmt.setString(8, appointment.getType());
                stmt.setString(9, "");
                stmt.setTimestamp(10, Timestamp.valueOf(appointment.getStart()));
                stmt.setTimestamp(11, Timestamp.valueOf(appointment.getEnd()));
                stmt.setTimestamp(12, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(13,LoginController.loggedinUser.getName());
                stmt.setTimestamp(14, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(15, LoginController.loggedinUser.getName());
                
                
                
                int row = stmt.executeUpdate();
                System.out.println(row + " Created in appointments");
                       
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
     public static void deleteAppointment(Appointment appointment) {
        PreparedStatement stmt;
        try {
            String query = "DELETE appointment FROM appointment WHERE appointmentId = ?";
            stmt = Main.databaseConnection.prepareStatement(query);
            stmt.setInt(1, appointment.getAppointmentId());
           stmt.execute();
        } catch(SQLException e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }
      public static void getAppointments() {
        PreparedStatement stmt;
        String query;
       

        try {
            query = "SELECT appointment.appointmentId, appointment.customerId, appointment.title, "
                            + "appointment.description, appointment.start, appointment.end, appointment.createdBy, "
                            + "appointment.location, appointment.contact, customer.customerName "
                            + "FROM appointment, customer "
                            + "WHERE appointment.customerId = customer.customerId ";
            stmt = Main.databaseConnection.prepareStatement(query);
                    
        
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(rs.getInt("appointment.appointmentId"));
                appointment.setCustomerId(rs.getInt("appointment.customerId"));
                appointment.setType(rs.getString("appointment.title"));
                appointment.setDescription(rs.getString("appointment.description"));
                appointment.setLocation(rs.getString("appointment.location"));
                appointment.setContact(rs.getString("appointment.contact"));
                Instant startInstant = rs.getTimestamp("appointment.start").toInstant();
                String start = formatter.format( startInstant );
//                Timestamp start = rs.getTimestamp("appointment.start");
                appointment.setStart(startInstant.toString());
                appointment.setLocalStart(start);
                Instant endInstant = rs.getTimestamp("appointment.end").toInstant();
                String end = formatter.format( endInstant );
//                Timestamp end = rs.getTimestamp("appointment.end");
                appointment.setEnd(endInstant.toString());
                appointment.setLocalEnd(end);
                appointment.setCustName(rs.getString("customer.customerName"));
                appointments.add(appointment);
                System.out.println(startInstant.toString());
//                System.out.println(start.toInstant().toString());
//                System.out.println(startFormatted);
//                System.out.println(end.toLocalDateTime().toString());
                System.out.println(start);
                System.out.println(end);
                
                
            }
        } catch (SQLException e) {
            System.out.println("Issue with SQL");
            e.printStackTrace();
        }
       
    }
      
       public static void refreshAppointmentTable(){
        appointments.clear();
        getAppointments();      
    }
      
}



