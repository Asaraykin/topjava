package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.dao.MealDaoCrudMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class MealCrudupdateServlet extends HttpServlet {
    MealDaoCrudMemoryImpl mealDaoCrudMemory = new MealDaoCrudMemoryImpl();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Meal meal = mealDaoCrudMemory.getById(id);
        request.setAttribute("meal", meal);
        request.setAttribute("id", id);
        request.getRequestDispatcher("update_meal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String description = new String(request.getParameter("description").getBytes("ISO-8859-1"),"UTF-8");
        System.out.println(description);
        System.out.println(("Encoding: " + response.getCharacterEncoding() ));

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(request.getParameter("dateTime"), dateTimeFormatter);

        int calories = Integer.parseInt(request.getParameter("calories"));

        int id = Integer.parseInt(request.getParameter("id"));

        mealDaoCrudMemory.updateMeal(id, dateTime, description, calories);

        request.setAttribute("mealWithExceedList", MealsUtil.getFilteredWithExceeded(mealDaoCrudMemory.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));
        response.sendRedirect("mealcrudmemory");
    }
}
