package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class MealRestController extends AbstractMealController {

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        meal.setUserId(SecurityUtil.authUserId());
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        Meal meal = service.get(id);
        if (meal.getUserId() == SecurityUtil.authUserId()) {
            service.delete(id);
        }
        else {
            throw new NotFoundException("not authorized removal");
        }
    }

    public Meal update(Meal meal) {
        if (meal.getUserId() == SecurityUtil.authUserId()){
            return service.update(meal);
        }
        else {
            throw  new NotFoundException("not authorized update");
        }
    }

    public Meal get(int id){
        Meal meal = service.get(id);
        if(meal.getUserId() == SecurityUtil.authUserId()){
            return meal;
        }
        else {
            throw  new NotFoundException("not found");
        }
    }

    public Collection<Meal> getAll(){
        Collection<Meal> meals = service.getAll();
        return meals.stream().filter((meal)-> meal.getUserId() == SecurityUtil.authUserId()).collect(Collectors.toList());
    }

    public Collection<MealWithExceed> getWithExceeded(){
        return MealsUtil.getFilteredWithExceeded(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, LocalTime.MIN, LocalTime.MAX);
    }

    public Collection<MealWithExceed> getWithExceeded(LocalTime startTime, LocalTime endTime){
        return MealsUtil.getFilteredWithExceeded(getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }



}