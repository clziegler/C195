/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_controller;

import Model.Appointment;
import Model.AppointmentDB;
import Model.AppointmentTypes;
import Model.Customer;
import Model.CustomerDB;
import java.net.URL;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author charlesziegler
 */
public class ReportsController implements Initializable {

    @FXML
    private Button returnButton;
    @FXML
    private PieChart pieChart;
    @FXML
    private Pane paneView;
    @FXML
    private Pane paneView2;
    @FXML
    private TableView<Appointment> scheduleTableView;
    @FXML
    private TableColumn<Appointment, String> userScheduleCol;
    
    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadUserSchedule();
        loadTypesPieChart();
        loadUsersPieChart();
        
        
    }  
    private void loadTypesPieChart(){
        List types = AppointmentDB.getAppointmentTypeNumber();
        String[] type = AppointmentTypes.type;
        
        
         ObservableList<PieChart.Data> pieChartDataTypes =
                FXCollections.observableArrayList(
                new PieChart.Data(type[0], Double.parseDouble(types.get(0).toString())),
                new PieChart.Data(type[1], Double.parseDouble(types.get(1).toString())),
                new PieChart.Data(type[2], Double.parseDouble(types.get(2).toString())),
                new PieChart.Data (type[3], Double.parseDouble(types.get(3).toString())));
        final PieChart appointmentTypesChart = new PieChart(pieChartDataTypes);
        appointmentTypesChart.setLabelsVisible(false);
        appointmentTypesChart.setTitle("Appointments By Type This Month");
        
        pieChartDataTypes.forEach(data ->
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " ", "(", data.pieValueProperty().intValue(), ")"
                    )
            )
        );
        
        paneView.getChildren().add(appointmentTypesChart);
        
    }
     private void loadUsersPieChart(){
        List types = AppointmentDB.getUserAppointmentNumber();
       
        
        
         ObservableList<PieChart.Data> pieChartDataTypes =
                FXCollections.observableArrayList(
                new PieChart.Data("test", Double.parseDouble(types.get(0).toString())),
                new PieChart.Data("chuck", Double.parseDouble(types.get(1).toString())));
                
        final PieChart appointmentUserChart = new PieChart(pieChartDataTypes);
        appointmentUserChart.setLabelsVisible(false);
        appointmentUserChart.setTitle("Appointments By Consultant");
        
        
        pieChartDataTypes.forEach(data ->
            data.nameProperty().bind(
                    Bindings.concat(
                            data.getName(), " ", "(", data.pieValueProperty().intValue(), ")"
                    )
            )
        );
        
        paneView2.getChildren().add(appointmentUserChart);
        
    }
     private void loadUserSchedule(){
         ObservableList<Appointment> schedule = AppointmentDB.getUserScedule();
        
        userScheduleCol.setCellValueFactory(cellData -> {return cellData.getValue().userScheduleProperty();});
        scheduleTableView.setPlaceholder(new Label("No Appointments Scheduled for any Consultant"));
        scheduleTableView.setItems(schedule);
        
     }

    @FXML
    private void returnButtonHandler(ActionEvent event) {
        Node node = (Node)event.getSource();
        Stage stage = (Stage)node.getScene().getWindow();
        stage.close();
    }
    
}
