package com.binar.finalproject.flightticket.controller;

import com.binar.finalproject.flightticket.security.helper.AuthenticationHelper;
import com.binar.finalproject.flightticket.security.helper.CookieHelper;
import com.binar.finalproject.flightticket.security.oauth2.GoogleAccountService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;

@Controller
@AllArgsConstructor
public class OAuthController {
    public static final String CALLBACK_BASE_URL = "/oauth2/login/code/google";
    public static final String OAUTH_COOKIE_NAME = "OAUTH";
    public static final String SESSION_COOKIE_NAME = "SESSION";

    private final GoogleAccountService googleAccountService;

    @SneakyThrows
    public void oauthRedirectResponse(HttpServletRequest request, HttpServletResponse response, String url){
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"redirectUrl\": \"%s\" }".formatted(url));
    }

    @SneakyThrows
    public void oauthSuccessHandler(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String accountId = AuthenticationHelper.retrieveAccountId(authentication);
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.addHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateCookie(SESSION_COOKIE_NAME, accountId, Duration.ofDays(1)));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"status\": \"success\"}");
    }

    @SneakyThrows
    public void oauthFailureHandler(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setHeader(HttpHeaders.SET_COOKIE, CookieHelper.generateExpiredCookie(OAUTH_COOKIE_NAME));
        response.getWriter().write("{\"status\": \"failure\"}");
    }
}
