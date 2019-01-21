<%-- 
    Document   : employeeLogin
    Created on : 23-Nov-2018, 2:39:40 PM
    Author     : Geoff Walker
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <%
        if(request.getParameter("msg") != null){
            out.println("<script>alert('" + request.getParameter("msg") + "');</script>");
        }
    %>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="includes/pizzaStyle.css">
        <title>Geoff's Janky Pizzeria - Employee Login</title>
    </head>
    <body>
    <center>
        <h1>Enter Login Information</h1>
        <form name="employeeLogin" id="employeeLogin" method="post" action="empLogin_proc"><br/>
            
            username: <input type="text" name="userName" placeholder="JohnSmith"><br/>
            password: <input type="password" name="password" placeholder="password here"><br/>
            
            <br/><br/><input type="submit" value="Submit"><br/><br/>
        </form>
    </center>
    </body>
</html>
