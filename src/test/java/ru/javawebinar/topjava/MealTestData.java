package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MealTestData {
    public static final Meal MEAL_1 = new Meal(100007, LocalDateTime.of(2018, Month.OCTOBER, 21, 20, 0), "Ужин", 510);
    public static final Meal MEAL_2 = new Meal(100006, LocalDateTime.of(2018, Month.OCTOBER, 21, 13, 0), "Обед", 500);
    public static final Meal MEAL_3 = new Meal(100005, LocalDateTime.of(2018, Month.OCTOBER, 21, 10, 0), "Завтрак", 1000);
    public static final Meal MEAL_4 = new Meal(100004, LocalDateTime.of(2018, Month.OCTOBER, 20, 20, 0), "Ужин", 500);
    public static final Meal MEAL_5 = new Meal(100003, LocalDateTime.of(2018, Month.OCTOBER, 20, 13, 0), "Обед", 1000);
    public static final Meal MEAL_6 = new Meal(100002, LocalDateTime.of(2018, Month.OCTOBER, 20, 10, 0), "Завтрак", 500);

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    private static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}