package com.ttn.ecommerce.model;

import com.ttn.ecommerce.validations.PasswordValid;
import com.ttn.ecommerce.validations.PasswordValueMatch;

import javax.validation.constraints.NotNull;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "Passwords do not match!"
        )
})
public class ForgotPasswordModel {

    @NotNull
    @PasswordValid
    private String password;

    @NotNull
    @PasswordValid
    private String confirmPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
