/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import C195.Main;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import View_controller.UserLoginController;
import com.mysql.jdbc.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
 

/**
 *
 * @author charlesziegler
 */
public class CustomerDB {
   public static ObservableList<Customer> customers = FXCollections.observableArrayList();
      
    private final DateTimeFormatter logTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss(z)");
    private static Customer addCountry(Customer customer){
        PreparedStatement stmt;
         
        try {
             //check if Country exists
            String query = "SELECT* FROM country WHERE country=?";
            stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getCountry());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("Known County");
                int countryId = rs.getInt("countryId");
                customer.setCountryId(countryId);
                return customer;
                        
            } else {
                //create new country
                query = "INSERT INTO country (country, createDate, createdBy,lastUpdateBy)" + "VALUES (?,?,?,?)";
                stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, customer.getCountry());
                stmt.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(3, "");
                stmt.setString(4, "");
                int row = stmt.executeUpdate();
                System.out.println("Country "+ row);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCountryId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                int countryID = rs.getInt("countryId");
                customer.setCountryId(countryID);
                return customer;
                
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    private static Customer addCity(Customer customer){
        PreparedStatement stmt;
         
        try {
             //check if City exists
            String query = "SELECT* FROM city WHERE city=?";
            stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, customer.getCity());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                System.out.println("Known City");            
                customer.setCityId(rs.getInt(1));
                return customer;
                        
            } else {
                //create new city
                query = "INSERT INTO city (city, countryId, createDate,createdBy,lastUpdateBy)" + "VALUES (?, ?,?,?,?)";
                stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, customer.getCity());
                stmt.setInt(2, customer.getCountryId());
                stmt.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(4, UserLoginController.loggedinUser.getName());
                 stmt.setString(5, UserLoginController.loggedinUser.getName());
                int row = stmt.executeUpdate();
                System.out.println("city" + row);
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setCityId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }                
                return customer;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }
    
    private static Customer addAddress(Customer customer) {
        PreparedStatement stmt;
        try {
            //check if address already exisits
            String query = "SELECT* FROM address WHERE address=? AND address2=? AND postalCode=? AND phone=?";
            stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1,customer.getAddress());          
            stmt.setString(2,customer.getAddress2());
            stmt.setString(3, customer.getPostalCode());
            stmt.setString(4, customer.getPhone());
            ResultSet rs = stmt.executeQuery();
     
            if (rs.next()) {
                System.out.println("Known Address");
                int addressId = rs.getInt("addressID");
                customer.setAddressId(addressId);
//                customer.setCountryId(rs.getInt(1))
                return customer;
                        
            } else {
                 //create new address
                query = "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy)" 
                        + "VALUES (?,?,?,?,?,?,?,?)";
                stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, customer.getAddress());
                stmt.setString(2, customer.getAddress2());
                stmt.setInt(3, customer.getCityId());
                stmt.setString(4, customer.getPostalCode());
                stmt.setString(5, customer.getPhone());
                stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(7, UserLoginController.loggedinUser.getName());
                stmt.setString(8, UserLoginController.loggedinUser.getName());
                int row = stmt.executeUpdate();
                System.out.println("Address " + row);
                
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        customer.setAddressId(generatedKeys.getInt(1));
                    }
                    else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
                              
                return customer;
              
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customer;
    }
    public static void addCustomer(Customer customer) {
         PreparedStatement stmt;
         String query;
        try {
            if (customer.getCustomerId() > 0) {
                System.out.println("Known Customer");
                addCountry(customer);
                addCity(customer);
                addAddress(customer);
                query = "UPDATE customer SET customerName=?, addressId=?, active=?, lastUpdate=?, lastUpdateBy=? "
                        + "WHERE customerId=?";
                stmt = Main.databaseConnection.prepareStatement(query);
                stmt.setString(1, customer.getName());
                stmt.setInt(2, customer.getAddressId());
                stmt.setInt(3, 1);
                stmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(5, UserLoginController.loggedinUser.getName());
                stmt.setInt(6, customer.getCustomerId());
                int row = stmt.executeUpdate();
                System.out.println("Customer " + row);
                         
            } else {
                addCountry(customer);
                addCity(customer);
                addAddress(customer);
                 //create new customer
                query = "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdate, lastUpdateBy)" 
                        + "VALUES (?,?,?,?,?,?,?)";
                stmt = Main.databaseConnection.prepareStatement(query);
                stmt.setString(1, customer.getName());
                stmt.setInt(2, customer.getAddressId());
                stmt.setInt(3, 1);
                stmt.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(5,UserLoginController.loggedinUser.getName());
                stmt.setTimestamp(6, new java.sql.Timestamp(System.currentTimeMillis()));
                stmt.setString(7, UserLoginController.loggedinUser.getName());
                int row = stmt.executeUpdate();
                System.out.println(row);
                       
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void deleteCustomer(Customer customer) {
        PreparedStatement stmt;
        try {
            String query = "DELETE customer.*, address.* FROM customer, address WHERE customer.customerId = ? AND customer.addressId = address.addressId";
            stmt = Main.databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, customer.getCustomerId());
           stmt.execute();
        } catch(SQLException e) {
            System.out.println("SQL error");
            e.printStackTrace();
        }
    }
    

    public static void getCustomers() {
           
            try {
                PreparedStatement stmt;
                String query = 
                    "SELECT customer.customerName, customer.customerId, address.phone, "
                        + "address.address, address.address2, address.postalCode, address.addressId, "
                        + "city.city, city.cityId, country.country, country.countryId "
                        + "FROM customer, address, city, country "
                        + "WHERE customer.addressId = address.addressId "
                        + "AND address.cityId = city.cityId AND city.countryId = country.countryId";
               
                stmt = Main.databaseConnection.prepareStatement(query);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    Customer customer = new Customer();
                    customer.setName(rs.getString("customer.customerName"));
                    customer.setPhone(rs.getString("address.phone"));
                    customer.setCustomerId(rs.getInt("customer.customerId"));
                    customer.setAddress(rs.getString("address.address"));
                    customer.setAddress2(rs.getString("address.address2"));
                    customer.setCity(rs.getString("city.city"));
                    customer.setPostalCode(rs.getString("address.postalCode"));
                    customer.setCountry(rs.getString("country.country"));
                    customer.setAddressId(rs.getInt("address.addressId"));
                    customer.setCityId(rs.getInt("city.cityId"));
                    customer.setCountryId(rs.getInt("country.countryId"));
                    customers.add(customer);
                }
            } catch (SQLException e) {
                System.out.println("SQL error");
                e.printStackTrace();
            }
            
            
          
            
    
    }
    public static void refreshCustomerTable(){
        AppointmentDB.refreshAppointmentTable();
        customers.clear();
        getCustomers();
        
        
    }
}