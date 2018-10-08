
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css" >
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h1 align="center">Meals</h1>
    <table class="table_solid" align="center">
        <tr><th>Дата</th><th>Описание</th> <th>Калории</th></tr>
        <c:forEach var = "meal" items = "${mealWithExceedList}">
           <c:set var="textColor" value="FF0000"/>
               <c:if test="${meal.isExceed()}"> <c:set var = "textColor" value="FF0000"/> </c:if>
            <c:if test="${!meal.isExceed()}"> <c:set var = "textColor" value="21610B"/> </c:if>
            <tr>
                <td><p style=" color:${textColor}" > ${meal.getDateTime().toLocalDate()} ${meal.getDateTime().toLocalTime()}  </p></td>
                <td><p style=" color:${textColor}"> ${meal.getDescription()} </p></td>
                <td><p style=" color:${textColor}"> ${meal.getCalories()} </p></td>
            </tr>
        </c:forEach>

    </table>



</body>
</html>
