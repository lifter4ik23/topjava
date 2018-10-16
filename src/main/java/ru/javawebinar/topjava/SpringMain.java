package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
//            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
//            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
//            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
//            adminUserController.getAll().forEach(System.out::println);

//            System.out.println("=========================================");
//            System.out.println(adminUserController.getByMail("alex@mail.ru"));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
//            mealRestController.getAll().forEach(System.out::println);
            System.out.println("===========================================");
//            mealRestController.get(7);
//            mealRestController.update(new Meal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500), 1);
//            mealRestController.update(new Meal(7, LocalDateTime.of(2018, Month.MAY, 30, 10, 0), "Завтрак2", 500), 7);
            mealRestController.getAll().forEach(System.out::println);
        }
    }
}
