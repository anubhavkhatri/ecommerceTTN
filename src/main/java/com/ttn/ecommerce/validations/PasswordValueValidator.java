package com.ttn.ecommerce.validations;

import com.ttn.ecommerce.model.CustomerRegisterModel;
import com.ttn.ecommerce.model.ForgotPasswordModel;
import com.ttn.ecommerce.model.SellerRegisterModel;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValueValidator
        implements ConstraintValidator<PasswordValueMatch, Object> {
    private String field;
    private String fieldMatch;
    private String message;

    public void initialize(PasswordValueMatch constraintAnnotation) {
        this.field = constraintAnnotation.field();
        this.fieldMatch = constraintAnnotation.fieldMatch();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){

        if(obj instanceof SellerRegisterModel){
            SellerRegisterModel seller = (SellerRegisterModel) obj;
            return seller.getPassword().equals(seller.getConfirmPassword());
        }
        else if(obj instanceof ForgotPasswordModel){
            ForgotPasswordModel passwords = (ForgotPasswordModel) obj;
            return passwords.getPassword().equals(passwords.getConfirmPassword());
        }
        else{
            CustomerRegisterModel customer = (CustomerRegisterModel) obj;
            return customer.getPassword().equals(customer.getConfirmPassword());
        }
    }
}
