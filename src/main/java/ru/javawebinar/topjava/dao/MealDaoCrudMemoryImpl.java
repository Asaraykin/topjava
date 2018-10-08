package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoCrudMemoryImpl implements MealDAO {

    private static AtomicInteger id = new AtomicInteger(0);

    private static CopyOnWriteArrayList<Meal>  mealCopyOnWriteArrayList;

    @Override
    public void addMeal(LocalDateTime dateTime, String description, int calories) {
        mealCopyOnWriteArrayList.add(new Meal(id.incrementAndGet(), dateTime, description, calories));
    }



    @Override
    public void deleteMeal(int id) {
        mealCopyOnWriteArrayList.remove(getById(id));
    }

    @Override
    public void updateMeal(int id, LocalDateTime dateTime, String description, int calories) {
        mealCopyOnWriteArrayList.set(id, new Meal(dateTime, description, calories));
    }

    @Override
    public void createMeal(LocalDateTime dateTime, String description, int calories) {

    }

    @Override
    public List<Meal> getAll() {
        if (mealCopyOnWriteArrayList == null){
            createListIfEmpty();
        }
        return mealCopyOnWriteArrayList;
    }

    @Override
    public Meal getById(int id) {
         return mealCopyOnWriteArrayList.stream().filter(meal -> meal.getId() == id).findFirst().get();
    }

    private void createListIfEmpty(){
        mealCopyOnWriteArrayList = new CopyOnWriteArrayList<>(Arrays.asList(
                new Meal(id.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new Meal(id.incrementAndGet(),LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new Meal(id.incrementAndGet(),LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new Meal(id.incrementAndGet(),LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new Meal(id.incrementAndGet(),LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new Meal(id.incrementAndGet(),LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        ));
    }
}
