package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;
import java.util.Optional;

public class MealDaoInMemory implements Dao<Meal> {
    private static List<Meal> mealList = Repository.getMealList();
    private static int counter = mealList.size();
    private int currentIndex;

    @Override
    public void add(Meal meal) {
        counter++;
        meal.setId(counter);
        mealList.add(meal);
    }

    @Override
    public void update(Meal meal) {
        mealList.set(currentIndex, meal);
    }

    @Override
    public void delete(Meal meal) {
        mealList.remove(meal);
    }

    @Override
    public Meal getById(int id) {
        Optional<Meal> elem = mealList.stream()
                .filter(meal -> meal.getId() == id)
                .findFirst();
        Meal meal = elem.get();

        currentIndex = mealList.indexOf(meal);

        return meal;
    }

    @Override
    public List<Meal> getList() {
        return mealList;
    }

}
