package com.ttn.ecommerce.validations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Phone {

    String message() default "invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
