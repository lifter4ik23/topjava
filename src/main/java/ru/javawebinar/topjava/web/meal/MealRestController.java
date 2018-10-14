package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public Collection<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(authUserId(), meal -> true), authUserCaloriesPerDay());
//        return getFiltered(null, null, null, null);
    }

    public Collection<MealWithExceed> getFiltered(String startDate, String endDate, String startTime, String endTime) {
        log.info("getAll");

        LocalDate startDateParsed = DateTimeUtil.parseDateFromJsp(startDate);
        LocalDate endDateParsed = DateTimeUtil.parseDateFromJsp(endDate);
        LocalTime startTimeParsed = DateTimeUtil.parseTimeFromJsp(startTime);
        LocalTime endTimeParsed = DateTimeUtil.parseTimeFromJsp(endTime);

        Collection<Meal> mealList;

        if (startDateParsed != null && endDateParsed != null) {
            mealList = service.getAll(authUserId(), meal -> DateTimeUtil.isBetween(meal.getDate(), startDateParsed, endDateParsed));
        } else {
            mealList = service.getAll(authUserId(), meal -> true);
        }

        if (startTimeParsed != null && endTimeParsed != null) {
            return MealsUtil.getFilteredWithExceeded(mealList, authUserCaloriesPerDay(), startTimeParsed, endTimeParsed);
        }

        return MealsUtil.getWithExceeded(mealList, authUserCaloriesPerDay());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        if (meal == null) {
            return null;
        }
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        if (meal == null) {
            return;
        }
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

}