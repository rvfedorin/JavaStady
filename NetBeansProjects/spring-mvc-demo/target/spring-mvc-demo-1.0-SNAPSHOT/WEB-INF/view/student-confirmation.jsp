<%-- 
    Document   : student-confirmation
    Created on : 16 авг. 2019 г., 16:52:32
    Author     : R. V. Fedorin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmation page</title>
    </head>
    <body>
        The student is confirmed: ${student.firstName} ${student.lastName}
        <br><br>
        Country is: ${student.country}
    </body>
</html>
