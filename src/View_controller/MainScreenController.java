package View_controller;


import Model.Appointment;
import Model.AppointmentDB;
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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import Model.Customer;
import Model.CustomerDB;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
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
    private TableColumn<Appointment, String> appointTypeCol;
    @FXML
    private TableColumn<Appointment, String> custAppointCol;
    @FXML
    private TableColumn<Appointment, String> appointStartCol;
    @FXML
     TableView<Customer> custTableView;
    @FXML
    private TableView<Appointment> appointTableView;
    @FXML
    private TableColumn<Appointment, String> appointEndCol;
    @FXML
    private RadioButton allRadioButton1;
    private ToggleGroup tg;
    private ArrayList<Appointment> newAppointments = AppointmentDB.appointmentsNow;
   
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userText.setText(UserLoginController.loggedinUser.getName());
//        checkAppointment();
        
        
        
        tg = new ToggleGroup();
        this.todayRadioButton.setToggleGroup(tg);
        this.weeklyRadioButton.setToggleGroup(tg);
        this.monthlyRadioButton.setToggleGroup(tg);
        this.allRadioButton1.setToggleGroup(tg);
        allRadioButton1.setSelected(true);
        
        
     
//     Lambda Expressions here for custom rendering of table cells
        
        custIDCol.setCellValueFactory(cellData -> {return cellData.getValue().customerIdProperty().asObject();});
        custNameCol.setCellValueFactory(cellData -> {return cellData.getValue().nameProperty();});
        custPhoneCol.setCellValueFactory(cellData -> {return cellData.getValue().phoneProperty();});
        
        custTableView.setPlaceholder(new Label("Click  \"New Customer\"  to add a Customer"));
        custTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        custTableView.setItems(CustomerDB.customers);
        

        appointTypeCol.setCellValueFactory(cellData -> {return cellData.getValue().typeProperty();});
        custAppointCol.setCellValueFactory(cellData -> {return cellData.getValue().custNameProperty();});
        appointStartCol.setCellValueFactory((cellData -> {return cellData.getValue().localStartProperty();}));
        appointEndCol.setCellValueFactory((cellData -> {return cellData.getValue().localEndProperty();}));
        
        appointTableView.setPlaceholder(new Label("Select Custoemer and click \"New Appointment\"  to add an Appointment"));
        appointTableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        appointTableView.setItems(AppointmentDB.appointments);
        
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
         FilteredList<Appointment> filteredAppointment = new FilteredList<>(AppointmentDB.appointments, e -> true);
       searcjAppointField.setOnKeyPressed(e -> {
            searcjAppointField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredAppointment.setPredicate((Predicate<? super Appointment>) appointment ->{
                    if(newValue == null || newValue.isEmpty()){
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if(appointment.getCustName().toLowerCase().contains(newValue)){
                        return true;
                    }else if(appointment.getType().toLowerCase().contains(lowerCaseFilter)){
                        return true; 
                    }else if(appointment.getStart().toLowerCase().contains(newValue)){
                        return true;
                    }else if(appointment.getEnd().toLowerCase().contains(newValue)){
                        return true;
                    } 
                   
                    return false;
                });
            });
            SortedList<Appointment> sortedAppointmentData = new SortedList<>(filteredAppointment);
            sortedAppointmentData.comparatorProperty().bind(appointTableView.comparatorProperty());
            appointTableView.setItems(sortedAppointmentData);
        });
        
//        Highlight text in textfiled for faster delete on re-entry
        SearchCustField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused) {
            Platform.runLater(() -> SearchCustField.selectAll());
        }
        
        
    });
        searcjAppointField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
        if (isNowFocused) {
            Platform.runLater(() -> searcjAppointField.selectAll());
        }
        /**
        * Using lambda functions to listen to radio buttons
        * to bind the Sorted list to the Table View
        */
        
    });
        
        todayRadioButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            AppointmentDB.clearAppointments();
            AppointmentDB.getDayAppointments();
    }));

        weeklyRadioButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            AppointmentDB.clearAppointments();
            AppointmentDB.getWeekAppointments();
    }));
       monthlyRadioButton.selectedProperty().addListener(((observable, oldValue, newValue) -> {
            AppointmentDB.clearAppointments();
            AppointmentDB.getMonthAppointments();
            
    }));
        allRadioButton1.selectedProperty().addListener(((observable, oldValue, newValue) -> {
             AppointmentDB.refreshAppointmentTable();
            
    }));
    }
    public void checkAppointment(){
        newAppointments.clear();
        AppointmentDB.getNextAppointment();
        StringBuilder urgent= new StringBuilder();
        System.out.println("chekcing for appointments...");
          for (Appointment i : newAppointments){
              System.out.println(i.toString());
          }
         
        if(!newAppointments.isEmpty()){
            System.out.println("no appointments");
           for (Appointment i : newAppointments){
               urgent.append(i.toString() + "\n");
               
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Upcoming appointment/s");
            alert.setContentText(urgent.toString());

            alert.showAndWait();
           
           }
           
           
       };
        
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
        System.out.println("You Clicked view customerButton");
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
                AppointmentDB.refreshAppointmentTable();

            } else {
                System.out.println("You clicked cancel.");
            
            }
        }
    }

    @FXML
    private void newAppointButtonHandler(ActionEvent event) throws IOException {
        Customer customer = custTableView.getSelectionModel().getSelectedItem();
        if(customer == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Customer Selected");
            error.setContentText("Select A Customer");
            error.showAndWait();
        }
        else{
            System.out.println("You clicked add appointment.");
             Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            Parent root;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            AddAppointmentController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setCustomerId(customer);
        }
    }

    @FXML
    private void veiwAppointButtonHandler(ActionEvent event) throws IOException {
        System.out.println("You clicked view appointment.");
        System.out.println("You Clicked view customerButton");
        Appointment appointment = appointTableView.getSelectionModel().getSelectedItem();
        if(appointment == null){
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewAppointment.fxml"));
            root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            ViewAppointmentController controller = loader.getController();
            controller.setAppointment(appointment);
        }
        
        
    }

    @FXML
    private void deleteAppointButtonHandler(ActionEvent event) {
        System.out.println("You clicked delete appointment.");
        Appointment appointment = appointTableView.getSelectionModel().getSelectedItem();
        if(appointment == null){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Error");
            error.setHeaderText("No Appointment Selected");
            error.setContentText("Select Appointment To Delete");
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

               AppointmentDB.deleteAppointment(appointment);
               AppointmentDB.refreshAppointmentTable();

            } else {
                System.out.println("You clicked cancel.");
            
            }
        }
    }

    private void todayRadioButtonHandler(ActionEvent event) {
        System.out.println("You clicked today radio.");
//       
    
    }

    private void weeklyRadioButtonHandler(ActionEvent event) {
        System.out.println("You clicked weekly radio.");
    }

    private void monthlyRadioButtonHandler(ActionEvent event) {
         System.out.println("You clicked monthly radio.");
    }

    @FXML
    private void radioButtonHandler(ActionEvent event) {
         
        
        
         
        
    }  
}