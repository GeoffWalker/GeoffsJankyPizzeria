/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BusinessObjects.Customer;
import BusinessObjects.Order;
import BusinessObjects.Toppings;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Geoff Walker
 */
public class Order_proc extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Order_proc</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //get customer info
            String fName = request.getParameter("firstName");
            String lName = request.getParameter("lastName");
            String phone = request.getParameter("phoneNumber");
            String email = request.getParameter("email");
            int houseNum = Integer.parseInt(request.getParameter("houseNumber"));
            String street = request.getParameter("street");
            String province = request.getParameter("province");
            String postal = request.getParameter("postalCode");
            
            //Create customer, insert customer to database, save the customerId.
            Customer c = new Customer();
            c.setFirstName(fName);
            c.setLastName(lName);
            c.setPhoneNumber(phone);
            c.setEmail(email);
            c.setHouseNumber(houseNum);
            c.setStreet(street);
            c.setProvince(province);
            c.setPostalCode(postal);
            
            int custId = c.InsertCustomer();
            
            //create an order for the current customer and insert it into the database. Save the orderId to session.
            Order o = new Order();
            o.setCustomerId(custId);
            int orderId = o.InsertNewOrder();
            
            HttpSession session = request.getSession();
            session.setAttribute("custId", custId);
            session.setAttribute("orderId", orderId);
            session.setAttribute("orderType", request.getParameter("orderType")); //save this for later.
            
            response.sendRedirect("OrderPizza2.jsp");
            
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Order_proc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Order_proc.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
