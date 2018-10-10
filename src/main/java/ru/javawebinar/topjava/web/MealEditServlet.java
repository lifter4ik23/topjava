package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealEditServlet extends HttpServlet {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Dao<Meal> mealDao = new MealDaoInMemory();
    private Meal meal;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        meal = mealDao.getById(id);

        if (request.getParameter("param") != null) {
            mealDao.delete(meal);
            response.sendRedirect("meals");
            return;
        }

        request.setAttribute("meal", meal);
        request.setAttribute("formatter", formatter);

        request.getRequestDispatcher("/mealEdit.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dateTime = request.getParameter("date") + " " + request.getParameter("time");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        mealDao.update(new Meal(localDateTime, description, calories, meal.getId()));

        response.sendRedirect("meals");
    }
}
