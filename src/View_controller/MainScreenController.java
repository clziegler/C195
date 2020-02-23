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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import Model.Customer;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class MainScreenController implements Initializable {

    @FXML
    private Button quitButton;
    @FXML
    private Button generaterReportButton;
    @FXML
    private Text userText;
    @FXML
    private Button newCustomerButton;
    @FXML
    private Button viewCustButton;
    @FXML
    private Button deleteCustButton;
    @FXML
    private Button newAppoitButton;
    @FXML
    private Button viewappointButton;
    @FXML
    private Button deleteAppointButton;
    @FXML
    private TextField SearchCustField;
    @FXML
    private TextField searcjAppointField;
    @FXML
    private RadioButton todayRadioButton;
    @FXML
    private RadioButton weeklyRadioButton;
    @FXML
    private RadioButton monthlyRadioButton;
    @FXML
    private TableColumn<?, ?> custIDCol;
    @FXML
    private TableColumn<?, ?> custNameCol;
    @FXML
    private TableColumn<?, ?> custPhoneCol;
    @FXML
    private TableColumn<?, ?> appointTypeCol;
    @FXML
    private TableColumn<?, ?> custAppointCol;
    @FXML
    private TableColumn<?, ?> appointStartCol;
    @FXML
    private TableView<?> custTableView;
    @FXML
    private TableView<?> appointTableView;
    @FXML
    private TableColumn<?, ?> appointEndCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        custIDCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        
        custTableView.setPlaceholder(new Label("Click  \"New Customer\"  to add a Customer"));
        custTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        custTableView.setItems(CustomerDB.getAllCustomers());

        appointTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointType"));
        custAppointCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        appointStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        
        appointTableView.setPlaceholder(new Label("Click  \"New Appointment\"  to add an Appointment"));
        appointTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        appointTableView.setItems(CustomerDB.getAllCustomers());
       
        // TODO
    }    

    @FXML
    private void quitButtonHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to quit?");
        alert.setContentText("Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
        else {
            System.out.println("You clicked quit.");
        }
    
    }

    @FXML
    private void generateReportHandler(ActionEvent event) {
        System.out.println("You clicked report.");
    }

    @FXML
    private void newCustomerButtonHandler(ActionEvent event) throws IOException {
        System.out.println("You clicked add customer.");
        
        Parent newPartParent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene newPartScene = new Scene(newPartParent);
        Stage app_stage = new Stage();
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.setScene(newPartScene);
        app_stage.showAndWait();
    }

    @FXML
    private void viewCustButtonHandler(ActionEvent event) throws IOException {
        System.out.println("You clicked view customer.");
        Parent newPartParent = FXMLLoader.load(getClass().getResource("ViewCustomer.fxml"));
        Scene newPartScene = new Scene(newPartParent);
        Stage app_stage = new Stage();
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.setScene(newPartScene);
        app_stage.showAndWait();
    }

    @FXML
    private void deleteCustHandler(ActionEvent event) {
        System.out.println("You clicked delete customer.");
    }

    @FXML
    private void newAppointButtonHandler(ActionEvent event) {
        System.out.println("You clicked add appointment.");
    }

    @FXML
    private void veiwAppointButtonHandler(ActionEvent event) {
        System.out.println("You clicked view appointment.");
    }

    @FXML
    private void deleteAppointButtonHandler(ActionEvent event) {
        System.out.println("You clicked delete appointment.");
    }

    @FXML
    private void todayRadioButtonHandler(ActionEvent event) {
        System.out.println("You clicked today radio.");
    }

    @FXML
    private void weeklyRadioButtonHandler(ActionEvent event) {
        System.out.println("You clicked weekly radio.");
    }

    @FXML
    private void monthlyRadioButtonHandler(ActionEvent event) {
         System.out.println("You clicked monthly radio.");
    }
    
}