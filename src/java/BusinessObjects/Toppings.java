/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import DataBeans.ToppingsDL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class Toppings {

    //data members
    private int toppingId = 0;
    private String name = "";
    private double price = 0;
    private LocalDate createDate = LocalDate.now();
    private int empAddedBy = 0;
    private boolean isActive = true;
    
    //default constructor
    public Toppings() {
    }

    //overloaded constructor
    public Toppings(int toppingId, String name, double price, LocalDate createDate, int emAddedBy, boolean isActive) {
        this.toppingId = toppingId;
        this.name = name;
        this.price = price;
        this.createDate = createDate;
        this.empAddedBy = empAddedBy;
        this.isActive = isActive;
    }

    //getters and setters for all data members
    public int getToppingId(){
        return toppingId;
    }
    
    public void setToppingId(int toppingId){
        this.toppingId = toppingId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
    
    public int getEmpAddedBy(){
        return empAddedBy;
    }
    
    public void setEmpAddedBy(int empAddedBy){
        this.empAddedBy = empAddedBy;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    //add topping method
    public boolean AddTopping() throws SQLException, ClassNotFoundException{
        return DataBeans.ToppingsDL.AddTopping(this);
    }
    
    //delete topping method
    public static boolean DeleteTopping(int toppingId) throws SQLException{
        return DataBeans.ToppingsDL.DeleteTopping(toppingId);
    }
    
    //update topping method
    public boolean UpdateTopping(int empId) throws SQLException{
        return DataBeans.ToppingsDL.UpdateTopping(this, empId);
    }
    
    //get all available toppings method
    public static ArrayList<Toppings> GetAllAvailableToppings() throws SQLException{
        return DataBeans.ToppingsDL.GetAllAvailableToppings();
        
    } 
    
    public static ArrayList<Integer> GetAllAvailableToppingIds() throws SQLException{
        return DataBeans.ToppingsDL.GetAllAvailableToppingIds();
    }

    public static ArrayList<Toppings> GetToppingsByPizza(int pizzaId) throws SQLException{
        return DataBeans.ToppingsDL.GetToppingsByPizza(pizzaId);
    }
}
