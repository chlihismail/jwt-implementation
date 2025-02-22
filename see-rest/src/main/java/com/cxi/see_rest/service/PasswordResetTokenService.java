package com.cxi.see_rest.service;

import java.nio.CharBuffer;
import java.security.SecureRandom;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Base64;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.cxi.see_rest.exception.AppException;
import com.cxi.see_rest.model.AppUser;
import com.cxi.see_rest.model.PasswordResetToken;
import com.cxi.see_rest.repository.AppUserRepository;
import com.cxi.see_rest.repository.PassworResetTokenRepository;

@Service
public class PasswordResetTokenService{
    private final AppUserRepository appUserRepository;
    private final PassworResetTokenRepository passworResetTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;

    public PasswordResetTokenService(AppUserRepository appUserRepository,
            PassworResetTokenRepository passworResetTokenRepository, PasswordEncoder passwordEncoder, JavaMailSender javaMailSender) {
        this.appUserRepository = appUserRepository;
        this.passworResetTokenRepository = passworResetTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
    }

    public void sendPasswordResetEmail(String email){
        AppUser appUser = appUserRepository.findByEmail(email).orElseThrow(()->new AppException("Unknown User!", HttpStatus.NOT_FOUND));
        Optional<PasswordResetToken> existingToken = passworResetTokenRepository.findByAppUser(appUser);
        if(existingToken.isPresent()){
            passworResetTokenRepository.delete(existingToken.get());
        }

        SecureRandom secureRandom = new SecureRandom(); 
        byte[] tokenBytes = new byte[64];
        secureRandom.nextBytes(tokenBytes);
        PasswordResetToken passwordResetToken = new PasswordResetToken();
        String randomToken = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        passwordResetToken.setToken(randomToken);
        passwordResetToken.setUser(appUser);
        passwordResetToken.setExpiryDate(Instant.now().plus(15, ChronoUnit.MINUTES));
        passworResetTokenRepository.save(passwordResetToken);

        String resetUrl = "https://cxiland.local:3000/reset-password?random=" + randomToken;
        sendEmail(appUser.getEmail(), "Demande de réinitialisation de mot de passe", "Cliquez ici pour réinitialiser votre mot de passe : " + resetUrl);
    } 

    private void sendEmail(String to, String subject, String content) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            javaMailSender.send(message);
        }catch(MailException ex){
            throw new AppException("Failed to send email!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public void resetPassword(String token, char[] newPassword){
        PasswordResetToken resetToken = passworResetTokenRepository.findByToken(token).orElseThrow(()-> new AppException("Invalid token!", HttpStatus.BAD_REQUEST));

        AppUser appUser = resetToken.getUser();
        appUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(newPassword)));
        Arrays.fill(newPassword, ' ');
        
        appUserRepository.save(appUser);
        passworResetTokenRepository.delete(resetToken);
    }

}
