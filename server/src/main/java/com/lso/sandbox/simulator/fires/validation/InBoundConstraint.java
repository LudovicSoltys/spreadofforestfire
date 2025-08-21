package com.lso.sandbox.simulator.fires.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = InBoundConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target( { ElementType.TYPE })
public @interface InBoundConstraint {
    String message() default "The input target is out of bounds.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
