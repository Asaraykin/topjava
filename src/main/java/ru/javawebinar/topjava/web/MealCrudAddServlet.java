package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoCrudMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealCrudAddServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealDaoCrudMemoryImpl mealDaoCrudMemory = new MealDaoCrudMemoryImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String description = request.getParameter("description");

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), dateTimeFormatter);

        int calories = Integer.parseInt(request.getParameter("calories"));

        mealDaoCrudMemory.addMeal(dateTime, description, calories);

        request.setAttribute("mealWithExceedList",
                MealsUtil.getFilteredWithExceeded(mealDaoCrudMemory.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));

        request.getRequestDispatcher("mealcrudmemory.jsp").forward(request, response);

    }
}
