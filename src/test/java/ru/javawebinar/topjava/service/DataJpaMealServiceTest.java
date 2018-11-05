package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL1;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL_ID;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles("datajpa")
public class DataJpaMealServiceTest extends MealServiceTest {

    @Test
    public void getWithOwner() throws Exception {
        Meal meal = service.getWithOwner(ADMIN_MEAL_ID, ADMIN_ID);
        User user = meal.getUser();
        assertMatch(meal, ADMIN_MEAL1);

        //Cannot locate field $$_hibernate_interceptor on class ru.javawebinar.topjava.model.User
//        UserTestData.assertMatch(user, ADMIN);

        UserTestData.assertMatch(ADMIN, user);
    }
}