/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BusinessObjects.Order;
import BusinessObjects.Toppings;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
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
public class EmployeePage extends HttpServlet {

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
            out.println("<link rel=\"stylesheet\" href=\"includes/pizzaStyle.css\">");
            out.println("<html>");
            
            //make sure they are logged in.
            HttpSession session = request.getSession();
            if(session.getAttribute("userName") == null){
                response.sendRedirect("employeeLogin.jsp?msg=You must login first.");
            }
            //check for error messages.
            if(request.getParameter("msg") != null){
                out.println("<script>alert('" + request.getParameter("msg") + "');</script>");
            }
            
            out.println("<head>");
            out.println("<title>Geoff's Janky Pizzeria - Employee Settings</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //javascript function to show/hide divs for orders
            out.println("<script>"
                    + "function togglePending(){"
                        + "var x = document.getElementById(\"pendingForm\");"
                        + "if(x.style.display === \"none\"){"
                            + "x.style.display = \"block\";"
                        + "} else{"
                            + "x.style.display = \"none\"; } }"
                    +"function toggleFilled(){"
                        + "var x = document.getElementById(\"filledForm\");"
                        + "if(x.style.display === \"none\"){"
                            + "x.style.display = \"block\";"
                        + "} else{"
                            + "x.style.display = \"none\"; } }"
                    +"</script>");
            
            out.println("<center>");
                out.println("<h1>Manage Toppings</h1>");
                out.println("<div style=\"width: 30%\">");
                    out.println("<form name=\"removeToppings\" action=\"RemoveTopping_proc\" method=\"post\">");
                        out.println("<div style=\"text-align: left\">");
                        out.println("<h4>Check to remove toppings:</h4>");

                        ArrayList<Toppings> toppings = BusinessObjects.Toppings.GetAllAvailableToppings();            
                        for(Toppings t: toppings){
                            out.println("<input type='checkbox' name='" + t.getToppingId()+ "' value=\"on\">" + t.getName().toString() +"</input><br/>");
                        }

                        out.println("<center><input type=\"submit\" value=\"submit\"></center>");
                        out.println("<br/>");
                        out.println("</div>");
                    out.println("</form>");
                    
                    out.println("<br/>");
                    
                    out.println("<form name=\"newTopping\" action=\"addTopping_proc\" method=\"post\">");
                        out.println("<div style=\"text-align: left\">");
                        out.println("<h4>Add new topping:</h4>");
                        out.println("Topping name: <input type=\"text\" name=\"toppingName\" placeholder=\"enter topping name\"><br/>");
                        out.println("Topping price: <input type=\"number\" min=\"0\" step=\"0.01\" name=\"toppingPrice\" placeholder=\"enter price\"><br/>");
                        out.println("<center><input type=\"submit\" value=\"submit\"></center>");
                        out.println("<br/>");
                        out.println("</div>");
                    out.println("</form>");
                    
                    out.println("<br/>");
                out.println("</div>");
                
                out.println("<h1>Manage Orders</h1>");
                
                out.println("<input type=\"button\" class=\"btn\" onclick=\"togglePending()\" value=\"Toggle Pending Orders\">&nbsp;&nbsp;&nbsp;&nbsp;");
                out.println("<input type=\"button\" class=\"btn\" onclick=\"toggleFilled()\" value=\"Toggle Filled Orders\">");
                out.println("<br/><br/>");
                
                out.println("<div style=\"width: 30%\">");
                    out.println("<div id=\"pendingForm\" style=\"display:none\">");
                        out.println("<form name=\"fillOrders\" action=\"fillOrder_proc\" method=\"post\">");
                            out.println("<div style=\"text-align: left\">");

                            ArrayList<Order> pendingOrders = BusinessObjects.Order.GetPendingOrders();
                            if (pendingOrders.size()>0){
                                out.println("<h4>Check to fill order:</h4>");
                                for(Order o: pendingOrders){
                                    out.println("<input type='checkbox' name='" + o.getOrderId()+ "' value=\"on\">Order ID: " + o.getOrderId() + " -> Total:" + 
                                        o.getTotalPrice() + "<br/>&nbsp;&nbsp;&nbsp;Deliver by: " + o.getDeliveryDateTime() + "<br/>&nbsp;&nbsp;&nbsp;Ordered: " + o.getPlaceDateTime() + 
                                        "<br/>&nbsp;&nbsp;&nbsp;Customer ID: " + o.getCustomerId() + "&nbsp;&nbsp;&nbsp;Status: " + o.getOrderStatus()+"</input><br/><br/>");
                                }
                                out.println("<center><input type=\"submit\" value=\"submit\"></center>");
                            }
                            else{
                                out.println("<h4>There are no pending orders.</h4>");
                            }
                            
                            out.println("<br/>");
                            out.println("</div>");
                        out.println("</form>");
                    out.println("</div>");
                    
                    out.println("<br/>");
                    
                    out.println("<div id=\"filledForm\" style=\"display:none\">");
                        out.println("<form name=\"filledOrders\" action=\"\" method=\"post\">");
                            out.println("<div style=\"text-align: left\">");
                            out.println("<h4>Filled orders:</h4>");

                            ArrayList<Order> filledOrders = BusinessObjects.Order.GetFilledOrders();
                            for(Order o: filledOrders){
                                out.println("Order ID: " + o.getOrderId() + " -> Total:" + o.getTotalPrice() + "<br/>&nbsp;&nbsp;&nbsp;Deliver by: " 
                                    + o.getDeliveryDateTime() + "<br/>&nbsp;&nbsp;&nbsp;Ordered: " + o.getPlaceDateTime() + 
                                    "<br/>&nbsp;&nbsp;&nbsp;Customer ID: " + o.getCustomerId() + "&nbsp;&nbsp;&nbsp;Status: " + o.getOrderStatus()+"<br/><br/>");
                            }
                            out.println("<br/>");
                            out.println("</div>");
                        out.println("</form>");
                    out.println("</div>");
                out.println("</div>");
            out.println("</center>");
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
            Logger.getLogger(EmployeePage.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(EmployeePage.class.getName()).log(Level.SEVERE, null, ex);
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
