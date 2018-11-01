package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public Meal save(Meal meal, int userId) {
        if(meal.isNew()){
            User ref = entityManager.getReference(User.class, userId);
            meal.setUser(ref);
            entityManager.persist(meal);
            return meal;
        }
        else {
            Meal mealFromDB = get(meal.getId(), userId);
            if(mealFromDB == null){
             return null;
            }
            else {
                meal.setUser(mealFromDB.getUser());
                return entityManager.merge(meal);
            }
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        Meal meal = get(id, userId);
        if(meal == null){
            return false;
        }
        else {
            entityManager.remove(meal);
            return true;
        }
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = entityManager.find(Meal.class, id);
        if(meal == null || meal.getUser().getId() != userId){
            return null;
        }
        else {
            return meal;
        }
    }

    @Override
    public List<Meal> getAll(int userId) {
        return (List<Meal>) entityManager.createNamedQuery(Meal.GET_ALL).setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        List<Meal> mealList = entityManager.createNamedQuery(Meal.GET_BETWEEN)
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        return mealList;
    }
}