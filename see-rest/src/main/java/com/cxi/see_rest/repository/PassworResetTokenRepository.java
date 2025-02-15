package com.cxi.see_rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.see_rest.model.AppUser;
import com.cxi.see_rest.model.PasswordResetToken;

@Repository
public interface PassworResetTokenRepository extends JpaRepository<PasswordResetToken, Long>{
    Optional<PasswordResetToken> findByAppUser(AppUser appUser);
    Optional<PasswordResetToken> findByToken(String token);
} 
