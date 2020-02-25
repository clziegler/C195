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
    //Getters and Setters
    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
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

    public final String getName() {
        return name.get();
    }

    public final void setName(String value) {
        name.set(value);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public final String getAddress() {
        return address.get();
    }

    public final void setAddress(String value) {
        address.set(value);
    }

    public StringProperty addressProperty() {
        return address;
    }

    public final String getAddress2() {
        return address2.get();
    }

    public final void setAddress2(String value) {
        address2.set(value);
    }

    public StringProperty address2Property() {
        return address2;
    }

    public final String getCity() {
        return city.get();
    }

    public final void setCity(String value) {
        city.set(value);
    }

    public StringProperty cityProperty() {
        return city;
    }

    public final String getPostalCode() {
        return postalCode.get();
    }

    public final void setPostalCode(String value) {
        postalCode.set(value);
    }

    public StringProperty postalCodeProperty() {
        return postalCode;
    }

    public final String getPhone() {
        return phone.get();
    }

    public final void setPhone(String value) {
        phone.set(value);
    }

    public StringProperty phoneProperty() {
        return phone;
    }

    public final String getCountry() {
        return country.get();
    }

    public final void setCountry(String value) {
        country.set(value);
    }

    public StringProperty countryProperty() {
        return country;
    }
    
}
