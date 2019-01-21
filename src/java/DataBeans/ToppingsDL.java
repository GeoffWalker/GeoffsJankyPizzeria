/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Toppings;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class ToppingsDL {

    private static Connection conn = null;
    
    public ToppingsDL() throws SQLException, ClassNotFoundException {
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
    
    //add topping method
    public static boolean AddTopping(Toppings t) throws SQLException{
        int toppingId = t.getToppingId();
        String name = t.getName();
        double price = t.getPrice();
        int empId = t.getEmpAddedBy();
        
        String sql = "INSERT INTO toppings (name, price, empAddedBy) VALUES ('" + name + "', '" + price + "', '" + empId + "');";
        
        Statement s = conn.createStatement();
        
        int success = s.executeUpdate(sql);
        
        if(success == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    //delete topping method
    public static boolean DeleteTopping(int toppingId) throws SQLException{        
        String sql = "UPDATE toppings SET isActive = 0 WHERE toppingId = '" + toppingId + "';";
                
        Statement s = conn.createStatement();
        
        int success = s.executeUpdate(sql);
        
        if(success == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    //update topping method
    public static boolean UpdateTopping(Toppings t, int empId) throws SQLException{
        int toppingId = t.getToppingId();
        String name = t.getName();
        double price = t.getPrice();
        LocalDate createDate = t.getCreateDate();
        String cdString = createDate.toString();
        
        int isActive = 0; 
        if (t.getIsActive()){
            isActive = 1;
        }
        
        String sql = "UPDATE toppings SET name = '" + name + "', price = '" + price + "', createDate = '" + cdString + 
                        "', empAddedBy = '" + empId + "', isActive = '" + isActive + " WHERE toppingId = '" + toppingId + "';";
                
        Statement s = conn.createStatement();
        
        int success = s.executeUpdate(sql);
        
        if(success == 1){
            return true;
        }
        else{
            return false;
        }
    }
    
    //get all available toppings method
    public static ArrayList<Toppings> GetAllAvailableToppings() throws SQLException{
        ArrayList<Toppings> toppings = new ArrayList();
        
        String sql = "SELECT * FROM toppings WHERE isActive = 1;";
        
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
            LocalDate formattedDateTime = LocalDate.parse(date, formatter);
            t.setCreateDate(formattedDateTime);
            
            t.setIsActive(rs.getBoolean(6));
            
            toppings.add(t);
        }while (rs.next());
        
        return toppings;
    }
    
    public static ArrayList<Integer> GetAllAvailableToppingIds() throws SQLException{
        ArrayList<Integer> toppings = new ArrayList();
        
        String sql = "SELECT toppingId FROM toppings WHERE isActive = 1;";
        
        Statement s = conn.createStatement();
        
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        
        do{
            toppings.add(rs.getInt(1));
        }while (rs.next());
        
        return toppings;
    }

    public static ArrayList<Toppings> GetToppingsByPizza(int pizzaId) throws SQLException{
        ArrayList<Toppings> toppings = new ArrayList();
        
        String sql = "SELECT t.* FROM toppings t INNER JOIN pizza_toppings_map tm ON t.toppingId = tm.toppingId WHERE tm.pizzaId = '" + pizzaId + "';";
        
        Statement s = conn.createStatement();
        
        ResultSet rs = s.executeQuery(sql);
        rs.beforeFirst();
        if(rs.next()){
            do{
                Toppings t = new Toppings();
                t.setToppingId(rs.getInt(1));
                t.setName(rs.getString(2));
                t.setPrice(rs.getDouble(3));

                String date = rs.getDate(4).toString();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate formattedDateTime = LocalDate.parse(date, formatter);
                t.setCreateDate(formattedDateTime);

                t.setIsActive(rs.getBoolean(6));

                toppings.add(t);
            }while (rs.next());
        }
        return toppings;
    }
}
