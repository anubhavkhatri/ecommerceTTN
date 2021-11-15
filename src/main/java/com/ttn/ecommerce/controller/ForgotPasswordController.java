package com.ttn.ecommerce.controller;

import com.ttn.ecommerce.model.ForgotPasswordModel;
import com.ttn.ecommerce.service.ForgotPasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class ForgotPasswordController {

    @Autowired
    ForgotPasswordService forgotPasswordService;


    /**
     * API to receive a token based url
     */
    @PostMapping("/forgot-password")
    public String resetPassword(@RequestBody String email){
        return forgotPasswordService.resetUserPassword(email);
    }

    /**
     * API to reset the password using the token
    */
    @PutMapping("/reset-password")
    public String setPassword(@Valid @RequestParam("token") String resetToken, @RequestBody ForgotPasswordModel forgotPasswordModel){
        return forgotPasswordService.updatePassword(resetToken, forgotPasswordModel);
    }

}
