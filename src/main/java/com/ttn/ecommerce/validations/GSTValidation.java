package com.ttn.ecommerce.validations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GSTValidation
        implements ConstraintValidator<GST, String> {

    private Pattern pattern;
    private Matcher matcher;

//    It should be 15 characters long.
//    The first 2 characters should be a number.
//    The next 10 characters should be the PAN number of the taxpayer.
//    The 13th character (entity code) should be a number from 1-9 or an alphabet.
//    The 14th character should be Z.
//    The 15th character should be an alphabet or a number.
    private static final String GST_PATTERN = "^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[1-9A-Z]{1}Z[0-9A-Z]{1}$";

    @Override
    public void initialize(GST constraintAnnotation) {
    }

    @Override
    public boolean isValid(String GST, ConstraintValidatorContext context){
        return (validateGST(GST));
    }

    private boolean validateGST(String GST) {
        pattern = Pattern.compile(GST_PATTERN);
        matcher = pattern.matcher(GST);
        return matcher.matches();
    }
}