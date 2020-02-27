/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import C195.Main;
import Model.AppointmentDB;
import Model.CustomerDB;
import Model.User;
import Utility.Error_Handler;
import static View_controller.LoginController.loggedinUser;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class UserLoginController implements Initializable {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button quitButton;
    @FXML
    private Text welcomeText;
    
    private static final SimpleDateFormat logTime= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
    private static Date date = new Date(System.currentTimeMillis());
    public String dateToday = logTime.format(date);
  
    private static ResourceBundle rb = ResourceBundle.getBundle("Language/lang", Locale.getDefault());
    
    public static User loggedinUser;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         rb = ResourceBundle.getBundle("Language/lang", Locale.getDefault());
        loginButton.requestFocus();
        welcomeText.setText(rb.getString("instructions"));
        quitButton.setText(rb.getString("quit_button"));
        loginButton.setText(rb.getString("login_button"));
        usernameField.setPromptText(rb.getString("login_button"));
        passwordField.setPromptText(rb.getString("password"));
     
        
        
    }

    @FXML
    private void loginButtonHandler(ActionEvent event) throws IOException {
        System.out.println("You clicked login");
        String userTry = usernameField.getText();
        String passwordTry = passwordField.getText();
        User authUser;

        if(Error_Handler.checkLoginFields(userTry, passwordTry)){
            User inputUser = new User(userTry, passwordTry);
            authUser = loginAtempt(inputUser);
//        Incorrect Login    
        if(authUser == null) { 
            Error_Handler.warningAlert(rb.getString("login_error_fail") );
        } else {
            //Login successful, set logged-in user
            loggedinUser = authUser;
            recordLogin();
            CustomerDB.refreshCustomerTable();
            AppointmentDB.refreshAppointmentTable();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(rb.getString("loged_in_title") );
            alert.setHeaderText(rb.getString("logged_in_header") );
            alert.setContentText(rb.getString("loged_in_content") );
            alert.showAndWait();
            Parent newPartParent = FXMLLoader.load(getClass().getResource("/View_controller/MainScreen.fxml"));
            Scene newPartScene = new Scene(newPartParent);
            Stage app_stage = new Stage();
            app_stage.setScene(newPartScene);
            app_stage.showAndWait();
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
            stmt.setString(2,inputUser.getPassword());
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
    //Writing log in local timezone of user
    private static void recordLogin() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("C195_user_log.txt", true));  
        writer.newLine(); 
        writer.write("User " + loggedinUser.getName() + " logged into system at: " + logTime.format(date));
        writer.close();
}
    
    
}