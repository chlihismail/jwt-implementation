package com.cxi.see_rest.security;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationFilter extends OncePerRequestFilter{
    
    private final AppUserAuthenticationProvider appUserAuthenticationProvider;

    public JwtAuthenticationFilter(AppUserAuthenticationProvider appUserAuthenticationProvider){
        this.appUserAuthenticationProvider=appUserAuthenticationProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
      String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(header != null){
            String[] authElements = header.split(" ");

            if(authElements.length == 2 && "Bearer".equals(authElements[0])){
                try {
                    SecurityContextHolder.getContext().setAuthentication(appUserAuthenticationProvider.validateToken(authElements[1]));
                } catch (RuntimeException e) {
                    SecurityContextHolder.clearContext();
                    throw e;
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
