/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

/**
 *
 * @author charlesziegler
 */
public class User {
  
    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleStringProperty password;
    private final SimpleIntegerProperty active;
    
    
    public User(String name, String password){
        
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty(name);
        this.password = new SimpleStringProperty(password);
        this.active = new SimpleIntegerProperty();
        
    }
    public User(){
        
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.active = new SimpleIntegerProperty();    
    }
    
     //setters and getters
     public int getID() {
        return id.get();
    }
    public IntegerProperty idProperty() {
        return id;
    }
    public void setID(int id) {
        this.id.set(id);
    }

    public String getName() {
        return name.get();
    }
    public StringProperty nameProperty() {
        return name;
    }
     public void setName(String name) {
        this.name.set(name);
    }

    public String getPassword() {
        return password.get();
    }
    public StringProperty passwordProperty() {
        return password;
    }
    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public int getActive() {
        return active.get();
    }
    public IntegerProperty activeProperty() {
        return active;
    }
    public void setActive(int active) {
        this.active.set(active);
    }
    
}
