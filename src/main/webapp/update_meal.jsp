<%--
  Created by IntelliJ IDEA.
  User: saraykin
  Date: 08.10.2018
  Time: 14:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="utf-8" />
    <title>Update Meal</title>
</head>
<body>

<h1 align="center"> Update Meal</h1>
<p></p>

<table align="center">
    <form accept-charset="UTF-8" method="post" action="update" >
        <tr>
            <td> <input type="text" name="description" value="${meal.getDescription()}"></td>
            <td><input type="datetime-local" name="dateTime" value="${meal.getDateTime()}"> </td>
            <td><input type="number" name="calories" value="${meal.getCalories()}"></td>
            <input type="hidden" name="id" value="${id}">
            <td><input type="submit" value="Обновить"></td>
        </tr>
    </form>
</table>

</body>
</html>
