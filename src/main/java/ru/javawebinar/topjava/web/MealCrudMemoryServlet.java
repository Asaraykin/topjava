package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoCrudMemoryImpl;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.slf4j.LoggerFactory.getLogger;

public class MealCrudMemoryServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private DateTimeFormatter dateTimeFormatter;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MealDaoCrudMemoryImpl mealDaoCrudMemory = new MealDaoCrudMemoryImpl();
        CopyOnWriteArrayList<MealWithExceed> mealWithExceedList;

        if(request.getParameter("delete") != null){

            mealDaoCrudMemory.deleteMeal(Integer.parseInt(request.getParameter("delete")));
            mealWithExceedList = new CopyOnWriteArrayList<>(MealsUtil.
                    getFilteredWithExceeded(mealDaoCrudMemory.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));

            request.setAttribute("mealWithExceedList", mealWithExceedList);
            response.sendRedirect("mealcrudmemory");
        }
        else {
            mealWithExceedList = new CopyOnWriteArrayList<>(MealsUtil.
                    getFilteredWithExceeded(mealDaoCrudMemory.getAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000));

            request.setAttribute("mealWithExceedList", mealWithExceedList);
            request.getRequestDispatcher("mealcrudmemory.jsp").forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CopyOnWriteArrayList<MealWithExceed> mealWithExceedList;

            String startTime = request.getParameter("startTime");
            String endTime = request.getParameter("endTime");

            dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            mealWithExceedList = new CopyOnWriteArrayList<>(MealsUtil.
                    getFilteredWithExceeded(new MealDaoCrudMemoryImpl().getAll(),
                            LocalTime.parse(startTime, dateTimeFormatter), LocalTime.parse(endTime, dateTimeFormatter), 2000));
            request.setAttribute("mealWithExceedList", mealWithExceedList);

        request.getRequestDispatcher("mealcrudmemory.jsp").forward(request, response);
    }
}
