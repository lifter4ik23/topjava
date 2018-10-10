<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>MealEdit</title>

</head>
<body>


<h3><a href="meals">Meals</a></h3>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Date/Time</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td>${meal.id}</td>
        <td>${formatter.format(meal.dateTime)}</td>
        <td>${meal.description}</td>
        <td>${meal.calories}</td>
    </tr>
    </tbody>
</table>

<br/>
<form method="post">
    Дата<input type="date" name="date" value="${meal.getDate()}"/>
    <br/>
    Время<input type="time" name="time" value="${meal.getTime()}"/>
    <br/>
    Название<input type="text" name="description" value="${meal.description}"/>
    <br/>
    Калории<input type="number" name="calories" value="${meal.calories}"/>
    <br/>
    <br/>
    <input type="submit" value="Apply changes"/>
</form>

</body>
</html>