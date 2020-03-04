
package View_controller;

import Model.Appointment;
import Model.AppointmentDB;
import Model.AppointmentTypes;
import Model.Customer;
import Utility.Error_Handler;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class ViewAppointmentController implements Initializable {

    @FXML
    private DatePicker datePicker;
    @FXML
    private ComboBox<String> startTimeComboBox;
    @FXML
    private ComboBox<String> endTimeComboBox;
    @FXML
    private ComboBox<String> titleComboBox;
    @FXML
    private Button cancelButton;
    @FXML
    private TextArea descriptionTextArea;
    @FXML
    private Text customerNameText;
    @FXML
    private Button modifyAppointmentButton;
    
    private ObservableList<String> startTimes;
    private ObservableList<String> endTimes;
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT);
    private final DateTimeFormatter dateTimeformatComboBox = DateTimeFormatter.ofPattern("h:mm a");
    private final DateTimeFormatter dtfAppoint = DateTimeFormatter.ofPattern("M/d/yy h:mm a");
    public static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    private String startBox;
    private String endBox;
    private int custId;
    private int appointId;
    private LocalDate dateBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startTimes = FXCollections.observableArrayList();
        endTimes = FXCollections.observableArrayList();
        descriptionTextArea.setWrapText(true);
        appointmentTypes.clear();
        createTypes();
        
        createTimes();
        // TODO
    } 
     public void setCustomer(Customer customer){
         customerNameText.setText(customer.getName());
        
     }

     private void createTypes() {
     String[] type = AppointmentTypes.type;
     appointmentTypes.addAll(type);
     titleComboBox.setItems(appointmentTypes);
     }
     
    public void setAppointment(Appointment appointment){
        appointId = appointment.getAppointmentId();
        customerNameText.setText(appointment.getCustName());
        descriptionTextArea.setPromptText("Add Description of Appointment");
        descriptionTextArea.setText(appointment.getDescription());
        custId = appointment.getCustomerId();
        titleComboBox.setValue(appointment.getType());
        LocalDateTime start = LocalDateTime.parse(appointment.getLocalStart(),dtfAppoint);
        LocalDateTime end = LocalDateTime.parse(appointment.getLocalEnd(),dtfAppoint);
        startTimeComboBox.setValue(dateTimeformatComboBox.format(start));
        startBox = dateTimeformatComboBox.format(start).toString();
        endBox = dateTimeformatComboBox.format(end).toString();
        endTimeComboBox.setValue(dateTimeformatComboBox.format(end));
        datePicker.setValue(start.toLocalDate());
        dateBox = start.toLocalDate();
        
        
        
     }
     private Timestamp combineDateAndTime(String time, LocalDate date) {
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        LocalDateTime localConverted = LocalDateTime.of(date, localTime);
        ZonedDateTime zdtConvert = localConverted.atZone(ZoneId.of(ZoneId.systemDefault().toString()));
        ZonedDateTime utczdt = zdtConvert.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime ldtIn = utczdt.toLocalDateTime();
        Timestamp utcTimeStamped = Timestamp.valueOf(ldtIn);
        return utcTimeStamped;
     }
     
     private void createTimes() {
        // Set up combobox times
        LocalTime time = LocalTime.of(8, 0);
        do {
            startTimes.add(time.format(timeFormatter));
            endTimes.add(time.format(timeFormatter));
            time = time.plusMinutes(15);
        } while(!time.equals(LocalTime.of(17, 15)));
            startTimeComboBox.setItems(startTimes);
            endTimeComboBox.setItems(endTimes);
    }

    @FXML
    private void cancelButtonHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to cancel?");
        alert.setContentText("Any unsaved changes will be lost.");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
            
        } else {
            System.out.println("You clicked cancel.");
        }
    }

    @FXML
    private void modifyAppointmentButtonHandler(ActionEvent event) {
        System.out.println("you clicked modify customer button");
       
        
        String startTime = startTimeComboBox.getValue();
        String endTime = endTimeComboBox.getValue();
        String type = titleComboBox.getValue();
        LocalDate localDate = datePicker.getValue();
        String description = descriptionTextArea.getText();
        
        if (Error_Handler.checkAppointmentFields(startTime, endTime, type, localDate) && Error_Handler.checkStartEndTimes(startTime, endTime)){
            System.out.println("all checked");
            
            Timestamp localStart = combineDateAndTime(startTime, localDate);
            Timestamp localEnd = combineDateAndTime(endTime, localDate);
            //If time does not change do not check for overlap
            if (startBox.equals(startTime) && endBox.equals(endTime) && dateBox.isEqual(localDate)){
           
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(appointId);
                appointment.setCustomerId(custId);
                appointment.setUserId(UserLoginController.loggedinUser.getID());
                appointment.setType(type);
                appointment.setDescription(description);
                appointment.setStart(localStart.toString());
                appointment.setEnd(localEnd.toString());
                System.out.println(appointment.customerIdProperty());
                AppointmentDB.addAppointment(appointment);
                AppointmentDB.refreshAppointmentTable();

                Node node = (Node)event.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
                stage.close();
             }else if (Error_Handler.checkOverlap(localStart, localEnd)){
                System.out.println("No overlaps");
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(appointId);
                appointment.setCustomerId(custId);
                appointment.setUserId(UserLoginController.loggedinUser.getID());
                appointment.setType(type);
                appointment.setDescription(description);
                appointment.setStart(localStart.toString());
                appointment.setEnd(localEnd.toString());
                System.out.println(appointment.customerIdProperty());
                AppointmentDB.addAppointment(appointment);
                AppointmentDB.refreshAppointmentTable();

                Node node = (Node)event.getSource();
                Stage stage = (Stage)node.getScene().getWindow();
                stage.close();
            
            }
        }
    }
    
}
