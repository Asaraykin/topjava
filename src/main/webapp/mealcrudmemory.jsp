
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" >
    <title>CRUD</title>
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<h1 align="center">Meals CRUD From Memory</h1>

<table align="center">
    <tr><td>Start time</td> <td>End time</td></tr>
        <tr>
            <form method="post" action="mealcrudmemory">
        <td><input  type="time" name="startTime" required></td>
        <td><input  type="time" name="endTime" required> <input type="submit" value="Фильтровать"></td>
            </form>
        </tr>
</table>

<table class="table_solid" align="center">
    <tr><th>Дата</th><th>Описание</th> <th>Калории</th><th></th><th></th></tr>
    <c:forEach var = "meal" items = "${mealWithExceedList}" varStatus="count" step="1" begin="0">
        <c:set var = "textColor" value="${meal.isExceed() ? 'FF0000' : '21610B'}"/>
        <tr>
            <td><p style=" color:${textColor}" > ${meal.getDateTime().toLocalDate()} ${meal.getDateTime().toLocalTime()}  </p></td>
            <td><p style=" color:${textColor}"> ${meal.getDescription()} </p></td>
            <td><p style=" color:${textColor}"> ${meal.getCalories()} </p></td>
            <td><a href="mealcrudmemory?delete=${meal.getId()}">delete</a></td>
            <td><a href="update?id=${meal.getId()}">update</a></td>
        </tr>
    </c:forEach>
 </table>
<p></p>

<table align="center">
    <form method="post" action="add_meal">
        <tr>
            <td> <input type="text" name="description"></td>
            <td><input type="datetime-local" name="dateTime" ></td>
            <td><input type="number" name="calories" ></td>
            <td><input type="submit" value="Добавить"></td>
        </tr>
    </form>
</table>
</body>
</html>
