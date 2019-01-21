/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import BusinessObjects.Pizza;
import BusinessObjects.Toppings;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
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
public class Order2_proc extends HttpServlet {

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
            out.println("<title>Servlet Order2_proc</title>");            
            out.println("</head>");
            out.println("<body>");
            
            //get the customer id and order id
            HttpSession session = request.getSession();
            int custId = Integer.parseInt(session.getAttribute("custId").toString());
            int orderId = Integer.parseInt(session.getAttribute("orderId").toString()); 
            
            //get size selection and size price
            String size = request.getParameter("size");
            double pizzaPrice = BusinessObjects.Pizza.GetSizePrice(size);
            
            //get crust selection
            String crust = request.getParameter("crust");
            
            //get toppings selections
            ArrayList<Integer> toppingChoices = new ArrayList();
            ArrayList<Integer> availableToppings = BusinessObjects.Toppings.GetAllAvailableToppingIds();
            
            Enumeration<String> parameterNames = request.getParameterNames(); //get a list of all post parameters
            while (parameterNames.hasMoreElements()) { //loop through all post parameters
                String paramName = parameterNames.nextElement(); 
                try{
                    if (availableToppings.contains(Integer.parseInt(paramName))){ //check if the post parameter matches one of the available topping IDs
                        String param = request.getParameter(String.valueOf(paramName));
                        if(param.equals("on")){ //check if the check box is checked
                            toppingChoices.add(Integer.parseInt(paramName)); //add the topping ID to the list of toppings for the pizza. 
                        }
                    }
                }
                catch (Exception ex){
                    continue;
                }
            }
            
            //update price to include toppings and tax
            pizzaPrice += BusinessObjects.Pizza.GetToppingPrice(toppingChoices);
            pizzaPrice = pizzaPrice * 1.15; 
            
            //check how many pizzas they want.
            int numPizzas = Integer.parseInt(request.getParameter("numPizzas"));
            
            //update the order price to include the price of the new pizza.
            BusinessObjects.Order.UpdateOrderPrice(pizzaPrice*numPizzas, orderId);
            
            //update pizza, then insert pizza into database. Do this x times, where x is the number of pizzas they want.
            Pizza p = new Pizza();
            p.setCrustType(crust);
            p.setSize(size);
            p.setPrice(pizzaPrice);
            p.setOrderId(orderId);
            
            for(int i = 1; i <= numPizzas; i++){
                int pizzaId = p.InsertPizza();
                
                //update the pizza id, then insert the toppings to the topping map for each pizza.
                p.setPizzaId(pizzaId);
                p.InsertToppings(toppingChoices);                
            }
            
            //redirect back to pizza order page
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
            Logger.getLogger(Order2_proc.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Order2_proc.class.getName()).log(Level.SEVERE, null, ex);
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
