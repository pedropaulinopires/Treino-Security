package com.pedro.spring.security.configurer;

import com.pedro.spring.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

//@Component
public class LoginInterceptor implements HandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        try {
//            if (CookieService.getCookie(request, "user") != null) {
//                return true;
//            }
//        } catch (Exception e) {
//            response.sendRedirect("/login");
//            return false;
//        }
//        return false;
//    }
}
