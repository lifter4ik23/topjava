package ru.javawebinar.topjava.service;


import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id) throws NotFoundException;

    User get(int id) throws NotFoundException;

    @Transactional
    default User getWithMeals(int id) {
        User user = get(id);

        //lazy exception
//        user.getMeals();

        //work!
        System.out.println(user.getMeals());

        //lazy exception
//        List<Meal> meals = user.getMeals();
        return user;
    }

    User getByEmail(String email) throws NotFoundException;

    void update(User user);

    List<User> getAll();
}