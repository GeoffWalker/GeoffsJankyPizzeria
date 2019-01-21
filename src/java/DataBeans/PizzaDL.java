/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Customer;
import BusinessObjects.Pizza;
import BusinessObjects.Toppings;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class PizzaDL {

    private static Connection conn = null;
    
    public PizzaDL() throws SQLException, ClassNotFoundException {
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
    
    // get toppings method
    public static ArrayList<Toppings> GetToppings(int pizzaId) throws SQLException{
        ArrayList<Toppings> toppings = new ArrayList();
        
        String sql = "SELECT t.* FROM toppings t INNER JOIN pizza_toppings_map tm ON t.toppingId = tm.toppingId WHERE tm.pizzaId = '" + pizzaId + "';";
        
        Statement s = conn.createStatement();
        
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        
        do{
            Toppings t = new Toppings();
            t.setToppingId(rs.getInt(1));
            t.setName(rs.getString(2));
            t.setPrice(rs.getDouble(3));
            
            String date = rs.getDate(4).toString();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate formattedDate = LocalDate.parse(date, formatter);
            t.setCreateDate(formattedDate);
            
            t.setIsActive(rs.getBoolean(6));
            
            toppings.add(t);
        }while (rs.next());
        
        return toppings;
    }
    
    //new method to get the price based on the size.
    public static double GetSizePrice(String size) throws SQLException{
        String sql = "SELECT price FROM sizes WHERE name = '" + size + "';";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        return rs.getDouble(1);
    }
    
    //new method to get a list of sizes.
    public static ArrayList<String> GetSizes() throws SQLException{
        String sql = "SELECT name from sizes;";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        ArrayList<String> sizes = new ArrayList();
        do{
            String size = rs.getString(1);
            sizes.add(size);
        }
        while(rs.next());
        
        return sizes;
    }
    
    //new method to get a list of crusts.
    public static ArrayList<String> GetCrusts() throws SQLException{
        String sql = "SELECT name from crusttypes;";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        ArrayList<String> crusts = new ArrayList();
        do{
            String crust = rs.getString(1);
            crusts.add(crust);
        }
        while(rs.next());
        
        return crusts;
    }
    
    public static double GetToppingPrice(ArrayList<Integer> toppings) throws SQLException{
        double toppingPrice = 0;
        for(int t: toppings){
            String sql = "SELECT price FROM toppings WHERE toppingId = ?;";
            PreparedStatement s = conn.prepareStatement(sql);
            s.setInt(1, t);
            ResultSet rs = s.executeQuery();
            rs.beforeFirst();
            if(rs.next()){
                toppingPrice += rs.getDouble(1);
            }
        }
        return toppingPrice;
    }

    public static int GetSizeId(String size) throws SQLException{
        String sql = "SELECT sizeid FROM sizes WHERE name = ?;";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setString(1, size);
        ResultSet rs = s.executeQuery();
        rs.beforeFirst();
        if(rs.next()){
            return rs.getInt(1);
        }
        else{
            return 0;
        }
        
    }
    
    public static String GetSizeName(int sizeId) throws SQLException{
        String sql = "SELECT name FROM sizes WHERE sizeid = ?;";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setInt(1, sizeId);
        ResultSet rs = s.executeQuery();
        rs.beforeFirst();
        if(rs.next()){
            return rs.getString(1);
        }
        else{
            return "";
        }
    }
    
    public static int GetCrustId(String crust) throws SQLException{
        String sql = "SELECT crustTypeId FROM crusttypes WHERE name = ?;";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setString(1, crust);
        ResultSet rs = s.executeQuery();
        rs.beforeFirst();
        if(rs.next()){
            return rs.getInt(1);
        }
        else{
            return 0;
        }
        
    }
    
    public static String GetCrustName(int crustId) throws SQLException{
        String sql = "SELECT name FROM crusttypes WHERE crustTypeId = ?;";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setInt(1, crustId);
        ResultSet rs = s.executeQuery();
        rs.beforeFirst();
        if(rs.next()){
            return rs.getString(1);
        }
        else{
            return "";
        }
        
    }
    
    public static int InsertPizza(Pizza p) throws SQLException{
//        String sql = "INSERT INTO pizza (sizeId, crustTypeId, price, orderId) VALUES (?, ?, ?, ?);";
//        PreparedStatement s = conn.prepareStatement(sql);
//        s.setInt(1, GetSizeId(p.getSize()));
//        s.setInt(2, GetCrustId(p.getCrustType()));
//        s.setDouble(3, p.getPrice());
//        s.setInt(4, p.getOrderId());
        
        String sql = "INSERT INTO pizza (sizeId, crustTypeId, price, orderId) VALUES ('" + GetSizeId(p.getSize()) + "', '" + GetCrustId(p.getCrustType()) + "', '" + p.getPrice() + "', '" + p.getOrderId() +"');";    
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
    
    public static boolean InsertToppings(ArrayList<Integer> toppingIds, int pizzaId) throws SQLException{
        boolean success = true;
        String sql = "INSERT INTO pizza_toppings_map (pizzaId, toppingId) VALUES (?, ?);";
        PreparedStatement s = conn.prepareStatement(sql);
        s.setInt(1, pizzaId);
        for(int t: toppingIds){
            s.setInt(2, t);
            if(s.executeUpdate() == 0){
                success = false;
            }
        }
        return success;
    }

    public static int GetPizzaCountByOrder(int orderId) throws SQLException{
        String sql = "SELECT COUNT(pizzaId) FROM pizza WHERE orderId = '" + orderId + "';";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        rs.beforeFirst();
        if(rs.next()){
            return rs.getInt(1);
        }
        else{
            return 0;
        }
    }

    public static ArrayList<Pizza> GetPizzasByOrder(int orderId) throws SQLException{
        String sql = "SELECT * FROM pizza WHERE orderId = '" + orderId + "';";
        Statement s = conn.createStatement();
        ResultSet rs = s.executeQuery(sql);
        ArrayList<Pizza> pizzas = new ArrayList();
        
        rs.beforeFirst();
        if(rs.next()){
            do{
                Pizza p = new Pizza();
                p.setPizzaId(rs.getInt(1));
                p.setSize(GetSizeName(rs.getInt(2)));
                if(rs.getInt(3)==0){
                    p.setIsFinished(false);
                }
                else{
                    p.setIsFinished(true);
                }
                p.setCrustType(GetCrustName(rs.getInt(4)));
                p.setPrice(rs.getDouble(5));
                p.setOrderId(rs.getInt(6));
                
                pizzas.add(p);
            }
            while(rs.next());
        }
        return pizzas;
    }

}
