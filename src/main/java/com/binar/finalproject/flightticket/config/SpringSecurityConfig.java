package com.binar.finalproject.flightticket.config;


import com.binar.finalproject.flightticket.controller.OAuthController;
import com.binar.finalproject.flightticket.security.AuthEntryPointJwt;
import com.binar.finalproject.flightticket.security.AuthTokenFilter;
import com.binar.finalproject.flightticket.security.oauth2.CustomAuthorizationRedirectFilter;
import com.binar.finalproject.flightticket.security.oauth2.CustomAuthorizationRequestResolver;
import com.binar.finalproject.flightticket.security.oauth2.CustomAuthorizedClientService;
import com.binar.finalproject.flightticket.security.oauth2.CustomStatelessAuthorizationRequestRepository;
import com.binar.finalproject.flightticket.service.impl.security.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@AllArgsConstructor
public class SpringSecurityConfig {

    final UserDetailsServiceImpl userDetailsService;

    private final AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.authorizeRequests().antMatchers("/user/**").permitAll()
                .antMatchers("/role/**").permitAll()
                .antMatchers("/airplane/**").permitAll()
                .antMatchers("/airports/**").permitAll()
                .antMatchers("/cities/**").permitAll()
                .antMatchers("/countries/**").permitAll()
                .antMatchers("/gates/**").permitAll()
                .antMatchers("/id-card/**").permitAll()
                .antMatchers("/order/**").permitAll()
                .antMatchers("/passport/**").permitAll()
                .antMatchers("/payment/**").permitAll()
                .antMatchers("/route/**").permitAll()
                .antMatchers("/schedule/**").permitAll()
                .antMatchers("/seat/**").permitAll()
                .antMatchers("/terminals/**").permitAll()
                .antMatchers("/ticket/**").permitAll()
                .antMatchers("/traveler-list/**").permitAll()
                .antMatchers("/notification/**").permitAll()
                .anyRequest().permitAll();
        http.oauth2Login(config -> {
            config.authorizationEndpoint(subconfig -> {
                subconfig.baseUri("oauth2/authorization/google");
                subconfig.authorizationRequestResolver(this.customAuthorizationRequestResolver);
                subconfig.authorizationRequestRepository(this.customStatelessAuthorizationRequestRepository);
            });
            config.redirectionEndpoint(subconfig -> subconfig.baseUri(OAuthController.CALLBACK_BASE_URL));
            config.authorizedClientService(this.customAuthorizedClientService);
            config.successHandler(this.oAuthController::oauthSuccessHandler);
            config.failureHandler(this.oAuthController::oauthFailureHandler);
        });

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(this.customAuthorizationRedirectFilter, OAuth2AuthorizationRequestRedirectFilter.class);
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    private final CustomStatelessAuthorizationRequestRepository customStatelessAuthorizationRequestRepository;
    private final CustomAuthorizedClientService customAuthorizedClientService;
    private final CustomAuthorizationRequestResolver customAuthorizationRequestResolver;
    private final OAuthController oAuthController;
    private final CustomAuthorizationRedirectFilter customAuthorizationRedirectFilter;
}
