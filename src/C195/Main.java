/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package C195;

import Model.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Utility.Database;
import View_controller.LoginController;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author charlesziegler
 */
public class Main extends Application {
    public static Connection databaseConnection;
    
    public static void userLog(String path){
        //creates file to log users
        File log = new File(path);
        try {
            if (!log.exists()) {
                log.createNewFile();
                System.out.println("No log exsists, creating user log: " + path);
            } else {
                System.out.println("Existing log found: " + path);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void start(Stage stage) {
        
        try {
            Parent root =  FXMLLoader.load(Main.class.getResource("/View_controller/Login.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Customer Calendar");
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        userLog("C195_user_log.txt");
        Database.databaseConnection();
        databaseConnection = Database.getDatabaseConnection();
        launch(args);
        Database.disconnect();
        
        
    }
    
}