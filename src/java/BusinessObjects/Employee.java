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
public class Employee {

    //data members
    private int employeeId = 0;
    private String userName = "";
    private String password = "";
    
    //default constructor
    public Employee() {
    }

    //overloaded constructor
    public Employee(int employeeId, String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    //getters and setters for all data members
    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
    
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
       
    public static Employee GetEmployee(String userName) throws SQLException{
        return DataBeans.EmployeeDL.GetEmployee(userName);
    }
}
