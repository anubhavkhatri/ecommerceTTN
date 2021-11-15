package com.ttn.ecommerce.service;

import com.ttn.ecommerce.domain.User;
import com.ttn.ecommerce.exception.UserNotFoundException;
import com.ttn.ecommerce.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountUnlockService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    public String unlockAccount(String username){

        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new UserNotFoundException("User not found !!", HttpStatus.NOT_FOUND);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Unlock Account");
        mailMessage.setText("To unlock your account, please click here : "
                +"http://localhost:8080/do-unlock?username="+ username);
        emailSenderService.sendEmail(mailMessage);

        return "Mail has been sent to you. Click on link to unlock your account";
    }

    @Transactional
    public String unlockAccountSuccess(String username){

        User user=userRepository.findByUsername(username);
        if(user==null)
            throw new UserNotFoundException("User not found !!", HttpStatus.NOT_FOUND);

        user.setAttempts(0);
        user.setIs_nonLocked(true);
        userRepository.save(user);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Unlock Successfull");
        mailMessage.setText("Account has been unlocked");
        emailSenderService.sendEmail(mailMessage);

        return "Your account is unlocked now";
    }


}
