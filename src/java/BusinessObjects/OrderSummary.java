/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessObjects;

import java.io.Serializable;
import java.sql.SQLException;
import java.time.LocalDateTime;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Geoff Walker
 */
@Named(value = "orderSummary")
@ViewScoped
public class OrderSummary implements Serializable{

    
    public OrderSummary() {
    }
    
    public static Order GetOrder(int orderId) throws SQLException{
        return DataBeans.OrdersDL.GetOrder(orderId);
    }
    
    public static LocalDateTime GetEndDate(int orderId) throws SQLException{
        return GetOrder(orderId).getDeliveryDateTime();
    }
    
}
