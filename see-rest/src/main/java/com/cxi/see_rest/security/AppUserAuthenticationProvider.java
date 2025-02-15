package com.cxi.see_rest.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.cxi.see_rest.exception.AppException;

import jakarta.annotation.PostConstruct;

@Component
public class AppUserAuthenticationProvider{

    @Value("${security.jwt.token.secret-key:secret-key}")   
    private String secretKey;

    private final AppUserDetailsService appUserDetailsService;

    public AppUserAuthenticationProvider(AppUserDetailsService appUserDetailsService) {
        this.appUserDetailsService = appUserDetailsService;
    }
    
    @PostConstruct
    protected void init(){
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String email, boolean rememberMe){
        Instant now = Instant.now();
        Instant validity;

        if(rememberMe){
            validity = now.plus(30, ChronoUnit.DAYS);
        }else{
            validity = now.plus(10, ChronoUnit.SECONDS);
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        return JWT.create()
                .withSubject(email)
                .withIssuedAt(now)
                .withExpiresAt(validity)
                .sign(algorithm);
    }

    public Authentication validateToken(String token){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secretKey);

            JWTVerifier verifier = JWT.require(algorithm).build();

            DecodedJWT decoded = verifier.verify(token);

            UserDetails appUser = appUserDetailsService.loadUserByUsername(decoded.getSubject());

            return new UsernamePasswordAuthenticationToken(appUser, null, appUser.getAuthorities());

        }catch(JWTVerificationException ex){
            throw new AppException("Invalid token", HttpStatus.BAD_REQUEST);
        }catch(Exception ex){
            throw new AppException("Authentication failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
