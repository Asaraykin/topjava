package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;


public interface MealDAO {
    void addMeal(LocalDateTime dateTime, String description, int calories);
    void deleteMeal(int id);
    void updateMeal(int id, LocalDateTime dateTime, String description, int calories);
    void createMeal(LocalDateTime dateTime, String description, int calories);
    List<Meal> getAll();
    Meal getById(int id);
}
