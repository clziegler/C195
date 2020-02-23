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

/**
 *
 * @author charlesziegler
 */
public class Customer {
    private final IntegerProperty customerId;
    private final StringProperty name;
    private final StringProperty address;
    private final StringProperty address2;
    private final StringProperty city;
    private final StringProperty postalCode;
    private final StringProperty phone;
    private final StringProperty country;
    private int addressId;
    private int cityId;
    private int countryId;
    
    public Customer(){
        this.customerId = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
        this.address2 = new SimpleStringProperty();
        this.city = new SimpleStringProperty();
        this.postalCode = new SimpleStringProperty();
        this.phone = new SimpleStringProperty();
        this.country = new SimpleStringProperty();
    }
}
