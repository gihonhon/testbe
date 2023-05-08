package com.binar.finalproject.flightticket.security.helper;

import lombok.NonNull;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;


import javax.servlet.http.Cookie;
import java.time.Duration;
import java.util.Optional;

import static java.util.Objects.isNull;

public class CookieHelper {
    private static final Boolean HTTP_ONLY = Boolean.TRUE;
    private static final Boolean SECURE = Boolean.FALSE;

    private CookieHelper(){}

    public static Optional<String> retrieve(Cookie[] cookies, @NonNull String name){
        if (isNull(cookies)){
            return Optional.empty();
        }
        for (Cookie cookie : cookies){
            if (cookie.getName().equalsIgnoreCase(name)){
                return Optional.ofNullable(cookie.getValue());
            }
        }
        return Optional.empty();
    }
    public static String generateCookie(@NonNull String name, @NonNull String value, @NonNull Duration maxAge){
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(HTTP_ONLY);
        cookie.setSecure(SECURE);
        cookie.setMaxAge((int) maxAge.toSeconds());
        cookie.setPath("/");
        Rfc6265CookieProcessor processor = new Rfc6265CookieProcessor();
        return processor.generateHeader(cookie);
    }
    public static String generateExpiredCookie(@NonNull String name){
        return generateCookie(name, "-", Duration.ZERO);
    }
}
