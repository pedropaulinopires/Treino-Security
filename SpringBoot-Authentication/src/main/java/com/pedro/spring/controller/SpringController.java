package com.pedro.spring.controller;

import com.pedro.spring.domain.User;
import com.pedro.spring.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SpringController {

    @GetMapping("/login")
    public ModelAndView index() {
        return new ModelAndView("/login/login");
    }

    @PostMapping("/login")
    public ModelAndView loginView(User user, HttpServletResponse response) {
        if (user.getUsername().equals("pedro") && user.getPassword().equals("123")) {
            CookieService.setCookie(response, "usuarioId", String.valueOf(123), 7);
            return new ModelAndView("redirect:/home");
        }
        ModelAndView mv = new ModelAndView("login/login");
        mv.addObject("loginError", Boolean.TRUE);
        mv.addObject("username", user.getUsername());
        return mv;
    }


    @GetMapping("/home")
    public ModelAndView home() {
        return new ModelAndView("/home/index");
    }

    @GetMapping("/special")
    public ModelAndView special() {
        return new ModelAndView("/home/special");
    }

    @GetMapping("/sessionExpire")
    public ModelAndView sessionExpire() {
        ModelAndView mv = new ModelAndView("/login/login");
//        mv.addObject("session", Boolean.TRUE);
        return mv;
    }

    @ModelAttribute("user")
    public User user() {
        return new User();
    }
}
