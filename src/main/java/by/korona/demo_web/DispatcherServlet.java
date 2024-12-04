package by.korona.demo_web;

import by.korona.demo_web.reflection.ReflectionUtils;
import by.korona.demo_web.reflection.WebService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.reflections.Reflections;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "dispatcherServlet", urlPatterns = "/dispatcher-servlet/*")
public class DispatcherServlet extends HttpServlet {

    private Map<String, Object> responsibleClasses = new ConcurrentHashMap<>();

    @Override
    public void init() throws ServletException {
        Reflections reflections = new Reflections("by.korona");

        // Find all classes annotated with @MyAnnotation
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(WebService.class);
        annotatedClasses.forEach(clazz ->  {
            try {
                Object processor = clazz.getDeclaredConstructor().newInstance();
                WebService webService = clazz.getAnnotation(WebService.class);
                responsibleClasses.put(webService.path(), processor);
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e) {
                throw new RuntimeException(e);
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        String servletPath = request.getPathInfo();
        PrintWriter out = response.getWriter();
        if (responsibleClasses.containsKey(servletPath)) {
            out.println(String.format("{\"name\":\"%s\"}", servletPath));
        } else {
            response.sendError(404);
        }



    }

    public void destroy() {
    }
}