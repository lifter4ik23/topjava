package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements Dao<Meal, MealWithExceed> {
    private static ConcurrentMap<Integer, Meal> repository = new ConcurrentHashMap<>();

    static {
        repository.put(1, new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        repository.put(2, new Meal(2, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        repository.put(3, new Meal(3, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        repository.put(4, new Meal(4, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        repository.put(5, new Meal(5, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        repository.put(6, new Meal(6, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    private static AtomicInteger counter = new AtomicInteger(repository.size());


    @Override
    public Meal add(Meal meal) {
        meal.setId(counter.incrementAndGet());
        return repository.put(counter.get(), meal);
    }

    @Override
    public Meal update(Meal meal) {
        return repository.put(meal.getId(), meal);
    }

    @Override
    public Meal delete(int id) {
        return repository.remove(id);
    }

    @Override
    public Meal getById(int id) {
        return repository.get(id);
    }

    @Override
    public List<MealWithExceed> getList() {
        return MealsUtil.getFilteredWithExceeded(new ArrayList<>(repository.values()), LocalTime.MIN, LocalTime.MAX, 2000);
    }
}
