package com.paj.psd2.aggregator.utils;

import com.paj.psd2.aggregator.config.JwtConfig;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Component
public class CookieUtils {

    private JwtConfig jwtConfig;

    public CookieUtils(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    public void addAuthenticationCookie(HttpServletResponse response, String value){
        this.addCookie(response, jwtConfig.getHeader(), value);
    }

    public void addCookie(HttpServletResponse response, String name, String value){
        response.addCookie(new Cookie(name, value));
    }

    public void deleteAuthenticationCookie(HttpServletResponse response){
        this.deleteCookie(response, jwtConfig.getHeader());
    }

    public void deleteCookie(HttpServletResponse response, String name){
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

/*    public String getCookie(HttpServletResponse response, String name, String value){
        return response.addCookie(new Cookie(name, value));
    }*/
}
