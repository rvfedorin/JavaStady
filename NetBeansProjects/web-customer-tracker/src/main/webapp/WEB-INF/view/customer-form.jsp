<%-- 
    Document   : customer-form
    Created on : 26 сент. 2019 г., 16:00:16
    Author     : R. V. Fedorin
--%>

<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Save Customer</title>
        
        <link type="text/css" rel="stylesheet" 
              href="${pageContext.request.contextPath}/resources/css/style.css" />
        
        <link type="text/css" rel="stylesheet"
              href="${pageContext.request.contextPath}/resources/css/add-customer-style.css" />
        
    </head>
    <body>
        <div id="wrapper">
            <div id="header">
                <h1>CRM - Customer Relation Sheep</h1>
            </div>
        </div>
        <div id="container">
            <h3>Save customer</h3>
            <form:form action="saveCustomer" modelAttribute="customer" method="Post">
                // to update customer we need set customer id
                <form:hidden path="id" />
                <table>
                    <tbody>
                        <tr>
                            <td><label>First name:</label></td>
                            <td><form:input path="firstName" /></td>
                        </tr>
                        <tr>
                            <td><label>Last name:</label></td>
                            <td><form:input path="lastName" /></td>
                        </tr>
                        <tr>
                            <td><label>E-mail:</label></td>
                            <td><form:input path="email" /></td>
                        </tr>
                        <tr>
                            <td><label></label></td>
                            <td><input type="submit" value="Save" class="save" /></td>
                        </tr>
                    </tbody>
                </table>
            </form:form>
            
            <div style="clear; both;">
                <p>
                    <a href="${pageContext.request.contextPath}/customer/list"> Back to list</a>
                </p>
            </div> 
            
        </div>
        
    </body>
</html>
