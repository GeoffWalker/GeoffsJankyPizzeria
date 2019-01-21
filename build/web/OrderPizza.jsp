<%-- 
    Document   : OrderPizza
    Created on : 23-Nov-2018, 2:21:22 PM
    Author     : Geoff Walker
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="BusinessObjects.Toppings"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="includes/pizzaStyle.css">
        <title>Geoff's Janky Pizzeria - Order Pizza</title>
    </head>
    <body>
        <div id="wrapper">
            <div id="inputs">
                <div>
                    <center>
                        <h1>Order</h1>
                        
                        <form name="orderPizza" action="Order_proc" method="post">    
                            
                            <p>Customer Info</p>
                            First name: <input type="text" maxlength="45" required name="firstName" placeholder="John"><br/>
                            Last name: <input type="text" maxlength="45" required name="lastName" placeholder="Smith"><br/>
                            Phone number: <input type="tel" maxlength="45" required name="phoneNumber" placeholder="506-444-5555"><br/>
                            email: <input type="email" maxlength="45" required name="email" placeholder="johnsmith@gmail.com"><br/>
                            House number: <input type="number" min="1" max="99999999999" required name="houseNumber" placeholder="123"><br/>
                            Street: <input type="text" required name="street" placeholder="Main st."><br/>
                            Province: <input type="text" maxlength="2" required name="province" placeholder="NB"><br/>
                            Postal code: <input type="text" maxlength="7" required name="postalCode" placeholder="A1A 1A1"><br/><br/>

                            <p>Choose your order type:</p>
                            <input type='radio' name='orderType' checked value='pickup' id='rdo_pickup'>Pick up in store</input><br/>
                            <input type='radio' name='orderType' value='delivery' id='rdo_delivery'>Delivery</input><br/><br/>
                            
                            <input type="submit" value="Next">
                        </form>
                    </center>
                </div>
            </div>
        </div>
    </body>
    </html>
    </body>
</html>
