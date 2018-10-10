package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final LocalTime startTime = LocalTime.MIN;
    private static final LocalTime endTime = LocalTime.MAX;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Dao<Meal> mealDao = new MealDaoInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> mealList = MealsUtil.getFilteredWithExceeded(mealDao.getList(), startTime, endTime, 2000);

        request.setAttribute("mealList", mealList);
        request.setAttribute("formatter", formatter);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
//        response.sendRedirect("meals.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dateTime = request.getParameter("date") + " " + request.getParameter("time");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        Meal meal = new Meal(localDateTime, description, calories);
        mealDao.add(meal);

        response.sendRedirect("meals");
    }
}
