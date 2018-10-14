package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class UsersUtil {
    public static final List<User> USERS = Arrays.asList(
            new User(null, "Pasha", "pasha@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Sasha", "sasha@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Habib", "habib3@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "John", "john@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Alex", "alex@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Connor", "connor@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Habib", "habib@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Misha", "misha@mail.ru", "password", Role.ROLE_ADMIN),
            new User(null, "Habib", "habib2@mail.ru", "password", Role.ROLE_ADMIN)
    );

    public static ArrayList<User> getValues(Map<Integer, User> map) {
        return new ArrayList<>(map.values());
    }
}
