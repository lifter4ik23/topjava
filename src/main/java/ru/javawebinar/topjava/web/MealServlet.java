package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.Dao;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private Dao<Meal, MealWithExceed> mealDao = new MealDaoInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        if (request.getParameter("param") != null) {
            int id = Integer.parseInt(request.getParameter("id"));

            if (request.getParameter("param").equals("delete")) {
                Meal meal = mealDao.delete(id);
                log.debug("Meal successfully deleted. Meal details: " + meal);
                response.sendRedirect("meals");
                return;
            }
            if (request.getParameter("param").equals("edit")) {
                Meal meal = mealDao.getById(id);
                request.setAttribute("meal", meal);
            }
        }

        List<MealWithExceed> mealList = mealDao.getList();

        request.setAttribute("mealList", mealList);
        request.setAttribute("formatter", formatter);

        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String dateTime = request.getParameter("date") + " " + request.getParameter("time");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        LocalDateTime localDateTime = LocalDateTime.parse(dateTime, formatter);

        if (request.getParameter("param").equals("add")) {
            Meal meal = mealDao.add(new Meal(localDateTime, description, calories));
            log.debug("Meal successfully added. Meal details: " + meal);
        }
        if (request.getParameter("param").equals("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));
            Meal meal = mealDao.update(new Meal(id, localDateTime, description, calories));
            log.debug("Meal successfully updated. Meal details: " + meal);
        }

        response.sendRedirect("meals");
    }
}
