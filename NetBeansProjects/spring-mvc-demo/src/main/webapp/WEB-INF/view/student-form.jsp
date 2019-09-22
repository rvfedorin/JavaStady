<%-- 
    Document   : student-form
    Created on : 16 авг. 2019 г., 15:41:02
    Author     : R. V. Fedorin
--%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Student registration form</title>
    </head>
    <body>
        <form:form action="processForm" modelAttribute="student">
            First name: <form:input path="firstName" />
            <br><br>
            Last name: <form:input path="lastName" />
            <br><br>
            <form:select path="country">
                <form:options items="${student.countryOptions}" />
            </form:select>
            <br><br>
            <input type="submit" value="Submit" />
            
        </form:form>
    </body>
</html>
