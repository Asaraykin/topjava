package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test(expected = NotFoundException.class)
    public void get() {
       service.get(MEAL_ID_1, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void delete() {
       service.delete(MEAL_ID_1, USER_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getBetweenDateTimes() {
    }

    @Test
    public void getAll() {
        List<Meal> mealList = service.getAll(USER_ID);
        assertThat(mealList).isEqualTo(Arrays.asList( SUPPER_USER, BREAKFAST_USER));
    }

    @Test(expected = NotFoundException.class)
    public void update() {
        service.update(DINNER_ADMIN, USER_ID);
    }

    @Test
    public void create() {
        MealTestData.init();
        Meal newMeal = new Meal(new Meal(LocalDateTime.of(2018, 02, 03, 00, 00), "test", 999));
        Meal createdMeal = service.create(newMeal, USER_ID);
        List<Meal> mealList = Arrays.asList(createdMeal, SUPPER_USER, BREAKFAST_USER);
        assertThat(service.getAll(USER_ID)).isEqualTo(mealList);
    }
}