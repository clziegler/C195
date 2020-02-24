/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import C195.Main;
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
import Model.CustomerDB;
import java.io.IOException;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
    private TableColumn<Customer, Integer> custIDCol;
    @FXML
    private TableColumn<Customer, String> custNameCol;
    @FXML
    private TableColumn<Customer, String> custPhoneCol;
    @FXML
    private TableColumn<?, ?> appointTypeCol;
    @FXML
    private TableColumn<?, ?> custAppointCol;
    @FXML
    private TableColumn<?, ?> appointStartCol;
    @FXML
     TableView<Customer> custTableView;
    @FXML
    private TableView<?> appointTableView;
    @FXML
    private TableColumn<?, ?> appointEndCol;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userText.setText(LoginController.loggedIn);
        
     
//     Lambda Expressions here for custom rendering of table cells
        
        custIDCol.setCellValueFactory(cellData -> {return cellData.getValue().customerIdProperty().asObject();});
        custNameCol.setCellValueFactory(cellData -> {return cellData.getValue().nameProperty();});
        custPhoneCol.setCellValueFactory(cellData -> {return cellData.getValue().phoneProperty();});
        
        custTableView.setPlaceholder(new Label("Click  \"New Customer\"  to add a Customer"));
        custTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        custTableView.setItems(CustomerDB.customers);
        

        appointTypeCol.setCellValueFactory(new PropertyValueFactory<>("appointType"));
        custAppointCol.setCellValueFactory(new PropertyValueFactory<>("custName"));
        appointStartCol.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        appointEndCol.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        
        appointTableView.setPlaceholder(new Label("Click  \"New Appointment\"  to add an Appointment"));
        appointTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
//        appointTableView.setItems(CustomerDB.getAllCustomers());
        
        /**
        * Here I am using 2 lambda expressions: 1 to wrap customer data in a filtered list, and the other
        * to bind the Sorted list to the Table View
        */
     
        FilteredList<Customer> filteredCustomer = new FilteredList<>(CustomerDB.customers, e -> true);
        SearchCustField.setOnKeyPressed(e -> {
            SearchCustField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredCustomer.setPredicate((Predicate<? super Customer>) customer ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(customer.customerIdProperty().toString().contains(newValue)){
                        return true;
                    }else if(customer.getName().toLowerCase().contains(lowerCaseFilter)){
                        return true; 
                    }else if(customer.getPhone().toLowerCase().contains(lowerCaseFilter)){
                        return true;
                    } 
                   
                    return false;
                });
            });
            SortedList<Customer> sortedCustomerData = new SortedList<>(filteredCustomer);
            sortedCustomerData.comparatorProperty().bind(custTableView.comparatorProperty());
            custTableView.setItems(sortedCustomerData);
        });
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
        CustomerDB.refreshCustomerTable();
        
        Parent newPartParent = FXMLLoader.load(getClass().getResource("AddCustomer.fxml"));
        Scene newPartScene = new Scene(newPartParent);
        Stage app_stage = new Stage();
        app_stage.initModality(Modality.APPLICATION_MODAL);
        app_stage.setScene(newPartScene);
        app_stage.showAndWait();
    }

    @FXML
    private void viewCustButtonHandler(ActionEvent event) throws IOException {
        System.out.println("You Clicked Modify Part Button");
        Customer customer = custTableView.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Customer Selected");
            error.setContentText("Select A Customer To Modify");
            error.showAndWait();
        }
       
        else{
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Parent root;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewCustomer.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ViewCustomerController controller = loader.getController();
            controller.setCustomer(customer);
        }
        

    }

    @FXML
    private void deleteCustHandler(ActionEvent event) {
        System.out.println("You Clicked Delete Customer Button");
        Customer customer = custTableView.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Customer Selected");
            error.setContentText("Select A Customer To Delete");
            error.showAndWait();
        }
        else{

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.initModality(Modality.NONE);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Are you sure you want to delete?");
            alert.setContentText("Deleting is a permanent action.");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {

                CustomerDB.deleteCustomer(customer);
                CustomerDB.refreshCustomerTable();

            } else {
                System.out.println("You clicked cancel.");
            
            }
        }
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