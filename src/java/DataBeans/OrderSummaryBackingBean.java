/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBeans;

import BusinessObjects.Order;
import static BusinessObjects.OrderSummary.GetOrder;
import BusinessObjects.Pizza;
import BusinessObjects.Toppings;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geoff Walker
 */
@Named(value = "orderSummaryBackingBean")
@SessionScoped
public class OrderSummaryBackingBean implements Serializable{

    FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
    private String orderType = session.getAttribute("orderType").toString();
    private double orderTotal;
    private LocalDateTime endDate;
    private int orderId = Integer.parseInt(session.getAttribute("orderId").toString());

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getEndDate() throws SQLException {
        Order o = BusinessObjects.Order.GetOrder(orderId);
        endDate = o.getDeliveryDateTime();
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getOrderTotal() throws SQLException {
        Order o = BusinessObjects.Order.GetOrder(orderId);
        orderTotal = o.getTotalPrice();

        return Double.parseDouble(new DecimalFormat("#.##").format(orderTotal));
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    
    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }
    
    public OrderSummaryBackingBean() {
    }
    
    public ArrayList<Pizza> GetPizzasByOrder(int orderId) throws SQLException{
        return BusinessObjects.Pizza.GetPizzasByOrder(orderId);
    }
    
    public ArrayList<Toppings> GetToppingsByPizza(int pizzaId) throws SQLException{
        return BusinessObjects.Toppings.GetToppingsByPizza(pizzaId);
    }
    
    public void SubmitOrderPaypal() throws IOException, SQLException{
        BusinessObjects.Order.ConfirmOrder(Integer.parseInt(session.getAttribute("orderId").toString()));
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect("http://www.paypal.com/");
    }

    public String SubmitOrderCash() throws IOException, SQLException{
        BusinessObjects.Order.ConfirmOrder(Integer.parseInt(session.getAttribute("orderId").toString()));
        return "ThankYou.xhtml";
    }
}
