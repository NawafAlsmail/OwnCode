package com.example.demoSecEx.Security;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtFilter jwtFilter;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(auth -> auth.disable())
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/h2-console/**", "/register", "/login",
                                        //these additional gateways are for the swagger
                                        "/swagger-ui/**","/v2/api-docs",
                                        "/v3/api-docs", "/v3/api-docs/**",
                                        "/swagger-resources","/swagger-resources/**",
                                        "/configuration/ui", "/configuration/security",
                                        "/swagger-ui.html","/webjars/**", "/swagger-ui")
                                .permitAll().anyRequest().authenticated());
        http.headers(auth->
                auth.frameOptions(ath-> ath.sameOrigin()));
        http.httpBasic(Customizer.withDefaults());
        http.sessionManagement(auth ->
                auth.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        //fetching the failed endpoint location at console
        http.exceptionHandling(exception->
                exception.authenticationEntryPoint((request, response,
                                                    authException) -> {
                    System.out.println("Auth error for: " + request.getServletPath());
                    authException.printStackTrace();
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
                }));
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);

        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
