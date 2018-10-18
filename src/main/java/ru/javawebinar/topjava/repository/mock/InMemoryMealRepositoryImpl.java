package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal->meal.setUserId(1));
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage

        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);

    }

    @Override
    public boolean delete(int id) {
        if(get(id).getUserId() == SecurityUtil.authUserId()) {
            return repository.remove(id) != null;
        }
        else {
            return false;
        }
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        return repository.values().stream()
                .sorted(((o1, o2) -> o2.getDate().compareTo(o1.getDate())))
                .collect(Collectors.toList());
    }
}

