<%-- 
    Document   : OrderPizza
    Created on : 23-Nov-2018, 2:21:22 PM
    Author     : Geoff Walker
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.ArrayList"%>
<%@page import="BusinessObjects.Toppings"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="includes/pizzaStyle.css">
        <title>Geoff's Janky Pizzeria - Add Pizzas</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="inputs">
                <div>
                    <center>
                        <h1>Your Order</h1>
                        
                        <%
                            //get the session variables to be used later.
                            int custId = Integer.parseInt(session.getAttribute("custId").toString());
                            int orderId = Integer.parseInt(session.getAttribute("orderId").toString());
                        %>

                        <%out.println("<p>Pizzas in Cart:&nbsp;" + BusinessObjects.Pizza.GetPizzaCountByOrder(orderId) + "</p>");%>

                        <%out.println("<p>Cart Total:&nbsp;" + new DecimalFormat("$#.##").format(BusinessObjects.Order.GetOrder(orderId).getTotalPrice()) + "</p>");%>

                        <% 
                            //display a pay button, if there is at least 1 pizza in the order.
                            if(BusinessObjects.Pizza.GetPizzaCountByOrder(orderId) > 0){
                                out.println("<a href=\"OrderConfirmation.xhtml\" class=\"paybutton\">Confirm and Pay</a>&nbsp;&nbsp;");

                                out.println("<br/><h2>Or add more pizzas below</h2>");
                            }

                        %>
                        
                        <form name="orderPizza" action="Order2_proc" method="post">
                            <p>Select Your Size</p>
                            <%
                                ArrayList<String> sizes = BusinessObjects.Pizza.GetSizes();
                                boolean sizeChecked = false;
                                for(String s: sizes){
                                    if(!sizeChecked){
                                        out.println("<input type='radio' checked name='size' value='" + s + "' id='rdo_" + s + "'>" + s + "</input><br/>");
                                        sizeChecked = true;
                                    }
                                    else{
                                        out.println("<input type='radio' name='size' value='" + s + "' id='rdo_" + s + "'>" + s + "</input><br/>");
                                    }
                                }
                                out.println("<hr/>");
                            %>
                            
                            <p>Select Your Crust</p>
                            <%
                                ArrayList<String> crusts = BusinessObjects.Pizza.GetCrusts();
                                boolean rdoChecked = false;
                                for(String c: crusts){
                                    if(!rdoChecked){
                                        out.println("<input type='radio' checked name='crust' value='" + c + "' id='rdo_" + c + "'>" + c + "</input><br/>");
                                        rdoChecked = true;
                                    }
                                    else{
                                        out.println("<input type='radio' name='crust' value='" + c + "' id='rdo_" + c + "'>" + c + "</input><br/>");
                                    }
                                }
                                out.println("<hr/>");
                            %>
                            
                            <p>Select Your Toppings</p>
                            <%
                                ArrayList<Toppings> toppings = BusinessObjects.Toppings.GetAllAvailableToppings();
                                boolean chkChecked = false;
                                for(Toppings t: toppings){
                                    if (!chkChecked){
                                        out.println("<input type='checkbox' checked name='" + t.getToppingId() + "'>" + t.getName().toString() + "   $" + t.getPrice() + "</input><br/>");
                                        chkChecked = true;
                                    }
                                    else{
                                        out.println("<input type='checkbox' name='" + t.getToppingId()+ "'>" + t.getName().toString() + "   $" + t.getPrice() + "</input><br/>");
                                    }
                                }
                                out.println("<hr/>");
                            %>
                            
                            <p>How many would you like?</p>
                            <input type="number" min="1" max="5" name="numPizzas" value="1"></input>
                            
                            <hr/>
                            
                            <input type="submit" value="Add to Cart">
                        </form>
                    </center>
                </div>
            </div>
        </div>
    </body>
    </html>
    </body>
</html>
