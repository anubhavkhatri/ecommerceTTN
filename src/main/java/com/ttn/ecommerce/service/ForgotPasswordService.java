package com.ttn.ecommerce.service;

import com.ttn.ecommerce.domain.User;
import com.ttn.ecommerce.exception.TokenExpiredException;
import com.ttn.ecommerce.exception.UserNotFoundException;
import com.ttn.ecommerce.model.ForgotPasswordModel;
import com.ttn.ecommerce.repositories.ResetPasswordRepository;
import com.ttn.ecommerce.repositories.UserRepository;
import com.ttn.ecommerce.tokens.ResetPasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private EmailSenderService emailService;

    public String resetUserPassword(String email){
        User user=userRepository.findByEmail(email);

        if(user == null) {
            throw new UserNotFoundException("User not found", HttpStatus.NOT_FOUND);
        }
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();

            resetPasswordToken = resetPasswordRepository.findByUser(user);
        if(resetPasswordToken!=null) {
            resetPasswordToken.calculateToken();
            resetPasswordRepository.save(resetPasswordToken);
        }

        else {
            resetPasswordToken = new ResetPasswordToken(user);
            resetPasswordRepository.save(resetPasswordToken);
        }
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Reset your password");
            mailMessage.setText("To reset your password , please click here "
                    +"http://localhost:8080/reset-password?token="+resetPasswordToken.getToken());

            emailService.sendEmail(mailMessage);
            return "A link has been sent to your email for password reset.";
    }

    @Transactional
    public String updatePassword(String resetToken, ForgotPasswordModel forgotPasswordModel){
        ResetPasswordToken resetPasswordToken= resetPasswordRepository.findByToken(resetToken);
        if(resetPasswordToken==null) {
            throw new TokenExpiredException("Invalid Token");
        }

        Date presentDate = new Date();
        if (resetPasswordToken.getExpiryDate().getTime() - presentDate.getTime() <= 0){
            resetPasswordRepository.delToken(resetToken);
            throw new TokenExpiredException("Token has been expired, request for new Token via Forgot Password Link");
        }

        else {
            User user = userRepository.findByEmail(resetPasswordToken.getUser().getEmail());

            String pass = forgotPasswordModel.getPassword();
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            user.setPassword(passwordEncoder.encode(pass));

            userRepository.save(user);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Password Updated");
            mailMessage.setText("Your password has been changed successfully!!");

            emailService.sendEmail(mailMessage);

            resetPasswordRepository.delToken(resetToken);

            return "Password updated successfully!!!";
        }

    }

}
