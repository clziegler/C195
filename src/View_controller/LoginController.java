/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import C195.Main;
import Model.User;
import Model.Error_Handler;
import Utility.Database;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane userName;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button quitButton;
    private final DateTimeFormatter logTime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss(z)");
    
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void loginButtonHandler(ActionEvent event) {
        System.out.println("You clicked login");
        String userTry = usernameField.getText();
        String passwordTry = passwordField.getText();
        User authUser;

        if(Error_Handler.checkLoginFields(userTry, passwordTry)){
            User inputUser = new User(userTry, passwordTry);
            authUser = loginAtempt(inputUser);
            
        if(authUser == null) { // login was incorrect or user not found
            Error_Handler.warningAlert("Fail!");
        } else { // login was valid
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("login_success_title");
            alert.setHeaderText("login_success_header");
            alert.setContentText("login_success_content");
            alert.showAndWait();
            // Sets current user for the current session
//                main.currentUser = validUser;
//                logLogin(inputUser.getUsername(), true);
//                main.rootLayoutController.setLoggedInUser(validUser.getUsername());
//                main.showAppointmentsScreen();
        }
        } 
    }

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        System.out.println("You clicked quit");
         System.exit(0);
    }
    
    private User loginAtempt(User inputUser) {
        User user = new User();
        PreparedStatement stmt = null;
        
        try {
            String query = "SELECT* FROM user WHERE userName=? AND password=?";
            stmt = Main.databaseConnection.prepareStatement(query);
            stmt.setString(1,inputUser.getName());
            System.out.println(inputUser.getName());
            stmt.setString(2,inputUser.getPassword());
            System.out.println(inputUser.getPassword());
            ResultSet rs = stmt.executeQuery();
     
            if (rs.next()) {
                System.out.println("Found user");
                user.setID(rs.getInt("userId"));
                user.setName(rs.getString("userName")); 
                user.setPassword(rs.getString("password"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return user;
    }
}