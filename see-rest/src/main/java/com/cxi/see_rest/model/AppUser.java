package com.cxi.see_rest.model;

import java.time.LocalDateTime;

import com.cxi.see_rest.enums.AppUserRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class AppUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstname;
    
    @Column(name = "last_name", nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private AppUserRole role;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean enabled = false;

    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private boolean locked = false;

    @Column(nullable = true)
    private LocalDateTime accountExpiryDate;

    @Column(nullable = true)
    private LocalDateTime  passwordExpiryDate;

    public AppUser() {
    }
    public AppUser(Long id, String firstname, String lastname, String email, AppUserRole role, String password, Boolean enabled,
            Boolean locked, LocalDateTime accountExpiryDate, LocalDateTime passwordExpiryDate) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.role = role;
        this.password = password;
        this.enabled = enabled;
        this.locked = locked;
        this.accountExpiryDate = accountExpiryDate;
        this.passwordExpiryDate = passwordExpiryDate;
    }

    @PrePersist
    public void setDefaultValues(){
        if(this.accountExpiryDate == null){
            this.accountExpiryDate = LocalDateTime.now().plusYears(1);
        }
        if(this.passwordExpiryDate == null){
            this.passwordExpiryDate = LocalDateTime.now().plusMonths(1);
        }
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstname() {
        return firstname;
    }
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public Boolean getLocked() {
        return locked;
    }
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
    public LocalDateTime getAccountExpiryDate() {
        return accountExpiryDate;
    }
    public void setAccountExpiryDate(LocalDateTime accountExpiryDate) {
        this.accountExpiryDate = accountExpiryDate;
    }
    public LocalDateTime getPasswordExpiryDate() {
        return passwordExpiryDate;
    }
    public void setPasswordExpiryDate(LocalDateTime passwordExpiryDate) {
        this.passwordExpiryDate = passwordExpiryDate;
    }
    @Override
    public String toString() {
        return "User [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email
                + ", password=" + password + ", enabled=" + enabled + ", locked=" + locked + ", accountExpiryDate="
                + accountExpiryDate + ", passwordExpiryDate=" + passwordExpiryDate + "]";
    }
    public AppUserRole getRole() {
        return role;
    }
    public void setRole(AppUserRole role) {
        this.role = role;
    }
}

