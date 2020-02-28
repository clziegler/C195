
package View_controller;

import Model.Appointment;
import Model.AppointmentDB;
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
    private final LocalDateTime ldt = LocalDateTime.now();
    private final ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.systemDefault());
    private final ZonedDateTime gmt = zdt.withZoneSameInstant(ZoneId.of("GMT"));
    private final Timestamp timestamp = Timestamp.valueOf(gmt.toLocalDateTime());
    public static ObservableList<String> appointmentTypes = FXCollections.observableArrayList();
    private int custId;
    private int appointId;

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
     String[] types = {
             "Introductory appoointment",
             "Follow up appointment",
             "Custommer feedback",
             "Other"
     };
     appointmentTypes.addAll(types);
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
        endTimeComboBox.setValue(dateTimeformatComboBox.format(end));
        datePicker.setValue(start.toLocalDate());
//        System.out.println(start);
//        System.out.println(end);

        
     }
     private Timestamp combineDateAndTime(String time, LocalDate date) {
        LocalTime localTime = LocalTime.parse(time, timeFormatter);
        LocalDateTime localConverted = LocalDateTime.of(date, localTime);
        
        ZonedDateTime utcZoned = localConverted.atZone(ZoneId.of("UTC"));
        Timestamp utcTimeStamped = Timestamp.from(utcZoned.toInstant());
        return utcTimeStamped;
     }
     
     private void createTimes() {
        // Set up times
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
        
        if (Error_Handler.checkAppointmentFields(startTime, endTime, type, localDate) && Error_Handler.verifyTimes(startTime, endTime)){
            System.out.println("all checked");
            
            Timestamp localStart = combineDateAndTime(startTime, localDate);
            Timestamp localEnd = combineDateAndTime(endTime, localDate);
            
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
