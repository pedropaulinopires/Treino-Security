package com.pedro.spring.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Optional;

public class CookieService {

    public static void setCookie(HttpServletResponse response, String key, String value, int seconds) throws UnsupportedEncodingException {
        Cookie cookie = new Cookie(key, URLEncoder.encode(value,"UTF-8"));
        cookie.setMaxAge(seconds);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        String value = Optional.ofNullable(request.getCookies())
                .flatMap(cookies -> Arrays.stream(cookies)
                .filter(cookie -> key.equals(cookie.getName()))
                .findAny()).map(e -> e.getValue()).orElse(null);
        value = URLDecoder.decode(value,"UTF-8");
        return value;
    }
}
