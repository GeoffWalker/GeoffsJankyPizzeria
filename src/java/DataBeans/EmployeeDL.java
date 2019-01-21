/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Employee;
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
public class EmployeeDL {

    private static Connection conn = null;
    
    public EmployeeDL() throws SQLException, ClassNotFoundException {
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
    
    public static Employee GetEmployee(String userName) throws SQLException{
        String sql = "SELECT * FROM employee WHERE username = '" + userName + "';";
        Statement s = conn.createStatement();
        
        Employee e = new Employee();
        
        ResultSet rs = s.executeQuery(sql);
        
        rs.beforeFirst();
        if(rs.next()){
            int id = rs.getInt("employeeid");
            e.setEmployeeId(id);
            e.setUserName(userName);
            e.setPassword(rs.getString(3));
        }
        return e;
    }
}
