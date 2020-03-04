/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author charlesziegler
 */

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Appointment {
    private final IntegerProperty appointmentId;
    private final IntegerProperty customerId;
    private final StringProperty type;
    private final StringProperty description;
    private final StringProperty location;
    private final IntegerProperty userId;
    private final StringProperty start;
    private final StringProperty end;
    private final StringProperty custName;
    private final StringProperty localStart;
    private final StringProperty localEnd;
    private final StringProperty contact;
    private final StringProperty userName;
    private final StringProperty userSchedule;
   
    
    public Appointment() {
        this.appointmentId = new SimpleIntegerProperty();
        this.customerId = new SimpleIntegerProperty();
        this.type = new SimpleStringProperty();
        this.description = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.userId = new SimpleIntegerProperty();
        this.start = new SimpleStringProperty();
        this.end = new SimpleStringProperty();
        this.custName = new SimpleStringProperty();
        this.localStart = new SimpleStringProperty();
        this.localEnd = new SimpleStringProperty();
        this.contact = new SimpleStringProperty();
        this.userName = new SimpleStringProperty();
        this.userSchedule = new SimpleStringProperty();
        
    }

    public final int getAppointmentId() {
        return appointmentId.get();
    }

    public final void setAppointmentId(int value) {
        appointmentId.set(value);
    }

    public IntegerProperty appointmentIdProperty() {
        return appointmentId;
    }

    public final int getCustomerId() {
        return customerId.get();
    }

    public final void setCustomerId(int value) {
        customerId.set(value);
    }

    public IntegerProperty customerIdProperty() {
        return customerId;
    }

    public final String getType() {
        return type.get();
    }

    public final void setType(String value) {
        type.set(value);
    }

    public StringProperty typeProperty() {
        return type;
    }

    public final String getDescription() {
        return description.get();
    }

    public final void setDescription(String value) {
        description.set(value);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public final String getLocation() {
        return location.get();
    }

    public final void setLocation(String value) {
        location.set(value);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public final Integer getUserId() {
        return userId.get();
    }

    public final void setUserId(Integer value) {
        userId.set(value);
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public final String getStart() {
        return start.get();
    }

    public final void setStart(String value) {
        start.set(value);
    }

    public StringProperty startProperty() {
        return start;
    }

    public final String getEnd() {
        return end.get();
    }

    public final void setEnd(String value) {
        end.set(value);
    }

    public StringProperty endProperty() {
        return end;
    }

    public final String getCustName() {
        return custName.get();
    }

    public final void setCustName(String value) {
        custName.set(value);
    }

    public StringProperty custNameProperty() {
        return custName;
    }

    public final String getLocalStart() {
        return localStart.get();
    }

    public final void setLocalStart(String value) {
        localStart.set(value);
    }

    public StringProperty localStartProperty() {
        return localStart;
    }

    public final String getLocalEnd() {
        return localEnd.get();
    }

    public final void setLocalEnd(String value) {
        localEnd.set(value);
    }

    public StringProperty localEndProperty() {
        return localEnd;
    }
    public final String getContactd() {
        return contact.get();
    }

    public final void setContact(String value) {
        contact.set(value);
    }

    public StringProperty contactProperty() {
        return contact;
    }
    public final String getUserName() {
        return userName.get();
    }

    public final void setUserName(String value) {
        userName.set(value);
    }

    public StringProperty userNameProperty() {
        return userName;
    }
     @Override
    public String toString(){
        return userName.get().substring(0,1).toUpperCase()+userName.get().substring(1).toLowerCase()
                + ": "+ localStart.get()+" " + type.get() +" with "+ custName.get();
    }
    public final String getUserSchedule() {
        return userSchedule.get();
    }

    public final void setUserSchedule(String value) {
       userSchedule.set(value);
    }
    
    public StringProperty userScheduleProperty(){
        return userSchedule;
        
    }
    
    
    
    
}
