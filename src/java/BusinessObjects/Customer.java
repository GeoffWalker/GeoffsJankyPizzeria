/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import java.sql.SQLException;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class Customer {

    //data members
    private int customerId = 0;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private int houseNumber;
    private String street;
    private String province;
    private String postalCode;

    //default constructor
    public Customer() {
    }

    //overloaded constructor
    public Customer(int customerId, String firstName, String lastName, String phoneNumber, String email, int houseNumber, String street, String province, String postalCode) {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.houseNumber = houseNumber;
        this.street = street;
        this.province = province;
        this.postalCode = postalCode;
    }
   
    //getters and setters for all data members
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    
    //get customer method
    public static Customer GetCustomer(int id) throws SQLException{
        return DataBeans.CustomerDL.GetCustomer(id);
    }
    
    //update customer method
    //decided this was not necessary. 
    
    //insert  customer method - this method returns the customer id of the inserted customer, or 0 is there is an error.
    public int InsertCustomer() throws SQLException{
        return DataBeans.CustomerDL.InsertCustomer(this);
    }
    
    
}
