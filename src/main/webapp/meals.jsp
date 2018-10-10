<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Users</title>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>

<c:if test="${empty mealList}">
    список пуст
</c:if>

<c:if test="${!empty mealList}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date/Time</th>
            <th>Description</th>
            <th>Calories</th>
            <th>Edit</th>
            <th>Delete</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${mealList}" var="meal">
            <tr style="${meal.exceed ? 'color:red' : 'color:green'}">
                <td>${meal.id}</td>
                <td>${formatter.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="mealEdit?id=${meal.id}">Edit</a></td>
                <td><a href="mealEdit?id=${meal.id}&param=del">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<br/>
<form method="post">
    Дата<input type="date" name="date"/>
    <br/>
    Время<input type="time" name="time"/>
    <br/>
    Название<input type="text" name="description"/>
    <br/>
    Калории<input type="number" name="calories"/>
    <br/>
    <br/>
    <input type="submit" value="Add Meal"/>
</form>
</body>
</html>
