package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void create() {
        Meal newMeal = new Meal(null, LocalDateTime.of(2018, Month.OCTOBER, 22, 12, 0), "Ланч", 1500);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), newMeal, MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeCreate() {
        service.create(new Meal(null, LocalDateTime.of(2018, Month.OCTOBER, 21, 13, 0), "Ланч", 1500), USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_3.getId(), USER_ID);
        assertMatch(service.getAll(USER_ID), MEAL_1, MEAL_2, MEAL_4, MEAL_5, MEAL_6);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        service.delete(MEAL_5.getId(), ADMIN_ID);
    }

    @Test
    public void get() {
        Meal meal = service.get(MEAL_4.getId(), USER_ID);
        assertMatch(meal, MEAL_4);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(MEAL_1.getId(), ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updated = new Meal(MEAL_6);
        updated.setDescription("Булочка");
        updated.setCalories(250);
        service.update(updated, USER_ID);
        assertMatch(service.get(updated.getId(), USER_ID), updated);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateTimeUpdate() {
        Meal updated = new Meal(MEAL_3);
        updated.setDateTime(LocalDateTime.of(2018, Month.OCTOBER, 21, 13, 0));
        service.update(updated, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        service.update(MEAL_3, ADMIN_ID);
    }

    @Test
    public void getAll() {
        List<Meal> all = service.getAll(USER_ID);
        assertMatch(all, MEAL_1, MEAL_2, MEAL_3, MEAL_4, MEAL_5, MEAL_6);
    }

    @Test
    public void getAllBetween() {
        List<Meal> allBetween = service.getBetweenDates(LocalDate.of(2018, Month.SEPTEMBER, 19), LocalDate.of(2018, Month.OCTOBER, 20), USER_ID);
        assertMatch(allBetween, MEAL_4, MEAL_5, MEAL_6);
    }
}