package com.cxi.see_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.cxi.see_rest.security.AppUserAuthenticationEntryPoint;
import com.cxi.see_rest.security.AppUserAuthenticationProvider;
import com.cxi.see_rest.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final AppUserAuthenticationEntryPoint appUserAuthenticationEntryPoint;
    private final AppUserAuthenticationProvider appUserAuthenticationProvider;

    public SecurityConfig(AppUserAuthenticationEntryPoint appUserAuthenticationEntryPoint,
            AppUserAuthenticationProvider appUserAuthenticationProvider) {
        this.appUserAuthenticationEntryPoint = appUserAuthenticationEntryPoint;
        this.appUserAuthenticationProvider = appUserAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(appUserAuthenticationEntryPoint))
            .addFilterBefore(new JwtAuthenticationFilter(appUserAuthenticationProvider), BasicAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable())
            .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .securityMatcher("/api/**")
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET, "/api/home").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/users/login", "/api/users/register").permitAll()
                // .requestMatchers("/admin/**").hasRole("ADMIN")
                // .requestMatchers("/user/**").hasRole("USER")
                .anyRequest().authenticated())
        ;

        return http.build();
    }

}
