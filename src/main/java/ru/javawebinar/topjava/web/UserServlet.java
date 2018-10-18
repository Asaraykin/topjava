package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Controller
public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Autowired
    private MealRestController mealRestController;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try (ConfigurableApplicationContext configurableApplicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml")){
            mealRestController = configurableApplicationContext.getBean(MealRestController.class);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to users");
        String authorizedUser = request.getParameter("userId");
        SecurityUtil.setAuthUserId(Integer.parseInt(authorizedUser));
        // System.out.println(authorizedUser);
        Collection<MealWithExceed> mealWithExceeds = mealRestController.getWithExceeded();
        request.setAttribute("meals", mealWithExceeds);
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}
