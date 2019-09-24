<%-- 
    Document   : customer-form
    Created on : 18 авг. 2019 г., 16:59:35
    Author     : R. V. Fedorin
--%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer registration form</title>
        
        <style>
            .error{color: red}
        </style>
    </head>
    <body>
        <i>Fill out the form. Asterisk (*) means requared.</i>
        <br>
        <form:form action="processForm" modelAttribute="customer">
            First name: <form:input path="firstName" />
            <form:errors path="firstName" cssClass="error" />
            <br><br>
            Last name(*): <form:input path="lastName" />
            <form:errors path="lastName" cssClass="error" />
            <br><br>
            Post code: <form:input path="postCode" />
            <form:errors path="postCode" cssClass="error" />
            <br><br>
            Free pass(*): <form:input path="freePass" />
            <form:errors path="freePass" cssClass="error" />
            <br><br>
            Free course code: <form:input path="courseCode" />
            <form:errors path="courseCode" cssClass="error" />
            <br><br>
            <input type="submit" value="Submit" />
        </form:form>
    </body>
</html>
