package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.function.Predicate;

public interface MealService {

    Meal create(Meal meal);

    void delete(int id, int usedId) throws NotFoundException;

    Meal get(int id, int userId) throws NotFoundException;

    void update(Meal meal);

    Collection<Meal> getAll(int userId, Predicate<Meal> filter);
}