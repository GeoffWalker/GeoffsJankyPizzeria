/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class Pizza {

    //data members
    private int pizzaId;
    private String crustType;
    private boolean isFinished;
    private String size = "test";
    private double price;
    private int orderId;

    
    //default constructor
    public Pizza() {
    }
    
    //overloaded constructor
    public Pizza(int pizzaId, String crustType, boolean isFinished, String size, double price, int orderId) {
        this.pizzaId = pizzaId;
        this.crustType = crustType;
        this.isFinished = isFinished;
        this.size = size;
        this.price = price;
        this.orderId = orderId = 0;
    }

    
    //getters and setters for all data members
    public int getPizzaId(){
        return pizzaId;
    }
    
    public void setPizzaId(int pizzaId){
        this.pizzaId = pizzaId;
    }
    
    public String getCrustType() {
        return crustType;
    }

    public void setCrustType(String crustType) {
        this.crustType = crustType;
    }

    public boolean isIsFinished() {
        return isFinished;
    }

    public void setIsFinished(boolean isFinished) {
        this.isFinished = isFinished;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public double getPrice() {
        return Double.parseDouble(new DecimalFormat("#.##").format(price));
    }

    public void setPrice(double price) {
        this.price = price;
    }
    
    public int getOrderId(){
        return orderId;
    }
    
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    //calculate price method
    public double CalculatePrice() throws SQLException{
        //get price based on the size
        double price = DataBeans.PizzaDL.GetSizePrice(this.size);
        
        //get arraylist of toppings
        ArrayList<Toppings> toppings = GetToppings(this.pizzaId);
        
        //add the price for each topping in the array list
        for (Toppings t: toppings){
            price += t.getPrice();
        }
        
        //update the price of the current pizza and return the price.
        this.price = price;
        return price;
    }
    
    //get toppings method
    public ArrayList<Toppings> GetToppings(int pizzaId) throws SQLException{
        return DataBeans.PizzaDL.GetToppings(pizzaId);
    }
    
    //get sizes method
    public static ArrayList<String> GetSizes() throws SQLException{
        return DataBeans.PizzaDL.GetSizes();
    }
    
    //get crusts method
    public static ArrayList<String> GetCrusts() throws SQLException{
        return DataBeans.PizzaDL.GetCrusts();
    }
    
    public static double GetSizePrice(String size) throws SQLException{
        return DataBeans.PizzaDL.GetSizePrice(size);
    }
    
    public static double GetToppingPrice(ArrayList<Integer> toppings) throws SQLException{
        return DataBeans.PizzaDL.GetToppingPrice(toppings);
    }
    
    public int GetSizeId() throws SQLException{
        return DataBeans.PizzaDL.GetSizeId(this.size);
    }
    
    public int InsertPizza() throws SQLException{
        return DataBeans.PizzaDL.InsertPizza(this);
    }

    public int GetCrustId() throws SQLException{
        return DataBeans.PizzaDL.GetCrustId(this.crustType);
    }
    
    public boolean InsertToppings(ArrayList<Integer> toppingIds) throws SQLException{
        return DataBeans.PizzaDL.InsertToppings(toppingIds, this.pizzaId);
    }
    
    public static int GetPizzaCountByOrder(int orderId) throws SQLException{
        return DataBeans.PizzaDL.GetPizzaCountByOrder(orderId);
    }
    
    public static ArrayList<Pizza> GetPizzasByOrder(int orderId) throws SQLException{
        return DataBeans.PizzaDL.GetPizzasByOrder(orderId);
    }
}
