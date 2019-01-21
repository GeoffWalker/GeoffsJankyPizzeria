/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Customer;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.ejb.Stateless;

/**
 *
 * @author Geoff Walker
 */
@Stateless
public class CustomerDL {

    private static Connection conn = null;
    
    public CustomerDL() throws SQLException, ClassNotFoundException {
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
    
    public static Customer GetCustomer(int id) throws SQLException{
        String sql = "SELECT * FROM customer WHERE customerid = '" + id + "';";
        Statement s = conn.createStatement();
        
        ResultSet rs = s.executeQuery(sql);
        rs.first();
        
        Customer c = new Customer();
        c.setCustomerId(id);
        c.setFirstName(rs.getString(2));
        c.setLastName(rs.getString(3));
        c.setPhoneNumber(rs.getString(4));
        c.setEmail(rs.getString(5));
        c.setHouseNumber(rs.getInt(6));
        c.setStreet(rs.getString(7));
        c.setProvince(rs.getString(8));
        c.setPostalCode(rs.getString(9));
        
        return c;
    }
    
    //this method returns the customer id of the inserted customer, or 0 is there is an error.
    public static int InsertCustomer(Customer c) throws SQLException{
        String sql = "INSERT INTO customer (firstName, lastName, phoneNumber, email, houseNumber, street, province, postalCode) VALUES ('" +
                        c.getFirstName() + "', '" + c.getLastName() + "', '" +
                        c.getPhoneNumber() + "', '" + c.getEmail() + "', '" +
                        c.getHouseNumber() + "', '" + c.getStreet() + "', '" +
                        c.getProvince() + "', '" + c.getPostalCode() + "');";
        
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
}
