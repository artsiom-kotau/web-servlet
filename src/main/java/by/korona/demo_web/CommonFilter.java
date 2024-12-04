package by.korona.demo_web;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter(urlPatterns = "/*")
public class CommonFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Инициализация фильтра");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        //put some info to context
        System.out.println("Фильтр сработал перед обработкой запроса");

        chain.doFilter(request, response);
        //clear some info
        System.out.println("Фильтр сработал после обработки запроса");
    }

    @Override
    public void destroy() {
        System.out.println("Фильтр уничтожен");
    }
}
