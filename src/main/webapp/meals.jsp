<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<c:if test="${!empty mealList}">
    <table border="1">
        <thead>
        <tr>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealList}" var="meal">
            <tr style="${meal.exceed ? 'color:red' : 'color:green'}">
                <td>${formatter.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>
