package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        System.out.println(getFilteredWithExceeded2(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));


    }

    private static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        return mealList.stream()
                .peek(e -> caloriesPerDayMap.merge(e.getDateTime().toLocalDate(), e.getCalories(), Integer::sum))
                .filter(e -> TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime))
                .sorted(Comparator.comparing(UserMeal::getDateTime))
                .map(e -> new UserMealWithExceed(e.getDateTime(), e.getDescription(), e.getCalories(),
                        caloriesPerDayMap.get(e.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static List<UserMealWithExceed> getFilteredWithExceeded2(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();
        mealList.forEach(e -> caloriesPerDayMap.merge(e.getDateTime().toLocalDate(), e.getCalories(), Integer::sum));
//                mealList.stream()
//                .collect(Collectors.toMap(userMeal -> userMeal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(e -> TimeUtil.isBetween(e.getDateTime().toLocalTime(), startTime, endTime))
                .map(e -> new UserMealWithExceed(e.getDateTime(), e.getDescription(), e.getCalories(),
                        caloriesPerDayMap.get(e.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

        public static List<UserMealWithExceed> getFilteredWithExceededByCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExceed> result = new ArrayList<>();
        List<UserMeal> filteredByTimeList = new ArrayList<>();
        Map<LocalDate, Integer> caloriesPerDayMap = new HashMap<>();

        for (UserMeal meal : mealList) {
            LocalDate mealDate = meal.getDateTime().toLocalDate();
            LocalTime mealTime = meal.getDateTime().toLocalTime();

            caloriesPerDayMap.put(mealDate, caloriesPerDayMap.getOrDefault(mealDate, 0) + meal.getCalories());

            if (TimeUtil.isBetween(mealTime, startTime, endTime)) {
                filteredByTimeList.add(meal);
            }
        }

        System.out.println(caloriesPerDayMap);

        for (UserMeal meal : filteredByTimeList) {
            if (caloriesPerDayMap.get(meal.getDateTime().toLocalDate()) > caloriesPerDay) {
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), true));
            } else {
                result.add(new UserMealWithExceed(meal.getDateTime(), meal.getDescription(), meal.getCalories(), false));
            }
        }

        for (UserMealWithExceed meal : result) {
            System.out.println(meal);
        }


        return result;
    }
}
