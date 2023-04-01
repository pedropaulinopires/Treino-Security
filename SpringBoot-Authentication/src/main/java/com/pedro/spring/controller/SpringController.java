package com.pedro.spring.controller;

import com.pedro.spring.domain.Users;
import com.pedro.spring.repository.UserRepository;
import com.pedro.spring.service.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class SpringController {

    private final UserRepository userRepository;

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView mv = new ModelAndView("login/login");
        return mv;
    }


    @GetMapping("/")
    public ModelAndView home(HttpServletResponse response, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) throws UnsupportedEncodingException {
        ModelAndView mv = new ModelAndView("home/index");
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        List<? extends GrantedAuthority> authorities = authentication.getAuthorities().stream().toList();
        CookieService.setCookie(response, "user", authentication.getName(), 60 * 60);
        mv.addObject("user", authentication.getName());
        mv.addObject("role", authorities.toString());

        int currentPage = page.orElse(1) - 1;
        int sizePage = size.orElse(5);

        PageRequest pageable = PageRequest.of(currentPage, sizePage, Sort.by("username"));
        Page<Users> listPageable = userRepository.findAll(pageable);

        mv.addObject("listPeoples", listPageable);

        int totalPages = listPageable.getTotalPages();
        if (totalPages > 0) {
            List<Integer> numberPage = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            mv.addObject("numberPages", numberPage);
        }

        return mv;
    }

    @GetMapping("/special")
    public ModelAndView special() {
        ModelAndView mv = new ModelAndView("home/special");
        return mv;
    }

    @GetMapping("/error")
    public ModelAndView error() {
        ModelAndView mv = new ModelAndView("errors/error");
        return mv;
    }

    @GetMapping("/home")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView("home/index");
        return mv;
    }

//    @GetMapping("/login")
//    public ModelAndView index() {
//        return new ModelAndView("/login/login");
//    }
//
//    @PostMapping("/login")
//    public ModelAndView loginView(User user, HttpServletResponse response,HttpServletRequest request) throws UnsupportedEncodingException {
//        if (user.getUsername().equals("pedro") && user.getPassword().equals("123")) {
//            int seconds = 60*60;
//            HttpSession session = request.getSession();
//            session.setAttribute("username","pedro");
//            CookieService.setCookie(response, "userId", String.valueOf(123), seconds);
//            return new ModelAndView("redirect:/home");
//        }
//        ModelAndView mv = new ModelAndView("login/login");
//        mv.addObject("loginError", Boolean.TRUE);
//        mv.addObject("username", user.getUsername());
//        return mv;
//    }
//
//
//    @GetMapping("/home")
//    public ModelAndView home( HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        ModelAndView mv = new ModelAndView("/home/index");
//        mv.addObject("username",session.getAttribute("username"));
//        return mv;
//    }
//
//    @GetMapping("/special")
//    public ModelAndView special() {
//        return new ModelAndView("/home/special");
//    }
//
//    @GetMapping("/logout")
//    public ModelAndView sessionExpire(HttpServletResponse response, HttpSession session) throws UnsupportedEncodingException {
//        CookieService.setCookie(response,"userId","",0);
//        session.invalidate();
//        return new ModelAndView("redirect:/login");
//    }
//
//    @ModelAttribute("user")
//    public User getUser() {
//        return new User();
//    }
}
