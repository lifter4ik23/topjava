package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    public List<MealWithExceed> getAll() {
        return MealsUtil.getWithExceeded(service.getAll(authUserId()), authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getFiltered(String startDateToParse, String endDateToParse, String startTimeToParse, String endTimeToParse) {
        log.info("getAll");

        LocalDate startDate = (startDateToParse == null || startDateToParse.equals("")) ? LocalDate.MIN : LocalDate.parse(startDateToParse);
        LocalDate endDate = (endDateToParse == null || endDateToParse.equals("")) ? LocalDate.MAX : LocalDate.parse(endDateToParse);
        LocalTime startTime = (startTimeToParse == null || startTimeToParse.equals("")) ? LocalTime.MIN : LocalTime.parse(startTimeToParse);
        LocalTime endTime = (endTimeToParse == null || endTimeToParse.equals("")) ? LocalTime.MAX : LocalTime.parse(endTimeToParse);

        List<Meal> mealList = service.getFiltered(authUserId(), startDate, endDate);

        return MealsUtil.getFilteredWithExceeded(mealList, authUserCaloriesPerDay(), startTime, endTime);
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