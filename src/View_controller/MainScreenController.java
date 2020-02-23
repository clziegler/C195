/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private TableColumn<?, ?> AppointEndCol;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void quitButtonHandler(ActionEvent event) {
    }

    @FXML
    private void generateReportHandler(ActionEvent event) {
    }

    @FXML
    private void newCustomerButtonHandler(ActionEvent event) {
    }

    @FXML
    private void viewCustButtonHandler(ActionEvent event) {
    }

    @FXML
    private void deleteCustHandler(ActionEvent event) {
    }

    @FXML
    private void newAppointButtonHandler(ActionEvent event) {
    }

    @FXML
    private void veiwAppointButtonHandler(ActionEvent event) {
    }

    @FXML
    private void deleteAppointButtonHandler(ActionEvent event) {
    }

    @FXML
    private void todayRadioButtonHandler(ActionEvent event) {
    }

    @FXML
    private void weeklyRadioButtonHandler(ActionEvent event) {
    }

    @FXML
    private void monthlyRadioButtonHandler(ActionEvent event) {
    }
    
}