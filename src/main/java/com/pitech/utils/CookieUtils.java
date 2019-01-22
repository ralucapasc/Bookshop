package com.pitech.utils;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

    public static final String COOKIE_CART = "shoppingCart";

    public static void setCookie(HttpServletResponse response, String cookieValue) throws Exception {

        Cookie cookie = new Cookie(COOKIE_CART, cookieValue);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static Cookie getCookie(HttpServletRequest request) {

        Cookie [] cookies = request.getCookies();

        for(Cookie cookie : cookies) {
            if(COOKIE_CART.equalsIgnoreCase(cookie.getName())) {

                return cookie;
            }
        }

        return new Cookie("shoppingCart", null);
    }
}
