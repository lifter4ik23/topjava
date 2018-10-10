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
                <td>${formatter.format(meal.dateTime)}</td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?id=${meal.id}&param=edit">Edit</a></td>
                <td><a href="meals?id=${meal.id}&param=delete">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<c:choose>
    <c:when test="${meal == null}">
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
            <button name="param" type="submit" value="add">Add Meal</button>
        </form>
    </c:when>
    <c:otherwise>
        <br/>
        <form method="post">
            <input type="text" name="id" hidden value="${meal.id}"/>
            Дата<input type="date" name="date" value="${meal.getDate()}"/>
            <br/>
            Время<input type="time" name="time" value="${meal.getTime()}"/>
            <br/>
            Название<input type="text" name="description" value="${meal.description}"/>
            <br/>
            Калории<input type="number" name="calories" value="${meal.calories}"/>
            <br/>
            <br/>
            <button name="param" type="submit" value="edit">Apply Changes</button>
        </form>
    </c:otherwise>
    </c:choose>
</body>
</html>
