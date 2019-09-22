<%-- 
    Document   : helloworld-form
    Created on : 14 авг. 2019 г., 14:20:43
    Author     : wolf
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hello world - Input Form</title>
    </head>
    <body>
        <form action="processFormThree" method="GET">
            <input type="text" name="studentName" 
                   placeholder="What's your name?" />
            <input type="submit" />
        </form>
    </body>
</html>
