/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class Order {

    //data members
    private int orderId = 0;
    private double totalPrice = 0;
    private String orderStatus = "PENDING";
    private LocalDateTime deliveryDatTime = LocalDateTime.now().plusMinutes(30); //add 30 mins for end time.
    private LocalDateTime placeDateTime = LocalDateTime.now();
    private int customerId;

    //default constructor
    public Order() {
    }

    //overloaded constructor
    public Order(int orderId, double totalPrice, String orderStatus, LocalDateTime deliveryDatTime, LocalDateTime placeDateTime, int customerId) {
        this.orderId = orderId;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.deliveryDatTime = deliveryDatTime;
        this.placeDateTime = placeDateTime;
        this.customerId = customerId;
    }

    //getters and setters for all data members
    public int getOrderId(){
        return orderId;
    }
    
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }
    
    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDatTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDatTime) {
        this.deliveryDatTime = deliveryDatTime;
    }

    public LocalDateTime getPlaceDateTime() {
        return placeDateTime;
    }

    public void setPlaceDateTime(LocalDateTime placeDateTime) {
        this.placeDateTime = placeDateTime;
    }
    
    public int getCustomerId(){
        return customerId;
    }
    
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    
    //get order method
    public static Order GetOrder(int orderId) throws SQLException{
        return DataBeans.OrdersDL.GetOrder(orderId);
    }
    
    public static ArrayList<Order> GetPendingOrders() throws SQLException{
        return DataBeans.OrdersDL.GetPendingOrders();
    }
    
    public static ArrayList<Order> GetFilledOrders() throws SQLException{
        return DataBeans.OrdersDL.GetFilledOrders();
    }
    
    //update order method
    public boolean UpdateOrder() throws SQLException{
        return DataBeans.OrdersDL.UpdateOrder(this);
    }
    
    public int InsertNewOrder() throws SQLException{
        return DataBeans.OrdersDL.InsertNewOrder(this);
    }
    
    public static double UpdateOrderPrice(double price, int orderId) throws SQLException{
        return DataBeans.OrdersDL.UpdateOrderPrice(price, orderId);
    }
    
    public static boolean ConfirmOrder(int orderId) throws SQLException{
        return DataBeans.OrdersDL.ConfirmOrder(orderId);
    }
}
