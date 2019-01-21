/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Customer;
import BusinessObjects.Order;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.ejb.Stateless;
import static jdk.nashorn.internal.runtime.Debug.id;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class OrdersDL {

    private static Connection conn = null;
    
    public OrdersDL() throws SQLException, ClassNotFoundException {
        if (conn == null){
            conn = GetConnection();
        }
    }
    
    public static Connection GetConnection() throws SQLException, ClassNotFoundException{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = null;
        String dbUrl = "jdbc:mysql://localhost:3306/pizzadb";
        String username = "root";
        String password = "";

        conn = DriverManager.getConnection(dbUrl, username, password);
        return conn;
    }
    
    //this method inserts a new order into the database. Returns the order id, or 0 if there is an error.
    public static int InsertNewOrder(Order o) throws SQLException{       
        LocalDateTime deliveryDateTime = o.getDeliveryDateTime();
        String ddtString = deliveryDateTime.toLocalDate().toString();
        ddtString += " ";
        ddtString += deliveryDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString();
        
        String sql = "INSERT INTO orders (customerId, deliveryDateTime) VALUES ('" + o.getCustomerId() + "', '" + ddtString + "');";
        Statement s = conn.createStatement();
        
        if(s.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS) == 1){
            ResultSet rs = s.getGeneratedKeys();
            rs.first();
            return rs.getInt(1);
        }
        else{
            return 0;
        }
    }
    
    public static Order GetOrder(int orderId) throws SQLException{
        String sql = "SELECT * FROM orders WHERE orderId = '" + orderId + "';";
        Statement s = conn.createStatement();
        
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        
        Order o = new Order();
        o.setOrderId(rs.getInt(1));
        o.setTotalPrice(rs.getDouble(2));
        String date = rs.getDate(3).toString();
        date += " ";
        date += rs.getTime(3).toString();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime formattedDateTime = LocalDateTime.parse(date, formatter);
        o.setDeliveryDateTime(formattedDateTime);
        date = rs.getDate(4).toString();
        date += " ";
        date += rs.getTime(4).toString();
        formattedDateTime = LocalDateTime.parse(date, formatter);
        o.setPlaceDateTime(formattedDateTime);
        o.setCustomerId(rs.getInt(5));
        o.setOrderStatus(rs.getString(6));
        
        return o;
    }
    
    public static ArrayList<Order> GetPendingOrders() throws SQLException{
        String sql = "SELECT * FROM orders WHERE orderStatus = 'PENDING' AND active = 'ACTIVE';";
        Statement s = conn.createStatement();
        
        ArrayList<Order> pendingOrders = new ArrayList();
        
        ResultSet rs = s.executeQuery(sql);
        rs.beforeFirst();
        
        if(rs.next()){
            do{
                Order o = new Order();
                o.setOrderId(rs.getInt(1));
                o.setTotalPrice(rs.getDouble(2));
                String date = rs.getDate(3).toString();
                date += " ";
                date += rs.getTime(3).toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime formattedDateTime = LocalDateTime.parse(date, formatter);
                o.setDeliveryDateTime(formattedDateTime);
                date = rs.getDate(4).toString();
                date += " ";
                date += rs.getTime(4).toString();
                formattedDateTime = LocalDateTime.parse(date, formatter);
                o.setPlaceDateTime(formattedDateTime);
                o.setCustomerId(rs.getInt(5));
                o.setOrderStatus(rs.getString(6));

                pendingOrders.add(o);
            }
            while(rs.next());
        }
        return pendingOrders;
    }
    
    public static ArrayList<Order> GetFilledOrders() throws SQLException{
        String sql = "SELECT * FROM orders WHERE orderStatus = 'FILLED';";
        Statement s = conn.createStatement();
        
        ArrayList<Order> filledOrders = new ArrayList();
        
        ResultSet rs = s.executeQuery(sql);
        rs.beforeFirst();
        if(rs.next()){
            do{
                Order o = new Order();
                o.setOrderId(rs.getInt(1));
                o.setTotalPrice(rs.getDouble(2));
                String date = rs.getDate(3).toString();
                date += " ";
                date += rs.getTime(3).toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime formattedDateTime = LocalDateTime.parse(date, formatter);
                o.setDeliveryDateTime(formattedDateTime);
                date = rs.getDate(4).toString();
                date += " ";
                date += rs.getTime(4).toString();
                formattedDateTime = LocalDateTime.parse(date, formatter);
                o.setPlaceDateTime(formattedDateTime);
                o.setCustomerId(rs.getInt(5));
                o.setOrderStatus(rs.getString(6));

                filledOrders.add(o);
            }
            while(rs.next());
        }
        return filledOrders;
    }
    
    public static boolean UpdateOrder(Order o) throws SQLException{
        int orderId = o.getOrderId();
        double totalPrice = o.getTotalPrice();
        
        LocalDateTime deliveryDateTime = o.getDeliveryDateTime();
        String ddtString = deliveryDateTime.toLocalDate().toString();
        ddtString += " ";
        ddtString += deliveryDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString();
        
        LocalDateTime placedDateTime = o.getPlaceDateTime();
        String pdtString = placedDateTime.toLocalDate().toString();
        pdtString += " ";
        pdtString += placedDateTime.toLocalTime().truncatedTo(ChronoUnit.SECONDS).toString();
        
        int customerId = o.getCustomerId();
        String orderStatus = o.getOrderStatus();
        
        String sql = "UPDATE orders SET totalPrice = '" + totalPrice + "', deliveryDateTime = '" + ddtString + "', placedDateTime = '" + pdtString + 
                        "', customerId = '" + customerId + "', orderStatus = '" + orderStatus + "' WHERE orderId = '" + orderId + "';";
                
        Statement s = conn.createStatement();
        
        int success = s.executeUpdate(sql);
        
        if(success == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    //this function updates the total price column of the order table, and returns the new total price for use later. returns 0 if there is an error.
    public static double UpdateOrderPrice(double price, int orderId) throws SQLException{
        String updateSql = "UPDATE orders SET totalPrice = totalPrice + '" + price + "' WHERE orderId = '" + orderId + "';";
        Statement s1 = conn.createStatement();
        int rows = s1.executeUpdate(updateSql);
        
        if (rows > 0){
            String querySql = "SELECT totalPrice FROM orders WHERE orderId = '" + orderId + "';";
            Statement s2 = conn.createStatement();
            ResultSet rs = s2.executeQuery(querySql);
            rs.first();
            return rs.getDouble(1);
        }
        else{
            return 0;
        }
    }
    
    public static boolean ConfirmOrder(int orderId) throws SQLException{
        String sql = "UPDATE orders SET active = 'ACTIVE' WHERE orderId = ?;";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setInt(1, orderId);
        
        if (s.executeUpdate() > 0){
            return true;
        }
        else{
            return false;
        }
    }
    
}
