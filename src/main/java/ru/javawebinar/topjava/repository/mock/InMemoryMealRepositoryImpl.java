package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(meal, 1));
        //for testing RestApi
        MealsUtil.MEALS_2.forEach(meal -> save(meal, 2));
        MealsUtil.MEALS_3.forEach(meal -> save(meal, 3));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            if (userMeals == null) {
                userMeals = new ConcurrentHashMap<>();
                userMeals.put(meal.getId(), meal);
                repository.put(userId, userMeals);
            } else {
                userMeals.put(meal.getId(), meal);
            }
            return meal;
        }

        return userMeals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        Meal meal = userMeals.get(id);
        if (meal == null) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll(int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        Map<Integer, Meal> userMeals = repository.get(userId);

        return userMeals.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

