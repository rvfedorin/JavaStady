<%-- 
    Document   : customer-confirmation
    Created on : 18 авг. 2019 г., 17:39:11
    Author     : R. V. Fedorin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login successed</title>
    </head>
    <body>
        Hello ${customer.firstName} ${customer.lastName} pass: ${customer.freePass}
        <br><br>
        Postal code: ${customer.postCode}
        Course code: ${customer.courseCode}
    </body>
</html>
