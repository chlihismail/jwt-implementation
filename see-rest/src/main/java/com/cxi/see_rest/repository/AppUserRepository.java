package com.cxi.see_rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cxi.see_rest.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String>{
    Optional<AppUser> findByEmail(String email);
}
