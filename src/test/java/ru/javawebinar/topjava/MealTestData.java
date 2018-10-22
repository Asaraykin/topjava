package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

public class MealTestData {


    public static final int MEAL_ID_1 = 1;
    public static final int MEAL_ID_2 = 2;
    public static final int MEAL_ID_3 = 3;

    public static final Meal DINNER_ADMIN = new Meal(MEAL_ID_1, LocalDateTime.of(2018, 01, 03, 00, 00), "dinner", 1200);
    public static final Meal SUPPER_USER = new Meal(MEAL_ID_2, LocalDateTime.of(2018, 01, 02, 00, 00), "supper", 200);
    public static final Meal BREAKFAST_USER = new Meal(MEAL_ID_3, LocalDateTime.of(2018, 01, 01, 00, 00), "breakfast", 500);

    public static void init(){
       // DINNER_ADMIN.setId(ADMIN_ID);
       // SUPPER_USER.setId(USER_ID);
       // BREAKFAST_USER.setId(USER_ID);
    }

}
